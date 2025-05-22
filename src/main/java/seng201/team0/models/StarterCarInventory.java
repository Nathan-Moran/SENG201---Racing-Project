package seng201.team0.models;

import seng201.team0.services.GameEnvironment;

/**
 * Represents a collection of starter cars available to the player at the beginning of the game.
 * This class extends {@link ItemStorage} to manage a specific set of {@link Car} objects
 * that are pre-defined as initial choices.
 */
public class StarterCarInventory extends ItemStorage {
    private final GameEnvironment gameEnvironment;
    /**
     * Constructs a new StarterCarInventory instance.
     * Calls the superclass constructor to initialize car and tuning part lists.
     * @param gameEnvironment The game environment instance.
     */
    public StarterCarInventory(GameEnvironment gameEnvironment) {
        super();
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Populates the starter car inventory with a predefined set of initial cars.
     * These cars are typically offered to the player at the start of the game.
     */
    public void setupStarterCarInventory() {
        getCarList().clear();
        ItemCatalogue catalogue = gameEnvironment.getItemCatalogue();
        if (catalogue != null) {
            for (Car starterCar : catalogue.getStarterCarPool()) {
                addCar(starterCar);
            }
        }
    }
}
