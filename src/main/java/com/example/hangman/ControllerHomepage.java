package com.example.hangman;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller of Homepage.fxml
 * This is the main page of the game.
 */
public class ControllerHomepage {

    private String dictID, libID;
//    private Game game;
    boolean solution = false, gameEnded = false;

    @FXML
    private BorderPane container;

    @FXML
    private Label welcomeText;


    @FXML
    private void onHelloButtonClick(){
        welcomeText.setText("You pressed the button");
    };

    // Application Menu
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
//    @FXML
//    private TableView<Choices> tableChoices;
//
//    @FXML
//    private TableColumn<Choices, Integer> indexCol;
//
//    @FXML
//    private TableColumn<Choices, String> choicesCol;

    // Input

    @FXML
    private ComboBox<Integer> indexChoice;

    @FXML
    private ComboBox<Character> letterChoice;

    @FXML
    private Button enterChoice;

    //--------------------------------------------------------------------------------

    @FXML
    private void enterAction(ActionEvent event) {
        try {
//            int isOver = game.gameOver();
//            if(solution == false) {
//                if(isOver == 0) {
//
//                    char c = letterChoice.getValue();
//                    int i = indexChoice.getValue();
//                    int succ = game.nextTurn(c, i);
//                    game.findPoints(succ, i, c, game.mapList, game.candidateWords.size());
//
//                    pointsText.setText(String.valueOf(game.points));;
//                    successText.setText(String.valueOf(game.success));;
//
//                    //Update hidden word and image
//                    updateActiveWord();
//                    updateTable();
//                    String img = "file:.\\images\\" + String.valueOf(game.faults+1) + ".png";
//                    Image image = new Image(img);
//                    imageView.setImage(image);
//                    //System.out.print(game.hiddenWord);
//
//                    letterChoice.getItems().clear();
//                    for(char ch: game.activeChoices[i]) {
//                        letterChoice.getItems().add(ch);
//                    }
//                    letterChoice.setDisable(false);
//
//                    gameEnded = true;
//
//                    isOver = game.gameOver();
//                    if(isOver == 1) {
//                        try {
//                            Alert alert = new Alert(AlertType.INFORMATION);
//                            alert.setTitle("WINNER WINNER CHICKEN DINNER");
//                            alert.setHeaderText("You won.");
//                            String cntText = "Points: " + pointsText.getText() + "\n" + "Hits: " + successText.getText();
//                            alert.setContentText(cntText);
//                            alert.showAndWait();
//
//                            String filename = ".\\src\\application\\History.txt";
//                            FileWriter myWriter = new FileWriter(filename, true);
//                            String hiddenword = (String) game.hiddenWord;
//                            int tries = game.success + game.faults;
//                            String numberOfTries = String.valueOf(tries);
//                            String winner = "Player";
//                            myWriter.write(hiddenword);
//                            myWriter.write("\n");
//                            myWriter.write(numberOfTries);
//                            myWriter.write("\n");
//                            myWriter.write(winner);
//                            myWriter.write("\n");
//
//                            myWriter.close();
//
//                        }
//                        catch (IOException e) {
//                            System.out.println(e.getMessage());
//                        }
//                    }
//                    else if (isOver == -1) {
//                        try {
//                            String filename = ".\\src\\application\\History.txt";
//                            FileWriter myWriter = new FileWriter(filename, true);
//                            String hiddenword = (String) game.hiddenWord;
//                            int tries = game.success + game.faults;
//                            String numberOfTries = String.valueOf(tries);
//                            String winner = "Computer";
//                            myWriter.write(hiddenword);
//                            myWriter.write("\n");
//                            myWriter.write(numberOfTries);
//                            myWriter.write("\n");
//                            myWriter.write(winner);
//                            myWriter.write("\n");
//
//                            myWriter.close();
//                        }
//                        catch (IOException e) {
//                            System.out.println(e.getMessage());
//                        }
//                    }
//                }
//
//
//            }


        } catch(NullPointerException e) {

        }
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
    private void startAction(ActionEvent event) {
        start();
    }

    private void start() {
        try{
//            game = new Game(this.dictID);
            solution = false;

            letterChoice.getItems().clear();
            indexChoice.getItems().clear();
//            tableChoices.getColumns().clear();

            //Update Informations
            dictText.setText(this.dictID);
//            numWordsText.setText(String.valueOf(game.num_words));
            pointsText.setText("0");;
            successText.setText("0");;

            // Update table with choices
//            game.sortChoices();
//            List<Choices> myList = new ArrayList<Choices>();
//            for(int i=0; i < game.hiddenWord.length(); i++) {
//                String choices = ListToString(game.activeChoices[i]);
//                myList.add(new Choices(i, choices));
//                indexChoice.getItems().add(i);
//            }
//            indexCol = new TableColumn<Choices, Integer>("Index");
//            indexCol.setCellValueFactory(new PropertyValueFactory<Choices, Integer>("index"));
//            choicesCol = new TableColumn<Choices, String>("Sorted Choices");
//            choicesCol.setCellValueFactory(new PropertyValueFactory<Choices, String>("choices"));
//
//            ObservableList<Choices> myObservableList = FXCollections.observableList(myList);
//            tableChoices.setItems(myObservableList);
//            tableChoices.getColumns().addAll(indexCol, choicesCol);

            //Update hidden word and image
            updateActiveWord();
            Image image = new Image("file:.\\images\\1.png");
            imageView.setImage(image);

            indexChoice.setValue(0);



        } catch(NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error starting the game");
            alert.setHeaderText("No dictionary defined");
            alert.setContentText("Please load or create a dictionary in order to start a new game!");
            alert.showAndWait();
        }
    }

    private void updateActiveWord() {
        String word = "";
        int i =0;
//        for(char letter: game.activeWord) {
//            if (letter == 0) word += "_";
//            else word += letter;
//            if (i != game.hiddenWord.length())
//                word += " ";
//            i+=1;
//        }
        hiddenWordText.setText(word);
    }

    private void updateTable() {
//        game.sortChoices();
//        List<Choices> myList = new ArrayList<Choices>();
//        for(int i=0; i < game.hiddenWord.length(); i++) {
//            String choices = ListToString(game.activeChoices[i]);
//            myList.add(new Choices(i, choices));
//        }
//        indexCol = new TableColumn<Choices, Integer>("Index");
//        indexCol.setCellValueFactory(new PropertyValueFactory<Choices, Integer>("index"));
//        choicesCol = new TableColumn<Choices, String>("Sorted Choices");
//        choicesCol.setCellValueFactory(new PropertyValueFactory<Choices, String>("choices"));
//
//        ObservableList<Choices> myObservableList = FXCollections.observableList(myList);
//        tableChoices.setItems(myObservableList);
    }

    @FXML
    private void loadAction(ActionEvent event) {
        try {
            Stage stage = new Stage ();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/LoadPopup.fxml"));
            Parent root = loader.load();
            stage.setTitle("MediaLab Hangman");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

//            ControllerLoadPopup popup = loader.<ControllerLoadPopup>getController();
//            popup.initialize();
//            stage.showAndWait();
//
//            dictID = popup.getDictID();
//            if (dictID != null)
//                start();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createAction(ActionEvent event) {
        try {
            Stage stage = new Stage ();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/CreatePopup.fxml"));
            Parent root = loader.load();
            stage.setTitle("MediaLab Hangman");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            stage.close();
//            ControllerCreatePopup popup = loader.getController();
//            dictID = popup.getDictID();
//            libID = popup.getLibID();
            if (dictID != null && libID != null)
                start();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void dictAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/DictionaryPopup.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("MediaLab Hangman");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
//            ControllerDictionaryPopup popup = loader.<ControllerDictionaryPopup>getController();
//            popup.initialize(game.activeDict);
            stage.show();
        } catch(NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No dictionary defined");
            alert.setContentText("Please load or create a dictionary in order to view its information!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void roundsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("application/RoundsPopup.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("MediaLab Hangman");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
//            ControllerRoundsPopup popup = loader.<ControllerRoundsPopup>getController();
//            popup.initialize();
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void solutionAction(ActionEvent event) {

        try {
            if (solution == false) {
                solution = true;
//                for(int i=0; i < game.hiddenWord.length(); i++) {
//                    game.activeWord[i] = game.hiddenWord.charAt(i);
//                }
                updateActiveWord();
                updateTable();
                String img = "file:.\\images\\" + String.valueOf(7) + ".png";
                Image image = new Image(img);
                imageView.setImage(image);

                String filename = ".\\src\\application\\History.txt";
                FileWriter myWriter = new FileWriter(filename, true);
//                String hiddenword = (String) game.hiddenWord;
//                int tries = game.success + game.faults;
//                String numberOfTries = String.valueOf(tries);
//                String winner = "Computer";
//                myWriter.write(hiddenword);
//                myWriter.write("\n");
//                myWriter.write(numberOfTries);
//                myWriter.write("\n");
//                myWriter.write(winner);
//                myWriter.write("\n");

                myWriter.close();
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No game has started yet");
            alert.setContentText("There is NO solution to \"NO game\"...");
            alert.showAndWait();
        }

    }
    @FXML
    private void indexBoxChanged(ActionEvent event) {

        indexChoice.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                letterChoice.getItems().clear();
                letterChoice.setDisable(true);
            } else {
                letterChoice.getItems().clear();
                int index = indexChoice.getValue();
//                for(char c: game.activeChoices[index]) {
//                    letterChoice.getItems().add(c);
//                }
                letterChoice.setDisable(false);

            }

        });
    }

}
