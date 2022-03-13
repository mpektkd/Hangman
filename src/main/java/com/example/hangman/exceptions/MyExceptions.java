package com.example.hangman.exceptions;

import java.util.Scanner;

public class MyExceptions extends Exception {
    public static class InvalidCountException extends Exception {

        private static String yes = new String(new StringBuilder("yes"));
        private static String no = new String(new StringBuilder("no"));

        public InvalidCountException(String s) {
            super(s);
        }

        public boolean Dialog() {
            Scanner input = new Scanner(System.in);

            String answer = "";

            while( !answer.equals(yes) && !answer.equals(no) ){

                // Getting answer
                System.out.print(getMessage());
                System.out.print("Would you like to filter only the distinct words?(yes/no)");
                answer = input.nextLine();

            }

            return true;
//            return answer.equals(yes); // has to been figured out the "equals"
        }
    }

    public static class UndersizeException extends Exception {
        public UndersizeException(String s) {
            super(s);
        }
    }

    public static class InvalidRangeException extends Exception {

        private static String yes = new String(new StringBuilder("yes"));
        private static String no = new String(new StringBuilder("no"));

        public InvalidRangeException(String s) {
            super(s);
        }

        public boolean Dialog() {
            Scanner input = new Scanner(System.in);

            String answer = "";

            while( !answer.equals(yes) && !answer.equals(no) ){

                // Getting answer
                System.out.print(getMessage());
                System.out.print("Would you like to filter the words?(yes/no)");
                answer = input.nextLine();

            }

            return true;
//            return answer.equals(yes); // has to been figured out the "equals"
        }
    }

            public static class UnbalancedException extends Exception {
        public UnbalancedException(String s) {
            super(s);
        }
    }
}
