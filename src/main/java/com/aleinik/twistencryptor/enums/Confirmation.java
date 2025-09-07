package com.aleinik.twistencryptor.enums;

public enum Confirmation implements CodedEnum{
    ACCEPT(1),
    DECLINE(2);

    private final int code;

    Confirmation(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public String getDisplayName(){
        return name();
    }

}
