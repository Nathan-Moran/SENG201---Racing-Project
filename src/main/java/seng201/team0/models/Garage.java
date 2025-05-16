package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Garage {
    private ObservableList<Car> reserveCarList;
    private ObservableList<TuningPart> tuningPartList;
    private Car selectedCar;
    private ObservableList<TuningPart> selectedTuningPartList;

    public Garage() {
        this.reserveCarList = FXCollections.observableArrayList();
        this.tuningPartList = FXCollections.observableArrayList();
        this.selectedTuningPartList = FXCollections.observableArrayList();


    }

    public void installTuningPart(TuningPart part) {
        if (part.getStat().equals("\uD83D\uDCA8") && selectedCar.getSpeedUpgrade() == null) {
            selectedCar.addSpeedUpgrade(part);
            removeTuningParts(part);
        } else if (part.getStat().equals("🎮") && selectedCar.getHandlingUpgrade() == null) {
            selectedCar.addHandlingUpgrade(part);
            removeTuningParts(part);
        }
    }

    public void removeTuningPart(TuningPart part) {
        if (part.getStat().equals("\uD83D\uDCA8")) {
            selectedCar.getSpeedUpgrade();
        }
    }

    public ObservableList<TuningPart> getInstalledTuningParts() {
        selectedTuningPartList.clear();
        if (selectedCar.getSpeedUpgrade() != null) {
            selectedTuningPartList.add(selectedCar.getSpeedUpgrade());
        }
        if (selectedCar.getHandlingUpgrade() != null) {
            selectedTuningPartList.add(selectedCar.getHandlingUpgrade());
        }
//        selectedTuningPartList.addAll(selectedCar.getSpeedUpgrade(), selectedCar.getHandlingUpgrade());
        return selectedTuningPartList;
    }

    public void setSelectedCar() {
        if (selectedCar == null) {
            if (!reserveCarList.isEmpty()) { //Maybe throw and catch errors
                selectedCar = reserveCarList.get(0);
                removeCar(selectedCar);

            }
        }
    }

    public void setSelectedCar(Car newSelectedCar) {
        Car oldSelectedCar = this.getSelectedCar();
        reserveCarList.add(oldSelectedCar);
        this.selectedCar = newSelectedCar;
        reserveCarList.remove(newSelectedCar);
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void addTuningParts(TuningPart part) {
        tuningPartList.add(part);
    }

    public void removeTuningParts(TuningPart part) {
        tuningPartList.remove(part);
    }

    public void removeCar(Car car) {
        reserveCarList.remove(car);
    }

    public void addCar(Car car) {
        reserveCarList.add(car);
    }

    public ObservableList<Car> getCarList() {
        return reserveCarList;
    }

    public ObservableList<TuningPart> getTuningPartList() {
        return tuningPartList;
    }
}
