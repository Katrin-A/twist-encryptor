package com.aleinik.twistencryptor.alphabet;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RussianAlphabet extends Alphabet {
    private static RussianAlphabet russianAlphabet;
    private final Set<String> dictionary;
    private static final String REGEX_LETTER_CLASS = "[А-ЯЁа-яё]";

    public RussianAlphabet() {
        super(
                (
                        "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
                                ".,–-!?:;\"'()[]{}<>/\\|@#$%^&*_+=`~" +
                                "0123456789" +
                                " "
                ).toCharArray()
        );

        dictionary = new HashSet<>(List.of(
                "и", "в", "не", "он", "на", "я", "что", "тот", "с", "быть", "как",
                "а", "по", "это", "из", "у", "к", "за", "ты", "но", "мы", "так", "же",
                "от", "до", "о", "его", "она", "сказал", "да", "нет", "был", "меня", "тебя"
        ));
    }

    public static RussianAlphabet getRussianAlphabet() {
        if (russianAlphabet == null) {
            russianAlphabet = new RussianAlphabet();
        }
        return russianAlphabet;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    @Override
    public String getRegexLetterClass() {
        return "[а-яА-ЯёЁ]";
    }
}
