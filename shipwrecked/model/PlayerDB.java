package shipwrecked.model;

import shipwrecked.controller.Player;
import shipwrecked.controller.Room;
import shipwrecked.gameException.GameException;

import java.sql.*;
import java.util.ArrayList;


/**
 * Class: PlayerDB
 * @author Joel Erulu
 * @version 1.2
 * Course: ITEC 3860
 * Written: April 15 2024
 * This class – tracks the Player interacts with the Player table to maintain the Player status.
 * For this example, only tracks player name and id.
 */
public class PlayerDB {

    private SQLiteDB sdb = null;
    public static final int DEFAULT_DAMAGE = 50;
    public static final int DEFAULT_HEALTH = 5000;
    public static final int FIRST_ROOM_ID = 1;

    /**
     * Method: updatePlayer
     * //	 * This method updates the player passed to it
     *
     * @param player
     */
    public void update(Player player) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        String query = "UPDATE Player SET playerName = ?, currentRoom = ?, playerHealth = ?, playerDamage = ? WHERE playerID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, player.getName());
            statement.setInt(2, player.getCurRoom());
            statement.setInt(3, player.getPlayerHealth());
            statement.setInt(4, player.getPlayerDamage());
            statement.setInt(5, player.getPlayerID());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GameException("Player with ID " + player.getPlayerID() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error updating player.");
        }
    }

    /**
     * Method: updateCurRoom
     * This method updates the current room of the player in the database.
     * @param player The player object whose current room is to be updated.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public void updateCurRoom(Player player) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        String query = "UPDATE Player SET currentRoom = ? WHERE playerID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, player.getCurRoom());
            statement.setInt(2, player.getPlayerID());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GameException("Player with ID " + player.getPlayerID() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error updating player.");
        }
    }

    /**
     * Method: updateHealthAndDamage
     * This method updates the health and damage of the player in the database.
     * @param player The player object whose health and damage are to be updated.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */

    public void updateHealthAndDamage(Player player) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        String query = "UPDATE Player SET playerHealth = ?, playerDamage = ? WHERE playerID = ?";
        try (Connection conn = DriverManager.getConnection(sdb.sDbUrl);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, player.getPlayerHealth());
            statement.setInt(2, player.getPlayerDamage());
            statement.setInt(3, player.getPlayerID());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GameException("Player with ID " + player.getPlayerID() + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error updating player.");
        }
    }

    /**
     * Method: getAllPlayers
     * This method retrieves all players from the database.
     * @return An ArrayList containing all player objects.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public ArrayList<Player> getAllPlayers() throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();
        ArrayList<Player> players = new ArrayList<>();

        String query = "SELECT * FROM Player";
        Connection conn = sdb.conn;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Player player = new Player();
                player.setPlayerID(resultSet.getInt("playerID"));
                player.setName(resultSet.getString("playerName"));
                player.setCurRoom(resultSet.getInt("currentRoom"));
                player.setPlayerHealth(resultSet.getInt("playerHealth"));
                player.setPlayerDamage(resultSet.getInt("playerDamage"));
                players.add(player);
            }
            return players;
        }
    }



    /**
     * Method: getPlayer
     * This method gets the player from the DB
     *
     * @param id - the player ID
     * @return Player - the player object from the DB
     * @throws GameException
     */
    public Player getPlayer(int id) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();


        String query = "SELECT * FROM Player WHERE playerID = ?";
        Connection conn = sdb.conn;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Player player = new Player();
                player.setPlayerID(resultSet.getInt("playerID"));
                player.setName(resultSet.getString("playerName"));
                player.setCurRoom(resultSet.getInt("currentRoom"));
                player.setPlayerHealth(resultSet.getInt("playerHealth"));
                player.setPlayerDamage(resultSet.getInt("playerDamage"));

                return player;
            } else {
                throw new GameException("Player with ID " + id + " not found.");
            }
        }
    }
    /**
     * Method: createPlayer
     * This method creates a new player in the database.
     * @param playerName The name of the new player.
     * @return The newly created player object.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public Player createPlayer(String playerName) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(sdb.sDbUrl);
            String sql = "INSERT INTO Player (playerName, currentRoom, playerHealth, playerDamage) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playerName);
            pstmt.setInt(2, FIRST_ROOM_ID); // Assuming the default current room is 1
            pstmt.setInt(3, DEFAULT_HEALTH); // Default player health
            pstmt.setInt(4, DEFAULT_DAMAGE); // Default player damage

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new GameException("Failed to create new player.");
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
            int lastInsertedPlayerID = rs.getInt(1);

            Player newPlayer = new Player();
            newPlayer.setPlayerID(lastInsertedPlayerID);
            newPlayer.setName(playerName);
            newPlayer.setCurRoom(FIRST_ROOM_ID);
            newPlayer.setPlayerHealth(DEFAULT_HEALTH);
            newPlayer.setPlayerDamage(DEFAULT_DAMAGE);

            return newPlayer;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Method: getPlayerByName
     * This method retrieves a player from the database by their name.
     * @param playerName The name of the player to retrieve.
     * @return The player object retrieved from the database.
     * @throws GameException If there's an error in the game.
     * @throws SQLException If a database access error occurs.
     * @throws ClassNotFoundException If the class is not found.
     */
    public Player getPlayerByName(String playerName) throws GameException, SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();

        String query = "SELECT * FROM Player WHERE playerName = ?";
        Connection conn = sdb.conn;
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Player player = new Player();
                player.setPlayerID(resultSet.getInt("playerID"));
                player.setName(resultSet.getString("playerName"));
                player.setCurRoom(resultSet.getInt("currentRoom"));
                player.setPlayerHealth(resultSet.getInt("playerHealth"));
                player.setPlayerDamage(resultSet.getInt("playerDamage"));
                return player;
            } else {
                throw new GameException("Player with name " + playerName + " not found.");
            }
        }
    }


}

