package shipwrecked.model;

import shipwrecked.controller.Exit;
import shipwrecked.controller.Item;
import shipwrecked.model.ItemDB;
import shipwrecked.gameException.GameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: ItemRoomDB
 * @author Rick Price
 * @version 1.0
 * Course: ITEC 3860
 * Written: September 20, 2022
 * This class – tracks the items and the room they are in. Since we can have many items in a room,
 * we must use an association table.
 */
public class ItemRoomDB {

    /**
     * Method: getRoomItems
     * This method gets the items that are in the room.
     * @param roomID
     * @return ArrayList<Item>
     * @throws GameException
     */
    protected ArrayList<Item> getRoomItems(int roomID) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();
        ItemDB idb = new ItemDB();

        ArrayList<Item> roomItems = new ArrayList<>();
        try (Connection conn = sdb.conn){
            String sql = "SELECT itemID FROM ItemRoom WHERE roomID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, roomID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int itemID = rs.getInt("itemID");
                        // Retrieve item details using itemID and add to roomItems list
                        Item item = idb.getItem(itemID); // Assuming a method to retrieve Item by ID
                        if (item != null) {
                            roomItems.add(item);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving items from the database.");
        }
        return roomItems;
    }

    /**
     * Method: removeItem
     * This item removes an item from the room.
     * @param item
     */
    public void removeItem(Item item) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB(); // Assuming SQLiteDB class for database connection

        try (Connection conn = sdb.conn) {
            String sql = "DELETE FROM ItemRoom WHERE itemID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, item.getItemID()); // Assuming getItemID() returns item's ID
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new GameException("Item not found in the room.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error removing item from the room.");
        }
    }

    /**
     * Method: addItem
     * This method adds an item to the room.
     * @param item
     * @param roomId
     */
    public void addItem(Item item, int roomId) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB(); // Assuming SQLiteDB class for database connection

        try (Connection conn = sdb.conn) {
            String sql = "INSERT INTO ItemRoom (itemID, roomID) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, item.getItemID()); // Assuming getItemID() returns item's ID
                pstmt.setInt(2, roomId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new GameException("Failed to add item to the room.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error adding item to the room.");
        }
    }

}