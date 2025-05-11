package seng201.team0.gui;

import seng201.team0.*;

public class GameEnvironment {
    private static Course selectedCourse;
    private static Route selectedRoute;
    private static Season currentSeason;

    public static void setSelectedCourse(Course course) {
        selectedCourse = course;
    }

    public static void setSelectedRoute(Route route) {
        selectedRoute = route;
    }

    public static void setCurrentSeason(Season season) {
        currentSeason = season;
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }

    public static Route getSelectedRoute() {
        return selectedRoute;
    }

    public static Season getCurrentSeason() {
        return currentSeason;
    }



    Garage shopInventory = new Garage();
    Garage playerInventory = new Garage();

    public GameEnvironment() {
        setupInventoryList();
        setupShopList();
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
}