package com.aleinik.twistencryptor.repository;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.alphabet.EnglishAlphabet;
import com.aleinik.twistencryptor.alphabet.RussianAlphabet;

public enum AlphabetCode {
    RUSSIAN_ALPHABET(new RussianAlphabet()),
    ENGLISH_ALPHABET(new EnglishAlphabet());

    private final Alphabet alphabet;

    private AlphabetCode(Alphabet alphabet){
       this.alphabet = alphabet;
    }

    public Alphabet getAlphabet(){
        return alphabet;
    }


}
