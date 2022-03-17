package com.example.hangman;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.hangman.exceptions.MyExceptions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import static com.example.hangman.Hangman.createDictionary;

/**
 * Controller of Homepage.fxml
 * This is the main page of the game.
 */
public class ControllerHomepage {

    private String dictID, libID;
    private Game game;
    boolean solution = false, gameEnded = false;
    public  int isOver;

    @FXML
    private BorderPane container;

    @FXML
    private Label welcomeText;

    // Game Start
    @FXML
    private String ChosenDictionary;

    @FXML
    private void onHelloButtonClick(){
        welcomeText.setText("You pressed the button");
    };

    // Application Menu
    @FXML
    private MenuBar LoadMenuBar;

    @FXML // fx:id="fruitCombo"
    private ComboBox<String> DictionariesCombo; // Value injected by FXMLLoader

    @FXML // fx:id="fruitCombo"
    private TextField DictionariesID; // Value injected by FXMLLoader

    @FXML
    private Menu applicationMenu;

    @FXML
    private MenuItem startMenuItem;

    @FXML
    private MenuItem createMenuItem;

    @FXML
    private MenuItem loadMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    // Details Menu
    @FXML
    private Menu detailsMenu;

    @FXML
    private MenuItem dictMenuItem;

    @FXML
    private MenuItem roundsMenuItem;

    @FXML
    private MenuItem solutionMenuItem;

    // Game Informations
    @FXML
    private Text dictText;

    @FXML
    private Text numWordsText;

    @FXML
    private Text pointsText;

    @FXML
    private Text successText;

    // Image and hidden word
    @FXML
    private Label hiddenWordText;

    @FXML
    private ImageView imageView;

    // Table with choices
    @FXML
    private TableView<TableChoice> tableChoices;

    @FXML
    private TableColumn<TableChoice, Integer> indexCol;

    @FXML
    private TableColumn<TableChoice, String> choicesCol;

    // Input

    @FXML
    private ComboBox<Integer> indexChoice;

    @FXML
    private ComboBox<String> letterChoice;

    @FXML
    private Button enterChoice;

    //--------------------------------------------------------------------------------

    @FXML
    private void enterAction(ActionEvent event) {
        play();
    }
    private static boolean isNumeric(String s) {
        return s != null && s.matches("^[0-9]*$");
    }

    private String ListToString(ArrayList<Character> list) {
        String listString = list.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        return listString;
    }


    @FXML
    private void StartGame(ActionEvent event) {
        start();
    }

    @FXML
    private void CreateDictionary(ActionEvent event) {

        Stage primaryStage = (Stage) LoadMenuBar.getScene().getWindow();
        popup(primaryStage, true);

    }

    @FXML
    private void LoadDictionary(ActionEvent event) {

        Stage primaryStage = (Stage) LoadMenuBar.getScene().getWindow();
        popup(primaryStage, false);

    }

    private void popup(final Stage primaryStage, boolean create){
        try{

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("Choose Dictionary ID"));

            if (!create){

                // Initialize dropdown List and add options
                DictionariesCombo = new ComboBox<>();
                DictionariesCombo.getItems().setAll(Dictionary.loadLib());
                dialogVbox.getChildren().add(DictionariesCombo);

                // Submit button for loading the dictionary
                Button submit = new Button();
                submit.setText("Submit");
                submit.setOnAction(
                        actionEvent -> {
                            ChosenDictionary = DictionariesCombo.getValue();
                            dictText.setText(ChosenDictionary);
                            numWordsText.setText(String.valueOf(CountWords()));
                            dialog.close();
                        }

                );
                dialogVbox.getChildren().add(submit);
            }
            else{
                // Initialize dropdown List and add options
                DictionariesID = new TextField();
                dialogVbox.getChildren().add(DictionariesID);

                // Submit button for loading the dictionary
                Button submit = new Button();
                submit.setText("Submit");
                submit.setOnAction(
                        actionEvent -> {
                            createDictionary(DictionariesID.getText());
                            dialog.close();
                        }

                );
                dialogVbox.getChildren().add(submit);
            }



            // Create new Scene for Dialog Window
            Scene dialogScene = new Scene(dialogVbox, 400, 300);
            dialog.setScene(dialogScene);

            dialog.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Stage CreateModal(ActionEvent event, String message) {

        Stage primaryStage = (Stage) container.getScene().getWindow();
        final Stage dialog = new Stage();

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(message));

        // Create new Scene for Dialog Window
        Scene dialogScene = new Scene(dialogVbox, 400, 300);
        dialog.setScene(dialogScene);

        return dialog;
    }

