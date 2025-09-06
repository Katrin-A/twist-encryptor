package com.aleinik.twistencryptor.view;


import com.aleinik.twistencryptor.entity.KeyCandidate;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.repository.Language;
import com.aleinik.twistencryptor.repository.Mode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import static com.aleinik.twistencryptor.constants.ApplicationResultConstants.EXCEPTION;
import static com.aleinik.twistencryptor.constants.ApplicationResultConstants.SUCCESS;

public class ConsoleView extends View {

    private static final int OPTION_ACCEPT = 1;
    private static final int OPTION_DECLINE = 2;


    private final Scanner scanner = new Scanner(System.in);

    @Override
    public UserParameters getParameters() {

        int languageCode = readIntRange(getMessage("language.prompt") + getMessage("exit.prompt"), Language.RUSSIAN.getCode(), Language.ENGLISH.getCode());
        Language language = Language.fromCode(languageCode);
        int modeCode = readIntRange(getMessage("encryption.mode.prompt"), 1, 4);
        Mode mode = Mode.fromCode(modeCode);
        int key = readKey(mode.getCode(), language);
        scanner.nextLine();

        Path path = readFilePath();
        return new UserParameters(language, mode, key, path);
    }

    @Override
    public void printResult(Result result) {
        switch (result.getResultCode()) {
            case OK -> print(SUCCESS);
            case ERROR -> print(EXCEPTION + result.getApplicationException().getMessage());
        }

    }

    @Override
    public int confirmDecryptionPreview(String decodedExample, int key) {
        print(MessageFormat.format(getMessage("confirm.key.prompt"), key));
        print(MessageFormat.format(getMessage("decoded.text.example.prompt"), decodedExample));
        print(MessageFormat.format(getMessage("accept.decline.prompt"), OPTION_ACCEPT, OPTION_DECLINE));

        int answer = -1;
        while (answer != OPTION_ACCEPT && answer != OPTION_DECLINE) {
            print(getMessage("your.choice.prompt"));
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
                if (answer != OPTION_ACCEPT && answer != OPTION_DECLINE) {
                    print(MessageFormat.format(getMessage("accept.decline.prompt"), OPTION_ACCEPT, OPTION_DECLINE));
                }
            } else {
                print(getMessage("not.a.number.prompt"));
                print(MessageFormat.format(getMessage("accept.decline.prompt"), OPTION_ACCEPT, OPTION_DECLINE));
                scanner.next();
            }
        }

        return answer;
    }

    public int chooseDecryptionKey(List<KeyCandidate> candidates) {
        print(getMessage("choose.best.candidate.prompt"));
        print(getMessage("decoded.examples.prompt"));
        for (int i = 0; i < candidates.size(); i++) {
            KeyCandidate candidate = candidates.get(i);
            print(MessageFormat.format(getMessage("print.decoded.examples.prompt"),
                    i + 1,
                    candidate.getKey(),
                    candidate.getPreviewString()
            ));
        }
        print(getMessage("exit.prompt"));
        int answer;
        while (true) {
            print(MessageFormat.format(getMessage("choose.the.best.key.candidate.prompt"), candidates.size()));
            if (!scanner.hasNextInt()) {
                print(getMessage("not.a.number.prompt"));
                scanner.next();
                continue;
            }
            answer = scanner.nextInt();
            if (answer == 0) {
                print(getMessage("goodbye.prompt"));
                System.exit(0);
            }

            if (answer >= 1 && answer <= candidates.size()) {
                break;
            }
            print(MessageFormat.format(getMessage("invalid.candidate.choice.prompt"), candidates.size()));
        }

        return answer - 1;
    }

    private int readKey(int mode, Language language) {
        if (!(mode == Mode.ENCODE.getCode()) && !(mode ==  Mode.DECODE.getCode())) {
            return 0;
        }
        int max = language.getAlphabet().getLength() - 1;
        String keyPrompt = MessageFormat.format(getMessage("encryption.key.prompt"), max);
        return readIntRange(keyPrompt + getMessage("exit.prompt"), 1, max);
    }

    private int readIntRange(String prompt, int min, int max) {
        int value;
        while (true) {
            print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= min && value <= max) {
                    return value;
                } else if (value == 0) {
                    print(getMessage("goodbye.prompt"));
                    System.exit(0);
                }
            } else {
                scanner.next();
            }
            print(MessageFormat.format(getMessage("invalid.choice.range.prompt"), min, max));
        }

    }

    private Path readFilePath() {
        while (true) {
            print(getMessage("path.prompt"));
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                continue;
            }
            Path filePath = Path.of(input).toAbsolutePath();
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
                return filePath;
            } else {
                print(getMessage("file.not.found.prompt"));
            }
        }
    }


    private void print(String message){
        System.out.println(message);
    }

    private String getMessage(String message){
        return bundle.getString(message);
    }


}
