package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;


public class BalanceManager {
    GameEnvironment gameEnvironment;

    public BalanceManager(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public boolean notEnoughBalance(int price) {
        return price > gameEnvironment.getBalance();
    }

    public void chooseStarterCar(Car selectedCar) {
        if (selectedCar != null && !notEnoughBalance(selectedCar.getPrice())) {
            if (gameEnvironment.getSelectedCar() == null) {
                gameEnvironment.getStarterCarInventory().removeCar(selectedCar);
                gameEnvironment.getPlayerInventory().setStarterCar(selectedCar);

            } else {
                gameEnvironment.getStarterCarInventory().removeCar(selectedCar);
                gameEnvironment.getPlayerInventory().addCar(selectedCar);
            }
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice());
        }
    }

    public void buySelectedCar(Car selectedCar) {
        if (selectedCar != null) {
            if (gameEnvironment.getBalance() >= selectedCar.getPrice()) {
                gameEnvironment.getShopInventory().removeCar(selectedCar);
                gameEnvironment.getPlayerInventory().addCar(selectedCar);
                gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice());
            }
        }
    }

    public void buySelectedPart(TuningPart selectedPart) {
        if (selectedPart != null) {
            if (gameEnvironment.getBalance() >= selectedPart.getPrice()) {
                gameEnvironment.getShopInventory().removeTuningPart(selectedPart);
                gameEnvironment.getPlayerInventory().addTuningPart(selectedPart);
                gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedPart.getPrice());
            }
        }
    }

    public void sellSelectedCar(Car selectedCar) {
        if (selectedCar != null) {
            gameEnvironment.getPlayerInventory().removeCar(selectedCar);
            gameEnvironment.getShopInventory().addCar(selectedCar);
            gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedCar.getPrice());
        }
    }

    public void sellSelectedPart(TuningPart selectedPart) {
        if (selectedPart != null) {
            gameEnvironment.getPlayerInventory().removeTuningPart(selectedPart);
            gameEnvironment.getShopInventory().addTuningPart(selectedPart);
            gameEnvironment.setBalance(gameEnvironment.getBalance() + selectedPart.getPrice());
        }
    }
}
