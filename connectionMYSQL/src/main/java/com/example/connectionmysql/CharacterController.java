package com.example.connectionmysql;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.HashMap;
import javafx.scene.control.cell.ComboBoxListCell;

public class CharacterController implements Runnable{

    CharacterModel model;
    itemModel itemmodel;
    private int Charisma;
    private int Dexterity;
    private int Constitution;
    private int Intelligence;
    private int Wisdom;
    private int Strength;
    private int currentStatus;
    private int hitDice;
    private int attackDice;
    private int attackSide;
    private String dbCharName;
    private String dbitemName;
    private CharacterView view;
    private List<Integer> statusList = new ArrayList<>();
    private List<Integer> removedList = new ArrayList<>(6);
    private ArrayList<Integer> mainStatus = new ArrayList<>();
    private ArrayList<Integer> mainBonus = new ArrayList<>();
    private ArrayList<Integer> mainModifier = new ArrayList<>();

    private ArrayList<String> proficiencyClass = new ArrayList<>();
    private ArrayList<String> proficiencyRace = new ArrayList<>();
    private ArrayList<String> equipment = new ArrayList<>();
    //private ArrayList<itemModel> Inventory = new ArrayList<>();
    private HashMap<String, itemModel> Inventory = new HashMap<>();
    private Integer gold;
    private boolean select;
    private String uriImage;
    private String sql;
    private Object model1;

    private Integer armorAC = 0;
    private Integer shieldAC = 0;
    private Integer AC;


    public CharacterController(CharacterView view){
        this.Charisma = 0;
        this.Dexterity = 0;
        this.Constitution = 0;
        this.Intelligence = 0;
        this.Wisdom = 0;
        this.Strength = 0;
        this.view = view;
        this.AC = 0;
        //this.armorAC = 0;
        //this.shieldAC = 0;

        this.select=FALSE;
        this.statusList.add(0);
        this.statusList.add(8);
        this.statusList.add(10);
        this.statusList.add(12);
        this.statusList.add(13);
        this.statusList.add(14);
        this.statusList.add(15);
        this.removedList.add(99);
        this.removedList.add(99);
        this.removedList.add(99);
        this.removedList.add(99);
        this.removedList.add(99);
        this.removedList.add(99);

        this.mainStatus.add(0);
        this.mainStatus.add(0);
        this.mainStatus.add(0);
        this.mainStatus.add(0);
        this.mainStatus.add(0);
        this.mainStatus.add(0);
        this.mainModifier.add(0);
        this.mainModifier.add(0);
        this.mainModifier.add(0);
        this.mainModifier.add(0);
        this.mainModifier.add(0);
        this.mainModifier.add(0);
        this.mainBonus.add(0);
        this.mainBonus.add(0);
        this.mainBonus.add(0);
        this.mainBonus.add(0);
        this.mainBonus.add(0);
        this.mainBonus.add(0);
        this.model = new CharacterModel();
        this.itemmodel = new itemModel();
        //this.Inventory = new ArrayList<itemModel>;
    }

    public void run(){
        System.out.println("Thread has ended");
    }

    protected void loadChar(){

        try{
            view.creation.plusminusbuttons.getChildren().clear();
            view.creation.Vstr.getChildren().clear();
            view.creation.Vcon.getChildren().clear();
            view.creation.Vint.getChildren().clear();
            view.creation.Vdex.getChildren().clear();
            view.creation.Vwis.getChildren().clear();
            view.creation.Vchar.getChildren().clear();

            view.creation.combo.getItems().clear();
            view.creation.classCombo.getItems().clear();

            //view.creation.rightBottomBox.getChildren().clear();
            view.creation.strField.setText("0");
            view.creation.conField.setText("0");
            view.creation.intField.setText("0");
            view.creation.dexField.setText("0");
            view.creation.wisField.setText("0");
            view.creation.charField.setText("0");

            view.creation.strMod.setText("0");
            view.creation.conMod.setText("0");
            view.creation.intMod.setText("0");
            view.creation.dexMod.setText("0");
            view.creation.wisMod.setText("0");
            view.creation.charMod.setText("0");

            view.creation.strBonus.setText("0");
            view.creation.conBonus.setText("0");
            view.creation.intBonus.setText("0");
            view.creation.dexBonus.setText("0");
            view.creation.wisBonus.setText("0");
            view.creation.charBonus.setText("0");

            view.creation.charName.clear();
            view.creation.description.clear();
            view.creation.proficiency.clear();
            view.creation.hitField.clear();

            view.creation.selectStatus.setSelected(false);
            view.creation.randomStatus.setSelected(false);

            this.resetImage();
            view.creation.listsaveCreator.getItems().clear();
            view.combo.setPromptText("Test");
            view.creation.characterCreation();}
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }



