package seng201.team0.unittests.services; // Assuming services package as per previous context, adjust if models

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;
    private TuningPart speedUpgrade;
    private TuningPart handlingUpgrade;

    @BeforeEach
    void setUp() {
        // Car(String name, double speed, double handling, double reliability, int fuelEconomy, int price)
        car = new Car("Test Car", 0.7, 0.6, 0.8, 25, 2000);
        speedUpgrade = new TuningPart("Speed Boost", 500, "💨", 1.2); // Assuming "💨" is for speed
        handlingUpgrade = new TuningPart("Handling Boost", 400, "🎮", 1.1); // Assuming "🎮" is for handling
    }

    @Test
    void constructorCopiesCorrectly() {
        Car originalCar = new Car("Original", 0.8, 0.7, 0.9, 30, 3000);
        // Add upgrades to original to ensure they are not copied to the new car's upgrade slots
        originalCar.addSpeedUpgrade(new TuningPart("Temp Speed", 100, "💨", 1.1));
        originalCar.addHandlingUpgrade(new TuningPart("Temp Handling", 100, "🎮", 1.1));
        originalCar.setCustomName("My Ride");

        Car copiedCar = new Car(originalCar);

        assertEquals(originalCar.getName(), copiedCar.getName());
        assertEquals(originalCar.getPrice(), copiedCar.getPrice());
        // Access base stats through getters which reflect them before upgrades for the new car
        assertEquals(0.8, copiedCar.getSpeed(), 0.001); // Base speed of original
        assertEquals(0.7, copiedCar.getHandling(), 0.001); // Base handling of original
        assertEquals(0.9, copiedCar.getReliability(), 0.001); // Base reliability of original
        assertEquals(30, copiedCar.getFuelEconomy(), 0.001); // Base fuel economy of original

        assertNull(copiedCar.getSpeedUpgrade(), "Copied car should not have speed upgrade initially.");
        assertNull(copiedCar.getHandlingUpgrade(), "Copied car should not have handling upgrade initially.");
        assertNull(copiedCar.getCustomName(), "Copied car should not have custom name initially.");
    }

    @Test
    void getReliabilityPercentFormatsCorrectly() {
        assertEquals("80%", car.getReliabilityPercent());
    }

    @Test
    void getSpeedStringFormatsCorrectly() {
        // baseSpeed * 350
        assertEquals(String.format("%.0f", 0.7 * 350.0), car.getSpeedString());
    }

    @Test
    void getHandlingPercentFormatsCorrectly() {
        assertEquals("60%", car.getHandlingPercent());
    }

    @Test
    void getFuelEconomyStringFormatsCorrectly() {
        // baseFuelEconomy * 30
        assertEquals(String.format("%.0f", car.getFuelEconomy() * 30.0), car.getFuelEconomyString());
    }


    @Test
    void getSpeedReturnsBaseSpeedInitially() {
        assertEquals(0.7, car.getSpeed(), 0.001);
    }

    @Test
    void getSpeedReturnsBoostedSpeed() {
        car.addSpeedUpgrade(speedUpgrade);
        assertEquals(0.7 * 1.2, car.getSpeed(), 0.001);
    }

    @Test
    void getHandlingReturnsBaseHandlingInitially() {
        assertEquals(0.6, car.getHandling(), 0.001);
    }

    @Test
    void getHandlingReturnsBoostedHandling() {
        car.addHandlingUpgrade(handlingUpgrade);
        assertEquals(0.6 * 1.1, car.getHandling(), 0.001);
    }

    @Test
    void getReliabilityReturnsBaseReliability() {
        assertEquals(0.8, car.getReliability(), 0.001);
    }

    @Test
    void getFuelEconomyReturnsBaseFuelEconomy() {
        assertEquals(25, car.getFuelEconomy(), 0.001);
    }

    @Test
    void addSpeedUpgradeWorksWhenSlotIsEmpty() {
        assertNull(car.getSpeedUpgrade());
        car.addSpeedUpgrade(speedUpgrade);
        assertEquals(speedUpgrade, car.getSpeedUpgrade());
    }

    @Test
    void addSpeedUpgradeDoesNotReplaceExisting() {
        TuningPart anotherSpeedUpgrade = new TuningPart("Another Speed", 700, "💨", 1.3);
        car.addSpeedUpgrade(speedUpgrade);
        car.addSpeedUpgrade(anotherSpeedUpgrade); // This should not change the upgrade
        assertEquals(speedUpgrade, car.getSpeedUpgrade());
    }

    @Test
    void addHandlingUpgradeWorksWhenSlotIsEmpty() {
        assertNull(car.getHandlingUpgrade());
        car.addHandlingUpgrade(handlingUpgrade);
        assertEquals(handlingUpgrade, car.getHandlingUpgrade());
    }

    @Test
    void addHandlingUpgradeDoesNotReplaceExisting() {
        TuningPart anotherHandlingUpgrade = new TuningPart("Another Handling", 600, "🎮", 1.2);
        car.addHandlingUpgrade(handlingUpgrade);
        car.addHandlingUpgrade(anotherHandlingUpgrade); // This should not change the upgrade
        assertEquals(handlingUpgrade, car.getHandlingUpgrade());
    }

    @Test
    void removeSpeedUpgradeWorks() {
        car.addSpeedUpgrade(speedUpgrade);
        assertNotNull(car.getSpeedUpgrade());
        car.removeSpeedUpgrade();
        assertNull(car.getSpeedUpgrade());
    }

    @Test
    void removeSpeedUpgradeOnEmptySlot() {
        assertNull(car.getSpeedUpgrade());
        car.removeSpeedUpgrade(); // Should not throw error
        assertNull(car.getSpeedUpgrade());
    }

    @Test
    void removeHandlingUpgradeWorks() {
        car.addHandlingUpgrade(handlingUpgrade);
        assertNotNull(car.getHandlingUpgrade());
        car.removeHandlingUpgrade();
        assertNull(car.getHandlingUpgrade());
    }

    @Test
    void removeHandlingUpgradeOnEmptySlot() {
        assertNull(car.getHandlingUpgrade());
        car.removeHandlingUpgrade(); // Should not throw error
        assertNull(car.getHandlingUpgrade());
    }

    @Test
    void getAndSetCustomName() {
        assertNull(car.getCustomName());
        car.setCustomName("MyTestCar");
        assertEquals("MyTestCar", car.getCustomName());
    }
}