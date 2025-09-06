package com.aleinik.twistencryptor.entity;

import com.aleinik.twistencryptor.repository.Language;
import com.aleinik.twistencryptor.repository.Mode;

import java.nio.file.Path;

public class UserParameters {
    private final Language language;
    private final Mode mode;
    private final Path path;
    private int key;

    public UserParameters(Language language, Mode mode, int key, Path path) {
        this.language = language;
        this.mode = mode;
        this.key = key;
        this.path = path;
    }

    public Language getLanguage() {
        return language;
    }

    public Mode getMode() {
        return mode;
    }

    public int getKey() {
        return key;
    }

    public Path getPath() {
        return path;
    }

    public void setKey(int key){
        this.key = key;
    }
}