    protected void loadLoader(){
        view.loader.characterLoader();

        ArrayList<String> keysWithTrueValues = new ArrayList<>(retrieveDBNames().keySet());

        for (int i = 0; i < keysWithTrueValues.size(); i++) {
            view.loader.names.addAll(keysWithTrueValues.get(i));
            view.loader.data.add(keysWithTrueValues.get(i));
        }
        view.loader.listsave.setItems(view.loader.data);
        view.loader.listsave.setCellFactory(ComboBoxListCell.forListView(view.loader.names));
    }




    protected void loadSheet(){
        view.loader.loadstage.close();
        view.sheet.characterSheet();
        this.setImage("/Users/bruno.andrade/Documents/IdeProjects/CharacterBuilder/src/main/resources/media/lowpolybasemeshgenericcharacter.png");
        CharacterModel newModel = deserializeObject(dbCharName);
        //System.out.println(newModel.getUri());
        try{
            this.setImage(newModel.getUri());
        }catch(NullPointerException e){
            this.setImage("/Users/bruno.andrade/Documents/IdeProjects/CharacterBuilder/src/main/resources/media/lowpolybasemeshgenericcharacter.png");
        }
        view.sheet.avatarLabel.setText("Name: " + newModel.getName());
        view.sheet.raceLabel.setText("Race: " + newModel.getCharRace());
        view.sheet.classLabel.setText("Class: " + newModel.getCharClass());
        view.sheet.hitLabel.setText("HitPoints: "+ newModel.getDice());
        view.sheet.Str.setText("Str: " + newModel.getStatus().get(0) + " +");
        view.sheet.Con.setText("Con: " + newModel.getStatus().get(1) + " +");
        view.sheet.Int.setText("Dex: " + newModel.getStatus().get(3) + " +");
        view.sheet.Dex.setText("Int: " + newModel.getStatus().get(2) + " +");
        view.sheet.Wis.setText("Wis: " + newModel.getStatus().get(4) + " +");
        view.sheet.Char.setText("Char: " + newModel.getStatus().get(5) + " +");
        view.sheet.bonusStr.setText(String.valueOf(newModel.getBonus().get(0) + newModel.getMod().get(0)));
        view.sheet.bonusCon.setText(String.valueOf(newModel.getBonus().get(1) + newModel.getMod().get(1)));
        view.sheet.bonusInt.setText(String.valueOf(newModel.getBonus().get(2) + newModel.getMod().get(2)));
        view.sheet.bonusDex.setText(String.valueOf(newModel.getBonus().get(3) + newModel.getMod().get(3)));
        view.sheet.bonusWis.setText(String.valueOf(newModel.getBonus().get(4) + newModel.getMod().get(4)));
        view.sheet.bonusChar.setText(String.valueOf(newModel.getBonus().get(5) + newModel.getMod().get(5)));
        for(itemModel k:newModel.getInventory()){
            view.sheet.inventorydata.add(new Item(k.getName(), k.getType(), k.getValue(), k.getDamage(), k.getAC()));
            System.out.println(k.getName());
        }
        //view.sheet.inventorydata.add(newModel.getInventory());
        //return(newModel);
    }

    protected void loadCreator(){

        view.itemcreator.topGridCreator.getChildren().clear();
        view.itemcreator.middleGridCreator.getChildren().clear();
        view.itemcreator.bottomGridCreator.getChildren().clear();
        view.itemcreator.listsaveCreator.getItems().clear();



       for (Object item: loadViewItems()){
            view.itemcreator.dataCreator.add(item);
        }
        view.itemcreator.listsaveCreator.setItems(view.itemcreator.dataCreator);
        view.itemcreator.listsaveCreator.setCellFactory(ComboBoxListCell.forListView(view.itemcreator.namesCreator));

        view.itemcreator.itemCreator();
    }

    protected void loadPopup(){

        for (Object item: loadViewItems()){
            view.creation.dataCreator.add(item);
        }
        view.creation.listsaveCreator.setItems(view.creation.dataCreator);
        view.creation.listsaveCreator.setCellFactory(ComboBoxListCell.forListView(view.creation.namesCreator));


        view.creation.popupItems();
    }

    protected ObservableList loadViewItems(){
        ObservableList dataCreator = FXCollections.observableArrayList();

        ArrayList<String> keysWithTrueValues = new ArrayList<>(retrieveItemNames().keySet());
        for (int i = 0; i < keysWithTrueValues.size(); i++) {
            dataCreator.add(keysWithTrueValues.get(i));
        }
        return(dataCreator);
    }

