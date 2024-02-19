package com.alex.asset.core.service.impl;


import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.core.repo.product.constatns.AssetStatusRepo;
import com.alex.asset.core.repo.product.constatns.KstRepo;
import com.alex.asset.core.repo.product.constatns.UnitRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaticService {

    private final AssetStatusRepo assetStatusRepo;
    private final KstRepo kstRepo;
    private final UnitRepo unitRepo;


    public AssetStatus getAssetStatus(String name){
        return assetStatusRepo.findByAssetStatus(name).orElse(null);
    }
    public KST getKST(String name){
        return kstRepo.findByKst(name).orElse(null);
    }
    public Unit getUnit(String name){
        return unitRepo.findByUnit(name).orElse(null);
    }
}
