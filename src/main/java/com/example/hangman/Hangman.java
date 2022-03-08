package com.example.hangman;

import com.example.hangman.apis.Api;
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

public class Hangman extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();

        String fxmlDocPath = Hangman.class.getResource("Homepage.fxml").toString();
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        VBox root = (VBox) fxmlLoader.load(fxmlStream);
        Scene scene = new Scene(root, 450, 450);

        Rectangle r = new Rectangle(25, 10, 250, 250);
        r.setFill(Color.BLUE);

        root.getChildren().add(r);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        try {

            URL url = new URL("https://www.metaweather.com/api/location/search/?query=London");
            Api api = new Api(url);

            String  response = api.response();
            System.out.println(response);

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}