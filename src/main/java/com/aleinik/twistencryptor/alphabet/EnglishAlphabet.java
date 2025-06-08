package com.aleinik.twistencryptor.alphabet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnglishAlphabet extends Alphabet {
    private static EnglishAlphabet englishAlphabet;
    private final Set<String> dictionary;

    public EnglishAlphabet() {
        super(
                (
                        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        ".,–-!?:;\"'()[]{}<>/\\|@#$%^&*_+=`~" +
                        "0123456789" +
                        " "
                ).toCharArray());

        dictionary = new HashSet<>(List.of("the", "and", "is", "in", "to", "of", "it", "that", "was", "he"));
    }

    public static EnglishAlphabet getEnglishAlphabet(){
        if(englishAlphabet == null){
            englishAlphabet = new EnglishAlphabet();
        }
        return englishAlphabet;
    }

    public Set <String> getDictionary(){
        return dictionary;
    }

    @Override
    public String getRegexLetterClass() {
        return "[a-zA-Z]";
    }

}
