package seng201.team0.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Garage extends ItemStorage {
    private Car selectedCar;
    private ObservableList<TuningPart> selectedTuningPartList;

    public Garage() {
        super();
        this.selectedTuningPartList = FXCollections.observableArrayList();
    }

    public boolean installTuningPart(TuningPart part) {
        if (selectedCar != null) {
            if (part.getStat().equals("💨") && selectedCar.getSpeedUpgrade() == null) {
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

    public void uninstallTuningPart(TuningPart part) {
        if (selectedCar != null) {
            if (part.getStat().equals("💨") && selectedCar.getSpeedUpgrade() != null) {
                addTuningPart(selectedCar.getSpeedUpgrade());
                selectedCar.removeSpeedUpgrade();
            }
            else if (part.getStat().equals("🎮") && selectedCar.getHandlingUpgrade() != null) {
                addTuningPart(selectedCar.getHandlingUpgrade());
                selectedCar.removeHandlingUpgrade();
            }
        }
    }


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

//    public void setSelectedCar() {
//        if (selectedCar == null) {
//            if (!reserveCarList.isEmpty()) { //Maybe throw and catch errors
//                selectedCar = reserveCarList.get(0);
//                removeCar(selectedCar);
//            }
//        }
//    }

    public void setSelectedCar(Car newSelectedCar) {
        if (selectedCar != null) {
            if (selectedCar.getSpeedUpgrade() != null) {
                removeTuningPart(selectedCar.getSpeedUpgrade());
            }
            if (selectedCar.getHandlingUpgrade() != null) {
                removeTuningPart(selectedCar.getHandlingUpgrade());
            }
            if (selectedCar.getCustomName() != null) {
                selectedCar.setCustomName(null);
            }
            Car oldSelectedCar = this.getSelectedCar();
            addCar(oldSelectedCar);
            this.selectedCar = newSelectedCar;
            removeCar(newSelectedCar);
        }
    }


    public void setStarterCar(Car newStarterCar) {
        this.selectedCar = newStarterCar;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }
}
