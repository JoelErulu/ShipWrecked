package shipwrecked.gameException;

import java.io.*;

/**
 * Class: GameException
 * @author Joel Erulu
 * @version 1.2
 * Course: ITEC 3860 Fall
 * Written: April 12, 2024
 *
 * This class – is the custom exception for the game.
 */
public class GameException extends IOException {

    public GameException() {
        super();
    }

    /**
     *
     * @param message
     */
    public GameException(String message) {
        super(message);
    }

}