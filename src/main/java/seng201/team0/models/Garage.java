package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the player's personal garage, managing their owned cars and tuning parts.
 * It extends {@link ItemStorage} to inherit basic item management functionalities.
 * The garage also handles the selection of the player's active car and the installation/uninstallation
 * of tuning parts on that car.
 */
public class Garage extends ItemStorage {
    /**
     * The currently selected car by the player for racing or modification.
     */
    private Car selectedCar;
    /**
     * An observable list of tuning parts currently installed on the {@link #selectedCar}.
     * This list is dynamically updated to reflect the selected car's upgrades.
     */
    private final ObservableList<TuningPart> selectedTuningPartList;

    /**
     * Constructs a new Garage instance.
     * Initializes the observable list for installed tuning parts.
     */
    public Garage() {
        super();
        this.selectedTuningPartList = FXCollections.observableArrayList();
    }

    /**
     * Installs a given tuning part onto the {@link #selectedCar}.
     * A tuning part can only be installed if a car is selected and
     * the corresponding upgrade slot (speed or handling) on the car is empty.
     * If successful, the part is removed from the garage's main tuning part list.
     *
     * @param part The {@link TuningPart} to be installed.
     * @return {@code true} if the tuning part was successfully installed, {@code false} otherwise.
     */
    public boolean installTuningPart(TuningPart part) {
        if (selectedCar != null) {
            if (part.getStat().equals("⚡") && selectedCar.getSpeedUpgrade() == null) {
                selectedCar.addSpeedUpgrade(part);
                removeTuningPart(part);
                return true;
            } else if (part.getStat().equals("🎮") && selectedCar.getHandlingUpgrade() == null) {
                selectedCar.addHandlingUpgrade(part);
                removeTuningPart(part);
                return true;
            }
        }
        return false;
    }

    /**
     * Uninstalls a tuning part from the {@link #selectedCar}.
     * The uninstalled part is returned to the garage's main tuning part list.
     *
     * @param part The {@link TuningPart} to be uninstalled.
     */
    public void uninstallTuningPart(TuningPart part) {
        if (selectedCar != null) {
            if (part.getStat().equals("⚡") && selectedCar.getSpeedUpgrade() != null) {
                addTuningPart(selectedCar.getSpeedUpgrade());
                selectedCar.removeSpeedUpgrade();
            }
            else if (part.getStat().equals("🎮") && selectedCar.getHandlingUpgrade() != null) {
                addTuningPart(selectedCar.getHandlingUpgrade());
                selectedCar.removeHandlingUpgrade();
            }
        }
    }

    /**
     * Returns an {@link ObservableList} of tuning parts currently installed on the {@link #selectedCar}.
     * This list is cleared and repopulated each time it's called to ensure accuracy.
     *
     * @return An ObservableList containing the tuning parts installed on the selected car.
     */
    public ObservableList<TuningPart> getInstalledTuningParts() {
        selectedTuningPartList.clear();
        if (selectedCar != null) {
            if (selectedCar.getSpeedUpgrade() != null) {
                selectedTuningPartList.add(selectedCar.getSpeedUpgrade());
            }
            if (selectedCar.getHandlingUpgrade() != null) {
                selectedTuningPartList.add(selectedCar.getHandlingUpgrade());
            }
        }
        return selectedTuningPartList;
    }

    /**
     * Sets a new car as the {@link #selectedCar} in the garage.
     * If there was a previously selected car, its installed tuning parts are uninstalled
     * and returned to the garage, and the old car is re-added to the general car list.
     * The new selected car is then removed from the general car list.
     *
     * @param newSelectedCar The {@link Car} to be set as the currently selected car.
     */
    public void setSelectedCar(Car newSelectedCar) {
        if (selectedCar != null) {
            if (selectedCar.getSpeedUpgrade() != null) {
                uninstallTuningPart(selectedCar.getSpeedUpgrade());
            }
            if (selectedCar.getHandlingUpgrade() != null) {
                uninstallTuningPart(selectedCar.getHandlingUpgrade());
            }

            if (selectedCar.getCustomName() != null) {
                selectedCar.setCustomName(null);
            }
            addCar(this.selectedCar);
        }
        this.selectedCar = newSelectedCar;
        removeCar(newSelectedCar);
    }


    /**
     * Checks if the garage is full, meaning it contains more than 3 cars.
     * @return {@code true} if the number of cars in the garage (excluding the selected car if it's stored separately)
     * is greater than 3, {@code false} otherwise.
     */
    public boolean garageFull() {
        return getCarList().size() > 3;
    }


    /**
     * Sets the initial starter car for the player. This is typically used at the beginning of the game.
     * @param newStarterCarTemplate The {@link Car} chosen as the starter car.
     */
    public void setStarterCar(Car newStarterCarTemplate) {
        if (newStarterCarTemplate != null) {
            this.selectedCar = new Car(newStarterCarTemplate);
        } else {
            this.selectedCar = null;
        }
    }

    /**
     * Gets the currently selected car in the garage.
     * @return The {@link Car} that is currently selected by the player.
     */
    public Car getSelectedCar() {
        return selectedCar;
    }
}