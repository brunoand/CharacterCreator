package com.example.connectionmysql;

import javafx.beans.property.SimpleStringProperty;

public class Item {
    private final SimpleStringProperty itemName;
    private final SimpleStringProperty itemType;
    private final SimpleStringProperty itemValue;
    private final SimpleStringProperty itemDice;
    private final SimpleStringProperty itemAC;

    Item(String iName, String itype, String ivalue, String idice, String iAC) {
        this.itemName = new SimpleStringProperty(iName);
        this.itemType = new SimpleStringProperty(itype);
        this.itemValue = new SimpleStringProperty(ivalue);
        this.itemDice = new SimpleStringProperty(idice);
        this.itemAC = new SimpleStringProperty(iAC);

    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String iName) {
        itemName.set(iName);
    }

    public String getItemType() {
        return itemType.get();
    }

    public void setItemType(String itype) {
        itemType.set(itype);
    }
    public String getItemValue() {
        return itemValue.get();
    }

    public void setItemValue(String ivalue) {
        itemValue.set(ivalue);
    }

    public void setItemDice(String idice){
        itemDice.set(idice);
    }
    public String getItemDice(){
        return itemDice.get();
    }

    private void setItemAC(String AC){
        itemAC.set(AC);
    }
    public String getItemAC(){
        return itemAC.get();
    }

}
