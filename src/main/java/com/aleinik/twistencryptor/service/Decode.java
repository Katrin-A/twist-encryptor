package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.exception.ApplicationException;
import com.aleinik.twistencryptor.enums.Language;
import com.aleinik.twistencryptor.enums.ResultCode;
import com.aleinik.twistencryptor.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Decode extends CaesarCipher implements Function {
    @Override
    public Result execute(UserParameters params, View view) {
        //TODO: move the path thing into other place (this is the repited code

        Path outputPath = params.getPath().getParent().resolve("decodeResult.txt");


        try (BufferedReader reader = Files.newBufferedReader(params.getPath());
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

            while (reader.ready()) {
                writer.write(decode(params, reader.readLine()));
                writer.newLine();
            }

        } catch (IOException e) {
            return new Result(ResultCode.ERROR, new ApplicationException());
        }

        return new Result(ResultCode.OK);
    }


    protected String decode(UserParameters params, String input) {
        Language language = params.getLanguage();
        int key = - params.getKey();
        return transform(input, language, key);
    }
}
