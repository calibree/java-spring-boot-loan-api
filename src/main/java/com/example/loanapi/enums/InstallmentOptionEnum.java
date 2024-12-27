package com.example.loanapi.enums;

import java.util.Objects;

public enum InstallmentOptionEnum{
    SIX(6),
    NINE(9),
    TWELVE(12),
    TWENTY_FOUR(24);


    private final Integer value;

    InstallmentOptionEnum(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static boolean isValidInstallment(Integer value) {
        for (InstallmentOptionEnum option : InstallmentOptionEnum.values()) {
            if (Objects.equals(option.value, value)) {
                return true;
            }
        }
        return false;
    }
}
