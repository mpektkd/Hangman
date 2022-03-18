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

    /**
     * Constructor of the class Dictionary
     * @param strTokens list of tokens
     */
    public Dictionary(List<String> strTokens){
        tokenArray = strTokens;

        File directory = new File(path);

        if(!directory.exists()){
            directory.mkdir();
        }
    }

    /**
     * Save the words of the dictionary in the specific folder PROJECT_ROOT/medialab/
     * in a file named corresponding to the ID
     * @param ID ID that describes the ebook on the OL
     * @param tokens list of words that are going to be stored in the file
     */
    public static void store(String ID, List<String> tokens) {

        try {
            File directory = new File(path);

            if(!directory.exists()){
                directory.mkdir();
            }
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

    /**
     * Returns the list of tokens that are stored in the specific
     * file-dictionary
     * @param dictionary the name of the file that is going to be load
     * @return  list of tokens
     */
    public static List<String> load(String dictionary) {
        File directory = new File(path);

        if(!directory.exists()){
            directory.mkdir();
        }
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

    /**
     * Load all the filenames of the folder PROJECT_ROOT/medialab/
     * @return  set of strings that are the names of the files
     */
    public static Set<String> loadLib() {
        File directory = new File(path);

        if(!directory.exists()){
            directory.mkdir();
        }
        return Stream.of(new File(path).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

    }
}