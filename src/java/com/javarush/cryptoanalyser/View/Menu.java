package com.javarush.cryptoanalyser.View;

import com.javarush.cryptoanalyser.Controller.Control;
import com.javarush.cryptoanalyser.Model.Alphabet;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Control control;

    public Menu(Control control) {
        this.control = control;
    }

    public void start() {
        System.out.println("What would you like to do?");
        System.out.println("Press 1 to encrypt file");
        System.out.println("Press 2 to decrypt file");
        System.out.println("Press 3 to exit");
        if (scanner.hasNextInt()) {
            switch (scanner.nextInt()) {
                case 1 -> crypt();
                case 2 -> decrypt();
                case 3 -> System.exit(0);
                default -> System.out.println("Please use numbers 1, 2 or 3");
            }
        } else {
            System.out.println("Please use only numbers\n");
            start();
        }
    }

    private void crypt() {
        System.out.println("What file would you like to encrypt(write absolute path)");
        String filePath = scanner.next();
        System.out.println("What shift do you like(from 1 to " + Alphabet.RUS_ALPHABET.length / 2 + ")");
        if (scanner.hasNextInt()) {
            int key = scanner.nextInt();
            if (key > 0 && key < Alphabet.RUS_ALPHABET.length / 2) {
                System.out.println(control.checkFile(filePath, key));
            } else {
                System.out.println("Sorry, it's out of the score.");
            }
        } else {
            scanner.next();
            System.out.println("Shift has to only number.");
        }
        start();
    }

    private void decrypt() {
        System.out.println("What file would you like to decrypt(write absolute path)");
        String filePath = scanner.next();
        System.out.println(control.checkFile(filePath, 0));
        start();
    }
}

