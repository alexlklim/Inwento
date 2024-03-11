package com.alex.asset.logs.domain;

public enum Section {

    USERS, COMPANY, CONFIGURE, INVENT, EVENT;


    public static Section fromString(String value) {
        for (Section section : Section.values()) {
            if (section.name().equalsIgnoreCase(value)) {
                return section;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found in enum Section");
    }
}
