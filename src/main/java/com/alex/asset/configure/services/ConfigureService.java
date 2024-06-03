package com.alex.asset.configure.services;


import com.alex.asset.configure.domain.AssetStatus;
import com.alex.asset.configure.domain.KST;
import com.alex.asset.configure.domain.MPK;
import com.alex.asset.configure.domain.Unit;
import com.alex.asset.configure.repo.AssetStatusRepo;
import com.alex.asset.configure.repo.KstRepo;
import com.alex.asset.configure.repo.MpkRepo;
import com.alex.asset.configure.repo.UnitRepo;
import com.alex.asset.exceptions.shared.ObjectAlreadyExistException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.utils.dto.DtoName;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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





    @SneakyThrows
    public MPK addMPK(DtoName dto, Long userId) {
        log.info(TAG + "Add MPK {}", dto.getName());
        if (mpkRepo.existsByMpk(dto.getName()))
            throw new ObjectAlreadyExistException("MPK with name " + dto.getName() + " already exists");
        logService.addLog(userId, Action.CREATE, Section.MPK, dto.toString());
        return mpkRepo.save(new MPK(dto.getName()));
    }



    @SneakyThrows
    public AssetStatus getAssetStatusById(Long id) {
        return assetStatusRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Asset status not found id " + id)
        );
    }

    @SneakyThrows
    public Unit getUnitById(Long id) {
        return unitRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Unit not found id " + id)
        );
    }

    @SneakyThrows
    public KST getKSTById(Long id) {
        return kstRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("KST not found id " + id)
        );
    }

    @SneakyThrows
    public MPK getMPKById(Long id) {
        return mpkRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("MPK not found id " + id)
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
        if (mpkFromDB == null) {
            return addMPK(new DtoName(mpk), userId);
        }
        return mpkFromDB;
    }



}
