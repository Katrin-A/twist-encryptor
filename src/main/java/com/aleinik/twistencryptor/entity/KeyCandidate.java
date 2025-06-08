package com.aleinik.twistencryptor.entity;

public class KeyCandidate {
    private final int key;
    private final String previewString;
    private final double score;

    public KeyCandidate(int key, String previewString, double score) {
        this.key = key;
        this.previewString = previewString;
        this.score = score;
    }

    public int getKey() {
        return key;
    }

    public String getPreviewString() {
        return previewString;
    }

    public double getScore() {
        return score;
    }
}
