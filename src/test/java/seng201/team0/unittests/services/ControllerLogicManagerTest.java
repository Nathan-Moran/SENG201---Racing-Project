package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.StarterCarInventory;
import seng201.team0.models.TuningPart;
import seng201.team0.services.ControllerService;
import seng201.team0.services.GameEnvironment;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerLogicManagerTest {

    private GameEnvironment gameEnvironment;
    private ControllerService logicManager;
    private Car testCar;

    @BeforeEach
    void setUp() {
        gameEnvironment = new GameEnvironment();
        logicManager = gameEnvironment.getControllerService();
        testCar = new Car("Test Model", 150.0, 0.85, 0.95, 12.5, 25000);
    }

    @Test
    void testStoreLeftOverCars() {
        StarterCarInventory starterInventory = gameEnvironment.getStarterCarInventory();
        starterInventory.setupStarterCarInventory();
        int initialStarterCars = starterInventory.getCarList().size();
        assertTrue(initialStarterCars > 0);
        int initialShopCars = gameEnvironment.getShopInventory().getCarList().size();

        logicManager.storeLeftOverCars(starterInventory);

        assertTrue(starterInventory.getCarList().isEmpty());
        assertEquals(initialShopCars + initialStarterCars, gameEnvironment.getShopInventory().getCarList().size());
    }

    @Test
    void testStoreLeftOverCarsWhenStarterEmpty() {
        StarterCarInventory starterInventory = gameEnvironment.getStarterCarInventory();
        starterInventory.getCarList().clear();
        int initialShopCars = gameEnvironment.getShopInventory().getCarList().size();

        logicManager.storeLeftOverCars(starterInventory);

        assertTrue(starterInventory.getCarList().isEmpty());
        assertEquals(initialShopCars, gameEnvironment.getShopInventory().getCarList().size());
    }

    @Test
    void testSetLabelsNoActiveCar() {
        assertNull(gameEnvironment.getPlayerInventory().getSelectedCar());
        assertEquals("-", logicManager.setLabels("Speed"));
        assertEquals("-", logicManager.setLabels("Model"));
        assertEquals("-", logicManager.setLabels("SpeedUpgrade"));
    }

    @Test
    void testSetLabelsSpeed() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(String.valueOf(testCar.getSpeed()), logicManager.setLabels("Speed"));
    }

    @Test
    void testSetLabelsFuel() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(String.valueOf(testCar.getFuelEconomy()), logicManager.setLabels("Fuel"));
    }

    @Test
    void testSetLabelsHandling() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(String.valueOf(testCar.getHandling()), logicManager.setLabels("Handling"));
    }

    @Test
    void testSetLabelsModelNoCustomName() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(testCar.getName(), logicManager.setLabels("Model"));
    }

    @Test
    void testSetLabelsModelWithCustomName() {
        testCar.setCustomName("My Ride");
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals("My Ride", logicManager.setLabels("Model"));
    }

    @Test
    void testSetLabelsReliability() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(String.valueOf(testCar.getReliability()), logicManager.setLabels("Reliability"));
    }

    @Test
    void testSetLabelsSpeedUpgradeNotInstalled() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals("-", logicManager.setLabels("SpeedUpgrade"));
    }

    @Test
    void testSetLabelsSpeedUpgradeInstalled() {
        TuningPart speedUpgrade = new TuningPart("Booster", 500, "Speed", 1.2);
        testCar.addSpeedUpgrade(speedUpgrade);
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(speedUpgrade.getName(), logicManager.setLabels("SpeedUpgrade"));
    }

    @Test
    void testSetLabelsHandlingUpgradeNotInstalled() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals("-", logicManager.setLabels("HandlingUpgrade"));
    }

    @Test
    void testSetLabelsHandlingUpgradeInstalled() {
        TuningPart handlingUpgrade = new TuningPart("Gripper", 400, "Handling", 1.1);
        testCar.addHandlingUpgrade(handlingUpgrade);
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertEquals(handlingUpgrade.getName(), logicManager.setLabels("HandlingUpgrade"));
    }

    @Test
    void testSetLabelsUnknownStat() {
        gameEnvironment.getPlayerInventory().setStarterCar(testCar);
        assertNull(logicManager.setLabels("Unknown"));
    }


    @Test
    void testNameCheckerValidName() {
        assertEquals("Valid Name", logicManager.nameChecker("Player1"));
        assertEquals("Valid Name", logicManager.nameChecker("abc"));
        assertEquals("Valid Name", logicManager.nameChecker("123456789012345"));
    }

    @Test
    void testNameCheckerTooShort() {
        assertEquals("Name must be between 3 and 15 characters", logicManager.nameChecker("ab"));
    }

    @Test
    void testNameCheckerTooLong() {
        assertEquals("Name must be between 3 and 15 characters", logicManager.nameChecker("1234567890123456"));
    }

    @Test
    void testNameCheckerInvalidCharacters() {
        assertEquals("Name must not contain special characters", logicManager.nameChecker("Player!"));
        assertEquals("Name must not contain special characters", logicManager.nameChecker("Player One"));
    }

    @Test
    void testNameCheckerInvalidLengthAndCharacters() {
        assertEquals("Name must be between 3 and 15 characters", logicManager.nameChecker("a!"));
    }
}