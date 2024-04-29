
package shipwrecked.model;

import shipwrecked.controller.Ally;
import shipwrecked.gameException.GameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllyDB {
    private SQLiteDB sdb;

    public AllyDB() throws SQLException, ClassNotFoundException {
        sdb = new SQLiteDB();
    }

    public Ally getAlly(int id) throws GameException {
        String query = "SELECT * FROM Ally WHERE alliesID = ?";
        try (Connection conn = sdb.conn; PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Ally ally = new Ally();
                ally.setAlliesID(resultSet.getInt("alliesID"));
                ally.setAlliesName(resultSet.getString("alliesName"));
                ally.setWeaponName(resultSet.getString("weaponName"));
                ally.setAlliesHealth(resultSet.getInt("alliesHealth"));
                ally.setAlliesEnergy(resultSet.getInt("alliesEnergy"));
                ally.setAlliesDamage(resultSet.getInt("alliesDamage"));
                return ally;
            } else {
                throw new GameException("Ally with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GameException("Error getting ally information.");
        }
    }

}
