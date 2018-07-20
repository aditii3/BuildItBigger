package com.example.android.jokeslib;

import java.util.Random;

public class MyJokes {
    public static final String[] JOKES_LIST = {"What did the spider do on the computer? \nMade a website! ",
            "Why was the computer cold?\nIt left it's Windows open! ",
            "Why did the computer squeak?\nBecause someone stepped on it's mouse! ",
            "Where do all the cool mice live?\nIn their mousepads ",
            "What do you get when you cross a computer with an elephant?\nLots of memory! ",
            "What is a computer virus?\nA terminal illness! ",
            "Why did the computer keep sneezing?\nIt had a virus! "

    };
    public static String getJoke(){
        Random random = new Random();
        return JOKES_LIST[random.nextInt(JOKES_LIST.length)];
    }
}
