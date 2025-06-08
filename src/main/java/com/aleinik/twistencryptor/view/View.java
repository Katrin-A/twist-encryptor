package com.aleinik.twistencryptor.view;


import com.aleinik.twistencryptor.entity.KeyCandidate;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;

import java.util.List;
import java.util.Scanner;

public interface View {
    UserParameters getParameters();

    void printResult(Result result);

    int confirmDecryptionPreview(String decodedExample, int key);

    int chooseDecryptionKey(List<KeyCandidate> candidates);
}
