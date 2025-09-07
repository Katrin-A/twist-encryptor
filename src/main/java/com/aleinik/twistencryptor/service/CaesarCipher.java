package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.enums.Language;

import java.util.Map;

public abstract class CaesarCipher {

    protected String transform(String input, Language language, int key){
        char[] chars = input.toCharArray();
        Alphabet alphabet = language.getAlphabet();
        Map<Character, Integer> charMap = alphabet.getCharToIndexMap();
        char[] symbols = alphabet.getSymbols();

        StringBuilder stringBuffer = new StringBuilder();
        for (Character ch : chars) {
            if (charMap.containsKey(ch)) {
                int index = charMap.get(ch);
                int newIndex =  (index + key + symbols.length) % symbols.length;
                char newSymbol = symbols[newIndex];
                stringBuffer.append(newSymbol);
            } else {
                stringBuffer.append(ch);
            }
        }
        return stringBuffer.toString();
    }

}
