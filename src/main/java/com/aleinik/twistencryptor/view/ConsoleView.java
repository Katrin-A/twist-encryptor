package com.aleinik.twistencryptor.view;


import com.aleinik.twistencryptor.entity.KeyCandidate;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;
import com.aleinik.twistencryptor.enums.CodedEnum;
import com.aleinik.twistencryptor.enums.Confirmation;
import com.aleinik.twistencryptor.enums.Language;
import com.aleinik.twistencryptor.enums.Mode;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import static com.aleinik.twistencryptor.constants.ApplicationResultConstants.EXCEPTION;
import static com.aleinik.twistencryptor.constants.ApplicationResultConstants.SUCCESS;

public class ConsoleView extends View {

    private static final int EXIT_CODE = 0;
    private final Scanner scanner = new Scanner(System.in);


    @Override
    public UserParameters getParameters() {

        Language language = chooseEnum(Language.class, getMessage("language.prompt"));
        Mode mode = chooseEnum(Mode.class, getMessage("encryption.mode.prompt"));
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
    public Confirmation confirmDecryptionPreview(String decodedExample, int key) {
        print(MessageFormat.format(getMessage("confirm.key.prompt"), key));
        print(MessageFormat.format(getMessage("decoded.text.example.prompt"), decodedExample));
        return chooseEnum(Confirmation.class, getMessage("accept.decline.prompt"));
    }

    public int chooseDecryptionKey(List<KeyCandidate> candidates) {
        print(getMessage("choose.best.candidate.prompt"));
        print(getMessage("decoded.examples.prompt"));
        for (int i = 0; i < candidates.size(); i++) {
            KeyCandidate candidate = candidates.get(i);
            int displayIndex = i + 1;
            print(MessageFormat.format(getMessage("print.decoded.examples.prompt"),
                    displayIndex,
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
            if (answer == EXIT_CODE) {
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
        if (!(mode == Mode.ENCODE.getCode()) && !(mode == Mode.DECODE.getCode())) {
            return 0;
        }
        int max = language.getAlphabet().getLength() - 1;
        int min = 1;
        String keyPrompt = MessageFormat.format(getMessage("encryption.key.prompt"), max);
        return readIntRange(keyPrompt + getMessage("exit.prompt"), min, max);
    }

    private int readIntRange(String prompt, int min, int max) {
        int value;
        while (true) {
            print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= min && value <= max) {
                    return value;
                } else if (value == EXIT_CODE) {
                    print(getMessage("goodbye.prompt"));
                    System.exit(0);
                }
            } else {
                scanner.next();
            }
            print(MessageFormat.format(getMessage("invalid.choice.range.prompt"), min, max));
        }

    }

    public <E extends Enum<E> & CodedEnum> E chooseEnum(Class<E> enumClass, String prompt) {
        E[] enumValues = enumClass.getEnumConstants();

        while (true) {
            print(prompt);

            for (E enumValue : enumValues) {
                print(String.format("%s - %d", enumValue.getDisplayName(), enumValue.getCode()));
            }
            print(getMessage("your.choice.prompt"));
            if (scanner.hasNextInt()) {
                int response = scanner.nextInt();
                for (E enumValue : enumValues) {
                    if (enumValue.getCode() == response) {
                        return enumValue;
                    }
                }
                print(MessageFormat.format(getMessage("invalid.choice.range.prompt"), 1, enumValues.length + 1));

            } else {
                print(bundle.getString("not.a.number.prompt"));
                scanner.next();
            }
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


    private void print(String message) {
        System.out.println(message);
    }

    private String getMessage(String message) {
        return bundle.getString(message);
    }


}
