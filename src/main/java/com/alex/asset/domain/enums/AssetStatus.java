package com.alex.asset.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum AssetStatus {

    NOWA("Nowa"),
    W_TRAKCIE_PRZYPISANIA("W trakcie przypisania"),
    PRZYPISANA("Przypisana"),
    W_TRAKCIE_PRZEGLADU("W trakcie przeglądu"),
    W_NAPRAWIE("W naprawie"),
    W_TRANSPORCIE("W transporcie"),
    WYCOFANY("Wycofany"),
    UTRACONY_LUB_SKRADZIONY("Utracony lub skradziony"),
    SPRZEDANY_LUB_PRZEKAZANY("Sprzedany lub przekazany"),
    ZAREZERWOWANY("Zarezerwowany");

    private final String status;

    AssetStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (AssetStatus assetStatus : AssetStatus.values()) {
            values.add(assetStatus.getStatus());
        }
        return values;
    }

    public static AssetStatus getByValue(String status) {
        for (AssetStatus key : AssetStatus.values()) {
            if (key.getStatus().equals(status)) {
                return key;
            }
        }
        return null;
    }


}
