package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.alphabet.Alphabet;
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

public class Encode implements Function {


    @Override
    public Result execute(UserParameters param, View view) {

        Path outputPath = param.getPath().getParent().resolve("encodeResult.txt");


        try (BufferedReader reader = Files.newBufferedReader(param.getPath());
             BufferedWriter writer = Files.newBufferedWriter(outputPath)) {

            while (reader.ready()) {
                writer.write(encode(param, reader.readLine()));
                writer.newLine();
            }

        } catch (IOException e) {
            //TODO: remove System out print LN
            System.out.println("Something went wrong...");
            return new Result(ResultCode.ERROR, new ApplicationException());
        }

        return new Result(ResultCode.OK);
    }


    private String encode(UserParameters param, String input) {

        char[] chars = input.toCharArray();
        Alphabet alphabet = param.getLanguage().getAlphabet();
        Map<Character, Integer> charMap = alphabet.getCharToIndexMap();
        char[] symbols = alphabet.getSymbols();

        StringBuilder stringBuffer = new StringBuilder();
        for (Character ch : chars) {
            if (charMap.containsKey(ch)) {
                int index = charMap.get(ch);
                int newIndex = (index + param.getKey()) % symbols.length;
                char newSymbol = symbols[newIndex];
                stringBuffer.append(newSymbol);
            } else {
                stringBuffer.append(ch);
            }
        }
        return stringBuffer.toString();
    }
}
