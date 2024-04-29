package shipwrecked.controller;

public class Ally {
    private int alliesID;
    private String alliesName;

    private String weaponName;
    private int alliesHealth;
    private int alliesEnergy;
    private int alliesDamage;

    public Ally(int alliesID, String alliesName, String weaponName, int alliesHealth, int alliesEnergy, int alliesDamage) {
        this.alliesID = alliesID;
        this.weaponName = weaponName;
        this.alliesHealth = alliesHealth;
        this.alliesEnergy = alliesEnergy;
        this.alliesDamage = alliesDamage;
    }

    public Ally() {

    }

    public int getAlliesID() {
        return alliesID;
    }

    public void setAlliesID(int alliesWepNumber) {
        this.alliesID = alliesWepNumber;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getAlliesName() {
        return alliesName;
    }

    public void setAlliesName(String alliesName) {
        this.alliesName = alliesName;
    }

    public int getAlliesHealth() {
        return alliesHealth;
    }

    public void setAlliesHealth(int alliesHealth) {
        this.alliesHealth = alliesHealth;
    }

    public int getAlliesEnergy() {
        return alliesEnergy;
    }

    public void setAlliesEnergy(int alliesEnergy) {
        this.alliesEnergy = alliesEnergy;
    }

    public int getAlliesDamage() {
        return alliesDamage;
    }

    public void setAlliesDamage(int alliesDamage) {
        this.alliesDamage = alliesDamage;
    }
}