    protected void loadItems(){
        itemModel newitemModel = deserializeItem(dbitemName);
        view.itemcreator.itemNameCreator.setText(newitemModel.getName());
        view.itemcreator.itemDamageCreator.setText(newitemModel.getDamage());
        view.itemcreator.itemDescriptionCreator.setText(newitemModel.getDescription());
        view.itemcreator.itemTypeCreator.getSelectionModel().select(newitemModel.getType());
        view.itemcreator.itemLvlCreator.setText(newitemModel.getlvl());
        view.itemcreator.armorCreator.setText(newitemModel.getAC());

    }

    protected void checkType(Item item){
        System.out.println(item.getItemType());

        switch (item.getItemType()){
            case "Weapon":
                view.sheet.weaponBox.setText(item.getItemName());
                break;

            case "Shield":
                view.sheet.shieldBox.setText(item.getItemName());
                AC += Integer.parseInt(item.getItemAC());
                view.sheet.acLabel.setText(String.valueOf(AC));
                break;
            case "Armor":
                view.sheet.armorBox.setText(item.getItemName());
                AC += Integer.parseInt(item.getItemAC());
                view.sheet.acLabel.setText(String.valueOf(AC));
                break;
            case "Ranged":
                view.sheet.rangedBox.setText(item.getItemName());
        }
    }
    protected void copyItem(){
        itemModel newitemModel = deserializeItem(dbitemName);
        Inventory.put(newitemModel.getName(), newitemModel);
        view.creation.inventorylist.add(newitemModel.getName());

    }

    /*protected void loadInventory(){

        CharacterModel newitemModel = deserializeItem(dbitemName);

        System.out.println(newitemModel.getInventory);
        //for(){

        }

        //for (String key : model.getInventory().keySet()) {
          //  model.getInventory(Inventory.get(key));
        //}*/


    protected void removeItem(String toRemove){
        Inventory.remove(toRemove);
        view.creation.inventorylist.remove(toRemove);
    }

    protected void setdbCharName(String charname){
        this.dbCharName = charname;
    }

    protected void setitemCharName(String itemname){
        this.dbitemName = itemname;
    }

    protected void comboChange(){
        //HashMap<String, Integer> status = new HashMap<String, Integer>();
        view.creation.listRacialProficiency.getItems().clear();
        //ArrayList<String> proficiencyClass = new ArrayList<>();
        proficiencyRace.clear();
        Object selectedItem = view.creation.combo.getSelectionModel().getSelectedItem();
        if (selectedItem == "Human"){
            proficiencyRace.add("Well...");
            mainBonus.set(0,1);mainBonus.set(1,1);mainBonus.set(2,1);
            mainBonus.set(3,1);mainBonus.set(4,1);mainBonus.set(5,1);
            updateStatus("1","1","1","1","1","1");
        } else if (selectedItem == "Dwarf"){
            proficiencyRace.add("Battle Axe");proficiencyRace.add("Hand Axe"); proficiencyRace.add("Light Hammer");
            proficiencyRace.add("Warhammer");proficiencyRace.add("Dark vision");
            mainBonus.set(0,2);mainBonus.set(1,2);mainBonus.set(2,0);
            mainBonus.set(3,0);mainBonus.set(4,0);mainBonus.set(5,0);
            updateStatus("2","2","0","0","0","0");
        } else if (selectedItem == "Halfling"){
            proficiencyRace.add("Lucky");proficiencyRace.add("Brave");
            mainBonus.set(0,0);mainBonus.set(1,1);mainBonus.set(2,0);
            mainBonus.set(3,2);mainBonus.set(4,0);mainBonus.set(5,0);
            updateStatus("0","1","0","2","0","0");
        } else {
            proficiencyRace.add("Fey Ancestry");proficiencyRace.add("Keen senses");proficiencyRace.add("Dark vision");
            mainBonus.set(0,0);mainBonus.set(1,0);mainBonus.set(2,0);
            mainBonus.set(3,2);mainBonus.set(4,1);mainBonus.set(5,0);
            updateStatus("0","0","0","2","1","0");
        }
        for(String items:proficiencyRace){view.creation.proficiencyRaciallist.add(items);};
    }

