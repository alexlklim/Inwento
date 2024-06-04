package com.alex.asset.configure.services;

import com.alex.asset.configure.domain.ServiceProvider;
import com.alex.asset.configure.mappers.ConfigureMapper;
import com.alex.asset.configure.domain.KST;
import com.alex.asset.configure.repo.AssetStatusRepo;
import com.alex.asset.configure.repo.KstRepo;
import com.alex.asset.configure.repo.UnitRepo;
import com.alex.asset.configure.services.services.BranchService;
import com.alex.asset.configure.services.services.MpkService;
import com.alex.asset.configure.services.services.TypeService;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.UserMapper;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dictionaries.UtilConfigurator;
import com.alex.asset.utils.dictionaries.UtilProduct;
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

    private final String TAG = "CONFIGURATOR_SERVICE - ";

    private final LogService logService;
    private final TypeService typeService;
    private final MpkService mpkService;
    private final BranchService branchService;
    private final ServiceService serviceService;


    private final AssetStatusRepo assetStatusRepo;
    private final UnitRepo unitRepo;
    private final KstRepo kstRepo;
    private final UserRepo userRepo;


    public Map<String, Object> getAllConfigurations(List<String> fields) {
        log.info(TAG + "Get all configurations");

        Map<String, Object> dtoMap = new HashMap<>();
        System.out.println(serviceService.getAllServiceProviders());

        Map<String, Supplier<Object>> dataFetchers = Map.of(
                UtilConfigurator.ASSET_STATUS, assetStatusRepo::findAll,
                UtilConfigurator.UNIT, unitRepo::findAll,
                UtilConfigurator.KST, kstRepo::findAll,
                UtilConfigurator.MPK, mpkService::getAll,
                UtilConfigurator.BRANCH, () -> ConfigureMapper.convertBranchToDTOs(branchService.getAllBranches()),
                UtilConfigurator.LOCATION, () -> ConfigureMapper.convertLocationToDTOs(branchService.getAllLocations()),
                UtilConfigurator.TYPE, () -> ConfigureMapper.convertTypesToDTOs(typeService.getAllTypes()),
                UtilConfigurator.SUBTYPE, () -> ConfigureMapper.convertSubtypesToDTOs(typeService.getAllSubtypes()),
                UtilConfigurator.SERVICE_PROVIDER, serviceService::getAllServiceProviders,
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
        handlers.put(UtilConfigurator.SERVICE_PROVIDER, this::handleServiceProviders);
        handlers.put(UtilConfigurator.CONTACT_PERSON, this::handleContactPerson);

        updates.forEach((key, value) -> {
            BiConsumer<List<Map<String, Object>>, User> handler = handlers.get(key);
            if (handler != null) {
                handler.accept((List<Map<String, Object>>) value, user);
            }
        });

    }


    private void handleAssetStatus(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleAssetStatus");
        for (Map<String, Object> map : maps) {
            assetStatusRepo.update(
                    (boolean) map.get(UtilConfigurator.ACTIVE),
                    ((Integer) map.get(UtilConfigurator.ID)).longValue());
        }
        logService.addLog(user, Action.UPDATE, Section.ASSET_STATUS, maps.toString());
    }

    private void handleUnits(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleUnits");
        for (Map<String, Object> map : maps) {
            unitRepo.update(
                    (boolean) map.get(UtilConfigurator.ACTIVE),
                    ((Integer) map.get(UtilConfigurator.ID)).longValue());
        }
        logService.addLog(user, Action.UPDATE, Section.UNIT, maps.toString());
    }


    private void handleKSTs(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleKSTs");
        maps.forEach(map -> kstRepo.update(
                (boolean) map.get(UtilConfigurator.ACTIVE),
                ((Integer) map.get(UtilConfigurator.ID)).longValue())
        );
        logService.addLog(user, Action.UPDATE, Section.KST, maps.toString());
    }


    private void handleMPKs(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleMPKs");
        for (Map<String, Object> map : maps) {
            if (map.containsKey(UtilConfigurator.ID)) {
                mpkService.updateMPK(
                        new DtoData(
                                ((Integer) map.get(UtilConfigurator.ID)).longValue(),
                                (boolean) map.get(UtilConfigurator.ACTIVE)),
                        user);
            } else if (map.containsKey(UtilConfigurator.NAME)) {
                mpkService.createMPK(
                        new DtoData((String) map.get(UtilConfigurator.NAME)),
                        user);
            }
        }
    }

    private void handleContactPerson(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleContactPerson");
        for (Map<String, Object> map : maps) {
            if (map.containsKey(UtilConfigurator.ID)) {
                serviceService.updateContactPerson(map,((Number) map.get(UtilProduct.ID)).longValue(), user);
            } else {
                serviceService.addContactPerson(map, user);
            }
        }
    }

    private void handleServiceProviders(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleServiceProviders");
        for (Map<String, Object> map : maps) {
            if (map.containsKey(UtilConfigurator.ID)) {
                serviceService.updateServiceProvider(map,((Number) map.get(UtilProduct.ID)).longValue(), user);
            } else {
                serviceService.addServiceProvider(map, user);
            }
        }
    }


    private void handleBranches(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleBranches");
        for (Map<String, Object> branchData : maps) {
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


    private void handleLocations(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleLocations");
        for (Map<String, Object> locationData : maps) {
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

    private void handleTypes(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleTypes");
        for (Map<String, Object> typeData : maps) {
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

    private void handleSubtypes(List<Map<String, Object>> maps, User user) {
        log.info(TAG + "Update configurations handleSubtypes");
        for (Map<String, Object> subtypeData : maps) {
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
