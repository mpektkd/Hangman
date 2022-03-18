package com.example.hangman;

import com.example.hangman.apis.Api;
import com.example.hangman.exceptions.MyExceptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import static com.example.hangman.StringTokenizerToArray.getTokensArray;

public class Hangman extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Hangman.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("MediaLab Hangman");
        stage.setScene(scene);
        stage.show();


    }

    public static void createDictionary(String ID) throws   MyExceptions.InvalidRangeException,
                                                            MyExceptions.InvalidCountException,
                                                            MyExceptions.UnbalancedException,
                                                            MyExceptions.UndersizeException{

        try {
            URL url = new URL("https://openlibrary.org/works/" + ID + ".json");  //description JsonObject
            Api api = new Api(url);

            String response = api.response();
            String value = api.parse(response);

            // Tokenize our dictionary
            List<String> TokensArray = getTokensArray(value, null);


            Dictionary dictionary = new Dictionary(TokensArray);

            dictionary.store(ID, TokensArray);

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch(args);

    }
}