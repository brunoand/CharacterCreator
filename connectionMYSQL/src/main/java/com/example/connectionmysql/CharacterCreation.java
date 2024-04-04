package com.example.connectionmysql;

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

public class CharacterCreation{

    Stage selectStage = new Stage();
    //CharacterController character = new CharacterController();
    ComboBox combo = new ComboBox<>();
    ComboBox classCombo = new ComboBox();
    BorderPane border = new BorderPane();
    Button rollButton = new Button("Roll");
    Label Str = new Label("STR:");
    Label Con = new Label("CON:");
    Label Int = new Label("INT:");
    Label Dex = new Label("DEX:");
    Label Char = new Label("CHAR:");
    Label Wis = new Label("WIS:");
    Label strBonus = new Label("0");
    Label conBonus = new Label("0");
    Label intBonus = new Label("0");
    Label dexBonus = new Label("0");
    Label charBonus = new Label("0");
    Label wisBonus = new Label("0");
    Label modifier = new Label("Modifier:");
    Label strMod = new Label("0");
    Label conMod = new Label("0");
    Label intMod = new Label("0");
    Label dexMod = new Label("0");
    Label charMod = new Label("0");
    Label wisMod = new Label("0");
    Label explainLabel = new Label();
    Label goldLabel = new Label("Gold: ");
    TextField gold = new TextField();
    Label equipmentLabel = new Label("Equipment");
    Label weaponLabel = new Label("Weapon: ");
    Label shieldLabel = new Label("Shield: ");
    Label armorLabel = new Label("Armor: ");
    Label rangedLabel = new Label("Ranged: ");
    Label diceLabel = new Label("Hit dice:");
    Label levelLabel = new Label("Level: ");
    TextField charName = new TextField();
    TextField strField = new TextField();
    TextField conField = new TextField();
    TextField intField = new TextField();
    TextField dexField = new TextField();
    TextField charField = new TextField();
    TextField wisField = new TextField();
    TextField hitField = new TextField();
    TextField level = new TextField();
    Label instructionsLabel = new Label("Instructions");
    TextArea instructions = new TextArea("The instructions are as follow:\n" +
            "1 - define a name for your Character, then select a class and race.\n" +
            "2 - Select how you want to define your Status.\n" +
            "3 - Select your items and roll for your HitDice." +
            "4 - Don't forget to add a backstory!!!");

    Label descriptionLabel = new Label("Character description:");
    TextArea description = new TextArea();
    Label proficiencyLabel = new Label("Class proficiency:");
    Label profiencyRacialLabel = new Label("Racial proficiency: ");
    Label inventoryLabel = new Label("Inventory:");

    String selectedItem;


    public static final ObservableList proficiencynames =
            FXCollections.observableArrayList();
    public static final ObservableList proficiencyClasslist =
            FXCollections.observableArrayList();

    ListView<String> listClassProficiency= new ListView<>(proficiencyClasslist);


    public static final ObservableList proficiencyRaciallist =
            FXCollections.observableArrayList();

    ListView<String> listRacialProficiency= new ListView<>(proficiencyRaciallist);

    public static final ObservableList inventorylist =
            FXCollections.observableArrayList();

    public final ObservableList namesCreator =
            FXCollections.observableArrayList();
    public final ObservableList dataCreator =
            FXCollections.observableArrayList();

    ListView<String> listsaveCreator= new ListView<>(dataCreator);

    ListView<String> inventory= new ListView<>(inventorylist);

    TextArea proficiency = new TextArea();
    Alert alert = new Alert(Alert.AlertType.WARNING);

    TabPane tab = new TabPane();
    RadioButton selectStatus = new RadioButton();
    RadioButton randomStatus = new RadioButton();
    Label selectLabel = new Label("I want to select it myself");
    Label randomLabel = new Label("I'm feeling lucky");
    ToggleGroup group = new ToggleGroup();
    Button downSTR = new Button();
    Button upSTR = new Button();
    Button upCON = new Button();
    Button downCON = new Button();
    Button upINT= new Button();
    Button downINT= new Button();
    Button upDEX= new Button();
    Button downDEX= new Button();
    Button upWIS= new Button();
    Button downWIS= new Button();
    Button upCHAR = new Button();
    Button downCHAR= new Button();
    Button validateButton = new Button("Save");
    Button clearButton = new Button("Clear");
    Button hitButton = new Button("Hit!!!");
    Button selectButton = new Button("Open");
    Label validateLabel = new Label();
    Button plusButton = new Button("+");
    Button minusButton = new Button("-");
    Button randomButton = new Button("Press me");
    Button addPopup = new Button("Add");
    final FileChooser fileChooser = new FileChooser();