    @FXML
    private void DictionaryStatistics(ActionEvent event) {

        try {

            if (ChosenDictionary == null)
                throw new MyExceptions.LoadingDictinaryException();

            List<String> strTokenArray = Dictionary.load(ChosenDictionary);

            List<Integer> lengths = strTokenArray.stream().map(String::length).toList();

            double count6 = 0;
            double  count7_9 = 0;
            double  count10_ = 0;

            double size = (double)lengths.size();

            for (Integer length : lengths) {
                if (length == 6)
                    count6++;
                if (length >= 7 && length <= 9 )
                    count7_9++;
                if (length >= 10)
                    count10_++;
            }

            Stage dialog = CreateModal(event, "Statistics");

            VBox vbox = (VBox) dialog.getScene().getRoot();

            vbox.getChildren().add(new Text("Percentage of words with 6 Letters: " + String.valueOf(count6 / size * 100)));
            vbox.getChildren().add(new Text("Percentage of words with 7 - 9 Letters: " + String.valueOf(count7_9 / size * 100)));
            vbox.getChildren().add(new Text("Percentage of words with 10+ Letters: " + String.valueOf(count10_ / size * 100)));

            dialog.show();
        }catch(MyExceptions.LoadingDictinaryException e){
            Stage dialog = CreateModal(event, "Statistics");
            VBox vbox = (VBox) dialog.getScene().getRoot();

            vbox.getChildren().add(new Text("You have to choose a Dictionary!"));

            dialog.show();
        }

    }

