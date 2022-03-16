package com.example.hangman;

import com.example.hangman.exceptions.MyExceptions;

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
    private static String path = System.getProperty("user.dir") + "/games";

    private String Word;
    private Integer Tries = 0;
    private String Winner;

    private String _dictionary;

    private Integer TotalPoints = 0;
    private Double Success = 0.0;

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



    }

    public void store() throws MyExceptions.OutOfGameStorage {


        try {
            if (CountGames() == 5)
                throw new MyExceptions.OutOfGameStorage();

            List<String> tokens = Arrays.asList(String.valueOf(this.Word), String.valueOf(this.Tries), String.valueOf(this.Winner));;

            String GameName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());

            File file = new File(path + "/" + "hangman_GAME - " + GameName + ".txt");

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

    public static List<String> load(String dictionary) {

        List<String> Tokens = new ArrayList<>();
        try {

            File file = new File(path + "/" + dictionary);

            Scanner rd = new Scanner(file);
            while(rd.hasNextLine()){
                String data = rd.nextLine();
                Tokens.add(data);
            }
            rd.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return Tokens;
    }

    public static Integer CountGames() {

        return Stream.of(Objects.requireNonNull(new File(path).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet()).size();

    }
}