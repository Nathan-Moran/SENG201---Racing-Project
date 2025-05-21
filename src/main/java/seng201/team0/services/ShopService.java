package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Course;
import seng201.team0.models.TuningPart;

import java.util.List;

/**
 * Handles all business logic related to the in-game shop, including buying, selling,
 * and unlocking cars and tuning parts. It interacts with the {@link GameEnvironment}
 * to update player balance and inventories.
 */
public class ShopService {
    /**
     * The central game environment managing the overall game state and inventories.
     */
    GameEnvironment gameEnvironment;

    /**
     * Constructs a new ShopService with a reference to the main game environment.
     * @param gameEnvironment The {@link GameEnvironment} instance for the current game session.
     */
    public ShopService(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Checks if the player's current balance is sufficient to cover a given price.
     * Also handles edge cases where price or balance might be negative.
     *
     * @param price The cost of the item to check against the player's balance.
     * @return {@code true} if the player does NOT have enough balance (or if inputs are invalid), {@code false} otherwise.
     */
    public boolean notEnoughBalance(int price) {
        // A negative price or balance is an invalid state, considered "not enough balance" to prevent exploits
        if ((price < 0) || (gameEnvironment.getBalance() < 0)) {
            return true;
        }
        return price > gameEnvironment.getBalance();
    }

    /**
     * Handles the selection and purchase of a starter car at the beginning of the game.
     * Deducts the car's price from the player's balance and adds the car to their garage.
     *
     * @param selectedCar The {@link Car} chosen by the player as their starter car.
     */
    public void chooseStarterCar(Car selectedCar) {
        // Ensure a car is selected and player can afford it
        if (selectedCar != null && !notEnoughBalance(selectedCar.getPrice())) {
            // If no car is currently selected (first car choice), set it as the starter
            if (gameEnvironment.getSelectedCar() == null) {
                gameEnvironment.getStarterCarInventory().removeCar(selectedCar);
                gameEnvironment.getPlayerInventory().setStarterCar(selectedCar); // Set as the active selected car
            } else {
                // If a car is already selected, add the new car to the general garage inventory
                gameEnvironment.getStarterCarInventory().removeCar(selectedCar);
                gameEnvironment.getPlayerInventory().addCar(selectedCar);
            }
            // Deduct cost
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice());
        }
    }

    /**
     * Handles the purchase of a selected car from the shop.
     * Removes the car from the shop inventory, adds it to the player's garage,
     * and deducts its price from the player's balance.
     *
     * @param selectedCar The {@link Car} to be bought.
     */
    public void buySelectedCar(Car selectedCar) {
        if (selectedCar != null && !notEnoughBalance(selectedCar.getPrice())) {
            gameEnvironment.getShopInventory().removeCar(selectedCar); // Remove from shop
            gameEnvironment.getPlayerInventory().addCar(selectedCar);   // Add to player's garage
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice()); // Deduct cost
        }
    }

    /**
     * Handles the purchase of a selected tuning part from the shop.
     * Removes the part from the shop inventory, adds it to the player's garage,
     * and deducts its price from the player's balance.
     *
     * @param selectedPart The {@link TuningPart} to be bought.
     */
    public void buySelectedPart(TuningPart selectedPart) {
        if (selectedPart != null && !notEnoughBalance(selectedPart.getPrice())) {
            gameEnvironment.getShopInventory().removeTuningPart(selectedPart); // Remove from shop
            gameEnvironment.getPlayerInventory().addTuningPart(selectedPart);   // Add to player's garage
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedPart.getPrice()); // Deduct cost
        }
    }

    /**
     * Handles the sale of a selected car from the player's garage back to the shop.
     * Removes the car from the player's garage, adds it back to the shop inventory,
     * and adds its price to the player's balance.
     *
     * @param selectedCar The {@link Car} to be sold.
     */
    public void sellSelectedCar(Car selectedCar) {
        if (selectedCar != null) {
            // Important: Logic for selling the *selected* car needs to be handled by Garage
            // (e.g., if it's the only car, prevent selling). This method assumes the car
            // is not the currently active selected car that cannot be unselected.
            gameEnvironment.getPlayerInventory().removeCar(selectedCar); // Remove from player's garage
            gameEnvironment.getShopInventory().addCar(selectedCar);     // Add back to shop (for simplicity, assumed infinite shop capacity)
            gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedCar.getPrice()); // Add price back to player
        }
    }

    /**
     * Handles the sale of a selected tuning part from the player's garage back to the shop.
     * Removes the part from the player's garage, adds it back to the shop inventory,
     * and adds its price to the player's balance.
     *
     * @param selectedPart The {@link TuningPart} to be sold.
     */
    public void sellSelectedPart(TuningPart selectedPart) {
        if (selectedPart != null) {
            // Important: Logic for selling *installed* parts needs to be handled by Garage uninstall logic first
            // This method assumes the part is in the player's general tuning part inventory.
            gameEnvironment.getPlayerInventory().removeTuningPart(selectedPart); // Remove from player's garage
            gameEnvironment.getShopInventory().addTuningPart(selectedPart);     // Add back to shop
            gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedPart.getPrice()); // Add price back to player
        }
    }

    /**
     * Iterates through all courses and unlocks new cars and tuning parts
     * in the shop if the player has won the corresponding course for the first time.
     * Once an item is unlocked, it's moved from the 'locked' map to the main shop inventory.
     */
    public void unlockNewPartsAndCars() {
        for (Course course : Course.values()) {
            if (gameEnvironment.hasWonCourse(course)) { // Check if player has won this course
                // Attempt to unlock a car
                Car carToUnlock = gameEnvironment.getShopInventory().getLockedCarsMap().get(course);
                if (carToUnlock != null && gameEnvironment.getShopInventory().getLockedCarsMap().containsKey(course)) {
                    gameEnvironment.getShopInventory().addCar(carToUnlock); // Add to regular shop inventory
                    gameEnvironment.getShopInventory().removeLockedCar(course); // Remove from locked map
                }

                // Attempt to unlock tuning parts
                List<TuningPart> tuningPartsToUnlock = gameEnvironment.getShopInventory().getLockedTuningPartMap().get(course);
                if (tuningPartsToUnlock != null && !tuningPartsToUnlock.isEmpty() && gameEnvironment.getShopInventory().getLockedTuningPartMap().containsKey(course)) {
                    for (TuningPart part : tuningPartsToUnlock) {
                        gameEnvironment.getShopInventory().addTuningPart(part); // Add each part to regular shop inventory
                    }
                    gameEnvironment.getShopInventory().removeLockedTuningParts(course); // Remove the list from locked map
                }
            }
        }
    }
}