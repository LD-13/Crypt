package com.javarush.cryptoanalyser.Model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Crypt {
    private final char[] alphabet = Alphabet.RUS_ALPHABET;
    private boolean fileIsEmpty = false;
    private String encryptFilePath;

    public Crypt(String filePath, String encryptFilePath, int key) {
        this.encryptFilePath = encryptFilePath;
        cryptFile(filePath, key);
    }

    private void cryptFile(String filePath, int key) {
        try (BufferedReader readFile = new BufferedReader(new FileReader(filePath))) {
            while (readFile.ready()) {
                String s = readFile.readLine().toLowerCase();
                StringBuilder st = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    for (int j = 0; j < alphabet.length; j++) {
                        if (c == alphabet[j]) {
                            int shift = j + key;
                            if (shift > alphabet.length - 1) {
                                c = alphabet[shift - (alphabet.length)];
                            } else {
                                c = alphabet[shift];
                            }
                            break;
                        }
                    }
                    st.append(c);
                }

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
        try (FileWriter fw = new FileWriter(encryptFilePath, true); BufferedWriter writeFile = new BufferedWriter(fw)) {
            writeFile.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void isFileIsEmpty() {
        try {
            if (Files.exists(Path.of(encryptFilePath))) {
                if (Files.size(Path.of(encryptFilePath)) != 0) {
                    Files.deleteIfExists(Path.of(encryptFilePath));
                }
            }
            fileIsEmpty = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
