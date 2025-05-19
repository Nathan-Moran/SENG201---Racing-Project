package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Shop;
import seng201.team0.models.TuningPart;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shop;

    @BeforeEach
    void setUp() {
        shop = new Shop();
    }

    @Test
    void testInitialShopIsEmpty() {
        assertTrue(shop.getCarList().isEmpty());
        assertTrue(shop.getTuningPartList().isEmpty());
    }

    @Test
    void testSetShopInventoryAddsCars() {
        shop.setShopInventory();
        ObservableList<Car> cars = shop.getCarList();
        assertFalse(cars.isEmpty());
        assertEquals(2, cars.size());
        assertTrue(cars.stream().anyMatch(car -> car.getName().equals("Toyota Supra")));
        assertTrue(cars.stream().anyMatch(car -> car.getName().equals("Ferrari 458")));
    }

    @Test
    void testSetShopInventoryAddsTuningParts() {
        shop.setShopInventory();
        ObservableList<TuningPart> parts = shop.getTuningPartList();
        assertFalse(parts.isEmpty());
        assertEquals(6, parts.size());
        assertTrue(parts.stream().anyMatch(part -> part.getName().equals("Ethanol")));
        assertTrue(parts.stream().anyMatch(part -> part.getName().equals("SuperCharger")));
        assertTrue(parts.stream().anyMatch(part -> part.getName().equals("TurboKit")));
        assertTrue(parts.stream().anyMatch(part -> part.getName().equals("StreetWheels")));
        assertTrue(parts.stream().anyMatch(part -> part.getName().equals("SportsWheels")));
        assertTrue(parts.stream().anyMatch(part -> part.getName().equals("RacingWheels")));
    }

    @Test
    void testSetShopInventoryCarDetails() {
        shop.setShopInventory();
        Car supra = shop.getCarList().stream().filter(c -> c.getName().equals("Toyota Supra")).findFirst().orElse(null);
        assertNotNull(supra);
        assertEquals(0.5, supra.getSpeed());
        assertEquals(0.5, supra.getHandling());
        assertEquals(0.8, supra.getReliability());
        assertEquals(15, supra.getFuelEconomy());
        assertEquals(2000, supra.getPrice());

        Car ferrari = shop.getCarList().stream().filter(c -> c.getName().equals("Ferrari 458")).findFirst().orElse(null);
        assertNotNull(ferrari);
        assertEquals(1.0, ferrari.getSpeed());
        assertEquals(0.7, ferrari.getHandling());
        assertEquals(0.7, ferrari.getReliability());
        assertEquals(10, ferrari.getFuelEconomy());
        assertEquals(3000, ferrari.getPrice());
    }

    @Test
    void testSetShopInventoryTuningPartDetails() {
        shop.setShopInventory();
        TuningPart ethanol = shop.getTuningPartList().stream().filter(p -> p.getName().equals("Ethanol")).findFirst().orElse(null);
        assertNotNull(ethanol);
        assertEquals(500, ethanol.getPrice());
        assertEquals("💨", ethanol.getStat());
        assertEquals(1.2, ethanol.getBoost());

        TuningPart streetWheels = shop.getTuningPartList().stream().filter(p -> p.getName().equals("StreetWheels")).findFirst().orElse(null);
        assertNotNull(streetWheels);
        assertEquals(400, streetWheels.getPrice());
        assertEquals("🎮", streetWheels.getStat());
        assertEquals(1.2, streetWheels.getBoost());
    }

    @Test
    void testSetShopInventoryIsIdempotentContentWise() {
        shop.setShopInventory();
        int initialCarCount = shop.getCarList().size();
        int initialPartCount = shop.getTuningPartList().size();

        shop.setShopInventory();
        assertEquals(initialCarCount * 2, shop.getCarList().size());
        assertEquals(initialPartCount * 2, shop.getTuningPartList().size());
    }

    @Test
    void testAddCarToShop() {
        Car newCar = new Car("New Shop Car", 200, 0.9, 0.95, 20, 50000);
        shop.addCar(newCar);
        assertTrue(shop.getCarList().contains(newCar));
    }

    @Test
    void testAddTuningPartToShop() {
        TuningPart newPart = new TuningPart("New Shop Part", 300, "TestStat", 1.05);
        shop.addTuningPart(newPart);
        assertTrue(shop.getTuningPartList().contains(newPart));
    }
}