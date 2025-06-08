package com.aleinik.twistencryptor.alphabet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Alphabet {
    protected final int length;
    protected final char[] symbols;
    protected final Map<Character, Integer> map;

    protected Alphabet(char[] symbols) {
        this.symbols = symbols;
        this.length = symbols.length;
        this.map = buildMap(symbols);
    }

    private Map<Character, Integer> buildMap(char[] symbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < symbols.length; i++) {
            map.put(symbols[i], i);
        }

        return map;
    }

    public char[] getSymbols() {
        return symbols;
    }

    public Map<Character, Integer> getCharToIndexMap() {
        return map;
    }

    public int getLength(){
        return length;
    }

    public abstract Set<String> getDictionary();

    public abstract String getRegexLetterClass();

}
