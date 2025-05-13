package seng201.team0.gui;

import seng201.team0.*;

public class GameEnvironment {
    private Race currentRace;
    private static Season currentSeason;
    private Difficulty selectedDifficulty;

    private int balance;
    private String name;
    private int seasonLength;

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    public void setCurrentSeason(Season season) {
        currentSeason = season;
    }

    public void setDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
    }

    public Race getCurrentRace() {
        return currentRace;
    }

    public Course getSelectedCourse() {
        return currentRace != null ? currentRace.getCourse() : null;
    }

    public Route getSelectedRoute() {
        return currentRace != null ? currentRace.getRoute() : null;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public Difficulty getDifficulty() {
        return selectedDifficulty;
    }


    Garage shopInventory = new Garage();
    Garage playerInventory = new Garage();

    public GameEnvironment() {
        setupInventoryList();
        setupShopList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSeasonLength(int seasonLength) {
        this.seasonLength = seasonLength;
    }

    public int getSeasonLength() {
        return seasonLength;
    }

    public void setupShopList() {
        Car Toyota = new Car("Toyota Supra", 180, 6, 8, 538, 67000);
        Car Mustang = new Car("Mustang", 250, 6, 6, 400, 89000);
        Car Ferrari = new Car("Ferrari 458", 330, 8, 7, 300, 485000);

        TuningPart Ethanol = new TuningPart("Ethanol", 4000, "\uD83D\uDCA8", 1.2);
        TuningPart SuperCharger = new TuningPart("SuperCharger", 14000, "\uD83D\uDCA8", 1.5);
        TuningPart TurboKit = new TuningPart("TurboKit", 21000, "\uD83D\uDCA8", 1.8);

        TuningPart StreetWheels = new TuningPart("StreetWheels", 2500, "\uD83C\uDFAE", 1.2);
        TuningPart SportWheels = new TuningPart("SportsWheels", 6400, "\uD83C\uDFAE", 1.5);
        TuningPart RacingWheels = new TuningPart("RacingWheels", 10000, "\uD83C\uDFAE", 1.8);


        shopInventory.addCar(Toyota);
        shopInventory.addCar(Mustang);
        shopInventory.addCar(Ferrari);

        shopInventory.addTuningParts(Ethanol);
        shopInventory.addTuningParts(SuperCharger);
        shopInventory.addTuningParts(TurboKit);
        shopInventory.addTuningParts(StreetWheels);
        shopInventory.addTuningParts(SportWheels);
        shopInventory.addTuningParts(RacingWheels);
    }

    public Garage getPlayerInventory() {
        return playerInventory;
    }

    public Garage getShopInventory() {
        return shopInventory;
    }

    public void setupInventoryList() {

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}