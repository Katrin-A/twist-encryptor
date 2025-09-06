package com.aleinik.twistencryptor.repository;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.alphabet.EnglishAlphabet;
import com.aleinik.twistencryptor.alphabet.RussianAlphabet;

public enum Language {
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

    public Alphabet getAlphabet(){

        return alphabet;
    }


    public static Language fromCode(int code)  {
        for (Language language : values()){
            if(language.getCode()== code){
                return language;
            }
        }

        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
