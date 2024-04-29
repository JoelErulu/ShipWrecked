package shipwrecked.controller;

import shipwrecked.gameException.GameException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;


public class Commands {


    public static final int EXIT_COMMAND = 5;
    public static final int SLEEP_RESTORE_HEALTH = 20;
    public static final int ROOM_NEEDS_KEY = 37;

    private Player player;
    private boolean isFirstRoomGetUsed = false; // Flag to track if "GET" command is used in the first room

    public static final int DEFAULT_DAMAGE = 50;
    public static final int DEFAULT_HEALTH = 5000;
    private GameController gc;


    /**
     * Method Commands
     * Constructor for the Commands class
     * Instantiates a new player object for tracking inventory in the game
     */
    public Commands() {
        this.player = new Player();
    }

    /**
     * Adds a player to the game with the specified ID.
     *
     * @param playerID the ID of the player to be added
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     * @throws GameException        if an error occurs during the game
     */
    public void addPlayer(int playerID) throws SQLException, ClassNotFoundException, GameException {
        Player newPlayer = player.getPlayer(playerID);
        setPlayer(newPlayer);
    }
    /**
     * Sets the current player.
     *
     * @param player the player object to be set
     */
    protected void setPlayer(Player player) {
        this.player = player;
    }
    /**
     * Gets the current room of the player.
     *
     * @return the ID of the current room
     */
    public int getPlayerCurrentRoom() {
        return this.player.getCurRoom();
    }

    /**
     * Validates the given command and returns its type.
     *
     * @param cmdLine the command line input
     * @return the type of the command
     * @throws GameException if the command is invalid
     */
    private int validateCommand(String cmdLine) throws GameException {
        return switch (cmdLine.toUpperCase()) {
            case "NORTH", "SOUTH", "EAST", "WEST", "UP", "DOWN", "ET", "DS", "LU", "IO", "SOLITUDE", "KEY" ->
                    1; // Movement command
            case "GET"
                    , "REMOVE" -> 2; // Gather commands
            case "EXAMINE" -> 3; // Examine command
            case "INVENTORY" -> 4; // Inventory command
            case "EXIT" -> EXIT_COMMAND; // Exit command
            case "TALK" -> 6; // Talk command
            case "RUN" -> 7; // Run command
            case "HIDE" -> 8; // Hide command
            case "SAVE" -> 9; // Save command
            case "BUILD" -> 10; // Build command
            case "SLEEP" -> 11; // Sleep command
            case "EAT" -> 12; // Eat command
            case "SELL" -> 13; // Sell command
            case "MAP" -> 14; // Map command
            case "HELP" -> 15; // Dodge command
            case "FIGHT" -> 16; // Fight command
            case "HEALTH" -> 17; // Gets the Current Player health
            case "DAMAGE" -> 18; // Gets the Current Player Damage
            case "BEFRIEND" -> 19; // Gets the Room Ally to Join you
            case "ALLIES" -> 20; // Inventory command
            default -> throw new GameException("Invalid command: " + cmdLine);
        };
    }

