package shipwrecked.model;

import shipwrecked.controller.Item;
import shipwrecked.gameException.GameException;

import java.sql.*;

public class ItemDB {



    /**
     * ItemDB constructor
     */
    public ItemDB() {

    }

    /**
     * Method getItem
     * Returns the Item requested by the ID. Only used in readRooms
     * @param id - the ID of the item requested.
     * @return Item - the requested item
     * @throws GameException if the item ID cannot be found
     * @throws SQLException if a database access error occurs
     */
    public Item getItem(int id) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();


        String query = "SELECT * FROM Item WHERE itemID = ?";
        Connection conn = DriverManager.getConnection(sdb.sDbUrl);
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Item item = new Item();
                item.setItemID(resultSet.getInt("itemID"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemDescription(resultSet.getString("itemDescription"));
                item.setItemRestore(resultSet.getInt("itemRestore"));
                item.setItemDamage(resultSet.getInt("itemDamage"));


                return item;
            } else {
                throw new GameException("Item with ID " + id + " not found.");
            }
        }
    }
}
