package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Shop extends ItemStorage {
    private ObservableList<Car> lockedCars;

    public Shop() {
        super();
        this.lockedCars = FXCollections.observableArrayList();
    }

    public void setShopInventory() {
        addCar(new Car("Toyota Supra", 0.85, 0.85, 0.80, 23.0, 6500));
        addCar(new Car("Mustang", 0.92, 0.78, 0.80, 19.0, 6800));
        addCar(new Car("Ferrari 458", 0.96, 0.96, 0.90, 15.0, 12500));

        addTuningPart(new TuningPart("Ethanol", 500, "\uD83D\uDCA8", 1.2));
        addTuningPart(new TuningPart("SuperCharger", 750, "\uD83D\uDCA8", 1.5));
        addTuningPart(new TuningPart("TurboKit", 1750, "\uD83D\uDCA8", 1.8));
        addTuningPart(new TuningPart("StreetWheels", 400, "\uD83C\uDFAE", 1.2));
        addTuningPart(new TuningPart("SportsWheels", 800, "\uD83C\uDFAE", 1.5));
        addTuningPart(new TuningPart("RacingWheels", 1250, "\uD83C\uDFAE", 1.8));

        lockedCars.add(new Car("Dune Drifter", 0.5, 0.8, 0.7, 22.0, 2200));         //Desert Drift
        lockedCars.add(new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25.0, 2800));    //Desert Long
        lockedCars.add(new Car("Cliff Climber", 0.6, 0.5, 0.9, 20.0, 2600));        //Mountain Steep
        lockedCars.add(new Car("Ridge Racer", 0.5, 0.9, 0.7, 28.0, 3000));          //Mountain Curves
        lockedCars.add(new Car("Farmers Flyer", 0.9, 0.4, 0.9, 30.0, 3800));        //Country Straight
        lockedCars.add(new Car("Vineyard Viper", 0.6, 0.8, 0.8, 24.0, 3000));       //Country Twist
        lockedCars.add(new Car("Alley Cat", 0.8, 0.5, 0.7, 23.0, 2600));            //City Alleys
        lockedCars.add(new Car("Commuter King", 0.4, 0.7, 0.9, 28.0, 3300));        //City Traffic


    }
}