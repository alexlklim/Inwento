package com.alex.asset.configure.services;


import com.alex.asset.configure.domain.*;
import com.alex.asset.configure.repo.*;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import com.alex.asset.utils.exceptions.errors.user_error.ObjectAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConfigureService {
    private final String TAG = "FIELD_SERVICE - ";

    private final LogService logService;

    private final MpkRepo mpkRepo;
    private final AssetStatusRepo assetStatusRepo;
    private final UnitRepo unitRepo;
    private final KstRepo kstRepo;



    public List<Unit> getUnits() {
        return unitRepo.getActive();
    }
    public List<MPK> getMPKs() {
        return mpkRepo.getActive();
    }
    public List<AssetStatus> getAssetStatuses() {
        return assetStatusRepo.getActive();
    }

    public List<AssetStatus> getAllAssetStatuses() {
        return assetStatusRepo.findAll();
    }
    public List<Unit> getAllUnits() {
        return unitRepo.findAll();
    }

    public List<KST> getAllKSTs() {
        return kstRepo.findAll();
    }

    public List<KST> getKSTsByNum(String name) {
        log.info(TAG + "Get KST by num");
        return kstRepo.findByNum(name);
    }

    public void updateUnits(List<DtoActive> DTOs, Long userId) {
        log.info(TAG + "Update units");
        for (DtoActive dto : DTOs) {
            unitRepo.update(dto.isActive(), dto.getId());
        }
        logService.addLog(userId, Action.UPDATE, Section.UNIT, DTOs.toString());
    }

    public void updateAssetStatuses(List<DtoActive> DTOs, Long userId) {
        log.info(TAG + "Update asset statuses");
        for (DtoActive dto : DTOs) {
            assetStatusRepo.update(dto.isActive(), dto.getId());
        }
        logService.addLog(userId, Action.UPDATE, Section.ASSET_STATUS, DTOs.toString());

    }

    public void updateKSTs(List<DtoActive> DTOs, Long userId) {
        log.info(TAG + "Update KSTs");
        for (DtoActive dto : DTOs) {
            kstRepo.update(dto.isActive(), dto.getId());
        }
        logService.addLog(userId, Action.UPDATE, Section.KST, DTOs.toString());
    }





    @SneakyThrows
    public MPK addMPK(DtoName dto, Long userId) {
        log.info(TAG + "Add MPK {}", dto.getName());
        if (mpkRepo.existsByMpk(dto.getName()))
            throw new ObjectAlreadyExistException("MPK with name " + dto.getName() + " already exists");
        logService.addLog(userId, Action.CREATE, Section.MPK, dto.toString());
        return mpkRepo.save(new MPK(dto.getName()));
    }

    @SneakyThrows
    public void updateMPK(DtoActive dto, Long userId) {
        log.info(TAG + "Update MPK {}", dto.getId());
        MPK mpk = mpkRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("MPK with id " + dto.getId() + " was not found"));
        mpk.setActive(dto.isActive());
        mpkRepo.save(mpk);
        logService.addLog(userId, Action.UPDATE, Section.MPK, dto.toString());
    }


    @SneakyThrows
    public AssetStatus getAssetStatusById(Long id) {
        return assetStatusRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Asset status not found id " + id )
        );
    }

    @SneakyThrows
    public Unit getUnitById(Long id) {
        return unitRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unit not found id " + id )
        );
    }

    @SneakyThrows
    public KST getKSTById(Long id) {
        return kstRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("KST not found id " + id )
        );
    }

    @SneakyThrows
    public MPK getMPKById(Long id) {
        return mpkRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("MPK not found id " + id )
        );
    }





    public KST getKSTByNum(String num) {
        return kstRepo.findKSTByNum(num).orElse(null);
    }

    public AssetStatus getAssetStatusByAssetStatus(String assetStatus) {
        return assetStatusRepo.findAssetStatusByAssetStatus(assetStatus).orElse(null);
    }

    public Unit getUnitByUnit(String unit) {
        return unitRepo.findUnitByUnit(unit).orElse(null);
    }



    public MPK getMPKByMPK(String mpk, Long userId) {
        MPK mpkFromDB = mpkRepo.findMPKByMpk(mpk).orElse(null);
        if (mpkFromDB == null){
            return addMPK(new DtoName(mpk), userId);
        }
        return mpkFromDB;
    }


}
