package com.example.connectionmysql;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.io.File;
import static java.awt.SystemColor.desktop;
import static javafx.application.Application.launch;
import static jdk.jfr.consumer.EventStream.openFile;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


public class CharacterView extends Application{

    //Stage selectStage = new Stage();
    CharacterController character = new CharacterController(this);
    ComboBox combo = new ComboBox<>();

    Label explainLabel = new Label();

    Alert alert = new Alert(Alert.AlertType.WARNING);

    FileInputStream inputstream = new FileInputStream("/Users/bruno.andrade/Documents/IdeProjects/CharacterBuilder/src/main/resources/media/lowpolybasemeshgenericcharacter.png");
    Image image = new Image(inputstream);
    ImageView imageView = new ImageView(image);

    Button loadChar = new Button ("Load character sheet");
    Button create = new Button("New Character");
    Button createItem = new Button("Item Editor");
    Button exit = new Button("Exit");
    VBox mainPane = new VBox();

    Stage stage = new Stage();
    CharacterCreation creation = new CharacterCreation(this);
    CharacterLoader loader = new CharacterLoader(this);
    CharacterSheet sheet = new CharacterSheet(this);
    ItemCreator itemcreator = new ItemCreator(this);


    //itemLoader itemLoader = new itemLoader(this);


    public CharacterView() throws FileNotFoundException {
    }


    @Override
    public void start(Stage stage) throws IOException {



        loadChar.setPrefWidth(150);
        create.setPrefWidth(120);
        createItem.setPrefWidth(120);
        exit.setPrefWidth(80);
        mainPane.getChildren().addAll(loadChar, create, createItem,exit);

        mainPane.setAlignment(Pos.CENTER);

        loadChar.setOnAction(e->character.loadLoader());
        create.setOnAction(e->character.loadChar());
        loader.listsave.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> ov,
                                String old_val, String new_val) {
                character.setdbCharName(loader.listsave.getSelectionModel().getSelectedItem());
            }
        });
        loader.loadButton.setOnAction(e-> character.loadSheet());
        //itemcreator.listsaveCreator.getItems().clear();
        createItem.setOnAction(e->character.loadCreator());
        creation.plusButton.setOnAction(e->character.loadPopup());


       // exit.setOnAction((ActionEvent event) -> {
        //    Platform.exit();
        //});
        //exit.setOnAction(e-> character.loadInventory());



        Scene root = new Scene(mainPane, 200, 200);
        stage.setTitle("Hello!");
        stage.setScene(root);
        stage.show();
    }




    public static void main(String[] args) {
        launch();
    }

   // public Stage


}


