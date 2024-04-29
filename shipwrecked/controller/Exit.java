package shipwrecked.controller;


import java.util.Arrays;

/**
 * Class: Exit
 * @author Joel erulu
 * @version 1.3
 * Course: ITEC 3860
 * Written: April 15, 2024
 *
 * This class – contains the Exit information. Allows the user to build an exit to be added to the Room
 */
public class Exit {

    private int exitID;
    private int roomID;
    private int destination;
    private String direction;

    private final java.util.List<String> VALID_DIRECTIONS = Arrays.asList(new String[] {"WEST", "NORTH", "SOUTH", "EAST", "UP", "DOWN"});

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getDestination() {
        return this.destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getExitID() {
        return this.exitID;
    }

    public void setExitID(int exitID) {
        this.exitID = exitID;
    }

    @Override
    public String toString() {
        return "Exit ID: " + exitID + "\n"
                + "Room ID: " + roomID + "\n"
                + "Destination: " + destination + "\n"
                + "Direction: " + direction + "\n";
    }
}