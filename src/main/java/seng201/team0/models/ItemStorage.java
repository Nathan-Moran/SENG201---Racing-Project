package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * An abstract base class for storing collections of cars and tuning parts.
 * This class provides fundamental functionalities for adding, removing, and retrieving
 * lists of {@link Car} and {@link TuningPart} objects using JavaFX's {@link ObservableList}.
 * Subclasses will typically manage specific types of inventories (e.g., player's garage, shop).
 */
public abstract class ItemStorage {
    /**
     * An {@link ObservableList} storing {@link Car} objects.
     */
    private final ObservableList<Car> CarList;
    /**
     * An {@link ObservableList} storing {@link TuningPart} objects.
     */
    private final ObservableList<TuningPart> tuningPartList;

    /**
     * Constructs a new ItemStorage instance, initializing its internal lists.
     */
    public ItemStorage() {
        this.CarList = FXCollections.observableArrayList();
        this.tuningPartList = FXCollections.observableArrayList();
    }

    /**
     * Adds a {@link Car} to the car list if it's not null.
     * @param car The car to be added.
     */
    public void addCar(Car car) {
        if (car != null) {
            CarList.add(car);
        }
    }

    /**
     * Removes a {@link Car} from the car list if it's not null and present in the list.
     * @param car The car to be removed.
     */
    public void removeCar(Car car) {
        if (car != null) {
            CarList.remove(car);
        }
    }

    /**
     * Retrieves the {@link ObservableList} of cars currently in storage.
     * @return An ObservableList containing cars.
     */
    public ObservableList<Car> getCarList() {
        return CarList;
    }

    /**
     * Adds a {@link TuningPart} to the tuning part list if it's not null.
     * @param tuningPart The tuning part to be added.
     */
    public void addTuningPart(TuningPart tuningPart) {
        if (tuningPart != null) {
            tuningPartList.add(tuningPart);
        }
    }

    /**
     * Removes a {@link TuningPart} from the tuning part list if it's not null and present in the list.
     * @param tuningPart The tuning part to be removed.
     */
    public void removeTuningPart(TuningPart tuningPart) {
        if (tuningPart != null) {
            tuningPartList.remove(tuningPart);
        }
    }

    /**
     * Retrieves the {@link ObservableList} of tuning parts currently in storage.
     * @return An ObservableList containing tuning parts.
     */
    public ObservableList<TuningPart> getTuningPartList() {
        return tuningPartList;
    }
}