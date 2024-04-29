package shipwrecked.controller;

import shipwrecked.gameException.GameException;
import shipwrecked.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class: Room
 * @author Joel erulu Price
 * @version 1.2
 * Course: ITEC 3860
 * This class handles the Room interactions. This class contains the roomID, name, description
 * ArrayList Exit as well as a boolean value to determine if the user has visited the room.
 * Items are retrieved in RoomDB and not maintained in the Room.
 * Exits are retrieved in RoomDB and are maintained in the Room class for performance reasons.
 * Monsters are retrieved in RoomDB and are maintained in the Room class for performance reasons.
 * Allies are retrieved in RoomDB and are maintained in the Room class for performance reasons.
 *
 */
public class Room {

    private int roomID;
    private String name;
    private String description;
    private int visited;
    private Collection<Exit> exits; // stores the room exits

    private Collection<Exit> monsters; // stores the room monsters

    private Collection<Exit> allies; // stores the room allies


    private RoomDB rdb;

    // Getters and setters

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int isVisited() {
        return visited;
    }


    public void setVisited(int visited) {
        this.visited = visited;
    }


    public void setExits(Collection<Exit> exits) {
        this.exits = exits;
    }


    /**
     * Method Room
     * Constructor for the Room class
     * Initializes RoomDB
     */

    public Room() {
        rdb = new RoomDB();
    }


