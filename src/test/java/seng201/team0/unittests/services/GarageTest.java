package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Garage;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

class GarageTest {

    private Garage garage;
    private Car car1;
    private Car car2;
    private TuningPart speedPart;
    private TuningPart handlingPart;
    private TuningPart anotherSpeedPart;

    @BeforeEach
    void setUp() {
        garage = new Garage(); //
        car1 = new Car("Car 1", 0.7, 0.6, 0.8, 25, 2000);
        car2 = new Car("Car 2", 0.8, 0.7, 0.9, 30, 3000);
        // Unicode for emojis were "💨" (speed) and "🎮" (handling) in TuningPart but "暢" and "式" in Garage
        // Using the ones from Garage.java for these tests as they are directly testing Garage.java
        speedPart = new TuningPart("Speed Boost", 500, "暢", 1.2);
        handlingPart = new TuningPart("Handling Boost", 400, "式", 1.1);
        anotherSpeedPart = new TuningPart("Super Speed", 1000, "暢", 1.5);

        garage.addCar(car1); //
        garage.addTuningPart(speedPart); //
        garage.addTuningPart(handlingPart); //
    }

    @Test
    void installSpeedTuningPartSuccessfully() {
        garage.setSelectedCar(car1); //
        assertTrue(garage.getTuningPartList().contains(speedPart)); //
        assertNull(car1.getSpeedUpgrade()); //
        boolean installed = garage.installTuningPart(speedPart); //
        assertTrue(installed); //
        assertEquals(speedPart, car1.getSpeedUpgrade()); //
        assertFalse(garage.getTuningPartList().contains(speedPart)); //
        assertTrue(garage.getInstalledTuningParts().contains(speedPart)); //
    }

    @Test
    void installHandlingTuningPartSuccessfully() {
        garage.setSelectedCar(car1); //
        assertTrue(garage.getTuningPartList().contains(handlingPart)); //
        assertNull(car1.getHandlingUpgrade()); //
        boolean installed = garage.installTuningPart(handlingPart); //
        assertTrue(installed); //
        assertEquals(handlingPart, car1.getHandlingUpgrade()); //
        assertFalse(garage.getTuningPartList().contains(handlingPart)); //
        assertTrue(garage.getInstalledTuningParts().contains(handlingPart)); //
    }

    @Test
    void installTuningPartFailsIfNoCarSelected() {
        boolean installed = garage.installTuningPart(speedPart); //
        assertFalse(installed); //
        assertTrue(garage.getTuningPartList().contains(speedPart)); //
    }

    @Test
    void installSpeedTuningPartFailsIfSlotFull() {
        garage.setSelectedCar(car1); //
        garage.installTuningPart(speedPart); //
        garage.addTuningPart(anotherSpeedPart); //

        boolean installed = garage.installTuningPart(anotherSpeedPart); //
        assertFalse(installed); //
        assertEquals(speedPart, car1.getSpeedUpgrade()); //
        assertTrue(garage.getTuningPartList().contains(anotherSpeedPart)); //
    }

    @Test
    void installHandlingTuningPartFailsIfSlotFull() {
        garage.setSelectedCar(car1); //
        garage.installTuningPart(handlingPart); //
        TuningPart anotherHandlingPart = new TuningPart("Super Handling", 1000, "式", 1.5);
        garage.addTuningPart(anotherHandlingPart); //

        boolean installed = garage.installTuningPart(anotherHandlingPart); //
        assertFalse(installed); //
        assertEquals(handlingPart, car1.getHandlingUpgrade()); //
        assertTrue(garage.getTuningPartList().contains(anotherHandlingPart)); //
    }


    @Test
    void installTuningPartFailsIfWrongType() {
        garage.setSelectedCar(car1); //
        TuningPart wrongTypePart = new TuningPart("Wrong Type", 300, "Wrong", 1.1);
        garage.addTuningPart(wrongTypePart); //
        boolean installed = garage.installTuningPart(wrongTypePart); //
        assertFalse(installed); //
        assertNull(car1.getSpeedUpgrade()); //
        assertNull(car1.getHandlingUpgrade()); //
        assertTrue(garage.getTuningPartList().contains(wrongTypePart)); //
    }

    @Test
    void uninstallSpeedTuningPartSuccessfully() {
        garage.setSelectedCar(car1); //
        garage.installTuningPart(speedPart); //
        assertNotNull(car1.getSpeedUpgrade()); //
        assertFalse(garage.getTuningPartList().contains(speedPart)); //

        garage.uninstallTuningPart(speedPart); //
        assertNull(car1.getSpeedUpgrade()); //
        assertTrue(garage.getTuningPartList().contains(speedPart)); //
        assertFalse(garage.getInstalledTuningParts().contains(speedPart)); //
    }

    @Test
    void uninstallHandlingTuningPartSuccessfully() {
        garage.setSelectedCar(car1); //
        garage.installTuningPart(handlingPart); //
        assertNotNull(car1.getHandlingUpgrade()); //
        assertFalse(garage.getTuningPartList().contains(handlingPart)); //

        garage.uninstallTuningPart(handlingPart); //
        assertNull(car1.getHandlingUpgrade()); //
        assertTrue(garage.getTuningPartList().contains(handlingPart)); //
        assertFalse(garage.getInstalledTuningParts().contains(handlingPart)); //
    }

    @Test
    void uninstallTuningPartNoCarSelected() {
        garage.addTuningPart(speedPart); //
        int initialPartCount = garage.getTuningPartList().size(); //
        garage.uninstallTuningPart(speedPart); //
        assertEquals(initialPartCount, garage.getTuningPartList().size()); //
    }

