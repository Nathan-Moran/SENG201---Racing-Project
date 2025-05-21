package seng201.team0.services;

import seng201.team0.models.Car;
import seng201.team0.models.Course;
import seng201.team0.models.TuningPart;

import java.util.List;


public class ShopService {
    GameEnvironment gameEnvironment;

    public ShopService(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public boolean notEnoughBalance(int price) {
        if ((price < 0) || (gameEnvironment.getBalance() < 0)) {
            return true;
        }
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
        if (selectedCar != null && !notEnoughBalance(selectedCar.getPrice())) {
            gameEnvironment.getShopInventory().removeCar(selectedCar);
            gameEnvironment.getPlayerInventory().addCar(selectedCar);
            gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedCar.getPrice());

        }
    }

    public void buySelectedPart(TuningPart selectedPart) {
        if (selectedPart != null && !notEnoughBalance(selectedPart.getPrice())) {
                gameEnvironment.getShopInventory().removeTuningPart(selectedPart);
                gameEnvironment.getPlayerInventory().addTuningPart(selectedPart);
                gameEnvironment.setBalance(gameEnvironment.getBalance() - selectedPart.getPrice());
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

    public void unlockNewPartsAndCars() {
        for (Course course : Course.values()) {
            if (gameEnvironment.hasWonCourse(course)) { // Or gameEnvironment.hasWonCourseFirst(course)
                Car carToUnlock = (Car) gameEnvironment.getShopInventory().getLockedCarsMap().get(course);
                if (carToUnlock != null && gameEnvironment.getShopInventory().getLockedCarsMap().containsKey(course)) {
                    gameEnvironment.getShopInventory().addCar(carToUnlock);
                    gameEnvironment.getShopInventory().removeLockedCar(course);
                }
                List<TuningPart> tuningPartsToUnlock = (List<TuningPart>) gameEnvironment.getShopInventory().getLockedTuningPartMap().get(course);
                if (tuningPartsToUnlock != null && !tuningPartsToUnlock.isEmpty() && gameEnvironment.getShopInventory().getLockedTuningPartMap().containsKey(course)) {
                    for (TuningPart part : tuningPartsToUnlock) {
                        gameEnvironment.getShopInventory().addTuningPart(part);
                    }
                    gameEnvironment.getShopInventory().removeLockedTuningParts(course);
                }
            }
        }
    }
}

