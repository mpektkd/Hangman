package com.example.hangman;

import com.example.hangman.exceptions.MyExceptions;
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

public class Choices {

    // File for storing last 5 games
    private String Word;
    private List<Character> WordList;
    private String _dictionary;
    private List<Character> predictedlistWord;

    // significant variables for representing the choices
    private List<String> letters = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I",
                                                       "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                                                            "R", "S", "T", "U", "V", "W", "X", "Y", "Z") ;
    private List<List<Pair<String, Double>>> Choices;

    public Choices(String word, String dictionary){

        Word = word;
        _dictionary = dictionary;
        predictedlistWord = new ArrayList<>();
        Choices = new ArrayList<>();
        WordList = new ArrayList<>();

        for (char ch : Word.toCharArray()) {
            predictedlistWord.add('e'); //empty
            WordList.add(ch);
        }

        for (int i = 0; i < Word.length(); i++){

            List<Pair<String, Double>> temp = new ArrayList<>();
            for (String letter : letters) {
                temp.add(new Pair(letter, 0.0));
            }
            Choices.add(temp);
        }

        update();


    }

    public void update(){

        boolean all_e = true;

        for (char ch : WordList){
            if (ch != 'e'){
                all_e = false;
                break;
            }

        }
        // keep only equal characters words
        List<String> tokens = Dictionary.load(_dictionary)
                                        .stream()
                                        .filter(p -> p.length() == Word.length())
                                        .toList();
        // list of indexes
        List<Integer> indexes = IntStream.range(0, tokens.size()).boxed().toList();


        // temporary ch-list for each token
        List<Character> temp = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++){

            for (char ch : tokens.get(i).toCharArray()) {
                temp.add(ch);
            }

            for(int j = 0; j < predictedlistWord.size(); j++){

                char c1 = predictedlistWord.get(j);
                char c2 = temp.get(j);

                if(c1 != 'e' && c1 != c2 && !all_e){
                    indexes.remove(i);
                }

            }

        }

        List<String> filterTokens = new ArrayList<>();

        for (Integer index: indexes){

            filterTokens.add(tokens.get(index));

        }


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
                temp_choice.set(j, new Pair(temp_choice.get(j).getKey(), count_temp / (double)filterTokens.size()));

            }
            Choices.set(i, temp_choice);
        }

        for (int i = 0; i < Word.length(); i++) {

            List<Pair<String, Double>> choice = Choices.get(i);
            choice.sort((o1, o2) -> {
                int res = 0;

                if (o1.getValue() < o2.getValue())
                    res = -1;
                if (o1.getValue() > o2.getValue())
                    res = 1;

                return res;
            });
        }
    }

}