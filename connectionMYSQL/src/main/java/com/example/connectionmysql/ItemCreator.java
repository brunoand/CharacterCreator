package com.example.connectionmysql;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.io.File;

import javafx.scene.control.cell.ComboBoxListCell;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.cell.ComboBoxListCell;
public class ItemCreator {
    Stage creatorStage = new Stage();
    CharacterView view;
    TextField itemNameCreator = new TextField();
    TextField itemDamageCreator = new TextField();
    TextField itemLvlCreator = new TextField();
    TextField armorCreator = new TextField();
    TextField itemValue = new TextField();
    TextArea itemDescriptionCreator = new TextArea();

    ComboBox itemTypeCreator = new ComboBox();

    Label nameLabelCreator = new Label("Item name: ");
    Label damageLabelCreator = new Label("Dice: ");
    Label descriptionLabelCreator = new Label("Item description:");
    Label typeLabelCreator = new Label("Item type:");
    Label itemLabelCreator = new Label("Level: ");
    Label saveLabelCreator = new Label();
    Label listLabelCreator = new Label("Saved items:");
    Label armorClassCreator = new Label("Armor class:");
    Label itemValueLabel = new Label("Value");

    Button exitButtonCreator = new Button("Exit");
    Button saveButtonCreator = new Button("Save");
    Scene itemscene;

    public final ObservableList namesCreator =
            FXCollections.observableArrayList();
    public final ObservableList dataCreator =
            FXCollections.observableArrayList();

    ListView<String> listsaveCreator= new ListView<>(dataCreator);


    GridPane topGridCreator = new GridPane();
    GridPane middleGridCreator = new GridPane();
    GridPane bottomGridCreator = new GridPane();

    Stage stage;

    CharacterController character;
    public ItemCreator(CharacterView view){
        this.view = view;
        this.character = view.character;
        this.stage = view.stage;
    }


    public void itemCreator(){

        GridPane itemMaker = new GridPane();
        itemTypeCreator.getItems().add("Weapon");
        itemTypeCreator.getItems().add("Armor");
        itemTypeCreator.getItems().add("Shield");
        itemTypeCreator.getItems().add("Potion");
        itemTypeCreator.getItems().add("Item");
        itemTypeCreator.setPromptText("Select type:");
        //topGridCreator.getChildren().removeAll();
        topGridCreator.add(nameLabelCreator,0,0);
        topGridCreator.add(itemNameCreator, 1,0);
        topGridCreator.add(typeLabelCreator, 2,0);
        topGridCreator.add(itemTypeCreator,3,0);
        topGridCreator.add(itemLabelCreator,4,0);
        topGridCreator.add(itemLvlCreator,5,0);

        topGridCreator.add(damageLabelCreator,0,1);
        topGridCreator.add(itemDamageCreator,1,1);
        topGridCreator.add(armorClassCreator, 2,1);
        topGridCreator.add(armorCreator, 3,1);
        topGridCreator.add(itemValueLabel, 4,1);
        topGridCreator.add(itemValue, 5,1);



        middleGridCreator.add(descriptionLabelCreator,0,0);middleGridCreator.add(listLabelCreator,1,0);
        middleGridCreator.add(itemDescriptionCreator,0,1);middleGridCreator.add(listsaveCreator,1,1);

        bottomGridCreator.add(saveButtonCreator,0,0);
        bottomGridCreator.add(exitButtonCreator,1,0);
        bottomGridCreator.add(saveLabelCreator,2,0);

        itemMaker.add(topGridCreator,0,0);itemMaker.add(middleGridCreator,0,1);itemMaker.add(bottomGridCreator,0,2);


        //event handlers
        saveButtonCreator.setOnAction(e->character.saveItem());
        exitButtonCreator.setOnAction(e->creatorStage.close());
        //itemLoader.loadButton.setOnAction(e->character.loadItems());

        listsaveCreator.setOnMouseClicked(new EventHandler<MouseEvent>(){


            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        character.setitemCharName(listsaveCreator.getSelectionModel().getSelectedItem());
                        character.loadItems();
                    }
                }
            }

        });


        Scene itemscene = new Scene(itemMaker, 690, 520);

        creatorStage.setTitle("Item editor");
        creatorStage.setScene(itemscene);
        creatorStage.showAndWait();
    }
    public void display() {
        creatorStage.showAndWait();
        //return tfInput.getText();
    }

}

