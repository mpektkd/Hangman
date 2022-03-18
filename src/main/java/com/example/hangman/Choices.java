package com.example.hangman;

import com.example.hangman.exceptions.MyExceptions;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.toLowerCase;

public class Choices {

    // File for storing last 5 games
    private String Word;
    private List<Character> WordList;
    private String _dictionary;
    public List<Character> predictedlistWord;

    // significant variables for representing the choices
    public List<String> letters = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I",
                                                "J", "K", "L", "M", "N", "O", "P", "Q",
                                                "R", "S", "T", "U", "V", "W", "X", "Y", "Z") ;
    public List<List<Pair<String, Double>>> Choices;

    public Choices(String word, String dictionary){

        Word = word;
        _dictionary = dictionary;
        predictedlistWord = new ArrayList<>();
        Choices = new ArrayList<>();
        WordList = new ArrayList<>();

        for (char ch : Word.toCharArray()) {
            predictedlistWord.add('ε'); //empty
            WordList.add(ch);
        }

        for (int i = 0; i < Word.length(); i++){

            List<Pair<String, Double>> temp = new ArrayList<>();
            for (String letter : letters) {
                temp.add(new Pair(letter, 0.0));
            }
            Choices.add(temp);
        }

    }

    public void update(){

        /*

            Calculate the probabilities

         */
        boolean all_e = true;

        for (char ch : predictedlistWord){
            if (ch != 'ε'){
                all_e = false;
                break;
            }

        }
        // keep only equal characters words
        List<String> tokens = Dictionary.load(_dictionary)
                                        .stream()
                                        .filter(p -> p.length() == Word.length())
                                        .toList();
        // list of filterTokens
        List<String> filterTokens = new ArrayList<>();

        boolean remove ;

        for (String token : tokens) {
            remove = false;

            // temporary ch-list for each token
            List<Character> temp = new ArrayList<>();

            for (char ch : token.toCharArray()) {
                temp.add(ch);
            }
            for (int j = 0; j < predictedlistWord.size(); j++) {

                char c1 = predictedlistWord.get(j);
                char c2 = temp.get(j);

                // lowercase defines wrong guess
//                System.out.println(c1 == toLowerCase(c2));
                // remove words that do not have the correct char
                if (c1 != 'ε' && c1 != c2 && !isLowerCase(c1) && !all_e) {
//                    System.out.println(c1 == toLowerCase(c2));
                    System.out.println("Im here" + c2 + " " + token);
                    remove = true;
                    break;
                }
                // remove words that do have the wrong char
                if (c1 != 'ε' && c1 == toLowerCase(c2) && isLowerCase(c1) && !all_e) {
//                    System.out.println(c1 == toLowerCase(c2));
                    System.out.println("Im here" + c2 + " " + token);
                    remove = true;
                    break;
                }
            }
            if (!remove)
                filterTokens.add(token);

        }
        System.out.println(tokens);
        System.out.println(filterTokens);

        for (int i = 0; i < Word.length(); i++){

            List<Pair<String, Double>> choice = Choices.get(i);
            List<Pair<String, Double>> temp_choice = new ArrayList<>(Choices.get(i));

            for (int j = 0; j < letters.size(); j++){

                char ch = choice.get(j).getKey().charAt(0);
                double count_temp = 0.0;

                for (String filterToken : filterTokens) {

                    if (filterToken.charAt(i) == ch)
                        count_temp++;

                }
                System.out.println(count_temp );
                System.out.println((double)filterTokens.size());
                System.out.println(count_temp / (double)filterTokens.size());
                temp_choice.set(j, new Pair(temp_choice.get(j).getKey(), count_temp / (double)filterTokens.size()));

            }
            Choices.set(i, temp_choice);
        }

        // Sorting of the letters based on the probabilities
        for (int i = 0; i < Word.length(); i++) {

            List<Pair<String, Double>> choice = Choices.get(i);
            choice.sort((o1, o2) -> {
                int res = 0;

                // for descending order
                if (o1.getValue() < o2.getValue())
                    res = 1;
                if (o1.getValue() > o2.getValue())
                    res = -1;

                return res;
            });
        }
    }

}