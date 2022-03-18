package com.example.hangman;

import com.example.hangman.exceptions.MyExceptions;
import com.example.hangman.exceptions.MyExceptions.*;

import java.io.FilterOutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringTokenizerToArray {

    // Store Dictionary in array
    public static List<String> strTokenArray = new ArrayList<>();

    // We chose lower bound as "6"
    private static Integer lower = 6;
    private static Integer candidatesCount = 20;
    private static Integer balance = 9;
    private static double balancePercentage = 0.2;

    private static List<Integer> lengths;

    public static List<String> getTokensArray(String strData, String strDelimiters)throws   MyExceptions.InvalidRangeException,
                                                                                            MyExceptions.InvalidCountException,
                                                                                            MyExceptions.UnbalancedException,
                                                                                            MyExceptions.UndersizeException
    {


        if( strData == null )
            return strTokenArray;

        //Remove Punctuation
        strData = strData.replaceAll("[^a-zA-Z ]", "").toUpperCase();

        //create tokenizer object
        StringTokenizer st = strDelimiters != null ? new StringTokenizer(strData, strDelimiters) : new StringTokenizer(strData);

        //iterate through all the tokens
        int count = 0;
        while( st.hasMoreTokens() ) {

            //add to an array
            strTokenArray.add(st.nextToken());
        }
        lengths = strTokenArray.stream().map(String::length).collect(Collectors.toList());

        System.out.println(Collections.min(lengths));
        // 1st level of constraint
        if (Collections.min(lengths) < lower)
            throw new MyExceptions.InvalidRangeException("Invalid Range!");

        if (dublicate(false))
            throw new MyExceptions.InvalidCountException("Invalid Count!");

        if(underSize())
            throw new MyExceptions.UndersizeException("Under Size Dataset!");

        if(unBalanced())
            throw new MyExceptions.UnbalancedException("UnBalanced Dataset!");

        return strTokenArray;

    }

    public static void filter()throws   MyExceptions.InvalidCountException,
                                        MyExceptions.UnbalancedException,
                                        MyExceptions.UndersizeException
    {

        List<String> temp = new ArrayList<>();

        for (int i = 0; i < lengths.size(); i++){

            if (lengths.get(i) > lower){

                temp.add(strTokenArray.get(i));
            }
        }

        strTokenArray = new ArrayList<>(temp);

        // check for the other constraints
        if (dublicate(false))
            throw new MyExceptions.InvalidCountException("Invalid Count!");

        if(underSize())
            throw new MyExceptions.UndersizeException("Under Size Dataset!");

        if(unBalanced())
            throw new MyExceptions.UnbalancedException("UnBalanced Dataset!");

    }

    public static boolean dublicate(boolean remove)throws   MyExceptions.UnbalancedException,
            MyExceptions.UndersizeException  {

        boolean invalidCount = false;

        final Set<String> set = new HashSet<>();
        final List<String> temp = new ArrayList<>();

        for (String s : strTokenArray) {

            if (!set.add(s)) {

                invalidCount = true;
            }
            else{
                if (remove) {
                    temp.add(s);
                }
            }
        }
        if (remove) {
            strTokenArray = new ArrayList<>(temp);

        }


        if(underSize())
            throw new MyExceptions.UndersizeException("Under Size Dataset!");

        if(unBalanced())
            throw new MyExceptions.UnbalancedException("UnBalanced Dataset!");

        return invalidCount;

    }

    public static boolean underSize() {

        return strTokenArray.size() < candidatesCount;

    }

    public static boolean unBalanced() {

        lengths = strTokenArray.stream().map(String::length).collect(Collectors.toList());

        int count = 0;

        for (Integer length : lengths) {
            if (length > balance)
                count++;
        }


        return  (double)count / (double)lengths.size() < balancePercentage;

    }
}