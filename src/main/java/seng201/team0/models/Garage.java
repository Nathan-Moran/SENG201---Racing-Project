package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Garage {
    private ObservableList<Car> reserveCarList;
    private ObservableList<TuningPart> tuningPartList;
    Car selectedCar;

    public Garage() {
        this.reserveCarList = FXCollections.observableArrayList();
        this.tuningPartList = FXCollections.observableArrayList();


    }

//    public void installTuningPart(TuningPart part, Car selectedCar) {
//        if (part.getStat().equals("Speed")) {
//            selectedCar.addSpeedUpgrade(part);
//        } else if (part.getStat().equals("Handling")) {
//            selectedCar.addHandlingUpgrade(part);
//        }
//    }

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