    @Test
    void uninstallTuningPartNotInstalled() {
        garage.setSelectedCar(car1); //
        garage.addTuningPart(speedPart); //
        int initialPartCount = garage.getTuningPartList().size(); //
        assertNull(car1.getSpeedUpgrade()); //

        garage.uninstallTuningPart(speedPart); //
        assertEquals(initialPartCount, garage.getTuningPartList().size()); //
        assertNull(car1.getSpeedUpgrade()); //
    }

    @Test
    void uninstallTuningPartWrongType() {
        garage.setSelectedCar(car1); //
        garage.installTuningPart(speedPart); // // Installs a speed part

        // Try to uninstall a handling part (which is not installed, and is of a different type than what was installed)
        TuningPart handlingPartNotInstalled = new TuningPart("NonInstalledHandling", 200, "式", 1.1);
        garage.addTuningPart(handlingPartNotInstalled); // Add to garage inventory but not to car

        garage.uninstallTuningPart(handlingPartNotInstalled); //

        assertNotNull(car1.getSpeedUpgrade()); // Speed part should still be there //
        assertEquals(speedPart, car1.getSpeedUpgrade()); //
        assertTrue(garage.getTuningPartList().contains(handlingPartNotInstalled)); // The non-installed part should still be in garage //
    }


    @Test
    void getInstalledTuningPartsEmptyInitially() {
        garage.setSelectedCar(car1); //
        assertTrue(garage.getInstalledTuningParts().isEmpty()); //
    }

    @Test
    void getInstalledTuningPartsAfterInstallation() {
        garage.setSelectedCar(car1); //
        garage.installTuningPart(speedPart); //
        garage.installTuningPart(handlingPart); //
        assertEquals(2, garage.getInstalledTuningParts().size()); //
        assertTrue(garage.getInstalledTuningParts().contains(speedPart)); //
        assertTrue(garage.getInstalledTuningParts().contains(handlingPart)); //
    }

    @Test
    void getInstalledTuningPartsWhenNoCarSelected() {
        assertTrue(garage.getInstalledTuningParts().isEmpty()); //
    }

    @Test
    void setSelectedCarFirstTime() {
        assertNull(garage.getSelectedCar()); //
        assertTrue(garage.getCarList().contains(car1)); //
        garage.setSelectedCar(car1); //
        assertEquals(car1, garage.getSelectedCar()); //
        assertFalse(garage.getCarList().contains(car1)); //
    }

    @Test
    void setSelectedCarSwitchesCar() {
        garage.addCar(car2); //
        garage.setSelectedCar(car1); //
        assertEquals(car1, garage.getSelectedCar()); //
        assertFalse(garage.getCarList().contains(car1)); //
        assertTrue(garage.getCarList().contains(car2)); //

        garage.setSelectedCar(car2); //
        assertEquals(car2, garage.getSelectedCar()); //
        assertTrue(garage.getCarList().contains(car1)); //
        assertFalse(garage.getCarList().contains(car2)); //
    }

    @Test
    void setSelectedCarUninstallsPartsAndClearsCustomNameFromOldCar() {
        garage.setSelectedCar(car1); //
        car1.setCustomName("Old Racer"); //
        garage.installTuningPart(speedPart); //
        garage.installTuningPart(handlingPart); //

        assertNotNull(car1.getSpeedUpgrade()); //
        assertNotNull(car1.getHandlingUpgrade()); //
        assertEquals("Old Racer", car1.getCustomName()); //
        int partsInGarageBeforeSwitch = garage.getTuningPartList().size(); //

        garage.addCar(car2); //
        garage.setSelectedCar(car2); //

        assertNull(car1.getSpeedUpgrade()); //
        assertNull(car1.getHandlingUpgrade()); //
        assertNull(car1.getCustomName()); //
        assertTrue(garage.getCarList().contains(car1)); //
        assertEquals(partsInGarageBeforeSwitch + 2, garage.getTuningPartList().size()); //
        assertTrue(garage.getTuningPartList().contains(speedPart)); //
        assertTrue(garage.getTuningPartList().contains(handlingPart)); //
        assertEquals(car2, garage.getSelectedCar()); //
    }


    @Test
    void garageFull() {
        garage.getCarList().clear(); //
        garage.addCar(new Car("C1", 0.1,0.1,0.1,1,1)); //
        garage.addCar(new Car("C2", 0.1,0.1,0.1,1,1)); //
        garage.addCar(new Car("C3", 0.1,0.1,0.1,1,1)); //
        assertFalse(garage.garageFull()); // 3 cars, not full //
        garage.addCar(new Car("C4", 0.1,0.1,0.1,1,1)); //
        assertTrue(garage.garageFull()); // 4 cars, full //
    }

    @Test
    void garageNotFull() {
        garage.getCarList().clear(); //
        garage.addCar(new Car("C1",0.1,0.1,0.1,1,1)); //
        assertFalse(garage.garageFull()); //
    }

    @Test
    void setStarterCar() {
        assertNull(garage.getSelectedCar()); //
        Car starter = new Car("Starter", 0.5, 0.5, 0.5, 10, 500);
        garage.setStarterCar(starter); //
        assertNotNull(garage.getSelectedCar()); //
        assertEquals("Starter", garage.getSelectedCar().getName()); //
        // Test that it's a copy
        assertNotSame(starter, garage.getSelectedCar()); //
        assertEquals(starter.getSpeed(), garage.getSelectedCar().getSpeed(), 0.001); //
    }

    @Test
    void setStarterCarNull() {
        garage.setStarterCar(null); //
        assertNull(garage.getSelectedCar()); //
    }

    @Test
    void getSelectedCar() {
        assertNull(garage.getSelectedCar()); //
        garage.setSelectedCar(car1); //
        assertEquals(car1, garage.getSelectedCar()); //
    }
}