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
     * An {@link ObservableList} storing {@link Car} objects that are initially locked
     * and must be unlocked through game progression (e.g., winning specific courses).
     */
    private final ObservableList<Car> lockedCarListInternal;
    /**
     * An {@link ObservableList} storing {@link Car} objects that are generally available
     * for purchase in the shop from the start of the game or become available through general shop rotation.
     */
    private final ObservableList<Car> shopCarListInternal;
    /**
     * An {@link ObservableList} storing {@link Car} objects that are offered to the player
     * as initial choices at the beginning of the game.
     */
    private final ObservableList<Car> starterCarPool;

    /**
     * Constructs a new ItemCatalogue instance.
     * Initializes the internal observable lists for locked cars, shop cars, and starter cars,
     * and then populates the entire catalogue with predefined game items.
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
     * and need to be unlocked through progression. It also adds all available tuning parts.
     */
    public void setupCatalogue() {
        Car honda = new Car("Honda Civic", 0.6, 0.5, 0.7, 20, 500);
        Car mazda = new Car("Mazda MPS", 0.5, 0.7, 0.7, 20, 500);
        Car nissan = new Car("Nissan Z", 0.5, 0.6, 0.8, 20, 500);

        Car supra = new Car("Toyota Supra", 0.85, 0.85, 0.80, 23, 2000);
        Car mustang = new Car("Mustang", 0.92, 0.8, 0.80, 19, 2500);
        Car ferrari = new Car("Ferrari", 0.96, 0.96, 0.90, 15, 4000);

        Car duneDrifter = new Car("Dune Drifter", 0.7, 0.7, 0.7, 22, 1200);
        Car ridgeRacer = new Car("Ridge Racer", 0.7, 0.8, 0.8, 25, 1500);
        Car vineyardViper = new Car("Vineyard Viper", 0.85, 0.7, 0.9, 30, 2000);
        Car commuterKing = new Car("CommuterKing", 0.9, 0.9, 0.7, 28, 3000);

        starterCarPool.addAll(honda, mazda, nissan);
        shopCarListInternal.addAll(supra, mustang, ferrari);
        lockedCarListInternal.addAll(duneDrifter, ridgeRacer, vineyardViper, commuterKing);

        TuningPart ethanol = new TuningPart("Ethanol", 250, "⚡", 1.1);
        TuningPart superCharger = new TuningPart("SuperCharger", 1000, "⚡", 1.3);
        TuningPart turboKit = new TuningPart("TurboKit", 2000, "⚡", 1.5);
        TuningPart streetWheels = new TuningPart("StreetWheels", 250, "\uD83C\uDFAE", 1.1);
        TuningPart sportsWheels = new TuningPart("SportsWheels", 1000, "\uD83C\uDFAE", 1.3);
        TuningPart racingWheels = new TuningPart("RacingWheels", 2000, "\uD83C\uDFAE", 1.5);


        super.getTuningPartList().addAll(ethanol, superCharger, turboKit, streetWheels, sportsWheels, racingWheels);
    }

    /**
     * Returns the {@link ObservableList} of starter cars available to the player at the beginning of the game.
     * @return An {@link ObservableList} of {@link Car} objects representing the starter car pool.
     */
    public ObservableList<Car> getStarterCarPool() {
        return starterCarPool;
    }

    /**
     * Returns the {@link ObservableList} of cars that are generally available for purchase in the shop.
     * @return An {@link ObservableList} of {@link Car} objects representing the shop's general car list.
     */
    public ObservableList<Car> getShopCarList() {
        return shopCarListInternal;
    }

    /**
     * Returns the {@link ObservableList} of cars that are initially locked and require specific game progression to unlock.
     * @return An {@link ObservableList} of {@link Car} objects representing the locked car list.
     */
    public ObservableList<Car> getLockedCarList() {
        return lockedCarListInternal;
    }
}
