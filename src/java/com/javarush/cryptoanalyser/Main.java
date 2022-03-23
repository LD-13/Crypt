package com.javarush.cryptoanalyser;

public class Main {
    public static void main(String[] args) {
        Control control = new Control();
        Menu menu = new Menu(control);
        menu.start();
    }
}
