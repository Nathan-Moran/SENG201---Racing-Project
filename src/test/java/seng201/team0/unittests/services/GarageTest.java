package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Garage;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

public class GarageTest {

    private Garage garage;
    private Car car1;
    private Car car2;
    private TuningPart speedPart;
    private TuningPart handlingPart;
    private TuningPart anotherSpeedPart;

    @BeforeEach
    void setUp() {
        garage = new Garage();
        car1 = new Car("Car 1", 100, 0.8, 0.9, 10, 1000);
        car2 = new Car("Car 2", 120, 0.7, 0.85, 12, 1500);
        speedPart = new TuningPart("Speed Boost", 200, "💨", 1.2);
        handlingPart = new TuningPart("Handling Grip", 150, "🎮", 1.1);
        anotherSpeedPart = new TuningPart("Super Speed Boost", 300, "💨", 1.3);
    }

    @Test
    void testInitialGarageState() {
        assertNull(garage.getSelectedCar());
        assertTrue(garage.getCarList().isEmpty());
        assertTrue(garage.getTuningPartList().isEmpty());
        assertTrue(garage.getInstalledTuningParts().isEmpty());
    }

    @Test
    void testAddAndGetCars() {
        garage.addCar(car1);
        assertEquals(1, garage.getCarList().size());
        assertTrue(garage.getCarList().contains(car1));
        garage.addCar(car2);
        assertEquals(2, garage.getCarList().size());
        assertTrue(garage.getCarList().contains(car2));
    }

    @Test
    void testAddNullCar() {
        garage.addCar(null);
        assertTrue(garage.getCarList().isEmpty());
    }

    @Test
    void testRemoveCar() {
        garage.addCar(car1);
        garage.removeCar(car1);
        assertTrue(garage.getCarList().isEmpty());
    }

    @Test
    void testRemoveNonExistentCar() {
        garage.addCar(car1);
        garage.removeCar(car2);
        assertEquals(1, garage.getCarList().size());
    }

    @Test
    void testRemoveNullCar() {
        garage.addCar(car1);
        garage.removeCar(null);
        assertEquals(1, garage.getCarList().size());
    }


    @Test
    void testAddAndGetTuningParts() {
        garage.addTuningPart(speedPart);
        assertEquals(1, garage.getTuningPartList().size());
        assertTrue(garage.getTuningPartList().contains(speedPart));
        garage.addTuningPart(handlingPart);
        assertEquals(2, garage.getTuningPartList().size());
        assertTrue(garage.getTuningPartList().contains(handlingPart));
    }

    @Test
    void testAddNullTuningPart() {
        garage.addTuningPart(null);
        assertTrue(garage.getTuningPartList().isEmpty());
    }

    @Test
    void testRemoveTuningPart() {
        garage.addTuningPart(speedPart);
        garage.removeTuningPart(speedPart);
        assertTrue(garage.getTuningPartList().isEmpty());
    }

    @Test
    void testRemoveNonExistentTuningPart() {
        garage.addTuningPart(speedPart);
        garage.removeTuningPart(handlingPart);
        assertEquals(1, garage.getTuningPartList().size());
    }

    @Test
    void testRemoveNullTuningPart() {
        garage.addTuningPart(speedPart);
        garage.removeTuningPart(null);
        assertEquals(1, garage.getTuningPartList().size());
    }

    @Test
    void testSetStarterCar() {
        assertNull(garage.getSelectedCar());
        garage.setStarterCar(car1);
        assertEquals(car1, garage.getSelectedCar());
        assertTrue(garage.getCarList().isEmpty());
    }

    @Test
    void testInstallSpeedTuningPart() {
        garage.setStarterCar(car1);
        garage.addTuningPart(speedPart);
        assertTrue(garage.installTuningPart(speedPart));
        assertEquals(speedPart, car1.getSpeedUpgrade());
        assertNull(car1.getHandlingUpgrade());
        assertTrue(garage.getTuningPartList().isEmpty());
        assertEquals(1, garage.getInstalledTuningParts().size());
        assertTrue(garage.getInstalledTuningParts().contains(speedPart));
    }

