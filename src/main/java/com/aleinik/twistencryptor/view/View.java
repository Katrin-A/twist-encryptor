package com.aleinik.twistencryptor.view;


import com.aleinik.twistencryptor.entity.KeyCandidate;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.enums.Confirmation;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class View {
    protected final ResourceBundle bundle;
    public View() {
        this.bundle = ResourceBundle.getBundle("properties.messages");
    }

    public View(Locale locale) {
        this.bundle = ResourceBundle.getBundle("properties.messages", locale);
    }
    public abstract UserParameters getParameters();

    public abstract void printResult(Result result);

    public abstract Confirmation confirmDecryptionPreview(String decodedExample, int key);

    public abstract int chooseDecryptionKey(List<KeyCandidate> candidates);
}
