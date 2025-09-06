package com.aleinik.twistencryptor.repository;

import com.aleinik.twistencryptor.service.*;

public enum Mode {
    ENCODE(1, new Encode()),
    DECODE(2, new Decode()),
    BRUTE_FORCE(3, new BruteForce()),
    STATISTICAL_ANALYSIS(4, new StatisticalAnalysis());

    private final Function function;

    private final int code;

    Mode(int code, Function function) {
        this.code = code;
        this.function = function;
    }


    public int getCode(){
        return code;
    }

    public Function getFunction() {

        return function;
    }

    public static Mode fromCode(int code){
        for(Mode mode : values()){
            if(mode.getCode() == code){
                return mode;
            }
        }

        throw new IllegalArgumentException("No such mode:" + code);
    }
}
