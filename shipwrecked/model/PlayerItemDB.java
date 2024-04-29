package shipwrecked.model;

import shipwrecked.controller.Ally;
import shipwrecked.controller.Exit;
import shipwrecked.controller.Item;
import shipwrecked.gameException.GameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: PlayerItemDB
 * @author Joel Erulu
 * @version 1.1
 * Course: ITEC 3860
 * Written: April 15, 2024
 * This class – tracks the Player inventory.
 */
public class PlayerItemDB {

    private SQLiteDB sdb = null;

    /**
     * Method: addItemToInventory
     * This method adds the selected item to their inventory
     *
     * @param playerID
     * @param item
     */
    public void addItemToInventory(int playerID, Item item) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        try (Connection conn = sdb.conn) {
            String sql = "INSERT INTO PlayerItem (playerID, itemID) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, playerID);
                pstmt.setInt(2, item.getItemID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error adding item to player's inventory.");
        }
    }

    /**
     * Method: removeItemFromInventory
     * THis method removes an item from the player's inventory
     *
     * @param item
     */
    public void removeItemFromInventory(Item item) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        try (Connection conn = sdb.conn) {
            String sql = "DELETE FROM PlayerItem WHERE itemID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, item.getItemID());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error removing item from player's inventory.");
        }
    }

    /**
     * Method: getInventory
     * This method gets the player's inventory
     *
     * @param playerID
     * @return ArrayList<Item> - items
     * @throws GameException
     */
    public ArrayList<Item> getInventory(int playerID) throws GameException, SQLException, ClassNotFoundException {
        ArrayList<Item> playerItems = new ArrayList<>();

        try (Connection conn = new SQLiteDB().conn;
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM Item JOIN PlayerItem ON Item.itemID = PlayerItem.itemID WHERE PlayerItem.playerID = ?")) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setItemID(rs.getInt("itemID"));
                    item.setItemName(rs.getString("itemName"));
                    item.setItemDescription(rs.getString("itemDescription"));
                    item.setItemRestore(rs.getInt("itemRestore"));
                    item.setItemDamage(rs.getInt("itemDamage"));

                    playerItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving player's inventory.");
        }

        return playerItems;
    }


}