    protected void comboClass(){

        Object selectedItem = view.creation.classCombo.getSelectionModel().getSelectedItem();


        view.creation.listClassProficiency.getItems().clear();

        proficiencyClass.clear();
        if (selectedItem == "Fighter"){
            hitDice = 10;
            proficiencyClass.add("Light Armor");proficiencyClass.add("Medium Armor"); proficiencyClass.add("Heavy armor");
            proficiencyClass.add("Shields");proficiencyClass.add("Simple Weapons");proficiencyClass.add("Martial Weapons");
            equipment.add("LongSword");equipment.add("None");equipment.add("Chain mail");equipment.add("Light Crossbow");
            view.creation.gold.setText(String.valueOf(rollAndDice(5,4)*10));

        } else if (selectedItem == "Paladin"){
            proficiencyClass.add("Light Armor");proficiencyClass.add("Medium Armor"); proficiencyClass.add("Heavy armor");
            proficiencyClass.add("Shields");proficiencyClass.add("Simple Weapons");proficiencyClass.add("Martial Weapons");
            equipment.add("Sword");equipment.add("Shield");equipment.add("Plate mail");equipment.add("Axe");
            hitDice = 10;
            view.creation.gold.setText(String.valueOf(rollAndDice(5,4)*10));
        } else if (selectedItem == "Mage"){
            proficiencyClass.add("Daggers");proficiencyClass.add("Darts"); proficiencyClass.add("Slings");
            proficiencyClass.add("Staffs");proficiencyClass.add("Light Crossbows");
            equipment.add("Staff");equipment.add("None");equipment.add("Robe");equipment.add("Light Crossbow");
            hitDice = 6;
            view.creation.gold.setText(String.valueOf(rollAndDice(4,4)*10));
        } else if (selectedItem == "Priest"){
            proficiencyClass.add("Light Armor");proficiencyClass.add("Medium Armor");
            proficiencyClass.add("Shields");proficiencyClass.add("Simple Weapons");
            equipment.add("Warhammer");equipment.add("None");equipment.add("Scale mail");equipment.add("Light Crossbow");
            hitDice = 8;
            view.creation.gold.setText(String.valueOf(rollAndDice(4,4)*10));

            //System.out.println(selectedItem);
        } else {
            proficiencyClass.add("Light Armor");proficiencyClass.add("Hand crossbows"); proficiencyClass.add("Longswords");
            proficiencyClass.add("Rapier");proficiencyClass.add("Shortswords");
            equipment.add("ShortSword");equipment.add("None");equipment.add("Leather mail");equipment.add("Light Crossbow");
            hitDice = 8;
            view.creation.gold.setText(String.valueOf(rollAndDice(4,4)*10));
        }
        for(String items:proficiencyClass){view.creation.proficiencyClasslist.add(items);};
        view.creation.weaponLabel.setText("Weapon: " + equipment.get(0));
        view.creation.shieldLabel.setText("Shield: " + equipment.get(1));
        view.creation.armorLabel.setText("Armor: " + equipment.get(2));
        view.creation.rangedLabel.setText("Ranged: " + equipment.get(3));
    }



    protected void updateStatus(String str, String con, String in, String dex, String wis, String ch){
        view.creation.strBonus.setText(str);
        view.creation.conBonus.setText(con);
        view.creation.intBonus.setText(in);
        view.creation.dexBonus.setText(dex);
        view.creation.wisBonus.setText(wis);
        view.creation.charBonus.setText(ch);
    }

    protected void rollStatus(){
        int strRoll = rollAndSum();

        mainStatus.set(0,strRoll);
        mainModifier.set(0,(strRoll-10)/2);
        view.creation.strField.setText(String.valueOf(mainStatus.get(0)));
        view.creation.strMod.setText(String.valueOf(mainModifier.get(0)));

        int conRoll = rollAndSum();
        mainStatus.set(1,conRoll);
        mainModifier.set(1,(conRoll-10)/2);
        view.creation.conField.setText(String.valueOf(mainStatus.get(1)));
        view.creation.conMod.setText(String.valueOf(mainModifier.get(1)));

        int intRoll = rollAndSum();
        mainStatus.set(2,intRoll);
        mainModifier.set(2,(intRoll-10)/2);
        view.creation.intField.setText(String.valueOf(mainStatus.get(2)));
        view.creation.intMod.setText(String.valueOf(mainModifier.get(2)));

        int dexRoll = rollAndSum();
        mainStatus.set(3,dexRoll);
        mainModifier.set(3,(dexRoll-10)/2);
        view.creation.dexField.setText(String.valueOf(mainStatus.get(3)));
        view.creation.dexMod.setText(String.valueOf(mainModifier.get(3)));

        int wisRoll = rollAndSum();
        mainStatus.set(4,wisRoll);
        mainModifier.set(4,(wisRoll-10)/2);
        view.creation.wisField.setText(String.valueOf(mainStatus.get(4)));
        view.creation.wisMod.setText(String.valueOf(mainModifier.get(4)));


        int charRoll = rollAndSum();
        mainStatus.set(5,charRoll);
        mainModifier.set(5,(charRoll-10)/2);
        view.creation.charField.setText(String.valueOf(mainStatus.get(5)));
        view.creation.charMod.setText(String.valueOf(mainModifier.get(5)));
    }

