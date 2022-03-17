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
import java.util.stream.Stream;

public class Game {

    // File for storing last 5 games
    public static String path = System.getProperty("user.dir") + "/games";

    public String Word;
    public Integer Tries = 0;
    public String Winner;

    public Integer faults = 0;
    public Integer correct = 0;
    public boolean solution = false;

    private String _dictionary;

    public Integer TotalPoints;

    public Choices choices;

    public Game(String dictionary){

        _dictionary = dictionary;

        File directory = new File(path);

        if(!directory.exists()){
            directory.mkdir();
        }

        List<String> tokens = Dictionary.load(_dictionary);

        java.util.Random random = new java.util.Random();
        int random_index = random.nextInt(tokens.size());

        // initialize game word
        Word = tokens.get(random_index);

        TotalPoints = Word.length() * 15;
        choices = new Choices(Word, _dictionary);

    }

    public void store() throws MyExceptions.OutOfGameStorage {


        try {
            if (CountGames() == 5)
                throw new MyExceptions.OutOfGameStorage();

            this.Tries = this.correct + this.faults;
            List<String> tokens = Arrays.asList(String.valueOf(this.Word), String.valueOf(this.Tries), String.valueOf(this.Winner));;

            String GameName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());

            File file = new File(path + "/" + "hangman_GAME - " + GameName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (String str: tokens){
                bw.write(str + System.getProperty("line.separator"));
            }
            bw.close();

        }catch(MyExceptions.OutOfGameStorage e){

            throw new MyExceptions.OutOfGameStorage();

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static Integer CountGames() {

        return Stream.of(Objects.requireNonNull(new File(path).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet()).size();

    }

    public void CalculatePointsAndSuccess(Integer index, Character ch){

        Double probability;

        if (Word.charAt(index) == ch){
            this.choices.predictedlistWord.set(index, ch);
            correct++;

            for (Pair<String, Double> pair : this.choices.Choices.get(index)){
                if (pair.getKey().charAt(0) == ch){
                    probability = pair.getValue();
                    if(probability >= 0.6)
                        TotalPoints += 5;
                    else if(probability <= 0.6 && probability >= 0.4)
                        TotalPoints += 10;
                    else if(probability <= 0.4 && probability >= 0.25)
                        TotalPoints += 15;
                    else if(probability <= 0.25)
                        TotalPoints += 30;
                }

            }

        }
        else{
            faults ++;
            TotalPoints -= 15;
        }

    }

    public Integer end(){

        if (faults == 5)
            return -1;
        else
            if (correct == Word.length())
                return 1;
        return 0;
    }

    public void delete(){

        List<String> history = Stream.of(Objects.requireNonNull(new File(path).listFiles()))
                                .filter(file -> !file.isDirectory())
                                .map(File::getName).toList();

        File file = new File(System.getProperty("user.dir") +"/games/" + history.get(history.size() - 1));

        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }

    }
}