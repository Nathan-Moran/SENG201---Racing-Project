package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.StarterCarInventory;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

public class StarterCarInventoryTest {

    private StarterCarInventory starterCarInventory;

    @BeforeEach
    void setUp() {
        starterCarInventory = new StarterCarInventory();
    }

    @Test
    void testInitialInventoryIsEmpty() {
        assertTrue(starterCarInventory.getCarList().isEmpty());
        assertTrue(starterCarInventory.getTuningPartList().isEmpty());
    }

    @Test
    void testSetupStarterCarInventoryAddsCars() {
        starterCarInventory.setupStarterCarInventory();
        ObservableList<Car> cars = starterCarInventory.getCarList();
        assertFalse(cars.isEmpty());
        assertEquals(3, cars.size());
        assertTrue(cars.stream().anyMatch(car -> car.getName().equals("Honda Civic R")));
        assertTrue(cars.stream().anyMatch(car -> car.getName().equals("Mazda MPS")));
        assertTrue(cars.stream().anyMatch(car -> car.getName().equals("Nissan Z")));
    }

    @Test
    void testSetupStarterCarInventoryNoTuningParts() {
        starterCarInventory.setupStarterCarInventory();
        assertTrue(starterCarInventory.getTuningPartList().isEmpty());
    }

    @Test
    void testSetupStarterCarInventoryCarDetailsHonda() {
        starterCarInventory.setupStarterCarInventory();
        Car honda = starterCarInventory.getCarList().stream()
                .filter(c -> c.getName().equals("Honda Civic R")).findFirst().orElse(null);
        assertNotNull(honda);
        assertEquals(0.6, honda.getSpeed());
        assertEquals(0.5, honda.getHandling());
        assertEquals(0.7, honda.getReliability());
        assertEquals(20, honda.getFuelEconomy());
        assertEquals(1000, honda.getPrice());
    }

    @Test
    void testSetupStarterCarInventoryCarDetailsMazda() {
        starterCarInventory.setupStarterCarInventory();
        Car mazda = starterCarInventory.getCarList().stream()
                .filter(c -> c.getName().equals("Mazda MPS")).findFirst().orElse(null);
        assertNotNull(mazda);
        assertEquals(0.5, mazda.getSpeed());
        assertEquals(0.7, mazda.getHandling());
        assertEquals(0.7, mazda.getReliability());
        assertEquals(20, mazda.getFuelEconomy());
        assertEquals(1000, mazda.getPrice());
    }

    @Test
    void testSetupStarterCarInventoryCarDetailsNissan() {
        starterCarInventory.setupStarterCarInventory();
        Car nissan = starterCarInventory.getCarList().stream()
                .filter(c -> c.getName().equals("Nissan Z")).findFirst().orElse(null);
        assertNotNull(nissan);
        assertEquals(0.5, nissan.getSpeed());
        assertEquals(0.6, nissan.getHandling());
        assertEquals(0.8, nissan.getReliability());
        assertEquals(20, nissan.getFuelEconomy());
        assertEquals(1000, nissan.getPrice());
    }

    @Test
    void testSetupStarterCarInventoryIsIdempotentContentWise() {
        starterCarInventory.setupStarterCarInventory();
        int initialCarCount = starterCarInventory.getCarList().size();

        starterCarInventory.setupStarterCarInventory();
        assertEquals(initialCarCount * 2, starterCarInventory.getCarList().size());
    }

    @Test
    void testAddCarToStarterInventory() {
        Car newCar = new Car("Extra Starter", 50, 0.4, 0.6, 25, 500);
        starterCarInventory.addCar(newCar);
        assertTrue(starterCarInventory.getCarList().contains(newCar));
        assertEquals(1, starterCarInventory.getCarList().size());

        starterCarInventory.setupStarterCarInventory();
        assertEquals(3 + 1, starterCarInventory.getCarList().size());
    }
}