    /**
     * Executes the given command and returns the result.
     *
     * @param cmd the command to be executed
     * @return the result of the command execution
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    public String executeCommand(String cmd) throws SQLException, ClassNotFoundException {
        try {
            int commandType = validateCommand(cmd); // Validate the command type

            switch (commandType) {
                case 1: // Movement command
                    System.out.println("You are moving to another room");
                    return move(cmd);
                case 2: // Gather command
                    System.out.println("You have chosen a gatherCommand");
                    return gather(cmd);
                case 3: // Examine command
                    System.out.println("You have chosen the examineCommand");
                    return examine(cmd);
                case 4: // Inventory command
                    System.out.println("You have chosen to view your inventory");
                    return player.printInventory();
                case EXIT_COMMAND: // Exit command
                    System.out.println("Exiting game...");
                    // Perform any necessary cleanup or exit logic here
                    System.exit(0); // Exit the program
                case 6: // Talk command
                    System.out.println("You have chosen the talkCommand");
                    return talk(cmd);
                case 7: // Run command
                    System.out.println("You have chosen the runCommand");
                    return run(cmd);
                case 8: // Hide command
                    System.out.println("You have chosen the hideCommand");
                    return hide(cmd);
                case 9: // Save command
                    System.out.println("You have chosen the saveCommand");
                    return save(cmd);
                case 10: // Build command
                    System.out.println("You have chosen the buildCommand");
                    return build(cmd);
                case 11: // Sleep command
                    System.out.println("You have chosen the sleepCommand");
                    return sleep(cmd);
                case 12: // Eat command
                    System.out.println("You have chosen the eatCommand");
                    return eat(cmd);
                case 13: // Sell command
                    System.out.println("You have chosen the sellCommand");
                    return sell(cmd);
                case 14: // Map command
                    System.out.println("You have chosen the mapCommand");
                    return map(cmd);
                case 15: // Help command
                    System.out.println("You have chosen the HelpCommand");
                    return help(cmd);
                case 16: // Fight command
                    System.out.println("You have chosen the fightCommand");
                    return fight(cmd);
                case 17: // Health command
                    System.out.println("You have chosen to View your Current Health");
                    return playerHeathView();
                case 18: // Damage command
                    System.out.println("You have chosen to view your Current Damage");
                    return playerDamageView();
                case 19: // Befriend command
                    System.out.println("You have chosen the befriendCommand");
                    return befriend(cmd);
                case 20: // View Your Current Allies
                    System.out.println("You have chosen the befriendCommand");
                    return player.printAllies();
                default:
                    throw new GameException("Invalid command: " + cmd);
            }
        } catch (NumberFormatException e) {
            // Catch and handle the NumberFormatException
            System.out.println("Invalid command.");
            return ""; // Or return any appropriate message
        } catch (GameException e) {
            // Catch and handle the GameException
            System.out.println("Invalid command.");
            return ""; // Or return any appropriate message
        }

        // Add a default return statement to handle the case where none of the switch cases match
    }

    /**
     * Moves the player to another room based on the specified direction.
     *
     * @param cmdRoom the command specifying the direction to move
     * @return a message indicating the result of the move
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String move(String cmdRoom) throws GameException, SQLException, ClassNotFoundException {
        cmdRoom = cmdRoom.toUpperCase();

        char direction = cmdRoom.charAt(0);
        Room room = new Room();

        // Get the ID of the current room
        int currentRoomID = player.getCurRoom();

        // Retrieve the room object for the current room
        Room currentRoom = room.retrieveByID(currentRoomID);

        // Check if there are monsters in the current room
        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();

        if (!roomMonsters.isEmpty()) {
            // Check if all monsters in the room are dead
            boolean allMonstersDead = true;
            for (Monster monster : roomMonsters) {
                if (monster.getMonsterHealth() > 0) {
                    allMonstersDead = false;
                    break;
                }
            }
            if (!allMonstersDead) {
                return "You cannot move because there are monsters in the room.";
            }
        }

        // Check if the destination room is Room 37
        if (currentRoomID == ROOM_NEEDS_KEY) {
            // Check if the player's inventory contains the "Goblin Boss key"
            boolean hasGoblinBossKey = player.getInventory().stream()
                    .anyMatch(item -> item.getItemName().equalsIgnoreCase("KEY"));
            if (!hasGoblinBossKey) {
                return "You need the goblin key. Go back to your previous save point";
            }
        }

// Check if the destination room requires the forest key fragments
        if (currentRoomID == ROOM_NEEDS_KEY) {
            // Check if the player's inventory contains all four forest key fragments
            boolean hasFragment1 = player.getInventory().stream()
                    .anyMatch(item -> item.getItemName().equalsIgnoreCase("forest key fragment 1"));
            boolean hasFragment2 = player.getInventory().stream()
                    .anyMatch(item -> item.getItemName().equalsIgnoreCase("forest key fragment 2"));
            boolean hasFragment3 = player.getInventory().stream()
                    .anyMatch(item -> item.getItemName().equalsIgnoreCase("forest key fragment 3"));
            boolean hasFragment4 = player.getInventory().stream()
                    .anyMatch(item -> item.getItemName().equalsIgnoreCase("forest key fragment 4"));

            // Check if the player has all four fragments
            if (!(hasFragment1 && hasFragment2 && hasFragment3 && hasFragment4)) {
                return "You need all four forest key fragments to pass.";
            }
        }




        // Validate the direction
        int destinationRoomID = currentRoom.validDirection(direction);

        // If the destination room is valid
        if (destinationRoomID != -1) {
            // Get the room object for the destination room
            Room destinationRoom = room.retrieveByID(destinationRoomID);
            player.setCurRoom(destinationRoomID);

            // Construct the message with the updated visit count
            StringBuilder message = new StringBuilder();
            message.append(destinationRoom.display()).append("\n");

            if (currentRoom.isVisited() > 0) {
                System.out.println("Visited");
            } else {
                System.out.println("Not Visited");
            }

            currentRoom.setVisited(currentRoom.isVisited() + 1); // Increment the visit count
            currentRoom.updateRoom();
            return message.toString();

        } else {
            return "You cannot move in that direction.";

        }

    }

    /**
     * Handles gathering items or removing items from the current room or inventory.
     *
     * @param gatherCmd the command specifying the action to perform (GET or REMOVE)
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String gather(String gatherCmd) throws GameException, SQLException, ClassNotFoundException {
        Room room = new Room();
        String action = gatherCmd.toUpperCase();

        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        return switch (action) {
            case "GET" -> {
                isFirstRoomGetUsed = (currentRoomID == 1);
                yield get(gatherCmd, currentRoom);
            }
            case "REMOVE" -> remove(gatherCmd, currentRoom);
            default -> throw new GameException("Invalid item action: " + action);
        };
    }


    /**
     * Handles the action of gathering items from the room.
     *
     * @param cmd  the command specifying the item to get
     * @param room the current room object
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String get(String cmd, Room room) throws GameException, SQLException, ClassNotFoundException {
        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        // Check if there are monsters in the room
        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();
        if (!roomMonsters.isEmpty()) {
            // Check if all monsters in the room are dead
            boolean allMonstersDead = true;
            for (Monster monster : roomMonsters) {
                if (monster.getMonsterHealth() > 0) {
                    allMonstersDead = false;
                    break;
                }
            }
            if (!allMonstersDead) {
                return "You cannot get items while there are monsters in the room.";
            }
        }

        // Proceed with getting items if there are no monsters or all monsters are dead
        ArrayList<Item> roomItems = room.getRoomItems();
        for (Item item : roomItems) {
            if (item.getItemName().equalsIgnoreCase(item.getItemName())) {
                player.addItem(item);
                room.removeItem(item);
                if (item.getItemDamage() > player.getPlayerDamage()) {
                    player.setPlayerDamage(item.getItemDamage());
                    player.updatePlayerHealthAndDamage();

                    System.out.println("Your Damage Has Increased");

                }
                return "You got " + item.getItemName() + " from the room.";
            }
        }
        return "No such item found in the room.";
    }
    /**
     * Handles the action of befriending allies in the room.
     *
     * @param cmd the command specifying the action to perform (BEFRIEND)
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String befriend(String cmd) throws GameException, SQLException, ClassNotFoundException {
        Room room = new Room();
        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        // Check if there are monsters in the room
        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();
        if (!roomMonsters.isEmpty()) {
            // Check if all monsters in the room are dead
            boolean allMonstersDead = true;
            for (Monster monster : roomMonsters) {
                if (monster.getMonsterHealth() > 0) {
                    allMonstersDead = false;
                    break;
                }
            }
            if (!allMonstersDead) {
                return "You cannot befriend allies while there are monsters in the room.";
            }
        }

        ArrayList<Ally> roomAllies = currentRoom.getRoomAllies();
        for (Ally ally : roomAllies) {
            player.addAllies(ally);
            room.removeAllies(ally);
            if (ally.getAlliesDamage() > player.getPlayerDamage()) {
                player.setPlayerDamage(ally.getAlliesDamage());
                player.updatePlayerHealthAndDamage();

                System.out.println("Your Damage Has Increased");
            }
            return "You got " + ally.getAlliesName() + " from the room.";
        }
        return "No such ally found in the room.";

    }

    /**
     * Handles the action of removing items from the player's inventory.
     *
     * @param cmd  the command specifying the action to perform (REMOVE)
     * @param room the current room object
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String remove(String cmd, Room room) throws GameException, SQLException, ClassNotFoundException {
        // Assuming "player" and "room" are class attributes

        // Get the current room ID from the player
        int currentRoomID = player.getCurRoom();

        // Retrieve the Room object associated with the current room ID
        Room currentRoom = room.retrieveByID(currentRoomID); // You need to implement this method

        ArrayList<Item> playerItems = player.getInventory();
        if (playerItems.isEmpty()) {
            return "There are no items in your inventory to remove.";
        } else {
            for (Item item : playerItems) {
                currentRoom.dropItem(item, currentRoom);
                player.removeItem(item);
                if (item.getItemDamage() > player.getPlayerDamage()) {
                    player.setPlayerDamage(DEFAULT_DAMAGE);
                    player.updatePlayerHealthAndDamage();
                }

            }
            currentRoom.updateRoom();

            return "Items from the room have removed from your Inventory";
        }
    }

    /**
     * Handles the action of examining the current room for items, monsters, and allies.
     *
     * @param cmd the command specifying the action to perform (EXAMINE)
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String examine(String cmd) throws GameException, SQLException, ClassNotFoundException {
        char action = Character.toUpperCase(cmd.charAt(0));
        Room room = new Room();

        int currentRoomID = player.getCurRoom();

        Room currentRoom = room.retrieveByID(currentRoomID);
        ArrayList<Item> roomItems = currentRoom.getRoomItems();
        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();
        ArrayList<Ally> roomAllies = currentRoom.getRoomAllies();


        for (Item item : roomItems) {
            System.out.println(" Name: " + item.getItemName() + " Description: " + item.getItemDescription() + " Restore: " + item.getItemRestore() + " Damage: " + item.getItemDamage());
        }

        for (Monster monster : roomMonsters) {
            System.out.println(" Name: " + monster.getMonsterName() + " Monster Health: " + monster.getMonsterHealth() + " Monster Attack: " + monster.getMonsterAttack());
        }

        for (Ally ally : roomAllies) {
            System.out.println(" Name: " + ally.getAlliesName() + " Ally Health: " + ally.getAlliesHealth() + " Ally Attack: " + ally.getAlliesDamage());
        }

        return "You have examined the Room";
    }

    /**
     * Handles the action of engaging in combat with monsters in the room.
     *
     * @param cmd the command specifying the action to perform (FIGHT)
     * @return a message indicating the result of the action
     * @throws GameException if an error occurs during the game
     */
    private String fight(String cmd) throws GameException {
        Room room = new Room();
        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();

        if (roomMonsters.isEmpty()) {
            System.out.println("They are no monsters to fight in this room");
        } else {
            StringBuilder fightResult = new StringBuilder();
            for (Monster monster : roomMonsters) {
                fightResult.append("You are fighting ").append(monster.getMonsterName()).append("...\n");

                // Calculate damage dealt by player and monster
                int playerDamageDealt = player.getPlayerDamage();
                int monsterDamageDealt = monster.getMonsterAttack();

                // Simulate battle
                while (player.getPlayerHealth() > 0 && monster.getMonsterHealth() > 0) {
                    // Player attacks
                    monster.takeDamage(playerDamageDealt);
                    fightResult.append("You dealt ").append(playerDamageDealt).append(" damage to ").append(monster.getMonsterName()).append(".\n");

                    // Check if monster is defeated
                    if (monster.getMonsterHealth() <= 0) {
                        fightResult.append("You have defeated ").append(monster.getMonsterName()).append("!\n");
                        currentRoom.removeMonster(monster);
                        currentRoom.updateRoom();
                        player.updatePlayerHealthAndDamage();
                        break;
                    }

                    // Monster attacks
                    player.takeDamage(monsterDamageDealt);
                    fightResult.append(monster.getMonsterName()).append(" dealt ").append(monsterDamageDealt).append(" damage to you.\n");

                    // Check if player is defeated
                    if (player.getPlayerHealth() <= 0) {
                        fightResult.append("You have been defeated by ").append(monster.getMonsterName()).append(". Exit the Game and restart at your previous Save point !\n");
                        break;
                    }
                }
            }
            return fightResult.toString();
        }

        return "Fight Scenario is Over";
    }

