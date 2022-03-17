package com.example.hangman;

import com.example.hangman.exceptions.MyExceptions;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class TableChoice {

    public IntegerProperty index;
    public StringProperty choice;

    public TableChoice(Integer index_, String choice_){

        setindex(index_);
        setchoice(choice_);

    }

    public void setindex(Integer value) { indexProperty().set(value); }
    public Integer getindex() { return indexProperty().get(); }
    public IntegerProperty indexProperty() {
        if (index == null) index = new SimpleIntegerProperty(this, "index");
        return index;
    }

    public void setchoice(String value) { choiceProperty().set(value); }
    public String getchoice() { return choiceProperty().get(); }
    public StringProperty choiceProperty() {
        if (choice == null) choice = new SimpleStringProperty(this, "choice");
        return choice;
    }

}