    private long CountWords() {

        long lines = -1;

        try {
            if (ChosenDictionary != null){
                String path_ = System.getProperty("user.dir") + "/medialab/" + ChosenDictionary;
                BufferedReader reader = new BufferedReader(new FileReader(path_));
                lines++;
                while (reader.readLine() != null) lines++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;

    }


    private void start() {
        try{
            if (ChosenDictionary == null)
                throw new MyExceptions.LoadingDictinaryException();

            game = new Game(ChosenDictionary);
//            solution = false;

            System.out.println(game.Word);
            // clear from the previous game
            indexChoice.getItems().clear();
            tableChoices.getColumns().clear();
            letterChoice.getItems().clear();

            // initialize the letters
            letterChoice.getItems().setAll(game.choices.letters);


            //Update Informations
            dictText.setText(ChosenDictionary);
            numWordsText.setText(String.valueOf(CountWords()));
            pointsText.setText("0");;
            successText.setText("0");;

            // Recalculate the probabilities and the sorting
            // change the predictedListWord based on the choice
            // of the user
            game.choices.update();

            // Update table with choices
            updateTable();

            //Update hidden word and image
            updateActiveWord();
            Image image = new Image(System.getProperty("user.dir") + "/images/1_phase.png");
            imageView.setImage(image);

        } catch(MyExceptions.LoadingDictinaryException e){
            Stage dialog = CreateModal(new ActionEvent(), "Statistics");
            VBox vbox = (VBox) dialog.getScene().getRoot();

            vbox.getChildren().add(new Text("You have to choose a Dictionary!"));

            dialog.show();
        }
    }

    public void play(){
        try {
//            int isOver = game.end();
//            if(!solution) {
//                if(isOver == 1) {

            // take the user choice
            String c = letterChoice.getValue();
            Integer i = indexChoice.getValue();

            // get success or not and in yes then update predictedWord
            game.CalculatePointsAndSuccess(i, c.charAt(0));

            // show the results to the UI
            pointsText.setText(String.valueOf(game.TotalPoints));;
            successText.setText(String.valueOf(game.correct));;

            // clear the word indexing, as it may be some
            // changes in the predictedListWord
            indexChoice.getItems().clear();

            // Recalculate the probabilities and the sorting
            // change the predictedListWord based on the choice
            // of the user
            game.choices.update();

            // Update table with choices
            updateTable();

            //Update hidden word and image
            updateActiveWord();
            String img = System.getProperty("user.dir") + "/images/" + String.valueOf(game.faults+1) + "_phase.png";
            Image image = new Image(img);
            imageView.setImage(image);



            isOver = game.end();
            if(isOver == 1) {
                try {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("WINNER!");
                    alert.setHeaderText("You won.");
                    String cntText = "Points: " + pointsText.getText() + "\n" + "Succeeded Hits: " + successText.getText();
                    alert.setContentText(cntText);
                    alert.showAndWait();

                    game.choices.update();

                    // Update table with choices
                    updateTable();

                    //Update hidden word and image
                    updateActiveWord();

                    // End game and define the winner
                    endGame();
                    game.Winner = "User";
                    game.store();
                }
                catch (MyExceptions.OutOfGameStorage e){
                    NotEnoughStorage();
                }
            }
            else if (isOver == -1) {
                try {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("LOSER!");
                    alert.setHeaderText("You lost.");
                    String cntText = "Points: " + pointsText.getText() + "\n" + "Succeeded Hits: " + successText.getText();
                    alert.setContentText(cntText);
                    alert.showAndWait();

                    // End game and define the winner
                    endGame();
                    game.Winner = "PC";
                    game.store();

                } catch (MyExceptions.OutOfGameStorage e) {
                    NotEnoughStorage();
                }
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void NotEnoughStorage(){
        Stage dialog = CreateModal(new ActionEvent(), "Not Enough Storage");
        VBox vbox = (VBox) dialog.getScene().getRoot();

        vbox.getChildren().add(new Text("Would you like to delete the oldest?"));

        Button yes = new Button();
        yes.setText("Yes");
        yes.setOnAction(
                actionEvent -> {
                    game.delete();
                    try {
                        game.store();
                    } catch (MyExceptions.OutOfGameStorage e) {
                        e.printStackTrace();
                    }
                    dialog.close();
                }

        );
        Button no = new Button();
        no.setText("No");
        no.setOnAction(
                actionEvent -> {
                    dialog.close();
                }

        );
        vbox.getChildren().add(yes);
        vbox.getChildren().add(no);

        dialog.show();
    }

    private void updateActiveWord() {
        StringBuilder word = new StringBuilder();
        for(int i = 0; i < game.choices.predictedlistWord.size(); i++) {
            Character letter = game.choices.predictedlistWord.get(i);
            if (letter == 'e') word.append("_");
            else word.append(letter);
            word.append(" ");
        }
        hiddenWordText.setText(word.toString());
    }

    private void updateTable() {
        List<TableChoice> possibleChoices = new ArrayList<TableChoice>();
        for(int i=0; i < game.Word.length(); i++) {

            if (game.choices.predictedlistWord.get(i) != 'e')
                continue;

            StringBuilder temp = new StringBuilder();
            for(int j = 0; j < game.choices.letters.size(); j++){
                temp.append(game.choices.Choices.get(i).get(j).getKey());
            }
            String sortedChoices = new String((temp));

            possibleChoices.add(new TableChoice(i, sortedChoices));
            indexChoice.getItems().add(i);
        }
        indexCol = new TableColumn<TableChoice, Integer>("Index");
        indexCol.setCellValueFactory(new PropertyValueFactory<TableChoice, Integer>("index"));
        choicesCol = new TableColumn<TableChoice, String>("Sorted Choices");
        choicesCol.setCellValueFactory(new PropertyValueFactory<TableChoice, String>("choice"));

        ObservableList<TableChoice> myObservableList = FXCollections.observableList(possibleChoices);
        tableChoices.setItems(myObservableList);
        tableChoices.getColumns().setAll(indexCol, choicesCol);

        // to avoid null reference
        if( indexChoice.getItems().size() != 0){
            indexChoice.setValue(indexChoice.getItems().get(0));
            letterChoice.setValue(letterChoice.getItems().get(0));
        }

    }
    @FXML
    private void exitAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void roundsAction(ActionEvent event) {
        try {

            // take the set of game-files
            Set<String> folder = Stream.of(Objects.requireNonNull(new File(Game.path).listFiles()))
                                                .filter(file -> !file.isDirectory())
                                                .map(File::getName)
                                                .collect(Collectors.toSet());

            Stage dialog = CreateModal(event, "Game History");

            VBox vbox = (VBox) dialog.getScene().getRoot();


            // iterate through the files

            int counter = 0;
            int indx;

            List<String> info = Arrays.asList("Word: ", ", Tries: ", ", Winner: ");
            String line;

            for (String filepath: folder){
                counter ++;
                indx = 0;

                BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") +"/games/" + filepath));

                Text text = new Text();
                text.setText(String.valueOf(counter + ". "));

                while ((line = reader.readLine()) != null){

                    text.setText(text.getText() + info.get(indx) + line);
                    indx++;

                }
                vbox.getChildren().add(text);

            }

            dialog.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ShowSolution(ActionEvent event) {

        showSolution();

    }

    public void showSolution(){
        try {
            game.solution = true;


            Stage dialog = CreateModal(new ActionEvent(), "Solution is revealed!");
            VBox vbox = (VBox) dialog.getScene().getRoot();

            vbox.getChildren().add(new Text("The secret word is: " + game.Word));

            Button ok = new Button();
            ok.setText("OK");
            ok.setOnAction(
                    actionEvent -> {
                        // clear the game board
                        dialog.close();
                        endGame();
                        game.Winner = "PC";
                        try {
                            game.store();
                        } catch (MyExceptions.OutOfGameStorage e) {
                            NotEnoughStorage();

                        }
                    }

            );
            vbox.getChildren().add(ok);

            dialog.show();



        } catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No game has started yet");
            alert.setContentText("There is NO solution to \"NO game\"...");
            alert.showAndWait();
        }
    }


    public void endGame(){

        indexChoice.getItems().clear();
        letterChoice.getItems().clear();
        tableChoices.getColumns().clear();

        dictText.setText("");
        numWordsText.setText("");
        pointsText.setText("");
        successText.setText("");

        hiddenWordText.setText("");
        imageView.setImage(null);
    }
}
