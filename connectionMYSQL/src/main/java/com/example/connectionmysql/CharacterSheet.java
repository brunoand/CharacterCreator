package com.example.connectionmysql;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

public class CharacterSheet {

    Stage sheetStage = new Stage();
    BorderPane pane = new BorderPane();
    TextArea dbNames = new TextArea();
    GridPane leftGrid = new GridPane();
    GridPane topLeftGrid = new GridPane();
    GridPane middleLeftGrid = new GridPane();
    GridPane bottomGrid = new GridPane();
    GridPane centerGrid= new GridPane();
    GridPane topCenterGrid = new GridPane();
    GridPane centerCenterGrid = new GridPane();
    GridPane bottomCenterGrid = new GridPane();

    GridPane rightGrid = new GridPane();

    GridPane topRightGrid = new GridPane();
    GridPane middleRightGrid = new GridPane();
    GridPane bottomRightGrid = new GridPane();



    ImageView avatar = new ImageView();
    Label avatarLabel = new Label("Name: ");
    Label raceLabel = new Label("Race: ");
    Label classLabel = new Label("Class: ");
    Label hitLabel = new Label("HitPoints:");
    Label armorLabel = new Label("AC: ");
    Label acLabel = new Label();
    Label statusLabel = new Label("Status:");
    Label inventoryLabel = new Label("Inventory:");
    Label skillsLabel = new Label("Skills");
    Label proficiencyLabel = new Label("Proficiency");
    Label promptLabel = new Label("Prompt:");
    Label equipmentLabel = new Label("Equipment");

    Label weaponLabel = new Label("Weapon: ");
    Label armorlabel = new Label("Armor: ");
    Label shieldLabel = new Label("Shield: ");
    Label rangedLabel = new Label("ranged: ");
    //ComboBox<itemModel> weaponBox = new ComboBox<>();
    Label weaponBox = new Label();
    Label armorBox = new Label();
    Label shieldBox = new Label();
    Label rangedBox = new Label();


    public ObservableList<Item> inventorydata = FXCollections.observableArrayList();

    TableView<Item> listsInventory= new TableView<>();

    public static final ObservableList skillnames =
            FXCollections.observableArrayList();
    public ObservableList skilldata =
            FXCollections.observableArrayList();

    ListView<String> listsSkills= new ListView<>(skilldata);

    public static final ObservableList proficiencynames =
            FXCollections.observableArrayList();
    public static final ObservableList proficiencydata =
            FXCollections.observableArrayList();

    ListView<String> listsProficiency= new ListView<>(proficiencydata);


    public static final ObservableList promptnames =
            FXCollections.observableArrayList();
    public static final ObservableList promptdata =
            FXCollections.observableArrayList();

    ListView<String> listsPrompt = new ListView<>(promptdata);

    Label levelLabel = new Label("Level: ");
    Label Str = new Label("STR:");
    Label Con = new Label("CON:");
    Label Int = new Label("INT:");
    Label Dex = new Label("DEX:");
    Label Char = new Label("CHAR:");
    Label Wis = new Label("WIS:");

    Label bonusStr = new Label();
    Label bonusCon = new Label();
    Label bonusInt = new Label();
    Label bonusDex = new Label();
    Label bonusChar = new Label();
    Label bonusWis = new Label();

    Label Attack = new Label("Attack");
    Label Initiative = new Label("Initiative");
    Label Check = new Label("Check");


    RadioButton radioStr = new RadioButton();
    RadioButton radioCon = new RadioButton();
    RadioButton radioDex = new RadioButton();
    RadioButton radioInt = new RadioButton();
    RadioButton radioWis = new RadioButton();
    RadioButton radioChar = new RadioButton();

    RadioButton radioAttack = new RadioButton();
    RadioButton radioCheck = new RadioButton();
    RadioButton radioInitiative = new RadioButton();

    ToggleGroup statusGroup = new ToggleGroup();
    ToggleGroup attackGroup = new ToggleGroup();

    Button roll = new Button("Roll");

    HBox hStr = new HBox();
    HBox hCon = new HBox();
    HBox hDex = new HBox();
    HBox hInt = new HBox();
    HBox hWis = new HBox();
    HBox hChar = new HBox();
    HBox hCheck = new HBox();
    HBox hAttack = new HBox();
    HBox hInitiative = new HBox();




    Stage stage;
    CharacterController character;


    public CharacterSheet(CharacterView view) throws FileNotFoundException {
        this.stage = view.stage;
        this.character = view.character;
    }

