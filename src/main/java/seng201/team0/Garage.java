package seng201.team0;

import java.util.ArrayList;

public class Garage {
    private ArrayList<Car> reserveCars;
    private ArrayList<TuningPart> tuningParts;
    Car selectedCar;

    public Garage() {
        this.reserveCars = new ArrayList<>();
        this.tuningParts = new ArrayList<>();
    }

    public void installTuningPart(TuningPart part, Car selectedCar) {
        if (part.getStat().equals("Speed")) {
            selectedCar.addSpeedUpgrade(part);
        } else if (part.getStat().equals("Handling")) {
            selectedCar.addHandlingUpgrade(part);
        }
    }

    public void setSelectedCar() {
        if (this.getSelectedCar() == null) {
            if (!reserveCars.isEmpty()) { //Maybe throw and catch errors
                selectedCar = reserveCars.get(0);
            }
        }
    }

    public void setSelectedCar(Car selectedCar) {
        Car tempCar = this.getSelectedCar();
        reserveCars.add(this.selectedCar);
        this.selectedCar = selectedCar;
        reserveCars.remove(tempCar);
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void addTuningParts(TuningPart part) {
        tuningParts.add(part);
    }

    public void removeTuningParts(TuningPart part) {
        tuningParts.remove(part);
    }

    public void removeCar(Car car) {
        reserveCars.remove(car);
    }

    public void addCar(Car car) {
        reserveCars.add(car);
    }
}
