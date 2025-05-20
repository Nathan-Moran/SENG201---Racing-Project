package seng201.team0.services;

import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {
    private Race currentRace;
    private Season currentSeason;
    private Difficulty selectedDifficulty;
    private int balance;
    private String name;
    private int seasonLength;
    private final Shop shopInventory;
    private final Garage playerInventory = new Garage();
    private final StarterCarInventory starterCarInventory;
    private final ShopService shopService;
    private final ControllerService controllerService;
    private int racesRemaining;
    private List<Integer> racePlacements = new ArrayList<>();
    private int totalPrizeMoney = 0;

    public GameEnvironment() {
        this.shopService = new ShopService(this);
        this.controllerService = new ControllerService(this);
        this.shopInventory = new Shop();
        this.starterCarInventory = new StarterCarInventory();
        setupShop();
        setBalance();
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

    public void setRacesRemaining(int racesRemaining) {
        this.racesRemaining = racesRemaining;
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
        return playerInventory.getSelectedCar();
    }

    public int getRacesRemaining() {
        return racesRemaining;
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

    public ShopService getShopService() {
        return shopService;
    }

    public ControllerService getControllerService() {
        return controllerService;
    }

    public void setupStarterCars() {
       starterCarInventory.setupStarterCarInventory();
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

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBalance() {
        if (selectedDifficulty != null) {
            this.balance = selectedDifficulty.getStartBudget();
        } else {
            this.balance = 0;
        }
    }

    public void decrementRacesRemaining() {
        if (racesRemaining > 0) {
            racesRemaining--;
        }
    }

    public double getAveragePlacement() {
        if (racePlacements.isEmpty()) {
            return 0; // Avoid division by zero
        }
        int sum = 0;
        for (int placement : racePlacements) {
            sum += placement;
        }
        return (double) sum / racePlacements.size();
    }

    public int getRacesCompleted() {
        return seasonLength - racesRemaining;
    }

    public void addPrizeMoney(int prize) {
        this.totalPrizeMoney += prize;
    }

    public int getTotalPrizeMoney() {
        return this.totalPrizeMoney;
    }

    public void quit() {
        System.exit(0);
    }
}
