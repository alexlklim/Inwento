package com.alex.asset.domain.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum KST {
    GROUP_0("GRUNTY"),
    GROUP_1("BUDYNKI I LOKALE ORAZ SPÓŁDZIELCZE PRAWO DO LOKALU UŻYTKOWEGO I SPÓŁDZIELCZE WŁASNOŚCIOWE PRAWO DO LOKALU MIESZKALNEGO"),
    GROUP_2("OBIEKTY INŻYNIERII LĄDOWEJ I WODNEJ"),
    GROUP_3("KOTŁY I MASZYNY ENERGETYCZNE"),
    GROUP_4("MASZYNY, URZĄDZENIA I APARATY OGÓLNEGO ZASTOSOWANIA"),
    GROUP_5("MASZYNY, URZĄDZENIA I APARATY SPECJALISTYCZNE"),
    GROUP_6("URZĄDZENIA TECHNICZNE"),
    GROUP_7("ŚRODKI TRANSPORTU"),
    GROUP_8("NARZĘDZIA, PRZYRZĄDY, RUCHOMOŚCI I WYPOSAŻENIE, GDZIE INDZIEJ NIESKLASYFIKOWANE"),
    GROUP_9("INWENTARZ ŻYWY");

    private final String description;

    KST(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }






    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (KST assetStatus : KST.values()) {
            values.add(assetStatus.getDescription());
        }
        return values;
    }

    public static KST getByValue(String description) {
        for (KST key : KST.values()) {
            if (key.getDescription().equalsIgnoreCase(description)) {
                return key;
            }
        }
        return null;
    }





}
