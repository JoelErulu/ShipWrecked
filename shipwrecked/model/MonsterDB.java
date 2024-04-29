package shipwrecked.model;


import shipwrecked.controller.Monster;
import shipwrecked.gameException.GameException;

import java.sql.*;

/**
 * Class: ItemDB
 * @author Joel Erulu
 * @version 1.0
 * Course: ITEC 3860
 * Written: April 12 2024
 * This class – Item Database class for mini game 3.
 * This allows the other classes to request these items.
 */
public class MonsterDB {



    /**
     * Monster constructor
     */
    public MonsterDB() throws SQLException, ClassNotFoundException {

    }

    /**
     * Method getMonster
     * Returns the Item requested by the ID. Only used in readRooms
     * @param id - the ID of the item requested.
     * @return Item - the requested item
     * @throws GameException if the item ID cannot be found
     * @throws SQLException if a database access error occurs
     */
    public Monster getMonster(int id) throws GameException, SQLException, ClassNotFoundException {
        SQLiteDB sdb = new SQLiteDB();


        String query = "SELECT * FROM Monster WHERE monsterID = ?";
        Connection conn = DriverManager.getConnection(sdb.sDbUrl);
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Monster monster = new Monster();
                monster.setMonsterID(resultSet.getInt("monsterID"));
                monster.setMonsterName(resultSet.getString("monsterName"));
                monster.setMonsterHealth(resultSet.getInt("monsterHealth"));
                monster.setMonsterAttack(resultSet.getInt("monsterAttack"));
                monster.setMonsterPoints(resultSet.getInt("monsterPoints"));

                return monster;
            } else {
                throw new GameException("Monster with ID " + id + " not found.");
            }
        }
    }
}
