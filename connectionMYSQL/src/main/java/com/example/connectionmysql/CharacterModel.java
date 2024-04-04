package com.example.connectionmysql;


import java.util.ArrayList;

public class CharacterModel implements java.io.Serializable{

    private String characterName;
    private String characterClass;
    private String characterRace;
    private String description;
    private ArrayList<String> proficiency;
    private String uriImage;
    private Integer hitDice;
    private ArrayList<Integer> Status;
    private ArrayList<Integer> Modifier;
    private ArrayList<Integer> Bonus;
    private ArrayList<itemModel> Inventory= new ArrayList<>();

    //private CharacterController controller;

    protected void setName(String name){
        this.characterName = name;
    }
    public String getName(){
        return(characterName);
    }

    protected void setClass(String charClass){
        this.characterClass = charClass;
    }
    public String getCharClass(){
        return(characterClass);
    }

    protected void setRace(String charRace){
        this.characterRace = charRace;
    }

    public String getCharRace(){
        return(characterRace);
    }

    protected void setDice(Integer Dice){
        this.hitDice = Dice;
    }
    public Integer getDice(){
        return(hitDice);
    }
    protected void setDescription(String descript){
        this.description = descript;
    }
    public String getDescription(){
        return(description);
    }
    protected void setProficiency(ArrayList<String> prof){
        this.proficiency = prof;
    }

    public ArrayList<String> getProficiency(){
        return(proficiency);
    }

    protected void setUri(String Path){
        this.uriImage = Path;
    }
    public String getUri(){
        return(this.uriImage);
    }

    protected void setStatus(ArrayList<Integer> stats){
        this.Status = stats;
    }

    public ArrayList<Integer> getStatus(){
        return(Status);
    }

    protected void setBonus(ArrayList<Integer> bonus){
        this.Bonus=bonus;
    }
    public ArrayList<Integer> getBonus(){
        return(Bonus);
    }
    protected void setModifier(ArrayList<Integer> mod){
        this.Modifier = mod;
    }
    public ArrayList<Integer> getMod(){
        return(Modifier);
    }

    protected void setInventory(itemModel Item){
        Inventory.add(Item);
    }
    public ArrayList<itemModel> getInventory(){
        return(Inventory);
    }
    public itemModel getItem(String item){
        return(Inventory.get(Inventory.indexOf(item)));
    }
}

