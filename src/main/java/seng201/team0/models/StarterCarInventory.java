package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of starter cars available to the player at the beginning of the game.
 * This class extends {@link ItemStorage} to manage a specific set of {@link Car} objects
 * that are pre-defined as initial choices.
 */
public class StarterCarInventory extends ItemStorage {
    /**
     * Constructs a new StarterCarInventory instance.
     * Calls the superclass constructor to initialize car and tuning part lists.
     */
    public StarterCarInventory() {
        super();
    }

    /**
     * Populates the starter car inventory with a predefined set of initial cars.
     * These cars are typically offered to the player at the start of the game.
     */
    public void setupStarterCarInventory() {
        Car HondaCivicR = new Car("Honda Civic R", 0.6, 0.5, 0.7, 20, 1000);
        Car MazdaMPS = new Car("Mazda MPS", 0.5, 0.7, 0.7, 20, 1000);
        Car NissanZ = new Car("Nissan Z", 0.5, 0.6, 0.8, 20, 1000);

        addCar(HondaCivicR);
        addCar(MazdaMPS);
        addCar(NissanZ);
    }
}