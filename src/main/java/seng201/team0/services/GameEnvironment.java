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
    private Shop shopInventory;
    private Garage playerInventory = new Garage();
    private StarterCarInventory starterCarInventory;
    private BalanceManager balanceManager;
    private ControllerLogicManager controllerLogicManager;
    private int STARTING_BALANCE = 1000;

    public GameEnvironment() {
        this.balanceManager = new BalanceManager(this);
        this.controllerLogicManager = new ControllerLogicManager(this);
        this.shopInventory = new Shop();
        this.starterCarInventory = new StarterCarInventory();
        setupShopList();
        setBalance();
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    public void setCurrentSeason(Season season) {
        currentSeason = season;
    }

    public void setDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
    }

    public void setSelectedCar(Car car) {
        this.selectedCar = car;
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


    public void setupShopList() {
        Car Toyota = new Car("Toyota Supra", 0.5, 0.5, 0.8, 15, 2000);
//        Car Mustang = new Car("Mustang", 250, 6, 6, 400, 89000);
        Car Ferrari = new Car("Ferrari 458", 1, 0.7, 0.7, 10, 3000);

        TuningPart Ethanol = new TuningPart("Ethanol", 500, "\uD83D\uDCA8", 1.2);
        TuningPart SuperCharger = new TuningPart("SuperCharger", 750, "\uD83D\uDCA8", 1.5);
        TuningPart TurboKit = new TuningPart("TurboKit", 1750, "\uD83D\uDCA8", 1.8);

        TuningPart StreetWheels = new TuningPart("StreetWheels", 400, "\uD83C\uDFAE", 1.2);
        TuningPart SportWheels = new TuningPart("SportsWheels", 800, "\uD83C\uDFAE", 1.5);
        TuningPart RacingWheels = new TuningPart("RacingWheels", 1250, "\uD83C\uDFAE", 1.8);


        shopInventory.addCar(Toyota);
//        shopInventory.addCar(Mustang);
        shopInventory.addCar(Ferrari);

        shopInventory.addTuningPart(Ethanol);
        shopInventory.addTuningPart(SuperCharger);
        shopInventory.addTuningPart(TurboKit);
        shopInventory.addTuningPart(StreetWheels);
        shopInventory.addTuningPart(SportWheels);
        shopInventory.addTuningPart(RacingWheels);
    }

    public void setupStarterCars() {
        Car HondaCivicR = new Car("Honda Civic R", 0.6, 0.5, 0.7, 20, 1000);
//        Car ToyotaGRCorolla  =  new Car("Toyota Corolla", 180, 7, 8, 500, 32000);
        Car MazdaMPS = new Car("Mazda MPS", 0.5, 0.7, 0.7, 20, 1000);
        Car NissanZ = new Car("Nissan Z", 0.5, 0.6, 0.8, 20, 1000);

        starterCarInventory.addCar(HondaCivicR);
        starterCarInventory.addCar(MazdaMPS);
        starterCarInventory.addCar(NissanZ);
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