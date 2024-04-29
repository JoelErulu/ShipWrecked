package shipwrecked.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class : DB.java
 * @author: Joel Erulu
 * @version: 1.0
 * Course: ITEC 3860
 * Written: April 11, 2024
 * This class controls basic DB functionality
 * Purpose:Has Query and Update DB
 */
public abstract class DB {

    protected String dbName = "src/shipwrecked/shipwrecked.db";
    protected String sJdbc;
    protected String sDriverName;
    protected java.sql.Connection conn;
    protected String sDbUrl;
    protected int timeout = 5;

    /**
     * Method: queryDB
     * Purpose: read from the database
     * @param sql
     * @return ResultSet
     * @throws SQLException
     */
    public java.sql.ResultSet queryDB(String sql) throws java.sql.SQLException {
        ResultSet rs = null;
        Statement stmt = conn.createStatement();
        stmt.setQueryTimeout(timeout);
        rs = stmt.executeQuery(sql);
        return rs;
    }

    /**
     * Method: updateDB
     * Purpose: Updates the database
     *
     * @param SQL
     * @throws SQLException
     */
    public void updateDB(String SQL) throws java.sql.SQLException {
        Statement stmt = conn.createStatement();
        boolean success = stmt.execute(SQL);
    }

    /**
     * Method: count
     * Purpose: Gets the count of records in the specified table
     * @param table
     * @return int
     */
    public int count(String table) {
        int cnt = 0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "Select count(*) as count from \"" + table + "\"";
            ResultSet rs = stmt.executeQuery(sql);
            cnt = rs.getInt(1);
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
        return cnt;
    }

    /**
     * Method: getMaxValue
     * Purpose: Gets max value for a particular field in a particular table
     * @param columnName
     * @param table
     * @return int
     */
    public int getMaxValue(String columnName, String table) {
        int max = 0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "Select MAX(" + columnName + ") from " + table;
            ResultSet rs = stmt.executeQuery(sql);
            max = rs.getInt(1);
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
        return max;
    }

    /**
     * Method: close
     * Purpose: Close the database connection
     */
    public void close() throws java.sql.SQLException {
        conn.close();
    }

}