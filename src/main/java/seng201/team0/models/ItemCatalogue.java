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
    /**
     * A list of cars that are initially locked and become available through gameplay progression.
     */
    private final ObservableList<Car> lockedCarList;
    /**
     * A list of cars that are always available for purchase in the shop from the start of the game.
     */
    private final ObservableList<Car> shopCarList;

    /**
     * Constructs a new ItemCatalogue instance.
     * Initializes the lists for locked and shop-available cars and then sets up the catalogue.
     */
    public ItemCatalogue() {
        super();
        this.lockedCarList = FXCollections.observableArrayList();
        this.shopCarList = FXCollections.observableArrayList();
        setupCatalogue();
    }

    /**
     * Populates the item catalogue with all the cars and tuning parts in the game.
     * This includes cars initially available in the shop, starter cars, and cars that are locked
     * and need to be unlocked through progression.
     */
    public void setupCatalogue() {
        // Starter cars (assuming these are available initially and maybe cheaper/lower tier)
        addCar(new Car("Honda Civic R", 0.6, 0.5, 0.7, 20, 1000));
        addCar(new Car("Mazda MPS", 0.5, 0.7, 0.7, 20, 1000));
        addCar(new Car("Nissan Z", 0.5, 0.6, 0.8, 20, 1000));

        // Cars available in the main shop from the start
        addShopCar(new Car("Toyota Supra", 0.85, 0.85, 0.80, 23, 6500));
        addShopCar(new Car("Mustang", 0.92, 0.78, 0.80, 19, 6800));
        addShopCar(new Car("Ferrari 458", 0.96, 0.96, 0.90, 15, 12500));

        // Tuning parts available in the main shop from the start
        addTuningPart(new TuningPart("Ethanol", 250, "\uD83D\uDCA8", 1.1));
        addTuningPart(new TuningPart("SuperCharger", 1000, "\uD83D\uDCA8", 1.3));
        addTuningPart(new TuningPart("TurboKit", 2500, "\uD83D\uDCA8", 1.5));
        addTuningPart(new TuningPart("StreetWheels", 250, "\uD83C\uDFAE", 1.1));
        addTuningPart(new TuningPart("SportsWheels", 1000, "\uD83C\uDFAE", 1.3));
        addTuningPart(new TuningPart("RacingWheels", 2500, "\uD83C\uDFAE", 1.5));

        // Cars that are initially locked and get unlocked via course completion (example associations)
        addLockedCar(new Car("Dune Drifter", 0.5, 0.8, 0.7, 22, 2200));         //Unlocked by Desert Course
        addLockedCar(new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25, 2800));    //Unlocked by Desert Course
        addLockedCar(new Car("Cliff Climber", 0.6, 0.5, 0.9, 20, 2600));        //Unlocked by Mountain Course
        addLockedCar(new Car("Ridge Racer", 0.5, 0.9, 0.7, 28, 3000));          //Unlocked by Mountain Course
        addLockedCar(new Car("Farmers Flyer", 0.9, 0.4, 0.9, 30, 3800));        //Unlocked by Country Course
        addLockedCar(new Car("Vineyard Viper", 0.6, 0.8, 0.8, 24, 3000));       //Unlocked by Country Course
        addLockedCar(new Car("Alley Cat", 0.8, 0.5, 0.7, 23, 2600));            //Unlocked by City Course
        addLockedCar(new Car("Commuter King", 0.4, 0.7, 0.9, 28, 3300));        //Unlocked by City Course
    }

    /**
     * Adds a car to the list of initially locked cars. These cars are not immediately
     * available in the shop but can be unlocked through gameplay.
     * @param car The {@link Car} to add to the locked list.
     */
    public void addLockedCar(Car car) {
        lockedCarList.add(car);
    }

    /**
     * Gets the list of cars that are currently locked in the catalogue.
     * @return An {@link ObservableList} of locked cars.
     */
    public ObservableList<Car> getLockedCarList() {
        return lockedCarList;
    }

    /**
     * Gets the list of cars that are available for purchase in the main shop.
     * @return An {@link ObservableList} of shop-available cars.
     */
    public ObservableList<Car> getShopCarList() {
        return shopCarList;
    }

    /**
     * Adds a car to the list of cars available in the main shop.
     * @param car The {@link Car} to add to the shop list.
     */
    public void addShopCar(Car car) {
        shopCarList.add(car);
    }
}