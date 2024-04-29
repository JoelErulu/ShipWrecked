package shipwrecked.model;


import shipwrecked.controller.Exit;
import shipwrecked.gameException.GameException;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class: ExitDB
 * @author Joel Erulu
 * @version 1.1
 * Course: ITEC 3860
 * Written: April 15, 2024
 * This class â€“ handles the Exit interaction with the DB. This class uses a buried association to
 * allow the exit to be assigned to a room. An Exit can only be in one room so we don't have to use an
 * association table.
 */
public class ExitDB {

    private SQLiteDB sdb = null;


    /**
     * Gets all the exits of a room
     * @param roomID
     */
    public java.util.ArrayList<Exit> getExits(int roomID) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        ArrayList<Exit> roomExits = new ArrayList<>();
        try (Connection conn = sdb.conn) {
            String sql = "SELECT * FROM Exit WHERE roomID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, roomID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int exitID = rs.getInt("exitID");
                        String direction = rs.getString("direction");
                        int destination = rs.getInt("destination");
                        Exit exit = new Exit();
                        exit.setExitID(exitID);
                        exit.setRoomID(roomID);
                        exit.setDirection(direction);
                        exit.setDestination(destination);
                        roomExits.add(exit);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving exits from the database.");
        }
        return roomExits;
    }
}
