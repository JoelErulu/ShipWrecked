
package shipwrecked.controller;

import shipwrecked.controller.Player;

public class ScoreBoard {
    private int score;
    private Player player;

    public ScoreBoard(Player player) {
        this.player = player;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int value) {
        this.score += value;
    }

    public void subtractScore(int value) {
        this.score -= value;
    }

    public void displayScore() {
        System.out.println("Current Score: " + this.score);
    }
}