    @Test
    void testInstallHandlingTuningPart() {
        garage.setStarterCar(car1);
        garage.addTuningPart(handlingPart);
        assertTrue(garage.installTuningPart(handlingPart));
        assertEquals(handlingPart, car1.getHandlingUpgrade());
        assertNull(car1.getSpeedUpgrade());
        assertTrue(garage.getTuningPartList().isEmpty());
        assertEquals(1, garage.getInstalledTuningParts().size());
        assertTrue(garage.getInstalledTuningParts().contains(handlingPart));
    }

    @Test
    void testInstallTuningPartNoSelectedCar() {
        garage.addTuningPart(speedPart);
        assertFalse(garage.installTuningPart(speedPart));
        assertNull(car1.getSpeedUpgrade());
        assertEquals(1, garage.getTuningPartList().size());
    }

    @Test
    void testInstallSpeedPartWhenSlotOccupied() {
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(anotherSpeedPart);
        garage.addTuningPart(speedPart);
        assertFalse(garage.installTuningPart(speedPart));
        assertEquals(anotherSpeedPart, car1.getSpeedUpgrade());
        assertEquals(1, garage.getTuningPartList().size());
    }

    @Test
    void testInstallHandlingPartWhenSlotOccupied() {
        garage.setStarterCar(car1);
        TuningPart existingHandlingPart = new TuningPart("Old Handling", 100, "🎮", 1.05);
        car1.addHandlingUpgrade(existingHandlingPart);
        garage.addTuningPart(handlingPart);
        assertFalse(garage.installTuningPart(handlingPart));
        assertEquals(existingHandlingPart, car1.getHandlingUpgrade());
        assertEquals(1, garage.getTuningPartList().size());
    }

    @Test
    void testInstallWrongTypePart() {
        garage.setStarterCar(car1);
        TuningPart wrongTypePart = new TuningPart("Wrong Type", 100, "WrongStat", 1.1);
        garage.addTuningPart(wrongTypePart);
        assertFalse(garage.installTuningPart(wrongTypePart));
        assertNull(car1.getSpeedUpgrade());
        assertNull(car1.getHandlingUpgrade());
        assertEquals(1, garage.getTuningPartList().size());
    }

    @Test
    void testUninstallSpeedTuningPart() {
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(speedPart);
        garage.uninstallTuningPart(speedPart);
        assertNull(car1.getSpeedUpgrade());
        assertEquals(1, garage.getTuningPartList().size());
        assertTrue(garage.getTuningPartList().contains(speedPart));
        assertTrue(garage.getInstalledTuningParts().isEmpty());
    }

    @Test
    void testUninstallHandlingTuningPart() {
        garage.setStarterCar(car1);
        car1.addHandlingUpgrade(handlingPart);
        garage.uninstallTuningPart(handlingPart);
        assertNull(car1.getHandlingUpgrade());
        assertEquals(1, garage.getTuningPartList().size());
        assertTrue(garage.getTuningPartList().contains(handlingPart));
        assertTrue(garage.getInstalledTuningParts().isEmpty());
    }

    @Test
    void testUninstallTuningPartNoSelectedCar() {
        car1.addSpeedUpgrade(speedPart);
        garage.uninstallTuningPart(speedPart);
        assertEquals(speedPart, car1.getSpeedUpgrade());
        assertTrue(garage.getTuningPartList().isEmpty());
    }

    @Test
    void testUninstallNotInstalledSpeedPart() {
        garage.setStarterCar(car1);
        garage.uninstallTuningPart(speedPart);
        assertNull(car1.getSpeedUpgrade());
        assertTrue(garage.getTuningPartList().isEmpty());
    }

    @Test
    void testUninstallNotInstalledHandlingPart() {
        garage.setStarterCar(car1);
        garage.uninstallTuningPart(handlingPart);
        assertNull(car1.getHandlingUpgrade());
        assertTrue(garage.getTuningPartList().isEmpty());
    }


