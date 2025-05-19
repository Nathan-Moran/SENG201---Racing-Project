package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private Car car;
    private TuningPart speedUpgrade;
    private TuningPart handlingUpgrade;

    @BeforeEach
    void setUp() {
        car = new Car("Test Car", 100.0, 0.8, 0.95, 15.0, 20000);
        speedUpgrade = new TuningPart("Speed Boost", 500, "暢", 1.2);
        handlingUpgrade = new TuningPart("Handling Boost", 400, "式", 1.1);
    }

    @Test
    void testCarCreation() {
        assertEquals("Test Car", car.getName());
        assertEquals(100.0, car.getSpeed());
        assertEquals(0.8, car.getHandling());
        assertEquals(0.95, car.getReliability());
        assertEquals(15.0, car.getFuelEconomy());
        assertEquals(20000, car.getPrice());
        assertNull(car.getSpeedUpgrade());
        assertNull(car.getHandlingUpgrade());
        assertNull(car.getCustomName());
    }

    @Test
    void testGetSpeedWithoutUpgrade() {
        assertEquals(100.0, car.getSpeed());
    }

    @Test
    void testGetSpeedWithUpgrade() {
        car.addSpeedUpgrade(speedUpgrade);
        assertEquals(100.0 * 1.2, car.getSpeed());
    }

    @Test
    void testGetHandlingWithoutUpgrade() {
        assertEquals(0.8, car.getHandling());
    }

    @Test
    void testGetHandlingWithUpgrade() {
        car.addHandlingUpgrade(handlingUpgrade);
        assertEquals(0.8 * 1.1, car.getHandling());
    }

    @Test
    void testAddSpeedUpgrade() {
        assertNull(car.getSpeedUpgrade());
        car.addSpeedUpgrade(speedUpgrade);
        assertNotNull(car.getSpeedUpgrade());
        assertEquals(speedUpgrade, car.getSpeedUpgrade());
    }

    @Test
    void testAddSpeedUpgradeWhenAlreadyPresent() {
        car.addSpeedUpgrade(speedUpgrade);
        TuningPart anotherSpeedUpgrade = new TuningPart("Another Speed Boost", 600, "暢", 1.3);
        car.addSpeedUpgrade(anotherSpeedUpgrade);
        assertEquals(speedUpgrade, car.getSpeedUpgrade());
        assertEquals(100.0 * 1.2, car.getSpeed());
    }

    @Test
    void testAddHandlingUpgrade() {
        assertNull(car.getHandlingUpgrade());
        car.addHandlingUpgrade(handlingUpgrade);
        assertNotNull(car.getHandlingUpgrade());
        assertEquals(handlingUpgrade, car.getHandlingUpgrade());
    }

    @Test
    void testAddHandlingUpgradeWhenAlreadyPresent() {
        car.addHandlingUpgrade(handlingUpgrade);
        TuningPart anotherHandlingUpgrade = new TuningPart("Another Handling Boost", 450, "式", 1.15);
        car.addHandlingUpgrade(anotherHandlingUpgrade);
        assertEquals(handlingUpgrade, car.getHandlingUpgrade());
        assertEquals(0.8 * 1.1, car.getHandling());
    }

    @Test
    void testRemoveSpeedUpgrade() {
        car.addSpeedUpgrade(speedUpgrade);
        assertNotNull(car.getSpeedUpgrade());
        car.removeSpeedUpgrade();
        assertNull(car.getSpeedUpgrade());
        assertEquals(100.0, car.getSpeed());
    }

    @Test
    void testRemoveSpeedUpgradeWhenNonePresent() {
        assertNull(car.getSpeedUpgrade());
        car.removeSpeedUpgrade();
        assertNull(car.getSpeedUpgrade());
    }

    @Test
    void testRemoveHandlingUpgrade() {
        car.addHandlingUpgrade(handlingUpgrade);
        assertNotNull(car.getHandlingUpgrade());
        car.removeHandlingUpgrade();
        assertNull(car.getHandlingUpgrade());
        assertEquals(0.8, car.getHandling());
    }

    @Test
    void testRemoveHandlingUpgradeWhenNonePresent() {
        assertNull(car.getHandlingUpgrade());
        car.removeHandlingUpgrade();
        assertNull(car.getHandlingUpgrade());
    }

    @Test
    void testGetSpeedUpgrade() {
        assertNull(car.getSpeedUpgrade());
        car.addSpeedUpgrade(speedUpgrade);
        assertEquals(speedUpgrade, car.getSpeedUpgrade());
    }

    @Test
    void testGetHandlingUpgrade() {
        assertNull(car.getHandlingUpgrade());
        car.addHandlingUpgrade(handlingUpgrade);
        assertEquals(handlingUpgrade, car.getHandlingUpgrade());
    }

    @Test
    void testSetAndGetCustomName() {
        assertNull(car.getCustomName());
        car.setCustomName("My Test Ride");
        assertEquals("My Test Ride", car.getCustomName());
    }

    @Test
    void testSetCustomNameToNull() {
        car.setCustomName("My Test Ride");
        assertEquals("My Test Ride", car.getCustomName());
        car.setCustomName(null);
        assertNull(car.getCustomName());
    }

    @Test
    void testSetNameFromPurchasable() {
        assertEquals("Test Car", car.getName());
        car.setName("New Test Car");
        assertEquals("New Test Car", car.getName());
    }

    @Test
    void testGetPriceFromPurchasable() {
        assertEquals(20000, car.getPrice());
    }
}