    public void characterSheet(){

        //Setting the position of the image
        avatar.setX(50);
        avatar.setY(25);
        //setting the fit height and width of the image view
        avatar.setFitHeight(150);
        avatar.setFitWidth(150);


        //setting the Hboxes
        hStr.getChildren().addAll(radioStr, Str, bonusStr);
        hCon.getChildren().addAll(radioCon, Con, bonusCon);
        hDex.getChildren().addAll(radioDex, Dex, bonusDex);
        hInt.getChildren().addAll(radioInt, Int, bonusInt);
        hWis.getChildren().addAll(radioWis, Wis, bonusWis);
        hChar.getChildren().addAll(radioChar, Char, bonusChar);

        //Turning radiobuttons invisible
        radioStr.setVisible(false); radioCon.setVisible(false); radioDex.setVisible(false);
        radioInt.setVisible(false); radioWis.setVisible(false); radioChar.setVisible(false);

        //setting tooglegroups;
        radioStr.setToggleGroup(statusGroup);
        radioCon.setToggleGroup(statusGroup);
        radioDex.setToggleGroup(statusGroup);
        radioInt.setToggleGroup(statusGroup);
        radioWis.setToggleGroup(statusGroup);
        radioChar.setToggleGroup(statusGroup);

        radioAttack.setToggleGroup(attackGroup);
        radioInitiative.setToggleGroup(attackGroup);
        radioCheck.setToggleGroup(attackGroup);

        //Creating columns for the inventory

        TableColumn itemname = new TableColumn("Item name");
        itemname.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));

        TableColumn type = new TableColumn("Type");
        type.setCellValueFactory(new PropertyValueFactory<String, String>("itemType"));
        TableColumn value = new TableColumn("Value");
        value.setCellValueFactory(new PropertyValueFactory<String, String>("itemValue"));

        listsInventory.getColumns().addAll(itemname, type, value);
        listsInventory.setItems(inventorydata);

        //setting listview sizes
        listsInventory.setPrefHeight(200);
        listsSkills.setPrefHeight(150);
        listsSkills.setPrefWidth(200);
        listsProficiency.setPrefHeight(150);
        listsProficiency.setPrefWidth(200);
        listsPrompt.setPrefHeight(200);
        listsPrompt.setPrefWidth(200);

        //Setting button sizes
        roll.setPrefHeight(60);
        roll.setPrefWidth(50);

        //Setting up Grids
        topLeftGrid.add(avatar,0,1);topLeftGrid.add(hitLabel,0,2);topLeftGrid.add(levelLabel, 0,3);
        middleLeftGrid.add(radioAttack,0,1);middleLeftGrid.add(Attack,1,1);middleLeftGrid.add(roll,2,0,3,3);
        middleLeftGrid.add(radioInitiative,0,0);middleLeftGrid.add(Initiative,1,0);
        middleLeftGrid.add(radioCheck,0,2);middleLeftGrid.add(Check,1,2);

        leftGrid.add(topLeftGrid,0,0); leftGrid.add(middleLeftGrid,0,1);

        topCenterGrid.add(avatarLabel, 0,1); topCenterGrid.add(raceLabel, 1,1);
        topCenterGrid.add(classLabel, 0,2); topCenterGrid.add(armorLabel, 1,2); topCenterGrid.add(acLabel,2,2);
        centerCenterGrid.add(statusLabel,0,0);
        centerCenterGrid.add(hStr, 0,1);centerCenterGrid.add(hCon, 1,1);centerCenterGrid.add(hDex, 2,1);
        centerCenterGrid.add(hInt, 0,2);centerCenterGrid.add(hWis, 1,2);centerCenterGrid.add(hChar, 2,2);
        bottomCenterGrid.add(inventoryLabel,0,0);
        bottomCenterGrid.add(listsInventory,0,1);
        //bottomCenterGrid.add(promptLabel, 0,2); bottomCenterGrid.add(listsPrompt, 0,3);

        centerGrid.add(topCenterGrid,0,0); centerGrid.add(centerCenterGrid, 0,1);centerGrid.add(bottomCenterGrid,0,3);

        //topRightGrid.add(skillsLabel,0,0); topRightGrid.add(listsSkills,0,1);
        topRightGrid.add(equipmentLabel,0,0);
        topRightGrid.add(weaponLabel, 0,1);topRightGrid.add(weaponBox,1,1); topRightGrid.add(shieldLabel, 2,1);topRightGrid.add(shieldBox,3,1);
        topRightGrid.add(armorlabel,0,2); topRightGrid.add(armorBox, 1,2);topRightGrid.add(rangedLabel,2,2);topRightGrid.add(rangedBox,3,2);
        middleRightGrid.add(proficiencyLabel,0,0); middleRightGrid.add(listsProficiency, 0,1);

        rightGrid.add(topRightGrid,0,0); rightGrid.add(middleRightGrid,0,1);

        //bottomGrid.add(promptLabel,0,0);bottomGrid.add(listsPrompt,0,1);

        //GridPane.setHalignment(listsPrompt, HPos.CENTER);
        //Event handlers
        //radioAttack.setOnAction(e-> character.rollAttack(8,4));


        /*listsInventory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> ov,
                                String old_val, String new_val) {
                //character.checkType(listsInventory.getSelectionModel().getSelectedItem().getItemName());
                System.out.println(listsInventory.getSelectionModel().getSelectedItem().getItemType());
            }
        });*/
        listsInventory.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        character.checkType(listsInventory.getSelectionModel().getSelectedItem());
                        //character.copyItem();
                    }
                }
            }

        });



        radioCheck.setOnAction(e-> character.rollCheck());
        //radioInitiative.setOnAction(e-> character.rollInitiative());
        roll.setOnAction(e->character.rollDice());

        pane.setLeft(leftGrid);
        pane.setCenter(centerGrid);
        pane.setRight(rightGrid);
        //pane.setBottom(bottomGrid);


        Scene scene = new Scene(pane, 690, 520);
        sheetStage.setTitle("Character sheet");
        sheetStage.setScene(scene);
        sheetStage.show();

    }

}