    @Test
    void testUninstallWrongTypePartFromCar() {
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(speedPart);
        TuningPart wrongTypePart = new TuningPart("Wrong Type", 100, "WrongStat", 1.1);
        garage.uninstallTuningPart(wrongTypePart);
        assertEquals(speedPart, car1.getSpeedUpgrade());
        assertTrue(garage.getTuningPartList().isEmpty());
    }

    @Test
    void testGetInstalledTuningParts() {
        garage.setStarterCar(car1);
        assertTrue(garage.getInstalledTuningParts().isEmpty());

        car1.addSpeedUpgrade(speedPart);
        assertEquals(1, garage.getInstalledTuningParts().size());
        assertTrue(garage.getInstalledTuningParts().contains(speedPart));

        car1.addHandlingUpgrade(handlingPart);
        assertEquals(2, garage.getInstalledTuningParts().size());
        assertTrue(garage.getInstalledTuningParts().contains(speedPart));
        assertTrue(garage.getInstalledTuningParts().contains(handlingPart));

        car1.removeSpeedUpgrade();
        assertEquals(1, garage.getInstalledTuningParts().size());
        assertTrue(garage.getInstalledTuningParts().contains(handlingPart));
    }


    @Test
    void testSetSelectedCarSwapping() {
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(speedPart);
        car1.setCustomName("Old Bessie");

        garage.addCar(car2);

        garage.setSelectedCar(car2);

        assertEquals(car2, garage.getSelectedCar());
        assertNull(car2.getSpeedUpgrade());
        assertNull(car2.getHandlingUpgrade());
        assertNull(car2.getCustomName());

        assertEquals(1, garage.getCarList().size());
        assertTrue(garage.getCarList().contains(car1));

        assertNull(car1.getSpeedUpgrade());
        assertNull(car1.getCustomName());

        assertEquals(1, garage.getTuningPartList().size());
        assertTrue(garage.getTuningPartList().contains(speedPart));
    }

    @Test
    void testSetSelectedCarWithUpgradesOnOldCar() {
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(speedPart);
        car1.addHandlingUpgrade(handlingPart);
        garage.addCar(car2);

        garage.setSelectedCar(car2);

        assertEquals(car2, garage.getSelectedCar());
        assertTrue(garage.getCarList().contains(car1));
        assertNull(car1.getSpeedUpgrade());
        assertNull(car1.getHandlingUpgrade());
        assertEquals(2, garage.getTuningPartList().size());
        assertTrue(garage.getTuningPartList().contains(speedPart));
        assertTrue(garage.getTuningPartList().contains(handlingPart));
    }

    @Test
    void testSetSelectedCarToItself() {
        Car car1CopyForReserve = new Car("Car 1 Copy", 100, 0.8, 0.9, 10, 1000);
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(speedPart);
        garage.addCar(car1CopyForReserve);

        garage.setSelectedCar(car1CopyForReserve);

        assertEquals(car1CopyForReserve, garage.getSelectedCar());
        assertTrue(garage.getCarList().contains(car1));
        assertNull(car1.getSpeedUpgrade());
        assertTrue(garage.getTuningPartList().contains(speedPart));
    }

    @Test
    void testSetSelectedCarWhenSelectedCarIsNull() {
        garage.addCar(car1);
        assertNull(garage.getSelectedCar());

        garage.setSelectedCar(car1);

        assertNull(garage.getSelectedCar());
        assertEquals(1, garage.getCarList().size());
        assertTrue(garage.getCarList().contains(car1));
    }

    @Test
    void testGetInstalledTuningPartsWhenNoSelectedCar() {
        assertTrue(garage.getInstalledTuningParts().isEmpty());
    }

    @Test
    void testGetInstalledTuningPartsClearsPreviousList() {
        garage.setStarterCar(car1);
        car1.addSpeedUpgrade(speedPart);
        assertEquals(1, garage.getInstalledTuningParts().size());

        car1.removeSpeedUpgrade();

        assertTrue(garage.getInstalledTuningParts().isEmpty());
        assertEquals(0, garage.getInstalledTuningParts().size());
    }
}