package com.aleinik.twistencryptor.entity;

import java.nio.file.Path;

public class UserParameters {
    private final int language;
    private final int mode;
    private final Path path;
    private int key;

    public UserParameters(int language, int mode, int key, Path path) {
        this.language = language;
        this.mode = mode;
        this.key = key;
        this.path = path;
    }

    public int getLanguage() {
        return language;
    }

    public int getMode() {
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
