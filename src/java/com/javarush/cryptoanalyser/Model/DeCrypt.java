package com.javarush.cryptoanalyser.Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DeCrypt {
    private final char[] alphabet = Alphabet.RUS_ALPHABET;
    private boolean fileIsEmpty = false;
    private char maxLetter = '_';
    private int key = 0;
    private boolean isDisplayAllKeys = false;
    private String decryptFilePath;

    public DeCrypt(String filePath, String decryptFilePath) {
        this.decryptFilePath = decryptFilePath;
        deCryptFile(filePath);
    }


    public void readPartOfFile(String filePath) {
        // todo сделать текст ошибки понятным и пробросить вывод в menu
        try (BufferedReader readFile = new BufferedReader(new FileReader(filePath))) {
            StringBuilder filePart = new StringBuilder();
            while (readFile.ready() && filePart.length() < 2000) {
                filePart.append(readFile.readLine());
            }
            if (filePart.length() > 2000) {
                findFrequentLetter(filePart);
            } else {
                isDisplayAllKeys = true;
                bruteForce(filePart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bruteForce(StringBuilder filePart) {
        int maxSpace = 0;
        int indexKeyOfMaxSpace = 0;
        for (key = 1; key < alphabet.length / 2; key++) {
            StringBuilder example = new StringBuilder();
            changeLetters(String.valueOf(filePart), example);
            if (isDisplayAllKeys) {
                //todo нужно чтобы текст выводился в Menu
                System.out.println("KEY NUMBER = " + key);
                System.out.println(example);
                System.out.println("**********");
            }
            int spaceAmount = example.toString().split(" ").length;
            if (spaceAmount > maxSpace) {
                maxSpace = spaceAmount;
                indexKeyOfMaxSpace = key;
            }
        }
        key = indexKeyOfMaxSpace;
    }


    private void findFrequentLetter(StringBuilder filePart) {
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = filePart.toString().toCharArray();
        for (char letter : chars) {
            if (map.containsKey(letter)) {
                map.put(letter, map.get(letter) + 1);
            } else
                map.put(letter, 1);
        }
        int max = Collections.max(map.values());
        for (char key : map.keySet()) {
            if (map.get(key) == max) {
                maxLetter = key;
                break;
            }
        }
        analysisByFrequentLetter();
    }

    private void analysisByFrequentLetter() {
        if (maxLetter == '_') {
            //todo нужно чтобы текст выводился в Menu
            System.out.println("The frequent letter didn't find");
        }
        int indexSpace = 0;
        int indexFrequentLetter = 0;
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == ' ') {
                indexSpace = i;
            }
            if (alphabet[i] == maxLetter) {
                indexFrequentLetter = i;
            }
        }
        key = alphabet.length + indexFrequentLetter - indexSpace;
    }


    private void deCryptFile(String filePath) {
        readPartOfFile(filePath);
        // todo сделать текст ошибки понятным и пробросить вывод в menu
        try (BufferedReader readFile = new BufferedReader(new FileReader(filePath))) {
            while (readFile.ready()) {
                String s = readFile.readLine().toLowerCase();
                StringBuilder st = new StringBuilder();
                changeLetters(s, st);
                saveFile(st);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile(StringBuilder line) {
        if (!fileIsEmpty) {
            isFileIsEmpty();
        }
        // todo сделать текст ошибки понятным и пробросить вывод в menu
        try (BufferedWriter writeFile = new BufferedWriter(new FileWriter(decryptFilePath, true))) {
            writeFile.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isFileIsEmpty() {
        try {
            if (Files.exists(Path.of(decryptFilePath))) {
                if (Files.size(Path.of(decryptFilePath)) != 0) {
                    Files.deleteIfExists(Path.of(decryptFilePath));
                }
            }
            fileIsEmpty = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeLetters(String s, StringBuilder st) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            for (int j = 0; j < alphabet.length; j++) {
                if (c == alphabet[j]) {
                    int shift = j - key;
                    if (shift < 0) {
                        c = alphabet[shift + (alphabet.length)];
                    } else {
                        c = alphabet[shift];
                    }
                    break;
                }
            }
            st.append(c);
        }
    }
}