    VBox Vstr = new VBox();
    VBox Vcon = new VBox();
    VBox Vint = new VBox();
    VBox Vdex = new VBox();
    VBox Vwis = new VBox();
    VBox Vchar = new VBox();

    HBox plusminusbuttons = new HBox();
    //HBox saveBox = new HBox():
    FileInputStream inputstream = new FileInputStream("/Users/bruno.andrade/Documents/IdeProjects/CharacterBuilder/src/main/resources/media/lowpolybasemeshgenericcharacter.png");
    Image image = new Image(inputstream);
    ImageView imageView = new ImageView(image);

    CharacterController character;
    Stage stage;


    public CharacterCreation(CharacterView view) throws FileNotFoundException {
        this.stage = view.stage;
        this.character = view.character;
    }

    public void popupItems(){
        Stage popupstage = new Stage();

        listsaveCreator.setPrefHeight(100);
        listsaveCreator.setPrefWidth(145);

        VBox popupBox = new VBox();
        popupBox.getChildren().addAll(listsaveCreator, addPopup);


        listsaveCreator.setOnMouseClicked(new EventHandler<MouseEvent>(){


            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        character.setitemCharName(listsaveCreator.getSelectionModel().getSelectedItem());
                        character.copyItem();
                    }
                }
            }

        });

        Scene popupscene = new Scene(popupBox);
        popupstage.setScene(popupscene);

        //This will hide the popup
        popupstage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (! isNowFocused) {
                popupstage.hide();
            }});


        popupstage.show();

    }

    public void characterCreation(){

        Stage newStage = new Stage();

        combo.getItems().add("Human");
        combo.getItems().add("Dwarf");
        combo.getItems().add("Halfling");
        combo.getItems().add("Elf");
        combo.setPromptText("Select Race");
        combo.setOnAction(e->character.comboChange());

        classCombo.getItems().add("Fighter");
        classCombo.getItems().add("Paladin");
        classCombo.getItems().add("Mage");
        classCombo.getItems().add("Priest");
        classCombo.getItems().add("Thief");
        classCombo.setPromptText("Select Class");
        classCombo.setOnAction(e->character.comboClass());

        HBox classRace = new HBox();
        classRace.getChildren().addAll(combo, classCombo);
        upSTR.setStyle("-fx-pref-height:5px");
        strField.setPrefWidth(30);
        conField.setPrefWidth(30);
        intField.setPrefWidth(30);
        dexField.setPrefWidth(30);
        wisField.setPrefWidth(30);
        charField.setPrefWidth(30);
        hitField.setPrefWidth(40);
        gold.setPrefWidth(40);

        description.setPrefWidth(300);
        description.setPrefHeight(100);
        proficiency.setPrefWidth(100);
        proficiency.setPrefHeight(100);


        upSTR.setMinSize(10,10); upSTR.setPrefSize(20,10);downSTR.setMinSize(10,10); downSTR.setPrefSize(20,10);
        upCON.setMinSize(10,10); upCON.setPrefSize(20,10);downCON.setMinSize(10,10); downCON.setPrefSize(20,10);
        upINT.setMinSize(10,10); upINT.setPrefSize(20,10);downINT.setMinSize(10,10); downINT.setPrefSize(20,10);
        upDEX.setMinSize(10,10); upDEX.setPrefSize(20,10);downDEX.setMinSize(10,10); downDEX.setPrefSize(20,10);
        upWIS.setMinSize(10,10); upWIS.setPrefSize(20,10);downWIS.setMinSize(10,10); downWIS.setPrefSize(20,10);
        upCHAR.setMinSize(10,10); upCHAR.setPrefSize(20,10);downCHAR.setMinSize(10,10); downCHAR.setPrefSize(20,10);

        upSTR.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\";");downSTR.setStyle("-fx-shape: \"M150 0 L75 -200 L225 -200 Z\";");
        upCON.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\";");downCON.setStyle("-fx-shape: \"M150 0 L75 -200 L225 -200 Z\";");
        upINT.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\";");downINT.setStyle("-fx-shape: \"M150 0 L75 -200 L225 -200 Z\";");
        upDEX.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\";");downDEX.setStyle("-fx-shape: \"M150 0 L75 -200 L225 -200 Z\";");
        upWIS.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\";");downWIS.setStyle("-fx-shape: \"M150 0 L75 -200 L225 -200 Z\";");
        upCHAR.setStyle("-fx-shape: \"M150 0 L75 200 L225 200 Z\";");downCHAR.setStyle("-fx-shape: \"M150 0 L75 -200 L225 -200 Z\";");

        //alert


        //.setPrefSize(1,1);
        Vstr.getChildren().addAll(upSTR, downSTR);
        Vcon.getChildren().addAll(upCON, downCON);
        Vint.getChildren().addAll(upINT, downINT);
        Vdex.getChildren().addAll(upDEX, downDEX);
        Vwis.getChildren().addAll(upWIS, downWIS);
        Vchar.getChildren().addAll(upCHAR, downCHAR);
        //Vstr.setPrefHeight();


        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);
        //setting the fit height and width of the image view
        imageView.setFitHeight(90);
        imageView.setFitWidth(90);
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        VBox imageBox = new VBox();
        //HBox charHBox = new HBox();
        Label avatarLabel = new Label("Avatar:");
        imageBox.getChildren().addAll(avatarLabel,imageView,selectButton);

        //VBox descriptionBox = new VBox();
        //descriptionBox.getChildren().addAll(descriptionLabel, description);

        //setting listview sizes

        listClassProficiency.setPrefHeight(100);
        listClassProficiency.setPrefWidth(145);
        listRacialProficiency.setPrefHeight(100);
        listRacialProficiency.setPrefWidth(145);

        inventory.setPrefHeight(100);
        inventory.setPrefWidth(300);


        VBox proficiencyBox = new VBox();
        proficiencyBox.getChildren().addAll(proficiencyLabel, listClassProficiency);

        VBox proficiencyBox2 = new VBox();
        proficiencyBox2.getChildren().addAll(profiencyRacialLabel, listRacialProficiency);

        Label nameLabel = new Label("Name: ");
        Label characterLabel=new Label("Character definition:");

        Label bonusLabel = new Label("Bonus:");
        Label statusLabel = new Label("Status:");
        GridPane centerBox = new GridPane();
        GridPane topCenterBox = new GridPane();
        GridPane middleCenterBox = new GridPane();
        GridPane bottomCenterBox = new GridPane();


        GridPane leftBox = new GridPane();
        GridPane topLeftBox = new GridPane();
        GridPane middleLeftBox = new GridPane();
        GridPane bottomLeftBox = new GridPane();
        GridPane rightBox = new GridPane();
        GridPane topRightBox = new GridPane();
        GridPane middleRightBox = new GridPane();
        GridPane bottomRightBox = new GridPane();

        GridPane choiceBox = new GridPane();
        GridPane rightBottomBox = new GridPane();
        GridPane centerBottomBox = new GridPane();



        selectStatus.setToggleGroup(group);
        randomStatus.setToggleGroup(group);

        selectStatus.setOnAction(e-> character.changeStats("Select"));
        randomStatus.setOnAction(e-> character.changeStats("Random"));


        plusminusbuttons.getChildren().addAll(plusButton,minusButton);


        //charBox.add(imageView,0,0);


        Vstr.setVisible(false); Vcon.setVisible(false); Vint.setVisible(false);
        Vdex.setVisible(false); Vwis.setVisible(false); Vchar.setVisible(false);

        topCenterBox.add(characterLabel,0,0,2,1);
        topCenterBox.add(nameLabel,0,1);topCenterBox.add(charName,1,1);
        topCenterBox.add(classRace, 1, 2);

        middleCenterBox.add(descriptionLabel,0,0); middleCenterBox.add(description,0,1);
        middleCenterBox.add(inventoryLabel,0,3); middleCenterBox.add(inventory,0,4);
        middleCenterBox.add(plusminusbuttons,0,5);
        middleCenterBox.add(levelLabel,0,6); middleCenterBox.add(level, 1,6);
        bottomCenterBox.add(proficiencyBox2, 0,0); bottomCenterBox.add(proficiencyBox,1,0);

        centerBox.add(topCenterBox,0,0);centerBox.add(middleCenterBox,0,1);centerBox.add(bottomCenterBox,0,2);

        GridPane.setValignment(statusLabel, VPos.TOP);
        GridPane.setHalignment(modifier,
                HPos.RIGHT);
        GridPane.setHalignment(bonusLabel,
                HPos.RIGHT);
        topCenterBox.setVgap(5);
        bottomCenterBox.setHgap(10);

        rollButton.setVisible(false);
        rollButton.setMaxSize(50, 60);
        rollButton.setMinSize(25, 35);
        rollButton.setPrefSize(30, 40);
        rollButton.setOnAction(e->character.rollStatus());
        rollButton.setStyle("-fx-shape: \"M23.6,0c-3.4,0-6.3,2.7-7.6,5.6C14.7,2.7,11.8,0,8.4,0C3.8,0,0,3.8,0,8.4c0,9.4,9.5,11.9,16,21.26.1-9.3,16-12.1,16-21.2C32,3.8,28.2,0,23.6,0z\";"+"-fx-background-color: \"#4DD0E1\";");

        upSTR.setOnAction(e->character.strIncrement(1));
        downSTR.setOnAction(e->character.strIncrement(-1));

        upCON.setOnAction(e->character.conIncrement(1));
        downCON.setOnAction(e->character.conIncrement(-1));

        upINT.setOnAction(e->character.intIncrement(1));
        downINT.setOnAction(e->character.intIncrement(-1));

        upDEX.setOnAction(e->character.dexIncrement(1));
        downDEX.setOnAction(e->character.dexIncrement(-1));

        upWIS.setOnAction(e->character.wisIncrement(1));
        downWIS.setOnAction(e->character.wisIncrement(-1));

        upCHAR.setOnAction(e->character.charIncrement(1));
        downCHAR.setOnAction(e->character.charIncrement(-1));

        // validateButton.setOnAction(e->character.validateStatus());
        validateButton.setOnAction(e->character.SaveStuff());
        //.setOnAction(e->character.clear());

        GridPane megaBox = new GridPane();


        //GridPane rightBox = new GridPane();
        Label statusLabel2 = new Label("How do you want to define your status?");

        topRightBox.add(statusLabel,0,0,2,1);
        topRightBox.add(statusLabel2,0,1,2,1);
        topRightBox.add(selectStatus, 0,2); topRightBox.add(selectLabel, 1,2);
        topRightBox.add(randomStatus,0,3); topRightBox.add(randomLabel, 1,3);
        topRightBox.add(rollButton, 0,4,2,2);
        topRightBox.setVgap(2);
        topRightBox.setHgap(1);
        GridPane.setHalignment(rollButton, HPos.CENTER);

        VBox rollExplain = new VBox();

        //rollExplain.getChildren().addAll(explainLabel, rollButton);


        middleRightBox.add(bonusLabel,2,2);middleRightBox.add(modifier,3,2);
        middleRightBox.add(Str, 0,3); middleRightBox.add(strField, 1,3); middleRightBox.add(Vstr,2,3); middleRightBox.add(strBonus, 3,3);middleRightBox.add(strMod, 4,3);
        middleRightBox.add(Con, 0,4); middleRightBox.add(conField, 1,4); middleRightBox.add(Vcon,2,4);middleRightBox.add(conBonus, 3,4);middleRightBox.add(conMod, 4,4);
        middleRightBox.add(Int, 0,5); middleRightBox.add(intField, 1,5); middleRightBox.add(Vint,2,5);middleRightBox.add(intBonus, 3,5);middleRightBox.add(intMod, 4,5);
        middleRightBox.add(Dex, 0,6); middleRightBox.add(dexField, 1,6); middleRightBox.add(Vdex,2,6);middleRightBox.add(dexBonus, 3,6);middleRightBox.add(dexMod, 4,6);
        middleRightBox.add(Wis, 0,7); middleRightBox.add(wisField, 1,7); middleRightBox.add(Vwis,2,7);middleRightBox.add(wisBonus, 3,7);middleRightBox.add(wisMod, 4,7);
        middleRightBox.add(Char, 0,8); middleRightBox.add(charField, 1,8); middleRightBox.add(Vchar,2,8);middleRightBox.add(charBonus, 3,8);middleRightBox.add(charMod, 4,8);
        //middleRightBox.add(validateButton, 0, 12); rightBottomBox.add(validateLabel, 1,12, 2,1);//rightBottomBox.add(clearButton,1,12);
        //bottomRightBox.add(instructionsLabel,0,0);
        //bottomRightBox.add(instructions,0,1);
        //instructions.setWrapText(true);
        //instructions.setPrefSize(200,100);
        /*bottomRightBox.add(equipmentLabel,0,0);
        bottomRightBox.add(weaponLabel, 0,1);
        bottomRightBox.add(shieldLabel, 1,1);
        bottomRightBox.add(armorLabel, 0,2);
        bottomRightBox.add(rangedLabel, 1,2);*/

        middleRightBox.setHgap(10);topCenterBox.setVgap(3);bottomRightBox.setVgap(20);bottomRightBox.setHgap(50);

        rightBox.add(topRightBox,0,0); rightBox.add(middleRightBox,0,1); rightBox.add(bottomRightBox, 0,2);
        //centerBox.add(topCenterBox,0,0); centerBox.add(middleCenterBox,0,1);

        //leftBox.add(imageView, 0, 0,3,1);
        // leftBox.add(selectButton, 0,1,2,1);
        topLeftBox.add(avatarLabel,0,0); topLeftBox.add(imageView,0,1); topLeftBox.add(selectButton,0,2);
        middleLeftBox.add(diceLabel,0,0);middleLeftBox.add(hitField,1,0);middleLeftBox.add(hitButton,2,0);
        middleLeftBox.add(goldLabel,0,4); middleLeftBox.add(gold, 1,4);
        bottomLeftBox.add(validateButton, 0,0);bottomLeftBox.add(validateLabel,0,1);
        validateLabel.setWrapText(true);
       // leftBox.add(diceLabel, 0,3);
        //leftBox.add(hitField, 0,4,2,1);
        //leftBox.add(hitButton, 0,5,2,1);
        leftBox.add(topLeftBox,0,0); leftBox.add(middleLeftBox, 0,1);leftBox.add(bottomLeftBox,0,2);
        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setHalignment(hitButton, HPos.CENTER);
        GridPane.setHalignment(selectButton, HPos.CENTER);
        leftBox.setVgap(5);


        inventory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> ov,
                                String old_val, String new_val) {
                selectedItem = inventory.getSelectionModel().getSelectedItem();
            }
        });

        minusButton.setOnAction(e-> character.removeItem(selectedItem));

        hitButton.setOnAction(e->character.roll());



        selectButton.setOnAction(e->character.imagehandle());









        megaBox.add(leftBox, 0,0); megaBox.add(centerBox, 1,0);megaBox.add(rightBox, 2,0);
       // megaBox.add(leftBox, 0,1);//megaBox.add(descriptionBox,1,1);megaBox.add(rightBottomBox, 2,1);
       // megaBox.add(proficiencyBox,1,2);megaBox.add(rollExplain, 2,2);
        //megaBox.add(rightBox, 2,1);
        megaBox.setHgap(20);




        VBox sceneBox = new VBox();
        //sceneBox.getChildren().addAll(charHBox, allbox);


        newStage.initOwner(stage);
        Scene scene = new Scene(megaBox, 750, 520);
        newStage.setTitle("Hello!");
        newStage.setScene(scene);
        newStage.show();



    }
}
