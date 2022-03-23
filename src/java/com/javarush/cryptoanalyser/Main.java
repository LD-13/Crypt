package com.javarush.cryptoanalyser;

import com.javarush.cryptoanalyser.Controller.Control;
import com.javarush.cryptoanalyser.View.Menu;

public class Main {
    public static void main(String[] args) {
        Control control = new Control();
        Menu menu = new Menu(control);
        menu.start();
    }
}
