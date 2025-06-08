package com.aleinik.twistencryptor.view;

import com.aleinik.twistencryptor.alphabet.AlphabetFactory;
import com.aleinik.twistencryptor.entity.KeyCandidate;
import com.aleinik.twistencryptor.entity.Result;
import com.aleinik.twistencryptor.entity.UserParameters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static com.aleinik.twistencryptor.constants.ApplicationResultConstants.EXCEPTION;
import static com.aleinik.twistencryptor.constants.ApplicationResultConstants.SUCCESS;

public class ConsoleView implements View {
    private final String languagePrompt = "Please choose the language: Russian - 1, English - 2;";
    private final String encryptionModePrompt = "Please choose the encryption mode: Encode - 1, Decode - 2, Brute Force - 3;";
    private final String encryptionKeyPrompt = "Please specify the key encryption key in a range of 1 to %s;";
    private final String pathPrompt = "Please specify the path to the file;";
    private final String exitPrompt = "\nFor Exit Press 0;";


    private final Scanner scanner = new Scanner(System.in);

    @Override
    public UserParameters getParameters() {

        int language = readIntRange(languagePrompt + exitPrompt, 1, 2);
        int mode = readIntRange(encryptionModePrompt + exitPrompt, 1, 3);
        int key = readKey(mode, language);
        scanner.nextLine();

        Path path = readFilePath(pathPrompt);
        return new UserParameters(language, mode, key, path);
    }

    @Override
    public void printResult(Result result) {
        switch (result.getResultCode()) {
            case OK -> System.out.println(SUCCESS);
            case ERROR -> System.out.println(EXCEPTION + result.getApplicationException().getMessage());
        }

    }

    @Override
    public int confirmDecryptionPreview(String decodedExample, int key) {
        System.out.println("Please, confirm decryption preview (key = " + key + "):");
        System.out.println("Decoded text example: \"" + decodedExample + "\"");
        System.out.println("1 - Accept");
        System.out.println("2 - Decline");

        int answer = -1;
        while (answer != 1 && answer != 2) {
            System.out.print("Your choice: ");
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
                if (answer != 1 && answer != 2) {
                    System.out.println("Please enter 1 (Accept) or 2 (Decline).");
                }
            } else {
                System.out.println("That's not a valid number.");
                scanner.next();
            }
        }

        return answer;
    }

    public int chooseDecryptionKey(List<KeyCandidate> candidates){
        System.out.println("Please, choose best candidate number");
        System.out.println("Decoded Examples are: ");
        for (int i = 0; i < candidates.size(); i++) {
            KeyCandidate candidate = candidates.get(i);
            System.out.println("Number: " + (i + 1) + " key: " + candidate.getKey() + " text example: " + candidate.getPreviewString());

        }
        System.out.println(exitPrompt);
        int answer = -1;
        while (true) {
            System.out.print("Your choice (1 - " + candidates.size() + ", or 0 to exit): ");
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt();
                if (answer == 0) {
                    System.out.println("You have exited the application. Goodbye!");
                    System.exit(0);
                } else if (answer >= 1 && answer <= candidates.size()) {
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and " + candidates.size() + ".");
                }
            } else {
                System.out.println("That's not a valid number.");
                scanner.next();
            }
        }

        return answer-1;
    }

    private int readKey(int mode, int language) {
        if (!(mode == 1) && !(mode == 2)) {
            return 0;
        }
        int max = AlphabetFactory.get(language).getLength() - 1;
        String keyPrompt = String.format(encryptionKeyPrompt, max);
        return readIntRange(keyPrompt + exitPrompt, 1, max);
    }

    private int readIntRange(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= min && value <= max) {
                    return value;
                } else if (value == 0) {
                    System.out.println("You have exited the application. Goodbye!");
                    System.exit(0);
                }
            } else {
                scanner.next();
            }
            System.out.printf("Input must be between %d and %d\n", min, max);
        }

    }

    private Path readFilePath(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                continue;
            }
            Path filePath = Path.of(input).toAbsolutePath();
            if (Files.exists(filePath) && Files.isReadable(filePath)) {
                return filePath;
            } else {
                System.out.println("The file does not exist or is not readable. Please try again.");
            }
        }
    }

}
