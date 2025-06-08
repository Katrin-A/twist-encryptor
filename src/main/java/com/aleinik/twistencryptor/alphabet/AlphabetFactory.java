package com.aleinik.twistencryptor.alphabet;

import com.aleinik.twistencryptor.repository.AlphabetCode;

public class AlphabetFactory {
    public static Alphabet get(int languageCode){
        return switch(languageCode){
            case(1) -> AlphabetCode.valueOf("RUSSIAN_ALPHABET").getAlphabet();
            case(2) -> AlphabetCode.valueOf("ENGLISH_ALPHABET").getAlphabet();
            default -> throw new IllegalArgumentException(("Unknown alphabet: " + languageCode));
        };
    }
}
