package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ItemStorage {
    private final ObservableList<Car> CarList;
    private final ObservableList<TuningPart> tuningPartList;

    public ItemStorage() {
        this.CarList = FXCollections.observableArrayList();
        this.tuningPartList = FXCollections.observableArrayList();
    }

    public void addCar(Car car) {
        CarList.add(car);
    }
    public void removeCar(Car car) {
        CarList.remove(car);
    }

    public ObservableList<Car> getCarList() {
        return CarList;
    }

    public void addTuningPart(TuningPart tuningPart) {
        tuningPartList.add(tuningPart);
    }

    public void removeTuningPart(TuningPart tuningPart) {
        tuningPartList.remove(tuningPart);
    }

    public ObservableList<TuningPart> getTuningPartList() {
        return tuningPartList;
    }
}
