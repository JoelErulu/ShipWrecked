package shipwrecked.model;

import shipwrecked.controller.Ally;
import shipwrecked.gameException.GameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerAllyDB {

    private SQLiteDB sdb = null;

    /**
     * Method: addAllyToSquad
     * This method adds an ally to the player's squad in the database.
     * @param playerID The ID of the player.
     * @param ally The ally object to be added.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public void addAllyToSquad(int playerID, Ally ally) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        try (Connection conn = sdb.conn) {
            String sql = "INSERT INTO PlayerAlly (playerID, alliesID) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, playerID);
                pstmt.setInt(2, ally.getAlliesID());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error adding ally to player's squad.");
        }
    }
    /**
     * Method: getAlliesSquad
     * This method retrieves all allies in the player's squad from the database.
     * @param playerID The ID of the player.
     * @return An ArrayList containing all ally objects in the player's squad.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public ArrayList<Ally> getAlliesSquad(int playerID) throws GameException, SQLException, ClassNotFoundException {
        ArrayList<Ally> playerAllies = new ArrayList<>();

        try (Connection conn = new SQLiteDB().conn;
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM Ally JOIN PlayerAlly ON Ally.alliesID = PlayerAlly.alliesID WHERE PlayerAlly.playerID = ?")) {
            pstmt.setInt(1, playerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Ally ally = new Ally();
                    ally.setAlliesID(rs.getInt("alliesID"));
                    ally.setAlliesName(rs.getString("alliesName"));
                    ally.setWeaponName(rs.getString("weaponName"));
                    ally.setAlliesEnergy(rs.getInt("alliesEnergy"));
                    ally.setAlliesDamage(rs.getInt("alliesDamage"));
                    ally.setAlliesHealth(rs.getInt("alliesHealth"));

                    playerAllies.add(ally);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error retrieving player's inventory.");
        }

        return playerAllies;
    }
}
