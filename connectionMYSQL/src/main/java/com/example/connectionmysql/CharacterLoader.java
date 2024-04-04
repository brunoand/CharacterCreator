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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.io.File;
import static java.awt.SystemColor.desktop;
import static javafx.application.Application.launch;
import static jdk.jfr.consumer.EventStream.openFile;
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

public class CharacterLoader {

    Stage loadstage = new Stage();
    VBox loadpane = new VBox();
    CharacterView view;

    public static final ObservableList names =
            FXCollections.observableArrayList();
    public static final ObservableList data =
            FXCollections.observableArrayList();

    ListView<String> listsave= new ListView<>(data);
    Button loadButton = new Button("Load");

    public CharacterLoader(CharacterView view){
        this.view = view;

    }
    public void characterLoader(){
        listsave.setPrefHeight(200);
        listsave.setPrefWidth(150);
        loadpane.getChildren().addAll(listsave, loadButton);
        Scene scene = new Scene(loadpane, 300, 300);
        loadstage.setTitle("Character sheet");
        loadstage.setScene(scene);
        loadstage.show();

    }
}