    private ArrayList<Integer> arrayClear(ArrayList<Integer> newArray){
        for (int i = 0; i < newArray.size(); i++) {
            newArray.set(i, 0);
        }
        return(newArray);
    }

    protected void strIncrement(int direction){


        if(direction>=0 && Strength <= statusList.size() && currentStatus<=15){
            Strength++;


        }else if (direction<0 && Strength >=1){
            Strength--;
        }

        removedList.set(0, statusList.get(Strength));

        mainStatus.set(0,statusList.get(Strength));
        mainModifier.set(0,(statusList.get(Strength)-10)/2);
        view.creation.strField.setText(String.valueOf(mainStatus.get(0)));
        view.creation.strMod.setText(String.valueOf(mainModifier.get(0)));

    }

    protected void conIncrement(int direction){

        if(direction>=0 && Constitution <= statusList.size() && statusList.get(Constitution)<=15){

            Constitution++;


        }else if (direction<0 && statusList.get(Constitution)>=0){
            Constitution--;

        }
        //view.conField.setText(String.valueOf(statusList.get(Constitution)));
        removedList.set(1, statusList.get(Constitution));

        //int modifier = Integer.parseInt(view.conBonus.getText())+(statusList.get(Constitution)-10)/2;
        //view.conMod.setText(String.valueOf(modifier));

        mainStatus.set(1,statusList.get(Constitution));
        mainModifier.set(1,(statusList.get(Constitution)-10)/2);
        view.creation.conField.setText(String.valueOf(mainStatus.get(1)));
        view.creation.conMod.setText(String.valueOf(mainModifier.get(1)));

    }

    protected void intIncrement(int direction){
        if(direction>=0 && Intelligence <= statusList.size() && statusList.get(Intelligence)<=15) {
            Intelligence++;
        }else if (direction <0 && statusList.get(Intelligence)>=0){
            Intelligence--;
        }

        removedList.set(2, statusList.get(Intelligence));


        mainStatus.set(2,statusList.get(Intelligence));
        mainModifier.set(2,(statusList.get(Intelligence)-10)/2);
        view.creation.intField.setText(String.valueOf(mainStatus.get(2)));
        view.creation.intMod.setText(String.valueOf(mainModifier.get(2)));
    }
    protected void dexIncrement(int direction){
        if(direction>=0 && Dexterity <= statusList.size() && statusList.get(Dexterity)<=15) {
            Dexterity++;
        }else if (direction<0 && statusList.get(Dexterity)>=0){
            Dexterity--;
        }

        removedList.set(3, statusList.get(Dexterity));


        mainStatus.set(3,statusList.get(Dexterity));
        mainModifier.set(3,(statusList.get(Dexterity)-10)/2);
        view.creation.dexField.setText(String.valueOf(mainStatus.get(3)));
        view.creation.dexMod.setText(String.valueOf(mainModifier.get(3)));

    }
    protected void wisIncrement(int direction){
        if(direction>=0 && Wisdom <= statusList.size() && statusList.get(Wisdom)<=15) {
            Wisdom++;
        }else if (direction<0 && statusList.get(Wisdom)>=0){
            Wisdom--;
        }

        removedList.set(4, statusList.get(Wisdom));

        mainStatus.set(4,statusList.get(Wisdom));
        mainModifier.set(4,(statusList.get(Wisdom)-10)/2);
        view.creation.wisField.setText(String.valueOf(mainStatus.get(4)));
        view.creation.wisMod.setText(String.valueOf(mainModifier.get(4)));

    }
    protected void charIncrement(int direction){
        if(direction>=0 && Charisma <= statusList.size() && statusList.get(Charisma)<=15) {
            Charisma++;
        }else if (direction<0 && statusList.get(Charisma)>=0){
            Charisma--;
        }

        removedList.set(5, statusList.get(Charisma));


        mainStatus.set(5,statusList.get(Charisma));
        mainModifier.set(5,(statusList.get(Charisma)-10)/2);
        view.creation.charField.setText(String.valueOf(mainStatus.get(5)));
        view.creation.charMod.setText(String.valueOf(mainModifier.get(5)));
    }

    protected Boolean validateStatus(){
        Collections.sort(removedList);
        statusList.removeFirst();
        boolean areEqual = statusList.equals(removedList);
        return (areEqual);
    }

    protected HashMap validateTosave(){
        HashMap valStuff = new HashMap();

        valStuff.put("Name", view.creation.charName.getText().isEmpty());
        valStuff.put("Race", view.creation.combo.getSelectionModel().isEmpty());
        valStuff.put("Class", view.creation.classCombo.getSelectionModel().isEmpty());
        valStuff.put("HitDice", view.creation.hitField.getText().isEmpty());
        valStuff.put("Description", view.creation.description.getText().isEmpty());

        return (valStuff);
    }


