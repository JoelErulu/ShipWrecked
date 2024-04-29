package shipwrecked.model;

import shipwrecked.gameException.GameException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * This class is responsible for creating database tables needed for the Shipwrecked game.
 */
public class GameDBCreate {

    SQLiteDB sdb;


    /**
     * Builds the Room table by reading SQL statements from a text file.
     *
     * @throws GameException if there is an error creating the Room table
     */
    public void buildTables() throws GameException {
        buildRoom();
        buildPlayer();
        buildPlayerItem();
        buildAlly();
        buildMonster();
        buildItem();
        buildItemRoom();
        buildAllyRoom();
        buildMonsterRoom();
        buildPlayerAlly();
        buildExit();

    }

    public void buildRoom() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/Rooms.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table Room");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void buildMonster() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/Monster.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table Monster");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void buildMonsterRoom() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/MonsterRoom.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void buildExit() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/Exit.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table Exit");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void buildPlayer() throws GameException {
        try {
            sdb = new SQLiteDB();
            String sql = "CREATE TABLE Player(playerID INTEGER PRIMARY KEY AUTOINCREMENT, playerName TEXT NOT NULL, currentRoom INT NOT NULL, playerHealth INT, playerDamage INT)";
            sdb.updateDB(sql);
            sql = "INSERT INTO Player(playerName, currentRoom, playerHealth, playerDamage) VALUES('Default', 1, 5000, 50)";
            sdb.updateDB(sql);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table Player");
        } finally {
            if (sdb != null) {
                try {
                    // Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void buildPlayerItem() throws GameException {
        try {
            sdb = new SQLiteDB();
            String sql = "CREATE TABLE PlayerItem(playerID int not Null, itemID int not null)";
            sdb.updateDB(sql);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table PlayerItem");
        } finally {
            if (sdb != null) {
                try {
                    // Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void buildPlayerAlly() throws GameException {
        try {
            sdb = new SQLiteDB();
            String sql = "CREATE TABLE PlayerAlly(playerID int not Null, alliesID int not null)";
            sdb.updateDB(sql);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table PlayerItem");
        } finally {
            if (sdb != null) {
                try {
                    // Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void buildAlly() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/Ally.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table Exit");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void buildAllyRoom() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/AllyRoom.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table AllyRoom");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public void buildItem() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/Item.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table Item");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void buildItemRoom() throws GameException {
        try {
            sdb = new SQLiteDB();

            FileReader fr;
            try {
                fr = new FileReader("src/shipwrecked/ItemRoom.txt");
                Scanner inFile = new Scanner(fr);
                while (inFile.hasNextLine()) {
                    String sql = inFile.nextLine();
                    sdb.updateDB(sql);
                }
                inFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Close the SQLiteDB connection since SQLite only allows one updater
        } catch (SQLException | ClassNotFoundException ex) {
            throw new GameException("Error creating table ItemRoom");
        }
        finally {
            if (sdb != null) {
                try {
                    //Close the SQLiteDB connection since SQLite only allows one updater
                    sdb.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
