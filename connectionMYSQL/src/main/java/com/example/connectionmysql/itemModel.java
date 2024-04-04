package com.example.connectionmysql;

public class itemModel implements java.io.Serializable{

    private String itemName;
    private String itemType;
    private String itemDamage;
    private String itemDescription;
    private String armorClass;

    private String value;

    private String lvl;

    protected void setName(String name){
        this.itemName = name;
    }
    public String getName(){
        return(this.itemName);
    }

    protected void setType(String type){
        this.itemType = type;
    }
    public String getType(){
        return(this.itemType);
    }
    protected void setDamage(String damage){
        this.itemDamage = damage;
    }
    public String getDamage(){
        return(itemDamage);
    }

    protected void setDescription(String description){
        this.itemDescription = description;
    }

    public String getDescription(){
        return(this.itemDescription);
    }
    protected void setlvl(String lvl){
        this.lvl = lvl;
    }
    public String getlvl(){
        return(this.lvl);
    }
    protected void setAC(String ac){
        this.armorClass = ac;
    }
    public String getAC(){
        return(this.armorClass);
    }

    protected void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return(this.value);
    }
}