    public static <K, V> boolean allThingsAreEmpty(HashMap<K, V> map) {
        for (V value : map.values()) {
            if (TRUE.equals(value)) {
                return true;
            }
        }
        return false; // No true value found
    }
    public static <K> Set<K> getKeyForTRUEValue(HashMap<K, Boolean> map) {
        Set<K> keysWithTrueValues = new HashSet<>();
        for (Map.Entry<K, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) {
                keysWithTrueValues.add(entry.getKey());
            }
        }
        return keysWithTrueValues;
    }
    public String errorMessage(){
        HashMap errorStuff = new HashMap();

        errorStuff.put("Name", "Name - Please name your character.");
        errorStuff.put("Race", "Race - Please choose a race.");
        errorStuff.put("Class", "Class - Please choose a class.");
        errorStuff.put("HitDice", "HitDice - Don't forget to roll your HitDice.");
        errorStuff.put("Description", "Description - Dude, a character without description is souless.");
        return(concatenateValuesForKeySet(errorStuff,getKeyForTRUEValue(validateTosave())));
    }

    public static <K, V> String concatenateValuesForKeySet(HashMap<K, V> map, Set<K> keys) {
        StringBuilder result = new StringBuilder();
        for (K key : keys) {
            V value = map.get(key);
            if (value != null) {
                result.append(value);
                result.append('\n');
            }
        }
        return result.toString();
    }

    public void resetImage(){
        File file = new File("/Users/bruno.andrade/Documents/IdeProjects/CharacterBuilder/src/main/resources/media/lowpolybasemeshgenericcharacter.png");
        this.openAndSetImage(file, view.creation.imageView);
    }

    public void setImage(String url){
        File file = new File(url);
        this.openAndSetImage(file, view.sheet.avatar);
    }

    public void imagehandle() {
        CopyURL copyImage = new CopyURL();
        File file = view.creation.fileChooser.showOpenDialog(view.stage);
        view.creation.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        if (file != null) {

            try {
                copyImage.setSource(file.toURI().toURL().toExternalForm());
                uriImage = copyImage.getDestinationPath();
                if (uriImage.isEmpty()){
                copyImage.copyFile();
                }
                File newfile = new File(uriImage);

                this.openAndSetImage(newfile, view.creation.imageView);
            } catch (MalformedURLException ex){

            }
        }
    }
    protected void changeStats(String status){
        if (status == "Select"){
            view.creation.rollButton.setVisible(false);
            view.creation.Vstr.setVisible(true); view.creation.Vcon.setVisible(true); view.creation.Vint.setVisible(true);
            view.creation.Vdex.setVisible(true); view.creation.Vwis.setVisible(true); view.creation.Vchar.setVisible(true);

            select = TRUE;

            //view.validateLabel.setText("Check your Status");
            view.alert.setContentText("You can distribute one of the following status: \n 15,14,13,12,10,8. \n No more, no less. \n The system will not allow you to save otherwise.");
            //view.alert.setTitle("Validation Error");
            //view.alert.setHeaderText("There were some problems with your character:");
            view.alert.showAndWait();

        }else{
            view.creation.rollButton.setVisible(true);
            view.creation.Vstr.setVisible(false); view.creation.Vcon.setVisible(false); view.creation.Vint.setVisible(false);
            view.creation.Vdex.setVisible(false); view.creation.Vwis.setVisible(false); view.creation.Vchar.setVisible(false);
            view.explainLabel.setText("We will run 4 D6 for you and then sum \n the highest 3");
            select = FALSE;
        }
    }

    protected void roll(){
        Random rand = new Random();
        int conMod = Integer.parseInt(view.creation.conMod.getText()) + Integer.parseInt(view.creation.conBonus.getText());
        view.creation.hitField.setText(String.valueOf(rand.nextInt(hitDice)+conMod));
        //view.creation.proficiencylist.add(proficiency);
    }

    protected void rollDice(){
        Random rand = new Random();
        if(view.sheet.radioAttack.isSelected()){
            int[] rolls = new int[this.attackDice];
            int sumRolls = 0;
            for (int i=0; i<this.attackDice; i++){
                sumRolls += rand.nextInt(this.attackSide)+1;
            }

            //view.sheet.promptnames.addAll("You rolled an attack of " + this.attackDice + "D" + this.attackSide);
            view.sheet.promptdata.add("You rolled an attack of " + this.attackDice + "D" + this.attackSide + "And got: " + sumRolls);
        }else if (view.sheet.radioInitiative.isSelected()){
            view.sheet.promptdata.add("You rolled initiative and got " + rand.nextInt(20));
            System.out.println(rand.nextInt(20));
        }else if (view.sheet.radioStr.isSelected()){
            view.sheet.promptdata.add("You rolled for a Strength check and got " + (rand.nextInt(20)+ Integer.parseInt(view.sheet.bonusStr.getText())));
            System.out.println(rand.nextInt(20) + Integer.parseInt(view.sheet.bonusStr.getText()));
        }else if (view.sheet.radioCon.isSelected()){
            view.sheet.promptdata.add("You rolled for a Constitution check and got " + (rand.nextInt(20)+ Integer.parseInt(view.sheet.bonusCon.getText())));
            System.out.println(rand.nextInt(20) + Integer.parseInt(view.sheet.bonusCon.getText()));
        }else if (view.sheet.radioDex.isSelected()){
            view.sheet.promptdata.add("You rolled for a Dexterity check and got " + (rand.nextInt(20)+ Integer.parseInt(view.sheet.bonusDex.getText())));
            System.out.println(rand.nextInt(20) + Integer.parseInt(view.sheet.bonusDex.getText()));
        }else if (view.sheet.radioInt.isSelected()){
            view.sheet.promptdata.add("You rolled for a Intelligence check and got " + (rand.nextInt(20)+ Integer.parseInt(view.sheet.bonusInt.getText())));
            System.out.println(rand.nextInt(20) + Integer.parseInt(view.sheet.bonusInt.getText()));
        }else if (view.sheet.radioWis.isSelected()){
            view.sheet.promptdata.add("You rolled for a Wisdom check and got " + (rand.nextInt(20)+ Integer.parseInt(view.sheet.bonusWis.getText())));
            System.out.println(rand.nextInt(20) + Integer.parseInt(view.sheet.bonusWis.getText()));
        }else if (view.sheet.radioChar.isSelected()){
            view.sheet.promptdata.add("You rolled for a Charisma check and got " + (rand.nextInt(20)+ Integer.parseInt(view.sheet.bonusChar.getText())));
            System.out.println(rand.nextInt(20) + Integer.parseInt(view.sheet.bonusChar.getText()));
        }

    }

    protected Integer rollAndDice(Integer Dice, Integer Side){
        Random rand = new Random();
        int[] rolls = new int[Dice];
        int sumRolls = 0;
        for (int i=0; i<Dice; i++){
            sumRolls += rand.nextInt(Side)+1;
        }
        return(sumRolls);
    }

    protected void rollCheck(){
        view.sheet.radioStr.setVisible(true); view.sheet.radioCon.setVisible(true); view.sheet.radioDex.setVisible(true);
        view.sheet.radioInt.setVisible(true); view.sheet.radioWis.setVisible(true); view.sheet.radioChar.setVisible(true);
    }
    protected void clear(){
        arrayClear(mainStatus);

        //view.combo.getButtonCell().set(null);
        view.creation.combo.getButtonCell().setText("Select Race");
        //view.combo.getButtonCell().setItem(null);
        //view.classCombo.valueProperty().set(null);
        //view.classCombo.getButtonCell().setText("Select Race");

    }
    protected static int rollAndSum() {
        // Array to store four dice rolls
        int[] rolls = new int[4];

        // Roll four dice
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            rolls[i] = rand.nextInt(6) + 1; // Rolling a single die (1-6)
        }

        // Sort the rolls in descending order
        Arrays.sort(rolls);
        // Sum the three highest values
        int sum = rolls[1] + rolls[2] + rolls[3];

        return sum;
    }

    protected void openAndSetImage(File file, ImageView imageView) {
        try {
            // Create an Image object from the selected file
            Image image = new Image(file.toURI().toString());

            // Set the Image object to the ImageView
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    protected void SaveStuff(){
        if (select == FALSE && this.allThingsAreEmpty(validateTosave()) == false || select == TRUE && this.validateStatus() == TRUE && this.allThingsAreEmpty(validateTosave()) == false){
            model.setName(view.creation.charName.getText());
            model.setClass(String.valueOf(view.creation.classCombo.getSelectionModel().getSelectedItem()));
            model.setRace(String.valueOf(view.creation.combo.getSelectionModel().getSelectedItem()));
            model.setDescription(view.creation.description.getText());
            model.setStatus(mainStatus);
            model.setModifier(mainModifier);
            model.setProficiency(proficiencyRace);
            model.setBonus(mainBonus);
            model.setUri(uriImage);
            model.setDice(Integer.parseInt(view.creation.hitField.getText()));
            for (String key : Inventory.keySet()) {
                model.setInventory(Inventory.get(key));
            }
            try {
                this.SaveToDatabase("Player");
                //this.retrieveDBNames();
                view.creation.validateLabel.setText("Your character was successfully saved.");
            }catch (SQLException e){
                e.printStackTrace();
            }catch (ClassNotFoundException f){
                f.printStackTrace();
            }
        }else{
            view.creation.validateLabel.setText("Check your Status");
            view.creation.alert.setContentText(errorMessage());
            view.creation.alert.setTitle("Validation Error");
            view.creation.alert.setHeaderText("There were some problems with your character:");
            view.creation.alert.showAndWait();
        }

    }

    protected void saveItem(){
        itemmodel.setName(view.itemcreator.itemNameCreator.getText());
        itemmodel.setDamage(view.itemcreator.itemDamageCreator.getText());
        itemmodel.setType(view.itemcreator.itemTypeCreator.getSelectionModel().getSelectedItem().toString());
        itemmodel.setDescription(view.itemcreator.itemDescriptionCreator.getText());
        itemmodel.setlvl(view.itemcreator.itemLvlCreator.getText());
        itemmodel.setAC(view.itemcreator.armorCreator.getText());
        itemmodel.setValue(view.itemcreator.itemValue.getText());
        try {
            this.SaveToDatabase("Item");
            //this.retrieveDBNames();
            view.itemcreator.saveLabelCreator.setText("Your Item was successfully saved.");
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException f){
            f.printStackTrace();
        }
        if (view.itemcreator.dataCreator.contains(view.itemcreator.itemNameCreator.getText())==false) {
            view.itemcreator.dataCreator.add(view.itemcreator.itemNameCreator.getText());
        }else{
            view.itemcreator.dataCreator.set(view.itemcreator.dataCreator.indexOf(view.itemcreator.itemNameCreator.getText()),
                    view.itemcreator.itemNameCreator.getText());
        }
    }


    // Method to serialize an object to byte array
    private void SaveToDatabase(String id) throws SQLException, ClassNotFoundException {
        Connection connection 	= DatabaseConnector.getConnection();




        if (id=="Player"){
            sql = "INSERT INTO Game VALUES (?,?)";
            byte[] serializedObject = serializeObject(model);
            try(connection){
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setBytes(1, serializedObject);
                statement.setString(2, model.getName());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Object saved to database successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (id =="Item"){
            sql = "INSERT INTO Item VALUES (?,?)";
            byte[] serializedObject = serializeObject(itemmodel);
            try(connection){
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setBytes(1, serializedObject);
                statement.setString(2, itemmodel.getName());

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Object saved to database successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
    private static byte[] serializeObject(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private CharacterModel deserializeObject(String name) {

        ArrayList<String> keysWithTrueValues = new ArrayList<>(retrieveDBNames().keySet());

        try{
            //ByteArrayInputStream inmodel = new ByteArrayInputStream(retrieveDBNames().get(name).getBinaryStream());
            ObjectInputStream iios = new ObjectInputStream(retrieveDBNames().get(name).getBinaryStream());
            CharacterModel deserializedObject = (CharacterModel) iios.readObject();
            return (deserializedObject);
            //Player deserializedObject = (Player) deserializeObject(serializedObject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private itemModel deserializeItem(String name) {

        ArrayList<String> keysWithTrueValues = new ArrayList<>(retrieveItemNames().keySet());

        try{
            //ByteArrayInputStream inmodel = new ByteArrayInputStream(retrieveDBNames().get(name).getBinaryStream());
            ObjectInputStream iios = new ObjectInputStream(retrieveItemNames().get(name).getBinaryStream());
            itemModel deserializedObject = (itemModel) iios.readObject();
            return (deserializedObject);
            //Player deserializedObject = (Player) deserializeObject(serializedObject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private HashMap<String, Blob> retrieveDBNames(){
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        HashMap<String, Blob> dbInfo = new HashMap<String, Blob>();

        try {
            // Connect to the MySQL database
            connection 	= DatabaseConnector.getConnection();

            // Create a statement
            stmt = connection.createStatement();

            // Execute a query to retrieve IDs from your table
            rs = stmt.executeQuery("SELECT object_data, name FROM Game");

            // Iterate through the result set and print the IDs
            while (rs.next()) {
                String name = rs.getString("name");
                Blob object = rs.getBlob("object_data");

                dbInfo.put(name, object);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    return(dbInfo);
    }

    private HashMap<String, Blob> retrieveItemNames(){
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        HashMap<String, Blob> dbInfo = new HashMap<String, Blob>();

        try {
            // Connect to the MySQL database
            connection 	= DatabaseConnector.getConnection();

            // Create a statement
            stmt = connection.createStatement();

            // Execute a query to retrieve IDs from your table
            rs = stmt.executeQuery("SELECT object_data, name FROM Item");

            // Iterate through the result set and print the IDs
            while (rs.next()) {
                String name = rs.getString("name");
                Blob object = rs.getBlob("object_data");

                dbInfo.put(name, object);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return(dbInfo);
    }


}

