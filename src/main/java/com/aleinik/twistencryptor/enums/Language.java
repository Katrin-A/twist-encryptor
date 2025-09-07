package com.aleinik.twistencryptor.enums;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.alphabet.EnglishAlphabet;
import com.aleinik.twistencryptor.alphabet.RussianAlphabet;

public enum Language implements CodedEnum{
    RUSSIAN(1, new RussianAlphabet()),
    ENGLISH(2, new EnglishAlphabet());

    private final int code;
    private final Alphabet alphabet;

    Language(int code, Alphabet alphabet){
        this.code = code;
        this.alphabet = alphabet;
    }

    public int getCode() {

        return code;
    }

    public String getDisplayName(){

        return this.name();
    }

    public Alphabet getAlphabet(){

        return alphabet;
    }
}
