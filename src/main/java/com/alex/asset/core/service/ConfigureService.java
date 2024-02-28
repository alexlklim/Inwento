package com.alex.asset.core.service;


import com.alex.asset.core.domain.fields.Branch;
import com.alex.asset.core.domain.fields.MPK;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.core.dto.simple.ActiveDto;
import com.alex.asset.core.dto.simple.DtoName;
import com.alex.asset.core.repo.product.BranchRepo;
import com.alex.asset.core.repo.product.MpkRepo;
import com.alex.asset.core.repo.product.AssetStatusRepo;
import com.alex.asset.core.repo.product.KstRepo;
import com.alex.asset.core.repo.product.UnitRepo;
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



    public List<Branch> getBranches(){
        return branchRepo.getActive();
    }
    public List<Unit> getUnits(){
        return unitRepo.getActive();
    }
    public List<MPK> getMPKs(){
        return mpkRepo.getActive();
    }
    public List<AssetStatus> getAssetStatuses(){
        return assetStatusRepo.getActive();
    }


    public void updateUnits(List<ActiveDto> DTOs) {
        log.info(TAG + "Update units");
        for (ActiveDto dto: DTOs){
            unitRepo.update(dto.isActive(), dto.getId());
        }
    }

    public void updateAssetStatuses(List<ActiveDto> DTOs) {
        log.info(TAG + "Update asset statuses");
        for (ActiveDto dto: DTOs){
            assetStatusRepo.update(dto.isActive(), dto.getId());
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
    public void updateBranch(ActiveDto dto) {
        log.info(TAG + "Update branch {}", dto.getId());
        branchRepo.update(dto.isActive(), dto.getId());
    }
    public void deleteBranch(Long id) {
        log.info(TAG + "Delete branch {}", id);
        branchRepo.deleteById(id);
    }


    public MPK addMPK(DtoName dto) {
        log.info(TAG + "Add MPK {}", dto.getName());
        return mpkRepo.save(new MPK(dto.getName()));
    }
    public void updateMPK(ActiveDto dto) {
        log.info(TAG + "Update MPK {}", dto.getId());
        mpkRepo.update(dto.isActive(), dto.getId());
    }
    public void deleteMPK(Long id) {
        log.info(TAG + "Delete MPK {}", id);
        mpkRepo.deleteById(id);
    }



    public AssetStatus getAssetStatusById(Long id){
        return assetStatusRepo.get(id);
    }

    public Unit getUnitById(Long id){
        return unitRepo.get(id);
    }

    public KST getKSTById(Long id){
        return kstRepo.get(id);
    }

    public MPK getMPKById(Long id){
        return mpkRepo.get(id);
    }


    public Branch getBranchById(Long id){
        return branchRepo.get(id);
    }




}
