
package shipwrecked.controller;

import shipwrecked.gameException.GameException;
import shipwrecked.model.PlayerAllyDB;
import shipwrecked.model.PlayerDB;
import shipwrecked.model.PlayerItemDB;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class: Player
 * @author Joel Erulu
 * @version 1.3
 * Course: ITEC 3860
 * Written: April 15, 2024
 *
 * This class – handles the Player object. This is concerned with tracking inventory and current room
 * for this implementation. Also contains code to update the player to persist the current player.
 */
public class Player {

    private int curRoom;
    private String name;
    private int playerID;

    private int playerHealth;

    private int playerDamage;

    public static final int DEFAULT_DAMAGE = 50;
    public static final int DEFAULT_HEALTH = 5000;
    public static final int FIRST_PLAYER_ID = 1;
    private static final int MAX_INVENTORY_SIZE = 10;

    private ScoreBoard scoreboard;
    public int getCurRoom() {
        return this.curRoom;
    }

    public void setCurRoom(int curRoom) {
        this.curRoom = curRoom;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerHealth() {
        return this.playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getPlayerDamage() {
        return this.playerDamage;
    }

    public void setPlayerDamage(int playerDamage) {
        this.playerDamage = playerDamage;
    }

    public void takeDamage(int damage) {
        playerHealth -= damage;
        if (playerHealth < 0) {
            playerHealth = 0; // Ensure health doesn't go below 0
        }
    }

    /**
     * Method Player
     * Constructor for the Player class
     * Creates a new player and sets that player's ID to the first room
     */
    public Player() {

        this.curRoom = 1; // Assuming the first room ID is 1
        this.scoreboard = new ScoreBoard(this);
        this.playerHealth = DEFAULT_HEALTH;
        this.playerDamage = DEFAULT_DAMAGE;
    }
    public int getScore() {
        return this.scoreboard.getScore();
    }
    public void increaseScore(int value) {
        this.scoreboard.addScore(value);
    }
    public void decreaseScore(int value) {
        this.scoreboard.subtractScore(value);
    }

    /**
     * Retrieves a player by their ID.
     *
     * @param id the ID of the player to retrieve
     * @return the player object corresponding to the given ID
     * @throws GameException if an error occurs during the game
     * @throws SQLException if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */

    public Player getPlayer(int id) throws GameException, SQLException, ClassNotFoundException {
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayer(id);
    }
    /**
     * Retrieves all players.
     *
     * @return an ArrayList containing all players
     * @throws GameException if an error occurs during the game
     * @throws SQLException if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    public  ArrayList<Player> getAllPlayers() throws GameException, SQLException, ClassNotFoundException {
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getAllPlayers();
    }
    /**
     * Method printPlayers
     * Returns the String of all the game players
     *
     * @return String - the String of the players
     */
    public void printPlayers() throws SQLException, ClassNotFoundException, GameException {
        for(int i = FIRST_PLAYER_ID; i <= getAllPlayers().size(); i++){
            System.out.println(getPlayer(i));
        }
    }
    /**
     * Creates a new player with the given name.
     *
     * @param playerName the name of the new player
     * @return the newly created player object
     * @throws GameException if an error occurs during the game
     * @throws SQLException if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    public Player createPlayer(String playerName) throws GameException, SQLException, ClassNotFoundException {
        PlayerDB playerDB = new PlayerDB();
        return playerDB.createPlayer(playerName);
    }


    /**
     * Method addItem
     * Adds an item to the player's inventory
     *
     * @param it - the Item to add to the inventory
     */
    protected void addItem(Item it) throws GameException {
        try {
            // Check if inventory is full before adding the item
            if (getInventory().size() < MAX_INVENTORY_SIZE) {
                PlayerItemDB playerItemDB = new PlayerItemDB();
                playerItemDB.addItemToInventory(this.getPlayerID(), it);
            } else {
                System.out.println("Inventory is full. Cannot add more items.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error adding item to inventory: " + e.getMessage());
        }
    }
    /**
     * Method allAlly
     * Adds an ally to the player's ally squad
     *
     * @param ally - the ally to add to the allly squad
     */
    protected void addAllies(Ally ally) throws GameException {
        try {
            PlayerAllyDB playerAllyDB = new PlayerAllyDB();
            playerAllyDB.addAllyToSquad(this.getPlayerID(), ally);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error adding ally to Squad: " + e.getMessage());
        }
    }


    /**
     * Method removeItem
     * Removes an item from the player's inventory
     *
     * @param it - the Item to remove from the inventory
     */
    protected void removeItem(Item it) throws GameException {
        try {
            PlayerItemDB playerItemDB = new PlayerItemDB();
            playerItemDB.removeItemFromInventory(it);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error adding item to inventory: " + e.getMessage());
        }
    }

    /**
     * Method printInventory
     * Returns the String of all items in the player's inventory
     *
     * @return String - the String of the player's inventory
     */
    public String printInventory() throws GameException, SQLException, ClassNotFoundException {

        ArrayList<Item> inventory = getInventory();

        if (inventory.isEmpty()) {
            return "Your inventory is empty.";
        }

        StringBuilder inventoryString = new StringBuilder("Your current inventory:\n");

        for (Item item : inventory) {
            inventoryString.append(item.getItemName()).append("\n");

        }

        return inventoryString.toString();
    }

    /**
     * Method pritnAllies
     * Returns the String of all player Allues
     *
     * @return String - the String of the player allies
     */

    public String printAllies() throws GameException, SQLException, ClassNotFoundException {

        ArrayList<Ally> allies = getAllies();

        if (allies.isEmpty()) {
            return "You have no allies";
        }

        StringBuilder alliesString = new StringBuilder("Your current Allies:\n");

        for (Ally ally : allies) {
            alliesString.append(ally.getAlliesName()).append("\n");

        }

        return alliesString.toString();
    }


    /**
     * Method getInventory
     * @return the ArrayList of the current Items in the player's inventory
     */
    public java.util.ArrayList<Item> getInventory() throws GameException, SQLException, ClassNotFoundException {
        PlayerItemDB playerItemDB = new PlayerItemDB();
        return playerItemDB.getInventory(playerID);
    }
    /**
     * Method getInventory
     * @return the ArrayList of the current allies for the player
     */
    public java.util.ArrayList<Ally> getAllies() throws GameException, SQLException, ClassNotFoundException {
        PlayerAllyDB playerAllyDB = new PlayerAllyDB();
        return playerAllyDB.getAlliesSquad(playerID);
    }

    /**
     * Method updatePlayer
     * Calls PlayerDB to update changes to the current player.
     */
    protected void updatePlayer() throws GameException {
        try {
            PlayerDB playerDB = new PlayerDB();
            playerDB.update(this);

        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating player: " + e.getMessage());
        }
    }
    /**
     * Method updatePlayer
     * Calls PlayerDB to update changes to the current player room.
     */
    protected void updatePlayerCurRoom() throws GameException {
        try {
            PlayerDB playerDB = new PlayerDB();
            playerDB.updateCurRoom(this);

        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating player: " + e.getMessage());
        }
    }
    /**
     * Method updatePlayer
     * Calls PlayerDB to update changes to the current player health and damage.
     */
    protected void updatePlayerHealthAndDamage() throws GameException {
        try {
            PlayerDB playerDB = new PlayerDB();
            playerDB.updateHealthAndDamage(this);

        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating player: " + e.getMessage());
        }
    }
    /**
     * Method getPlayerByBame
     * Calls PlayerDB get the players name by name
     */
    public Player getPlayerByName(String playerName) throws GameException, SQLException, ClassNotFoundException {
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayerByName(playerName);
    }



    public String toString() {
        return "Player ID: " + this.playerID + ", Name: " + this.name + ", Current Room: " + this.curRoom  + ", Health: " + this.playerHealth  + ", Damage: " + this.playerDamage;
    }

}
