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
        if ((price < 0) || (gameEnvironment.getBalance() < 0)) {
            return true;
        }
        return price > gameEnvironment.getBalance();
    }

    /**
     * Handles the selection and purchase of a starter car at the beginning of the game.
     * Deducts the car's price from the player's balance and adds the car to their garage.
     *
     * @param selectedCarTemplate The {@link Car} chosen by the player as their starter car.
     */
    public void chooseStarterCar(Car selectedCarTemplate) {
        if (selectedCarTemplate != null && !notEnoughBalance(selectedCarTemplate.getPrice())) {
            gameEnvironment.getStarterCarInventory().removeCar(selectedCarTemplate);
            if (gameEnvironment.getSelectedCar() == null) {

                gameEnvironment.getPlayerInventory().setStarterCar(selectedCarTemplate);
            } else {
                Car playerOwnedCar = new Car(selectedCarTemplate);
                gameEnvironment.getPlayerInventory().addCar(playerOwnedCar);
            }
            // Deduct cost
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCarTemplate.getPrice());
        }
    }

    /**
     * Handles the purchase of a selected car from the shop.
     * Removes the car from the shop inventory, adds it to the player's garage,
     * and deducts its price from the player's balance.
     *
     * @param selectedCarTemplate The {@link Car} to be bought.
     */
    public void buySelectedCar(Car selectedCarTemplate) {
        if (selectedCarTemplate != null && !notEnoughBalance(selectedCarTemplate.getPrice())) {
            Car playerOwnedCar = new Car(selectedCarTemplate);

            gameEnvironment.getShopInventory().removeCar(selectedCarTemplate);
            gameEnvironment.getShopInventory().removeCarFromAllAvailable(selectedCarTemplate);
            gameEnvironment.getPlayerInventory().addCar(playerOwnedCar);
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCarTemplate.getPrice());
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
            gameEnvironment.getShopInventory().removeTuningPart(selectedPart);
            gameEnvironment.getShopInventory().removePartFromAllAvailable(selectedPart);
            gameEnvironment.getPlayerInventory().addTuningPart(selectedPart);
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedPart.getPrice());
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
            gameEnvironment.getPlayerInventory().removeCar(selectedCar);
            gameEnvironment.getShopInventory().addCar(selectedCar);
            gameEnvironment.getShopInventory().addCarToAllAvailable(selectedCar);
            gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedCar.getPrice());
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
            gameEnvironment.getPlayerInventory().removeTuningPart(selectedPart);
            gameEnvironment.getShopInventory().addTuningPart(selectedPart);
            gameEnvironment.getShopInventory().addPartToAllAvailable(selectedPart);
            gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedPart.getPrice());
        }
    }

    /**
     * Iterates through all courses and unlocks new cars and tuning parts
     * in the shop if the player has won the corresponding course for the first time.
     * Once an item is unlocked, it's moved from the 'locked' map to the main shop inventory.
     */
    public void unlockNewCars() {
        for (Course course : Course.values()) {
            if (gameEnvironment.hasWonCourse(course)) {
                Car carToUnlock = gameEnvironment.getShopInventory().getLockedCarsMap().get(course);
                if (carToUnlock != null && gameEnvironment.getShopInventory().getLockedCarsMap().containsKey(course)) {
                    gameEnvironment.getShopInventory().addCar(carToUnlock);
                    gameEnvironment.getShopInventory().removeLockedCar(course);
                }
            }
        }
    }
}