    /**
     * Handles the action of talking to NPCs or allies in the room.
     *
     * @param cmd the command specifying the action to perform (TALK)
     * @return a message indicating the result of the action
     * @throws GameException if an error occurs during the game
     */
    private String talk(String cmd) throws GameException {
        Room room = new Room();
        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        ArrayList<Ally> allies = currentRoom.getRoomAllies();
        ArrayList<Monster> monsters = currentRoom.getRoomMonsters();
        ArrayList<Item> items = currentRoom.getRoomItems();


        if (allies.isEmpty() && monsters.isEmpty() && items.isEmpty()) {
            return "I should probably choose a direction to go in..........";
        }
        if (!monsters.isEmpty()) {
            // If there are no allies or NPCs, player talks to themselves and receives hints
            return "I should probably fight the monster................";
        }
        if (!items.isEmpty()) {
            // If there are no allies or NPCs, player talks to themselves and receives hints
            return "I should probably grab some items................";
        }


        return "";
    }
    /**
     * Handles the action of attempting to run away from monsters in the room.
     *
     * @param cmd the command specifying the action to perform (RUN)
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String run(String cmd) throws GameException, SQLException, ClassNotFoundException {
        Room room = new Room();
        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();

        if (!roomMonsters.isEmpty()) {
            int totalDamageTaken = 0;

            for (Monster monster : roomMonsters) {
                int damageTaken = monster.getMonsterAttack();
                totalDamageTaken += damageTaken;
            }

            player.takeDamage(totalDamageTaken);

            // Check if the player is defeated
            if (player.getPlayerHealth() <= 0) {
                return "You have been defeated by the monsters. Exit the Game and restart at your previous Save point!";
            } else {
                int previousRoomID = player.getCurRoom() - 1;
                player.setCurRoom(previousRoomID);
                player.updatePlayerHealthAndDamage();

                return "You ran away from the monsters but took " + totalDamageTaken + " total damage. You returned to the previous room.\n" + room.retrieveByID(previousRoomID);
            }
        }

        return "There are no monsters to run away from in this room.";
    }

    /**
     * Handles the action of hiding from enemies or threats in the room.
     *
     * @param cmd the command specifying the action to perform (HIDE)
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String hide(String cmd) throws GameException, SQLException, ClassNotFoundException {
        Room room = new Room();
        int currentRoomID = player.getCurRoom();
        Room currentRoom = room.retrieveByID(currentRoomID);

        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();

        if(roomMonsters.isEmpty()) {
            return "There are no monsters to hide from in this room.";
        } else {
            // Generate a random number between 0 and 100
            int randomNum = new Random().nextInt(100);

            for (Monster monster : roomMonsters) {
                String monsterName = monster.getMonsterName().toLowerCase();

                if (monsterName.equals("horse eater")) {
                    return "You cannot hide from the Horse Eater.";
                } else if (monsterName.equals("goblin boss") || monsterName.equals("tiger")) {
                    // 50% chance to successfully hide from Goblin Boss or Tiger
                    if (randomNum < 50) {
                        return "You have successfully hidden from the " + monsterName + ".";
                    } else {
                        return "You failed to hide from the " + monsterName + ".";
                    }
                } else {
                    // 80% chance to successfully hide from common enemies
                    if (randomNum < 80) {
                        return "You have successfully hidden from the " + monsterName + ".";
                    } else {
                        return "You failed to hide from the " + monsterName + ".";
                    }
                }
            }
        }
        return "Command Still In Development";
    }


    /**
     * Saves the current game progress.
     *
     * @param cmd the command specifying the action to perform (SAVE)
     * @return a message indicating the result of the action
     */
    private String save(String cmd) {
        try {
            Room room = new Room();
            int currentRoomID = player.getCurRoom();
            Room currentRoom = room.retrieveByID(currentRoomID);

            // Check if the current room's ID is valid for saving
            if (isValidSaveRoom(currentRoom.getRoomID())) {
                player.updatePlayerCurRoom();

                return "Game saved successfully.";

            } else
                return "Saving is not allowed in this room.";


        } catch (GameException e) {
            // Handle exceptions appropriately
            e.printStackTrace(); // For debugging purposes, you can replace this with proper error handling
            return "An error occurred while saving the game.";
        }
    }
    /**
     * Checks if the current room is valid for saving the game progress.
     *
     * @param roomID the ID of the current room
     * @return true if the room is valid for saving, otherwise false
     */
    private boolean isValidSaveRoom(int roomID) {
        // Define valid room IDs for saving
        int[] validRoomIDs = {7, 12,23, 36};
        for (int validID : validRoomIDs) {
            if (roomID == validID) {
                return true;
            }
        }
        return false;
    }

