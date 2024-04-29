package shipwrecked.model;

import shipwrecked.controller.Ally;
import shipwrecked.controller.Item;
import shipwrecked.controller.Monster;
import shipwrecked.controller.Room;
import shipwrecked.gameException.GameException;

import java.sql.*;
import java.util.ArrayList;


/**
 * Class: RoomDB
 * @author Joel erulu
 * @version 1.0
 * Course: ITEC 3860
 * Written: April 12, 2024
 * This class – Holds the Room data for the game. Contains an ArrayList Room
 * This reads information from ItemDB when retrieving a Room.
 * This allows the other classes to request these items.
 */
public class RoomDB {

    private SQLiteDB sdb = null;

    /**
     * Method getRoom
     * returns the Room with the ID requested
     * If not found, throws a GameException
     *
     * @param roomID - the int for the room requested
     * @return Room - the requested room
     * @throws GameException if the roomID cannot be found
     */
    public Room getRoom(int roomID) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();


        String query = "SELECT * FROM Room WHERE roomID = ?";
        Connection conn = DriverManager.getConnection(sdb.sDbUrl);
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, roomID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Room room = new Room();
                room.setRoomID(resultSet.getInt("roomID"));
                room.setName(resultSet.getString("roomName"));
                room.setDescription(resultSet.getString("roomDescription"));
                room.setVisited(resultSet.getInt("visited"));
                return room;
            } else {
                throw new GameException("Room with ID " + roomID + " not found.");
            }
        }
    }

    /**
     * Method getItems
     * Returns the list of items in a room
     * throws an exception if the room ID is not found
     *
     * @param roomID - the id of the room requested
     * @return ArrayList -  the items contained in a room
     * @throws GameException if the roomID cannot be found
     */
    public ArrayList<Item> getItems(int roomID) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();
        ArrayList<Item> items = new ArrayList<>();

        String query = "SELECT * FROM Item JOIN ItemRoom ON Item.itemID = ItemRoom.itemID WHERE ItemRoom.roomID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, roomID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Item item = new Item();
                    item.setItemID(resultSet.getInt("itemID"));
                    item.setItemName(resultSet.getString("itemName"));
                    item.setItemDescription(resultSet.getString("itemDescription"));
                    item.setItemRestore(resultSet.getInt("itemRestore"));
                    item.setItemDamage(resultSet.getInt("itemDamage"));

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving items for room with ID " + roomID);
        }

        return items;
    }


    /**
     * Method getMonsters
     * Returns the list of monsters in a room
     * Throws an exception if the room ID is not found
     *
     * @param roomID - the id of the room requested
     * @return ArrayList - the allies contained in a room
     * @throws GameException if the roomID cannot be found or if there is an error retrieving monsters
     */
    public java.util.ArrayList<Monster> getMonsters(int roomID) throws GameException, SQLException, ClassNotFoundException {

        sdb = new SQLiteDB();
        ArrayList<Monster> roommonsters = new ArrayList<>();

        String query = "SELECT * FROM Monster JOIN MonsterRoom ON Monster.monsterID = MonsterRoom.monsterID WHERE MonsterRoom.roomID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, roomID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Monster monster = new Monster();
                    monster.setMonsterID(resultSet.getInt("monsterID"));
                    monster.setMonsterName(resultSet.getString("monsterName"));
                    monster.setMonsterHealth(resultSet.getInt("monsterHealth"));
                    monster.setMonsterAttack(resultSet.getInt("monsterAttack"));
                    monster.setMonsterPoints(resultSet.getInt("monsterPoints"));

                    roommonsters.add(monster);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving items for room with ID " + roomID);
        }

        return roommonsters;
    }
    /**
     * Method getAllies
     * Returns the list of allies in a room
     * Throws an exception if the room ID is not found
     *
     * @param roomID - the id of the room requested
     * @return ArrayList - the allies contained in a room
     * @throws GameException if the roomID cannot be found or if there is an error retrieving allies
     */
    public ArrayList<Ally> getAllies(int roomID) throws GameException, SQLException, ClassNotFoundException {

        sdb = new SQLiteDB();
        ArrayList<Ally> roomAllies = new ArrayList<>();

        String query = "SELECT * FROM Ally JOIN AllyRoom ON Ally.alliesID = AllyRoom.alliesID WHERE AllyRoom.roomID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, roomID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ally ally = new Ally();
                    ally.setAlliesID(resultSet.getInt("alliesID"));
                    ally.setAlliesName(resultSet.getString("alliesName"));
                    ally.setWeaponName(resultSet.getString("weaponName"));
                    ally.setAlliesHealth(resultSet.getInt("alliesHealth"));
                    ally.setAlliesEnergy(resultSet.getInt("alliesEnergy"));
                    ally.setAlliesDamage(resultSet.getInt("alliesDamage"));

                    roomAllies.add(ally);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving items for room with ID " + roomID);
        }

        return roomAllies;
    }


    /**
     * Method getMap
     * Returns the String of the complete map
     *
     * @return String
     */
    public String getMap() throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();
        StringBuilder map = new StringBuilder();

        String query = "SELECT roomID, roomName, roomDescription FROM Room";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                map.append(resultSet.getInt("roomID")).append(": ")
                        .append(resultSet.getString("roomName")).append(": ")
                        .append(resultSet.getString("roomDescription")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving map.");
        }

        return map.toString();
    }


    /**
     * Method updateRoom
     * Updates the room in the current map
     * throws an exception if the room is not found
     *
     * @param rm - The Room that is being updated
     */
    public void updateRoom(Room rm) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        String query = "UPDATE Room SET roomName = ?, roomDescription = ?, visited = ? WHERE roomID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, rm.getName());
            statement.setString(2, rm.getDescription());
            statement.setInt(3, rm.isVisited());
            statement.setInt(4, rm.getRoomID());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GameException("Room with ID " + rm.getRoomID() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error updating room.");
        }

    }
}