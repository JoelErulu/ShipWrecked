package shipwrecked.controller;

public class Monster {

    private int monsterID;
    private String monsterName;
    private int monsterHealth;
    private int monsterAttack;
    private int monsterPoints;

    // Getter for monsterID
    public int getMonsterID() {
        return monsterID;
    }

    // Setter for monsterID
    public void setMonsterID(int monsterID) {
        this.monsterID = monsterID;
    }

    // Getter for monsterName
    public String getMonsterName() {
        return monsterName;
    }

    // Setter for monsterName
    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    // Getter for monsterHealth
    public int getMonsterHealth() {
        return monsterHealth;
    }

    // Setter for monsterHealth
    public void setMonsterHealth(int monsterHealth) {
        this.monsterHealth = monsterHealth;
    }

    // Getter for monsterAttack
    public int getMonsterAttack() {
        return monsterAttack;
    }

    // Setter for monsterAttack
    public void setMonsterAttack(int monsterAttack) {
        this.monsterAttack = monsterAttack;
    }

    // Getter for monsterPoints
    public int getMonsterPoints() {
        return monsterPoints;
    }

    // Setter for monsterPoints
    public void setMonsterPoints(int monsterPoints) {
        this.monsterPoints = monsterPoints;
    }


    /**
     * Reduces the monster's health by the specified damage amount.
     * If the health drops below 0, it is set to 0.
     *
     * @param damage the amount of damage to be taken by the monster
     */
    public void takeDamage(int damage) {
        monsterHealth -= damage;
        if (monsterHealth < 0) {
            monsterHealth = 0; // Ensure health doesn't go below 0
        }
    }
}


