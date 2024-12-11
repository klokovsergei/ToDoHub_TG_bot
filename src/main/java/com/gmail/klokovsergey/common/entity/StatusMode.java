package com.gmail.klokovsergey.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum StatusMode {
    START("START"), ADMIN("ADMIN");

    private final String mode;
    StatusMode(String mode) { this.mode = mode; }

    @JsonValue
    public String getMode() { return mode; }

    @JsonCreator
    public static StatusMode fromMode(String mode) {
        return Arrays.stream(StatusMode.values())
                .filter(type -> type.mode.equals(mode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Нет такого режима пользователя: " + mode));

    }
}
