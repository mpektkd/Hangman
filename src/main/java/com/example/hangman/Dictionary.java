package com.example.hangman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dictionary {

    private static String path = System.getProperty("user.dir") + "/medialab";
    private List<String> tokenArray;

    public Dictionary(List<String> strTokens){
        tokenArray = strTokens;

        File directory = new File(path);

        if(!directory.exists()){
            directory.mkdir();
        }
    }

    public static void store(String ID, List<String> tokens) {

        try {

            File file = new File(path + "/" + "hangman_DICTIONARΥ - " + ID + ".txt");

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (String str: tokens){
                bw.write(str + System.getProperty("line.separator"));
            }
            bw.close();

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

    public static Set<String> loadLib() {

        return Stream.of(new File(path).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

    }
}