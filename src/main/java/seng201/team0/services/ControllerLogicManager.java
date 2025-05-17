package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.StarterCarInventory;

public class ControllerLogicManager {
    GameEnvironment gameEnvironment;

    public ControllerLogicManager(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }


    //CarSelector Controller
    public void storeLeftOverCars(StarterCarInventory startingCarsGarage) {
        if (!gameEnvironment.getStarterCarInventory().getCarList().isEmpty()) {
            System.out.println(gameEnvironment.getPlayerInventory().getCarList());
            gameEnvironment.getShopInventory().getCarList().addAll(startingCarsGarage.getCarList());
            startingCarsGarage.getCarList().clear();
        }
    }

    //Garage Controller
    String label;
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
                    label = activeCar.getName();
                    break;
                case "Reliability":
                    label = String.valueOf(activeCar.getReliability());
                    break;
                case "SpeedUpgrade":
                    if (activeCar.getSpeedUpgrade() != null) {
                        label = String.valueOf(activeCar.getSpeedUpgrade().getName());
                    } else {
                        label = "-";
                    }
                    break;
                case "HandlingUpgrade":
                    if (activeCar.getHandlingUpgrade() != null) {
                        label = String.valueOf(activeCar.getHandlingUpgrade().getName());
                    } else {
                        label = "-";
                    }
                    break;
            }
        } else {
            label = "-";
        }
        return label;
    }


    //StartMenuController
    String prompt;
    boolean invalidNameLength;
    boolean invalidNameCharacters;

    public String nameChecker(String name) {
        invalidNameLength = false;
        invalidNameCharacters = false;
        prompt = "Valid Name";
        if (!(name.length() >= 3 && name.length() <= 15)) {
            invalidNameLength = true;
        }
        for (int i = 0; i < name.length(); i++) {
            // Checks each character in Name to check if it is a special character
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                invalidNameCharacters = true;
            }
        }
        //Maybe remove if both
        if (invalidNameLength) {
            prompt = "Name must be between 3 and 15 characters";
        } else if (invalidNameCharacters) {

            prompt = "Name must not contain special characters";

        }
        return prompt;
    }
}
