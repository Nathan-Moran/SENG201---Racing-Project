package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a comprehensive catalogue of all cars and tuning parts available in the game,
 * including those that are initially locked and those available in the shop.
 * This class extends {@link ItemStorage} to manage lists of various item types.
 * It serves as a central repository for all game items.
 */
public class ItemCatalogue extends ItemStorage {
    private final ObservableList<Car> lockedCarListInternal;
    private final ObservableList<Car> shopCarListInternal;
    private final ObservableList<Car> starterCarPool;

    /**
     * Constructs a new ItemCatalogue instance.
     * Initializes the lists for locked and shop-available cars and then sets up the catalogue.
     */
    public ItemCatalogue() {
        super();
        this.lockedCarListInternal = FXCollections.observableArrayList();
        this.shopCarListInternal = FXCollections.observableArrayList();
        this.starterCarPool = FXCollections.observableArrayList();
        setupCatalogue();
    }

    /**
     * Populates the item catalogue with all the cars and tuning parts in the game.
     * This includes cars initially available in the shop, starter cars, and cars that are locked
     * and need to be unlocked through progression.
     */
    public void setupCatalogue() {
        Car honda = new Car("Honda", 0.6, 0.5, 0.7, 20, 1000);
        Car mazda = new Car("Mazda", 0.5, 0.7, 0.7, 20, 1000);
        Car nissan = new Car("Nissan", 0.5, 0.6, 0.8, 20, 1000);

        Car supra = new Car("Toyota Supra", 0.85, 0.85, 0.80, 23, 6500);
        Car mustang = new Car("Mustang", 0.92, 0.78, 0.80, 19, 6800);
        Car ferrari = new Car("Ferrari", 0.96, 0.96, 0.90, 15, 12500);

        Car duneDrifter = new Car("Dune Drifter", 0.5, 0.8, 0.7, 22, 2200);
        Car sandstormStrider = new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25, 2800);
        Car cliffClimber = new Car("Cliff Climber", 0.6, 0.5, 0.9, 20, 2600);
        Car ridgeRacer = new Car("Ridge Racer", 0.5, 0.9, 0.7, 28, 3000);

        starterCarPool.addAll(honda, mazda, nissan);
        shopCarListInternal.addAll(supra, mustang, ferrari);
        lockedCarListInternal.addAll(duneDrifter, sandstormStrider, cliffClimber, ridgeRacer);

        TuningPart ethanol = new TuningPart("Ethanol", 250, "\uD83D\uDCA8", 1.1);
        TuningPart superCharger = new TuningPart("SuperCharger", 1000, "\uD83D\uDCA8", 1.3);
        TuningPart turboKit = new TuningPart("TurboKit", 2500, "\uD83D\uDCA8", 1.5);
        TuningPart streetWheels = new TuningPart("StreetWheels", 250, "\uD83C\uDFAE", 1.1);
        TuningPart sportsWheels = new TuningPart("SportsWheels", 1000, "\uD83C\uDFAE", 1.3);
        TuningPart racingWheels = new TuningPart("RacingWheels", 2500, "\uD83C\uDFAE", 1.5);

        super.getTuningPartList().addAll(ethanol, superCharger, turboKit, streetWheels, sportsWheels, racingWheels);
    }


    public ObservableList<Car> getStarterCarPool() {
        return starterCarPool;
    }


    public ObservableList<Car> getShopCarList() { // Already exists
        return shopCarListInternal;
    }


    public ObservableList<Car> getLockedCarList() { // Already exists
        return lockedCarListInternal;
    }
}