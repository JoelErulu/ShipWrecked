package shipwrecked.model;

import shipwrecked.controller.Item;
import shipwrecked.controller.Monster;
import shipwrecked.gameException.GameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: MonsterRoomDB
 * @author Joel erulu
 * @version 1.0
 * Course: ITEC 3860
 * Written: September 20, 2022
 * This class – tracks the items and the room they are in. Since we can have many items in a room,
 * we must use an association table.
 */
public class MonsterRoomDB {

    /**
     * Method: getRoomMonsters
     *
     * This method retrieves the monsters present in the specified room.
     *
     * @param roomID The ID of the room to retrieve monsters from.
     * @return An ArrayList containing the monsters present in the room.
     * @throws GameException If an error occurs while retrieving monsters from the database.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class representing the database driver is not found.
     */
    protected ArrayList<Monster> getRoomMonsters(int roomID) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();
        MonsterDB mdb = new MonsterDB();

        ArrayList<Monster> roomMonsters = new ArrayList<>();
        try (Connection conn = sdb.conn){
            String sql = "SELECT monsterID FROM MonsterRoom WHERE roomID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, roomID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int itemID = rs.getInt("monsterID");
                        // Retrieve item details using itemID and add to roomItems list
                        Monster monster = mdb.getMonster(itemID); // Assuming a method to retrieve Item by ID
                        if (monster != null) {
                            roomMonsters.add(monster);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving items from the database.");
        }
        return roomMonsters;
    }

    /**
     * Method: removeMonster
     * This removes a monster from the room.
     * @param monster
     */
    public void removeMonster(Monster monster) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB(); // Assuming SQLiteDB class for database connection

        try (Connection conn = sdb.conn) {
            String sql = "DELETE FROM MonsterRoom WHERE monsterID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, monster.getMonsterID()); // Assuming getItemID() returns item's ID
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

}