    private String build(String cmd) {
        // Implement logic to construct or create objects
        return "Command Still in Development, Currently not Available";
    }

    /**
     * Handles the action of eating edible items to restore health.
     *
     * @param cmd the command specifying the action to perform (EAT)
     * @return a message indicating the result of the action
     * @throws GameException        if an error occurs during the game
     * @throws SQLException        if an SQL exception occurs
     * @throws ClassNotFoundException if the class is not found
     */
    private String eat(String cmd) throws GameException, SQLException, ClassNotFoundException {
        ArrayList<Item> playerItems = player.getInventory();
        boolean foundEdibleItem = false;

        for (Item item : playerItems) {
            if (item.getItemRestore() > 0) {
                int restore = item.getItemRestore();
                int newHealth = player.getPlayerHealth() + restore;
                player.setPlayerHealth(newHealth);
                System.out.println("You have restored your health by " + restore + " from item " + item.getItemName());
                foundEdibleItem = true;
                player.updatePlayerHealthAndDamage();
                player.removeItem(item);
            }
        }

        if (foundEdibleItem) {
            return "You have finished eating.";
        } else {
            return "No edible items to eat.";
        }
    }



    /**
     * Handles the action of restoring health by sleeping.
     *
     * @param cmd the command specifying the action to perform (SLEEP)
     * @return a message indicating the result of the action
     * @throws GameException if an error occurs during the game
     */
    private String sleep(String cmd) throws GameException {
        // Get the current room's ID
        int currentRoomID = player.getCurRoom();

        // Retrieve the room object for the current room
        Room room= new Room();
        Room currentRoom = room.retrieveByID(currentRoomID);

        // Check if the current room has monsters
        ArrayList<Monster> roomMonsters = currentRoom.getRoomMonsters();
        if (!roomMonsters.isEmpty()) {
            return "You cannot sleep here. There are monsters in the room!";
        }

        // Restore player's health
        int restoredHealth = player.getPlayerHealth() + SLEEP_RESTORE_HEALTH;
        player.setPlayerHealth(restoredHealth);
        player.updatePlayer();

        return "You have slept and restored " + SLEEP_RESTORE_HEALTH  + " health points.";
    }



