package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.StarterCarInventory;

/**
 * Provides various controller-related services and helper methods that interact
 * with the {@link GameEnvironment} to manage game state and UI logic.
 * This service centralizes common operations needed by multiple controllers.
 */
public class ControllerService {
    /**
     * The central game environment managing the overall game state.
     */
    GameEnvironment gameEnvironment;

    /**
     * Constructs a new ControllerService with a reference to the main game environment.
     * @param gameEnvironment The {@link GameEnvironment} instance for the current game session.
     */
    public ControllerService(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }


    /**
     * Moves any unselected starter cars from the {@link StarterCarInventory}
     * to the shop's inventory at the beginning of the game setup.
     * This ensures that cars not chosen by the player are made available for purchase.
     *
     * @param startingCarsGarage The {@link StarterCarInventory} containing the initial car choices.
     */
    public void storeLeftOverCars(StarterCarInventory startingCarsGarage) {
        if (!gameEnvironment.getStarterCarInventory().getCarList().isEmpty()) {
            System.out.println(gameEnvironment.getPlayerInventory().getCarList());
            gameEnvironment.getShopInventory().getAllAvailableCars().addAll(startingCarsGarage.getCarList());
            startingCarsGarage.getCarList().clear();
            gameEnvironment.getShopInventory().setShopInventory();
        }
    }

    //Garage Controller specific methods

    /**
     * Helper variable used to store the label text for display.
     */
    String label;

    /**
     * Sets a descriptive label string based on the requested car statistic or property.
     * This method retrieves information from the {@link Car} currently selected in the player's garage.
     *
     * @param stat A string indicating which car statistic or property to get a label for (e.g., "Speed", "Fuel", "Model").
     * @return A string representing the value of the requested stat or property, or "-" if the selected car is null or the upgrade slot is empty.
     */
    public String setLabels(String stat) {
        Car activeCar = gameEnvironment.getPlayerInventory().getSelectedCar();
        if (activeCar != null) {
            switch (stat) {
                case "Speed":
                    label = String.valueOf(activeCar.getSpeed());
                    break;
                case "Fuel":
                    label = String.valueOf(activeCar.getFuelEconomy());
                    break;
                case "Handling":
                    label = String.valueOf(activeCar.getHandling());
                    break;
                case "Model":
                    if (activeCar.getCustomName() == null) {
                        label = activeCar.getName();
                    } else {
                        label = activeCar.getCustomName();
                    }
                    break;
                case "Reliability":
                    label = String.valueOf(activeCar.getReliability());
                    break;
                case "SpeedUpgrade":
                    if (activeCar.getSpeedUpgrade() != null) {
                        label = String.valueOf(activeCar.getSpeedUpgrade().getName());
                    } else {
                        label = "-"; // Indicate no upgrade
                    }
                    break;
                case "HandlingUpgrade":
                    if (activeCar.getHandlingUpgrade() != null) {
                        label = String.valueOf(activeCar.getHandlingUpgrade().getName());
                    } else {
                        label = "-"; // Indicate no upgrade
                    }
                    break;
                default:
                    label = "-"; // For unrecognized stats
                    break;
            }
        } else {
            label = "-"; // No car selected
        }
        return label;
    }


    //StartMenuController specific methods

    /**
     * Helper variable to store the validation prompt message.
     */
    String prompt;
    /**
     * Flag indicating if the entered name's length is invalid.
     */
    boolean invalidNameLength;
    /**
     * Flag indicating if the entered name contains invalid characters.
     */
    boolean invalidNameCharacters;

    /**
     * Validates a given player name based on length and character type.
     * A name must be between 3 and 15 characters long and contain only letters or digits.
     *
     * @param name The player's proposed name.
     * @return A string message indicating the validity of the name (e.g., "Valid Name", "Name must be between 3 and 15 characters", "Name must not contain special characters").
     */
    public String nameChecker(String name) {
        invalidNameLength = false;
        invalidNameCharacters = false;
        prompt = "Valid Name"; // Default to valid

        // Check length
        if (!(name.length() >= 3 && name.length() <= 15)) {
            invalidNameLength = true;
        }

        // Check for special characters
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                invalidNameCharacters = true;
                break; // No need to check further if one invalid character is found
            }
        }

        // Determine the prompt message based on validation flags
        if (invalidNameLength) {
            prompt = "Name must be between 3 and 15 characters";
        } else if (invalidNameCharacters) {
            prompt = "Name must not contain special characters";
        }
        // If both are true, the length error is prioritized as it's typically checked first.
        // If neither is true, prompt remains "Valid Name".

        return prompt;
    }
}