package com.alex.asset.logs.domain;

public enum Action {
    CREATE, UPDATE, DELETE;

    public static Action fromString(String value) {
        for (Action action : Action.values()) {
            if (action.name().equalsIgnoreCase(value)) {
                return action;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found in enum Action");
    }
}
