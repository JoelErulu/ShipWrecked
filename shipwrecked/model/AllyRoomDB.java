package shipwrecked.model;

import shipwrecked.controller.Ally;
import shipwrecked.controller.Item;
import shipwrecked.gameException.GameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Class: AllyRoomDB
 * @author Joel erulu
 * @version 1.0
 * Course: ITEC 3860
 * Written: April 16 2024
 * This class – tracks the allues and the room they are in. Since we can have many allies in a room,
 * we must use an association table.
 */
public class AllyRoomDB {

    /**
     * Method: getRoomAllies
     * This method gets the allies that are in the room.
     * @param roomID
     * @return ArrayList<ally>
     * @throws GameException
     */
    protected ArrayList<Ally> getRoomAllies(int roomID) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();
        AllyDB aldb = new AllyDB();

        ArrayList<Ally> roomAllies = new ArrayList<>();
        try (Connection conn = sdb.conn){
            String sql = "SELECT alliesID FROM AllyRoom WHERE roomID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, roomID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int alliesID = rs.getInt("alliesID");
                        // Retrieve item details using itemID and add to roomItems list
                        Ally ally = aldb.getAlly(alliesID); // Assuming a method to retrieve Item by ID
                        if (ally != null) {
                            roomAllies.add(ally);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving allies from the database.");
        }
        return roomAllies;
    }

    /**
     * Method: removeAlly
     * This item removes an ally from the room.
     * @param ally
     */
    public void removeAlly(Ally ally) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB(); // Assuming SQLiteDB class for database connection

        try (Connection conn = sdb.conn) {
            String sql = "DELETE FROM AllyRoom WHERE alliesID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, ally.getAlliesID()); // Assuming getItemID() returns item's ID
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new GameException("Allies not found in the room.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error removing allies from the room.");
        }
    }

    /**
     * Method: addAlly
     * This method adds an ally to the room.

     */
    public void addAlly(Ally ally, int roomId) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB(); // Assuming SQLiteDB class for database connection

        try (Connection conn = sdb.conn) {
            String sql = "INSERT INTO AllyRoom (alliesID, roomID) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, ally.getAlliesID());
                pstmt.setInt(2, roomId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new GameException("Failed to add ally to the room.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error adding ally to the room.");
        }
    }

}