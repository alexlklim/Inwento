package com.alex.asset.core.service;


import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConverterService {



    public static List<String> convertAssetStatusesToString(List<AssetStatus> statusList){
        List<String> list = new ArrayList<>();
        for (AssetStatus status: statusList){
            list.add(status.getAssetStatus());
        }
        return list;
    }


    public static List<String> convertUnitsToString(List<Unit> unitList){
        List<String> list = new ArrayList<>();
        for (Unit unit: unitList){
            list.add(unit.getUnit());
        }
        return list;
    }

    public static List<String> convertKSTToString(List<KST> kstList){
        List<String> list = new ArrayList<>();
        for (KST kst: kstList){
            list.add(kst.getKst());
        }
        return list;
    }
}
