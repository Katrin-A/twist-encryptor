package com.aleinik.twistencryptor.repository;

import com.aleinik.twistencryptor.service.*;

public enum FunctionCode {
    ENCODE(new Encode()),
    DECODE(new Decode()),
    BRUTE_FORCE(new BruteForce()),
    UNSUPPORTED_FUNCTION(new UnsupportedFunction());

    private Function function;

    private FunctionCode(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }
}
