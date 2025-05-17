package seng201.team0.services;

import seng201.team0.models.*;

public class GameEnvironment {
    private Race currentRace;
    private Season currentSeason;
    private Difficulty selectedDifficulty;
    private Car selectedCar;
    private int balance;
    private String name;
    private int seasonLength;
    private final Shop shopInventory;
    private final Garage playerInventory = new Garage();
    private final StarterCarInventory starterCarInventory;
    private final BalanceManager balanceManager;
    private final ControllerLogicManager controllerLogicManager;
    private final int STARTING_BALANCE;

    public GameEnvironment() {
        this.balanceManager = new BalanceManager(this);
        this.controllerLogicManager = new ControllerLogicManager(this);
        this.shopInventory = new Shop();
        this.starterCarInventory = new StarterCarInventory();
        this.STARTING_BALANCE = 1000;
        setupShop();
        setBalance();
        setSelectedCar();
    }

    public void setupShop() {
        shopInventory.setShopInventory();
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    public void setCurrentSeason(Season season) {
        this.currentSeason = season;
    }

    public void setDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
    }

    public void setSelectedCar() {
        this.selectedCar = playerInventory.getSelectedCar();
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

    public Car getSelectedCar() {
        return selectedCar;
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

    public BalanceManager getBalanceManager() {
        return balanceManager;
    }

    public ControllerLogicManager getControllerLogicManager() {
        return controllerLogicManager;
    }


    public void setupStarterCars() {
       starterCarInventory.setupStarterCarInventory();
    }

    public StarterCarInventory getStarterCars() {
        return starterCarInventory;
    }

    public Garage getPlayerInventory() {
        return playerInventory;
    }

    public Shop getShopInventory() {
        return shopInventory;
    }

    public StarterCarInventory getStarterCarInventory() {
        return starterCarInventory;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance() {
        this.balance = STARTING_BALANCE;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
}