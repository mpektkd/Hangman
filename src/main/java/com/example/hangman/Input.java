package com.example.hangman;

import java.util.Scanner;

class Input {
    public static String scan() {

        Scanner input = new Scanner(System.in);

        // Getting ID input
        System.out.print("Enter ID: ");
        String ID = input.next();
        System.out.println("Text entered = " + ID);

        return ID;
    }
}