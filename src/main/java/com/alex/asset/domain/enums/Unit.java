package com.alex.asset.domain.enums;

import java.util.ArrayList;
import java.util.List;

public enum Unit {
    PIECE, BOX, PACK, SET, PAIR, DOZEN, BOTTLE, ROLL;

    public static List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (Unit unit : Unit.values()) {
            values.add(unit.name());
        }
        return values;
    }

    public static Unit getByValue(String str) {
        for (Unit unit : Unit.values()) {
            if (unit.name().equalsIgnoreCase(str)) {
                return unit;
            }
        }
        return null;
    }


}
