package shipwrecked.controller;

import shipwrecked.gameException.GameException;
import shipwrecked.model.GameDBCreate;
import shipwrecked.model.RoomDB;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;


public class GameController {

    public static final int FIRST_ROOM = 1;
    private Commands commands;


    public GameController() {
        this.commands = new Commands();
    }

    /**
     * Initializes the game by creating the database if it doesn't exist.
     *
     * @throws GameException if an error occurs during the game
     * @throws SQLException if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    public void start() throws GameException, SQLException, ClassNotFoundException {
        GameDBCreate dbCreator = new GameDBCreate();

        File dbFile = new File("src/shipwrecked/shipwrecked.db");
        if (!dbFile.exists()) {
            dbCreator.buildTables();
            System.out.println("Game Created");
        } else {
            System.out.println("Game Loaded");
        }

    }

    /**
     * Restarts the game by deleting the existing database file.
     *
     * @throws GameException if an error occurs during the game
     */
    public void restartGame() throws GameException {
        File dbFile = new File("src/shipwrecked/shipwrecked.db");
        if (dbFile.exists()) {
            if (dbFile.delete()) {
                System.out.println("Game Data will be Deleted");
            } else {
                throw new GameException("Failed to delete database because table is open.");
            }
        } else {
            System.out.println("Database file does not exist.");
        }

    }
    /**
     * Displays information about the first room in the game.
     *
     * @return a string describing the first room
     * @throws GameException if an error occurs during the game
     * @throws SQLException if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */

    public String displayFirstRoom() throws GameException, SQLException, ClassNotFoundException {
        Room room = new Room();
        room.retrieveByID(FIRST_ROOM);
        if (room.retrieveByID(FIRST_ROOM) == null) {
            throw new GameException("First room not found.");

        }
        return 	"You have entered the first Room\n" + room.retrieveByID(FIRST_ROOM) +"\n";


    }

/*
    public String executeCommand(String cmd) throws GameException, SQLException, ClassNotFoundException {
        try {
            return commands.executeCommand(cmd);
        } catch (UnsupportedOperationException e) {
            // This catches UnsupportedOperationException, if any, thrown by commands.executeCommand(cmd)
            throw new GameException("Command execution failed: " + e.getMessage());
        } catch (NumberFormatException e) {
            // This catches NumberFormatException, if any, indicating that the command cannot be parsed as an integer
            return "Invalid command: please enter a valid non-numeric command.";
        }
    }

 */

    /**
     * Displays the game map.
     *
     * @return a string representing the game map
     * @throws GameException if an error occurs during the game
     * @throws SQLException if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    public String printMap() throws GameException, SQLException, ClassNotFoundException {
        RoomDB roomDB = new RoomDB();
        if (roomDB.getMap() == null) {
            throw new GameException("Map data not available.");
        }

        return roomDB.getMap();
    }


/*
    public String getPlayerName() throws SQLException, ClassNotFoundException, GameException {
        if (commands.getPlayerName() != null) {
            return commands.getPlayerName();
        } else {
            return "Unknown Player";
        }	}




    public void setPlayer(Player player) {
        this.commands.setPlayer(player);
    }

 */



}