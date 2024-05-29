package com.alex.asset.configure.services;

import com.alex.asset.configure.mappers.ConfigureMapper;
import com.alex.asset.configure.domain.KST;
import com.alex.asset.configure.repo.AssetStatusRepo;
import com.alex.asset.configure.repo.KstRepo;
import com.alex.asset.configure.repo.UnitRepo;
import com.alex.asset.configure.services.services.BranchService;
import com.alex.asset.configure.services.services.MpkService;
import com.alex.asset.configure.services.services.TypeService;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.security.UserMapper;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilConfigurator;
import com.alex.asset.utils.dto.DtoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConfiguratorService {

    private final String TAG = "FIELD_SERVICE - ";

    private final LogService logService;
    private final TypeService typeService;
    private final MpkService mpkService;
    private final BranchService branchService;
    private final ConfigureMapper configureMapper;


    private final AssetStatusRepo assetStatusRepo;
    private final UnitRepo unitRepo;
    private final KstRepo kstRepo;
    private final UserRepo userRepo;


    public Map<String, Object> getAllConfigurations(List<String> fields) {
        log.info(TAG + "Get all configurations");

        Map<String, Object> dtoMap = new HashMap<>();
        Map<String, Supplier<Object>> dataFetchers = Map.of(
                UtilConfigurator.ASSET_STATUS, assetStatusRepo::findAll,
                UtilConfigurator.UNIT, unitRepo::findAll,
                UtilConfigurator.KST, kstRepo::findAll,
                UtilConfigurator.MPK, mpkService::getAll,
                UtilConfigurator.BRANCH, branchService::getAllBranches,
                UtilConfigurator.LOCATION, () -> configureMapper.convertLocationToDTOs(branchService.getAllLocations()),
                UtilConfigurator.TYPE, () -> configureMapper.convertTypesToDTOs(typeService.getAllTypes()),
                UtilConfigurator.SUBTYPE, () -> configureMapper.convertSubtypesToDTOs(typeService.getAllSubtypes()),
                UtilConfigurator.EMPLOYEE, () -> userRepo.getActiveUsers().stream().map(UserMapper::toEmployee).collect(Collectors.toList())
        );
        for (String field : fields) {
            Supplier<Object> dataFetcher = dataFetchers.get(field);
            if (dataFetcher != null) {
                dtoMap.put(field, dataFetcher.get());
            }
        }
        return dtoMap;
    }

    @SuppressWarnings("unchecked")
    public void updateConfigurations(Map<String, Object> updates, Long userId) {
        log.info(TAG + "Update configurations");
        User user = userRepo.getUser(userId);

        Map<String, BiConsumer<List<Map<String, Object>>, User>> handlers = new HashMap<>();
        handlers.put(UtilConfigurator.ASSET_STATUS, this::handleAssetStatus);
        handlers.put(UtilConfigurator.UNIT, this::handleUnits);
        handlers.put(UtilConfigurator.KST, this::handleKSTs);
        handlers.put(UtilConfigurator.MPK, this::handleMPKs);
        handlers.put(UtilConfigurator.BRANCH, this::handleBranches);
        handlers.put(UtilConfigurator.LOCATION, this::handleLocations);
        handlers.put(UtilConfigurator.TYPE, this::handleTypes);
        handlers.put(UtilConfigurator.SUBTYPE, this::handleSubtypes);

        updates.forEach((key, value) -> {
            BiConsumer<List<Map<String, Object>>, User> handler = handlers.get(key);
            if (handler != null) {
                handler.accept((List<Map<String, Object>>) value, user);
            }
        });

    }

    private void handleAssetStatus(List<Map<String, Object>> DTOs, User user) {
        log.info(TAG + "Update configurations handleAssetStatus");
        for (Map<String, Object> assetStatusData : DTOs) {
            assetStatusRepo.update(
                    (boolean) assetStatusData.get(UtilConfigurator.ACTIVE),
                    ((Integer) assetStatusData.get(UtilConfigurator.ID)).longValue());
        }
        logService.addLog(user, Action.UPDATE, Section.ASSET_STATUS, DTOs.toString());
    }

    private void handleUnits(List<Map<String, Object>> DTOs, User user) {
        log.info(TAG + "Update configurations handleUnits");
        for (Map<String, Object> unitData : DTOs) {
            unitRepo.update(
                    (boolean) unitData.get(UtilConfigurator.ACTIVE),
                    ((Integer) unitData.get(UtilConfigurator.ID)).longValue());
        }
        logService.addLog(user, Action.UPDATE, Section.UNIT, DTOs.toString());
    }


    private void handleKSTs(List<Map<String, Object>> DTOs, User user) {
        log.info(TAG + "Update configurations handleKSTs");
        DTOs.forEach(kstData -> kstRepo.update(
                (boolean) kstData.get(UtilConfigurator.ACTIVE),
                ((Integer) kstData.get(UtilConfigurator.ID)).longValue())
        );
        logService.addLog(user, Action.UPDATE, Section.KST, DTOs.toString());
    }


    private void handleMPKs(List<Map<String, Object>> mpkList, User user) {
        log.info(TAG + "Update configurations handleMPKs");
        for (Map<String, Object> mpkData : mpkList) {
            if (mpkData.containsKey(UtilConfigurator.ID)) {
                mpkService.updateMPK(
                        new DtoData(
                                ((Integer) mpkData.get(UtilConfigurator.ID)).longValue(),
                                (boolean) mpkData.get(UtilConfigurator.ACTIVE)),
                        user);
            } else if (mpkData.containsKey(UtilConfigurator.NAME)) {
                mpkService.createMPK(
                        new DtoData((String) mpkData.get(UtilConfigurator.NAME)),
                        user);
            }
        }
    }


    private void handleBranches(List<Map<String, Object>> branchList, User user) {
        log.info(TAG + "Update configurations handleBranches");
        for (Map<String, Object> branchData : branchList) {
            if (branchData.containsKey(UtilConfigurator.ID)) {
                branchService.updateBranch(
                        new DtoData(
                                ((Integer) branchData.get(UtilConfigurator.ID)).longValue(),
                                (boolean) branchData.get(UtilConfigurator.ACTIVE)),
                        user.getId());
            } else if (branchData.containsKey(UtilConfigurator.NAME)) {
                branchService.addBranch(
                        new DtoData((String) branchData.get(UtilConfigurator.NAME)),
                        user.getId());
            }
        }
    }


    private void handleLocations(List<Map<String, Object>> locationList, User user) {
        log.info(TAG + "Update configurations handleLocations");
        for (Map<String, Object> locationData : locationList) {
            if (locationData.containsKey(UtilConfigurator.ID)) {
                branchService.updateLocation(
                        new DtoData(
                                ((Integer) locationData.get(UtilConfigurator.ID)).longValue(),
                                (boolean) locationData.get(UtilConfigurator.ACTIVE)),
                        user.getId());
            } else if (locationData.containsKey(UtilConfigurator.NAME)) {
                branchService.addLocation(
                        new DtoData(
                                (String) locationData.get(UtilConfigurator.NAME),
                                ((Integer) locationData.get(UtilConfigurator.BRANCH_ID)).longValue()),
                        user.getId());
            }
        }
    }

    private void handleTypes(List<Map<String, Object>> typesList, User user) {
        log.info(TAG + "Update configurations handleTypes");
        for (Map<String, Object> typeData : typesList) {
            if (typeData.containsKey(UtilConfigurator.ID)) {
                typeService.updateType(
                        new DtoData(
                                ((Integer) typeData.get(UtilConfigurator.ID)).longValue(),
                                (boolean) typeData.get(UtilConfigurator.ACTIVE)),
                        user);
            } else if (typeData.containsKey(UtilConfigurator.NAME)) {
                typeService.addType(
                        new DtoData((String) typeData.get(UtilConfigurator.NAME)),
                        user);
            }
        }
    }

    private void handleSubtypes(List<Map<String, Object>> subtypeList, User user) {
        log.info(TAG + "Update configurations handleSubtypes");
        for (Map<String, Object> subtypeData : subtypeList) {
            if (subtypeData.containsKey(UtilConfigurator.ID)) {
                typeService.updateSubtype(
                        new DtoData(
                                ((Integer) subtypeData.get(UtilConfigurator.ID)).longValue(),
                                (boolean) subtypeData.get(UtilConfigurator.ACTIVE)),
                        user);
            } else if (subtypeData.containsKey(UtilConfigurator.NAME)) {
                typeService.addSubtype(
                        new DtoData(
                                (String) subtypeData.get(UtilConfigurator.NAME),
                                ((Integer) subtypeData.get(UtilConfigurator.TYPE_ID)).longValue()),
                        user);
            }
        }
    }


    public List<KST> getKSTsByNum(String name) {
        log.info(TAG + "Get KST by num");
        return kstRepo.findByNum(name);
    }
}
