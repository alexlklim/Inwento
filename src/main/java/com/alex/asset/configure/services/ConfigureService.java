package com.alex.asset.configure.services;


import com.alex.asset.configure.domain.*;
import com.alex.asset.configure.repo.*;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.dto.DtoName;
import lombok.RequiredArgsConstructor;
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

    private final BranchRepo branchRepo;
    private final MpkRepo mpkRepo;
    private final AssetStatusRepo assetStatusRepo;
    private final UnitRepo unitRepo;
    private final KstRepo kstRepo;


    public List<Branch> getBranches() {
        return branchRepo.getActive();
    }

    public List<Unit> getUnits() {
        return unitRepo.getActive();
    }

    public List<MPK> getMPKs() {
        return mpkRepo.getActive();
    }

    public List<AssetStatus> getAssetStatuses() {
        return assetStatusRepo.getActive();
    }

    public List<KST> getAllKSTs() {
        return kstRepo.findAll();
    }

    public void updateUnits(List<DtoActive> DTOs) {
        log.info(TAG + "Update units");
        for (DtoActive dto : DTOs) {
            unitRepo.update(dto.isActive(), dto.getId());
        }
    }

    public void updateAssetStatuses(List<DtoActive> DTOs) {
        log.info(TAG + "Update asset statuses");
        for (DtoActive dto : DTOs) {
            assetStatusRepo.update(dto.isActive(), dto.getId());
        }
    }

    public void updateKSTs(List<DtoActive> DTOs) {
        log.info(TAG + "Update KSTs");
        for (DtoActive dto : DTOs) {
            kstRepo.update(dto.isActive(), dto.getId());
        }
    }

    public List<KST> getKSTsByNum(String name) {
        log.info(TAG + "Get KST by num");
        return kstRepo.findByNum(name);
    }

    public Branch addBranch(DtoName dto) {
        log.info(TAG + "Add branch {}", dto.getName());
        return branchRepo.save(new Branch(dto.getName()));
    }

    public void updateBranch(DtoActive dto) {
        log.info(TAG + "Update branch {}", dto.getId());
        branchRepo.update(dto.isActive(), dto.getId());
    }


    public MPK addMPK(DtoName dto) {
        log.info(TAG + "Add MPK {}", dto.getName());
        return mpkRepo.save(new MPK(dto.getName()));
    }

    public void updateMPK(DtoActive dto) {
        log.info(TAG + "Update MPK {}", dto.getId());
        mpkRepo.update(dto.isActive(), dto.getId());
    }


    public AssetStatus getAssetStatusById(Long id) {
        return assetStatusRepo.get(id);
    }

    public Unit getUnitById(Long id) {
        return unitRepo.get(id);
    }

    public KST getKSTById(Long id) {
        return kstRepo.get(id);
    }

    public MPK getMPKById(Long id) {
        return mpkRepo.get(id);
    }


    public Branch getBranchById(Long id) {
        return branchRepo.get(id);
    }

}
