package com.aleinik.twistencryptor.enums;

import com.aleinik.twistencryptor.service.*;

public enum Mode implements CodedEnum {
    ENCODE(1, new Encode()),
    DECODE(2, new Decode()),
    BRUTE_FORCE(3, new BruteForce());

    private final Function function;

    private final int code;

    Mode(int code, Function function) {
        this.code = code;
        this.function = function;
    }


    public int getCode() {

        return code;
    }

    public String getDisplayName() {

        return this.name();
    }

    public Function getFunction() {

        return function;
    }
}