    /**
     * Room constructor
     * constructs the room object with the given ID
     * @param id id of the room to be constructed
     */
    public Room(int id) throws GameException {
        try {
            RoomDB roomDB = new RoomDB();
            Room room = roomDB.getRoom(id);
            if (room != null) {
                this.roomID = room.getRoomID();
                this.name = room.getName();
                this.description = room.getDescription();
                this.visited = room.isVisited();
                this.exits = room.getExits(); // Set exits as well

            } else {
                throw new GameException("Room with ID " + id + " not found.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error retrieving room information.");
        }
    }

    // Other methods

    /**
     * Method display
     * Builds a String representation of the current room
     * Calls buildItems, buildDescription, and displayExits to build this String
     * @return String - the current room display String
     * @throws GameException if the Item String cannot be built
     */
    public String display() throws GameException {
        try {
            String itemsString = buildItems();
            String descriptionString = buildDescription();
            String exitsString = displayExits();

            return "\nRoomID: " + roomID + "\nName: " + name + "\nRoom Description: " + descriptionString + "\n";
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error displaying the room: " + e.getMessage());
        }
    }

    /**
     * Method buildDescription
     * Builds a String of the description
     * @return String - the current room description text
     */
    private String buildDescription() throws SQLException, ClassNotFoundException, GameException {
        return rdb.getRoom(roomID).getDescription();
    }

    /**
     * Method buildItems
     * Builds a String of the items in the room
     * @return String - the current room items text
     * @throws GameException if an error retrieving items
     */
    private String buildItems() throws GameException, SQLException, ClassNotFoundException {
        ArrayList<Item> items = rdb.getItems(roomID);
        StringBuilder itemsString = new StringBuilder();
        for (Item item : items) {
            itemsString.append(item.getItemName()).append("\n");
        }
        return itemsString.toString();
    }
    /**
     * Method removeItem
     * Removes an item from the room.
     * @param item - the Item to remove
     */
    public void removeItem(Item item) throws GameException {
        try {
            ItemRoomDB itemRDB = new ItemRoomDB();
            itemRDB.removeItem(item);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating room: " + e.getMessage());
        }
    }


    /**
     * Method dropItem
     * Adds an item to the room. Removes it and calls updateRoom to save the changes
     * @param item - the Item to remove
     */
    public void dropItem(Item item, Room room) throws GameException {
        try {
            ItemRoomDB itemRDB = new ItemRoomDB();
            itemRDB.addItem(item, room.getRoomID());
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating room: " + e.getMessage());
        }
    }
    /**
     * Method updateRoom
     * Calls RoomDB updateRoom(this) to save the current room in the map
     */
    public void updateRoom() throws GameException {
        try {
            RoomDB roomDB = new RoomDB();
            roomDB.updateRoom(this);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating room: " + e.getMessage());
        }
    }


    /**
     * Method displayExits
     * Builds a String of the exits in the room
     * @return String - the current room exits text
     */
    public String displayExits() throws GameException {
        exits = getExits(); // Retrieve exits
        if (exits == null || exits.isEmpty()) {
            return "There are no exits in this room.";
        }

        StringBuilder exitsString = new StringBuilder();
        for (Exit exit : exits) {
            exitsString.append(exit.getDirection()).append(": ").append(exit.getDestination()).append("\n");
        }
        return exitsString.toString();
    }


    /**
     * Method retrieveByID
     * Retrieves the requested Room from RoomDB. Sets its values into the current Room and returns it
     * @param roomNum ID of the room to retrieve
     * @return Room - the requested Room
     * @throws GameException if the room cannot be found
     */
    public Room retrieveByID(int roomNum) throws GameException {
        try {
            RoomDB rooms = new RoomDB();
            return rooms.getRoom(roomNum);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error retrieving room: " + e.getMessage());
        }
    }

    /**
     * Method validDirection
     * Determines if the direction entered by the user is valid for this room
     * Throws an exception if this is invalid
     * @param cmd - The direction the user wants to move
     * @return int - the ID of the destination room
     * @throws GameException if the direction chosen is not valid
     */
    public int validDirection(char cmd) throws GameException {

        Collection<Exit> exits = getExits();
        for (Exit exit : exits) {
            if (cmd == exit.getDirection().charAt(0)) {
                return exit.getDestination();
            }
        }
        return -1; // Indicates an invalid direction
    }



    /**
     * Method: getRoomItems
     * This method calls RoomDB to get the items that are in the current room.
     * @return ArrayList Item - the items in the room
     * @throws GameException if the list of items cannot be built
     */
    public ArrayList<Item> getRoomItems() throws GameException {
        ArrayList<Item> roomItems;
        try {
            RoomDB rdb = new RoomDB();
            roomItems = new ArrayList<>(rdb.getItems(roomID));
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error getting room items: " + e.getMessage());
        }
        return roomItems;
    }


    /**
     * Method getExits
     * getter for the ArrayList Exit in the room
     * @return the room exits
     */
    public Collection<Exit> getExits() throws GameException {
        Collection<Exit> roomExits;
        try {
            ExitDB exitDB = new ExitDB();
            roomExits = new ArrayList<>(exitDB.getExits(roomID));
        } catch (Exception e) {
            throw new GameException("Error retrieving exits: " + e.getMessage());
        }
        return roomExits;
    }
    /**
     * Method getExits
     * getter for the ArrayList Allies  in the room
     * @return the room exits
     */
    public ArrayList<Ally> getRoomAllies() throws GameException {
        ArrayList<Ally> roomAllies;
        try {
            RoomDB rdb = new RoomDB();
            roomAllies = new ArrayList<>(rdb.getAllies(roomID));
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error getting room allies: " + e.getMessage());
        }
        return roomAllies;
    }

    public void removeAllies(Ally ally) throws GameException {
        try {
            AllyRoomDB allyRoomDB = new AllyRoomDB();
            allyRoomDB.removeAlly(ally);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating room: " + e.getMessage());
        }
    }


    /**
     * Method getExits
     * getter for the ArrayList Monsters in the room
     * @return the room exits
     */
    public ArrayList<Monster> getRoomMonsters() throws GameException {
        ArrayList<Monster> roomMonsters;
        try {
            RoomDB rdb = new RoomDB();
            roomMonsters = new ArrayList<>(rdb.getMonsters(roomID));
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error getting room monsters: " + e.getMessage());
        }
        return roomMonsters;
    }


    public void removeMonster(Monster monster) throws GameException {
        try {
            MonsterRoomDB monsterRDB = new MonsterRoomDB();
            monsterRDB.removeMonster(monster);
        } catch (SQLException | ClassNotFoundException e) {
            throw new GameException("Error updating room: " + e.getMessage());
        }
    }


    public String toString() {
        return "\nRoom ID: " + roomID
                + "\n Name: " + name
                + "\n Description: " + description
                + "\n";
    }

}
