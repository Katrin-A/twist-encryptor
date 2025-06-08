package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.alphabet.AlphabetFactory;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.exception.ApplicationException;
import com.aleinik.twistencryptor.repository.ResultCode;
import com.aleinik.twistencryptor.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Decode implements Function {
    @Override
    public Result execute(UserParameters params, View view) {

        Path outputPath = params.getPath().getParent().resolve("decodeResult.txt");


        try (BufferedReader reader = Files.newBufferedReader(params.getPath());
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

            while (reader.ready()) {
                writer.write(decode(params.getLanguage(), params.getKey(), reader.readLine()));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Something went wrong...");
            return new Result(ResultCode.ERROR, new ApplicationException());
        }

        return new Result(ResultCode.OK);
    }


    protected String decode(int language, int key, String input) {
        char[] chars = input.toCharArray();
        Alphabet alphabet = AlphabetFactory.get(language);
        Map<Character, Integer> charMap = alphabet.getCharToIndexMap();
        char[] symbols = alphabet.getSymbols();

        StringBuilder stringBuffer = new StringBuilder();
        for (Character ch : chars) {
            if (!charMap.containsKey(ch)) {
                stringBuffer.append(ch);
                continue;
            }
            int index = charMap.get(ch);
            int newIndex = (index - key+ symbols.length) % symbols.length;
            char newSymbol = symbols[newIndex];
            stringBuffer.append(newSymbol);
        }
        return stringBuffer.toString();
    }
}
