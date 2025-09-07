package com.aleinik.twistencryptor.service;

import com.aleinik.twistencryptor.alphabet.Alphabet;
import com.aleinik.twistencryptor.entity.KeyCandidate;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.enums.Confirmation;
import com.aleinik.twistencryptor.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BruteForce implements Function {

    private final Decode decoder = new Decode();


    @Override
    public Result execute(UserParameters params, View view) {
        List<KeyCandidate> candidates = new ArrayList<>();
        int bestMatchIndex = 0;
        candidates.addAll(findKey(params));

        List<KeyCandidate> topCandidates = candidates.subList(0, Math.min(5, candidates.size()));
        Confirmation answer = view.confirmDecryptionPreview(candidates.get(0).getPreviewString(), candidates.get(0).getKey());
        if (answer == Confirmation.DECLINE) {
            bestMatchIndex = chooseDecryptionKey(view, topCandidates);
        }

        params.setKey(candidates.get(bestMatchIndex).getKey());
        Result result = decoder.execute(params, view);
        return result;
    }

    private List<KeyCandidate> findKey(UserParameters params) {
        List<KeyCandidate> candidates = new ArrayList<>();

        List<String> sampleLines = readSampleLines(params.getPath(), 20);
        Alphabet alphabet = params.getLanguage().getAlphabet();
        Collection<Integer> keySet = alphabet.getCharToIndexMap().values();

        for (Integer key : keySet) {
            List<String> decodedLines = decodeSample(params, sampleLines);
            double fitness = evaluateFitnessByPatterns(decodedLines, alphabet);
            candidates.add(new KeyCandidate(key, decodedLines.get(0), fitness));
        }

        candidates.sort(Comparator.comparing(KeyCandidate::getScore).reversed());
        return candidates;
    }


    private double evaluateFitnessByPatterns(List<String> decodedLines, Alphabet alphabet) {
        String letterClass = alphabet.getRegexLetterClass();

        Set<Pattern> patterns = Set.of(
                Pattern.compile("\\s" + letterClass + "{2,}\\s"),
                Pattern.compile("\\s" + letterClass + "{2,},\\s"),
                Pattern.compile("\\s" + letterClass + "{2,}[.!?:…;](\\s|$)"),
                Pattern.compile("\\s" + letterClass + "{2,}[.!?:…;](|$)"),
                Pattern.compile("(^|\\s)" + letterClass + "{2,}[.!?:…;]($|\\s)")
        );
        int matchCount = 0;
        for (String line : decodedLines) {
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    matchCount++;
                }
            }
        }
        int wordCount = 0;
        for (String line : decodedLines) {
            wordCount += line.split("\\s+").length;
        }
        return (double) matchCount / wordCount;
    }

    private int chooseDecryptionKey(View view, List<KeyCandidate> candidates) {
        int result = view.chooseDecryptionKey(candidates);
        return result;
    }

    private List<String> readSampleLines(Path path, int maxLines) {
        List<String> sampleLines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < maxLines) {
                sampleLines.add(line);
                count++;
            }

        } catch (IOException e) {
            //TODO: Remove this and wrap the IOException into Runtime exception
            System.out.println("Something went wrong...");
        }
        return sampleLines;
    }

    private List<String> decodeSample(UserParameters params, List<String> sampleLines) {
        List<String> decodedSample = new ArrayList<>(sampleLines.size());
        for (String line : sampleLines) {
            decodedSample.add(decoder.decode(params, line));
        }

        return decodedSample;

    }


}
