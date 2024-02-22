package com.alex.asset.core.service;


import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ConverterService {
    private static final String TAG = "CONVERTER_SERVICE - ";



    public static List<String> convertAssetStatusesToString(List<AssetStatus> statusList){
        log.info(TAG + "Convert Asset Status to String");
        List<String> list = new ArrayList<>();
        for (AssetStatus status: statusList){
            list.add(status.getAssetStatus());
        }
        return list;
    }


    public static List<String> convertUnitsToString(List<Unit> unitList){
        log.info(TAG + "Convert Unit to String");

        List<String> list = new ArrayList<>();
        for (Unit unit: unitList){
            list.add(unit.getUnit());
        }
        return list;
    }

    public static List<String> convertKSTToString(List<KST> kstList){
        log.info(TAG + "Convert KST to String");
        List<String> list = new ArrayList<>();
        for (KST kst: kstList){
            list.add(kst.getKst());
        }
        return list;
    }
}
