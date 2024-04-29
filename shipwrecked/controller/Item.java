package shipwrecked.controller;

import shipwrecked.gameException.GameException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class: Item
 * @author Joel Erulu
 * @version 1.0
 * Course: ITEC 3860 Fall 2023
 * Written: April 12th 2024
 * This class – Handles Items in the game.
 */
public class Item {

    private int itemID;
    private String itemName;
    private String itemDescription;
    private int itemRestore;
    private int itemDamage;

    public int getItemID() {
        return this.itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemRestore() {
        return this.itemRestore;
    }

    public void setItemRestore(int itemRestore) {
        this.itemRestore = itemRestore;
    }

    public int getItemDamage() {
        return this.itemDamage;
    }

    public void setItemDamage(int itemDamage) {
        this.itemDamage = itemDamage;
    }

    /**
     * Method display
     * This method returns the itemDescription which is the String that will be displayed in the game
     *
     * @return the String to display in the game
     */
    public String display() {
        return this.itemDescription;
    }


    @Override
    public String toString() {
        return "Item{" +
                "itemID=" + itemID +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemRestore=" + itemRestore +
                ", itemDamage=" + itemDamage +
                '}';
    }
}
