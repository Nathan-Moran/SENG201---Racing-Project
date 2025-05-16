package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;

public class BalanceManager {
    GameEnvironment gameEnvironment;

    public BalanceManager(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }
    public void chooseStarterCar(Car selectedCar) {
        if (gameEnvironment.getBalance() >= selectedCar.getPrice()) {
            gameEnvironment.getStarterCarInventory().removeCar(selectedCar);
            gameEnvironment.getPlayerInventory().setStarterCar(selectedCar);
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice());
        }
    }

    public void buySelectedCar(Car selectedCar) {
        if (gameEnvironment.getBalance() >= selectedCar.getPrice()) {
            gameEnvironment.getShopInventory().removeCar(selectedCar);
            gameEnvironment.getPlayerInventory().addCar(selectedCar);
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice());
        }
    }

    public void buySelectedPart(TuningPart selectedPart) {
        if (gameEnvironment.getBalance() >= selectedPart.getPrice()) {
            gameEnvironment.getShopInventory().removeTuningParts(selectedPart);
            gameEnvironment.getPlayerInventory().addTuningParts(selectedPart);
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedPart.getPrice());
        }
    }

    public void sellSelectedCar(Car selectedCar) {
        gameEnvironment.getPlayerInventory().removeCar(selectedCar);
        gameEnvironment.getShopInventory().addCar(selectedCar);
        gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedCar.getPrice());
    }

    public void sellSelectedPart(TuningPart selectedPart) {
        gameEnvironment.getPlayerInventory().removeTuningParts(selectedPart);
        gameEnvironment.getShopInventory().addTuningParts(selectedPart);
        gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedPart.getPrice());
    }
}