    private String sell(String cmd) {
        // Implement logic to sell items for in-game currency
        return "Command Still in Development, Currently not Available";
    }
    /**
     * Returns the map of the game.
     *
     * @param cmd The command string (not used in this method).
     * @return The map of the game as a string.
     * @throws SQLException          If a database access error occurs.
     * @throws ClassNotFoundException If the database driver class is not found.
     * @throws GameException         If an error occurs during game processing.
     */
    private String map(String cmd) throws SQLException, ClassNotFoundException, GameException {
        GameController gc = new GameController();
        return gc.printMap();
    }

    private String dodge(String cmd) {
        // Implement logic to dodge attacks or obstacles
        return "Command Still in Development, Currently not Available";
    }
    /**
     * Provides explanations for each command.
     *
     * @param cmd The command string (not used in this method).
     * @return The help message containing explanations for each command.
     */
    private String help(String cmd) {
        // Provide explanations for each command
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Available Commands:\n");
        helpMessage.append("MOVEMENT: NORTH, SOUTH, EAST, WEST, UP, DOWN\n");
        helpMessage.append("    Move in the specified direction.\n");
        helpMessage.append("GATHER: GET, REMOVE\n");
        helpMessage.append("    Collect items from the room or remove items from your inventory.\n");
        helpMessage.append("EXAMINE\n");
        helpMessage.append("    Examine the current room for items and monsters.\n");
        helpMessage.append("INVENTORY\n");
        helpMessage.append("    View your current inventory.\n");
        helpMessage.append("EXIT\n");
        helpMessage.append("    Exit the game.\n");
        helpMessage.append("TALK\n");
        helpMessage.append("    Engage in conversation with NPCs.\n");
        helpMessage.append("RUN\n");
        helpMessage.append("    Attempt to run from a monster but suffer a single attack from it\n");
        helpMessage.append("HIDE\n");
        helpMessage.append("    Hide from enemies or threats.\n");
        helpMessage.append("SAVE\n");
        helpMessage.append("    Save the game progress.\n");
        helpMessage.append("BUILD\n");
        helpMessage.append("    Construct or create objects.\n");
        helpMessage.append("SLEEP\n");
        helpMessage.append("    Restore health by sleeping.\n");
        helpMessage.append("EAT\n");
        helpMessage.append("    Consume edible items to restore health.\n");
        helpMessage.append("SELL\n");
        helpMessage.append("    Sell items for in-game currency.\n");
        helpMessage.append("MAP\n");
        helpMessage.append("    View the game map.\n");
        helpMessage.append("HELP\n");
        helpMessage.append("    Display available commands and their explanations.\n");
        helpMessage.append("FIGHT\n");
        helpMessage.append("    Engage in combat with monsters.\n");
        helpMessage.append("HEALTH\n");
        helpMessage.append("    View your current health.\n");
        helpMessage.append("DAMAGE\n");
        helpMessage.append("    View your current weapon damage.\n");
        helpMessage.append("PUZZLE: ET, DS, LU, IO, SOLITUDE, KEY\n");
        helpMessage.append("    Solve puzzles in the game.\n");
        helpMessage.append("BEFRIEND\n");
        helpMessage.append("    Collect Allies Throughout the Journey which Boost Damage.\n");
        helpMessage.append("ALLIES\n");
        helpMessage.append("    View your current allies.\n");
        return helpMessage.toString();
    }

    /**
     * Returns the current player's health.
     *
     * @return A string indicating the current player's health.
     */
    private String playerHeathView() {
        return "Current Player Health is " + player.getPlayerHealth();
    }

    /**
     * Returns the current player's damage.
     *
     * @return A string indicating the current player's damage.
     */
    private String playerDamageView() {
        return "Current Player Damage is " + player.getPlayerDamage();
    }
    /**
     * Retrieves the player's name.
     *
     * @return The player's name.
     * @throws SQLException          If a database access error occurs.
     * @throws ClassNotFoundException If the database driver class is not found.
     * @throws GameException         If an error occurs during game processing.
     */
    public String getPlayerName() throws SQLException, ClassNotFoundException, GameException {
        addPlayer(player.getPlayerID());
        return  player.getName();
    }

}