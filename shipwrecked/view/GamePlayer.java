package shipwrecked.view;

import shipwrecked.controller.*;
import shipwrecked.gameException.GameException;
import shipwrecked.controller.Commands;



import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * The GamePlayer class facilitates playing the Shipwrecked game.
 */
public class GamePlayer {
    private Scanner input;
    private GameController gc;
    private Commands commands;

    /**
     * Constructs a GamePlayer object, initializing GameController, Scanner, and Commands.
     */
    public GamePlayer() {
        gc = new GameController();
        input = new Scanner(System.in);
        commands = new Commands();
    }

    /**
     * Initiates the gameplay loop.
     *
     * @throws SQLException        if a database access error occurs
     * @throws ClassNotFoundException if the class cannot be located
     * @throws GameException      if an error occurs during gameplay
     */
    public void playGame() throws SQLException, ClassNotFoundException, GameException {
        GameController gc = new GameController(); // Create an instance of GameController
        Player player = new Player();
        Room room = new Room();

        Scanner input = new Scanner(System.in);

        // Prompt for player name
        System.out.print("Enter your name: ");
        String playerName = input.nextLine();
        Player currentPlayer = null;

        try {
            // Attempt to retrieve player from database
            currentPlayer = player.getPlayerByName(playerName);
            System.out.println("Welcome back, " + currentPlayer.getName() + "!");

            // Display first room only if the player's current room is 1
        } catch (GameException e) {
            System.out.println(e.getMessage());
            System.out.println("Would you like to create a new player? (yes/no)");
            String choice = input.nextLine().toLowerCase();
            if (choice.equals("yes")) {
                try {
                    currentPlayer = player.createPlayer(playerName);
                    System.out.println("Player created successfully with ID: " + currentPlayer.getPlayerID());
                } catch (GameException | SQLException | ClassNotFoundException ex) {
                    System.out.println("Error creating player: " + ex.getMessage());
                    System.exit(1); // Exit the program if player creation fails
                }
            } else {
                System.out.println("Goodbye!");
                System.exit(0); // Exit the program if player doesn't want to create a new player
            }
        }

        try {
            commands.addPlayer(currentPlayer.getPlayerID()); // Pass the ID of the newly created player

            System.out.println("Welcome to the game " + commands.getPlayerName());
            if (commands.getPlayerCurrentRoom() == 1) {
                System.out.print(gc.displayFirstRoom());
            } else {
                System.out.print(room.retrieveByID(currentPlayer.getCurRoom()));
            }

            while (true) {

                try {
                    String result = commands.executeCommand(getCommand());
                    System.out.println(result);

                    // Check if the game should exit based on the command result
                    if (result.equals("exit")) {
                        System.out.println("Exiting game...");
                        break; // Exit the loop
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (SQLException | ClassNotFoundException | GameException e) {
            throw new RuntimeException(e);
        } finally {
            input.close();
        }
    }

    /**
     * Retrieves the player's command from the input.
     *
     * @return The command entered by the player
     */
    private String getCommand() {
        System.out.print("Enter your command: ");
        return input.nextLine();
    }

    /**
     * The main method to start the Shipwrecked game.
     *
     * @param args The command-line arguments
     * @throws SQLException        if a database access error occurs
     * @throws ClassNotFoundException if the class cannot be located
     * @throws GameException      if an error occurs during gameplay
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, GameException {
        GamePlayer gameplayer = new GamePlayer(); // Create an instance of GamePlayer
        Player player = new Player();
        gameplayer.gc.start();

        Scanner input = new Scanner(System.in);

        System.out.println("----------------------------------------------------------------------");
        while (true) {
            System.out.println("1. Display Map");
            System.out.println("2. Play Game");
            System.out.println("3. View Current Players");
            System.out.println("4. Exit");
            System.out.println("5. Exit and Delete all Data");

            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        try {
                            System.out.println("\n" + gameplayer.gc.printMap()); // Access gc through the adventure instance
                        } catch (GameException | SQLException | ClassNotFoundException e) {
                            System.out.println("Error printing map: " + e.getMessage());
                        }
                        break;
                    case 2:
                        gameplayer.playGame();
                        break;
                    case 3:
                        player.printPlayers();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        input.close();
                        System.exit(0);
                    case 5:
                        gameplayer.gc.restartGame();
                        System.out.println("Exiting...and Deleting Game All data.............");
                        input.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                input.nextLine();

            }
        }
    }
}