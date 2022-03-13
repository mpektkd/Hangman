package com.example.hangman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

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

}