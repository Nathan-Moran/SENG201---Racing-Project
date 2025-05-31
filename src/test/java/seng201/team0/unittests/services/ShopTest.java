package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Course;
import seng201.team0.models.Shop;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;


import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private Shop shop;
    private GameEnvironment gameEnvironment;

    @BeforeEach
    void setUp() {
        gameEnvironment = new GameEnvironment(); 
        shop = gameEnvironment.getShopInventory(); 
    }

    @Test
    void constructorInitializesLockedMapsAndLists() {
        assertNotNull(shop.getLockedCarsMap()); 
        
        assertEquals(2, shop.getLockedCarsMap().size()); 
        assertTrue(shop.getLockedCarsMap().containsKey(Course.DESERT)); 
        assertEquals("Dune Drifter", shop.getLockedCarsMap().get(Course.DESERT).getName()); 
        assertTrue(shop.getLockedCarsMap().containsKey(Course.CITY)); 
        assertEquals("Ridge Racer", shop.getLockedCarsMap().get(Course.CITY).getName()); 

        assertNotNull(shop.getAllAvailableCars()); 
        assertEquals(3, shop.getAllAvailableCars().size()); 
        assertTrue(shop.getAllAvailableCars().stream().anyMatch(c -> c.getName().equals("Toyota Supra"))); 

        assertNotNull(shop.getAllAvailableTuningParts()); 
        assertEquals(6, shop.getAllAvailableTuningParts().size()); 
        assertTrue(shop.getAllAvailableTuningParts().stream().anyMatch(p -> p.getName().equals("Ethanol"))); 
    }

    @Test
    void populateBaseMasterListsCorrectly() {
        assertFalse(shop.getAllAvailableCars().isEmpty()); 
        assertFalse(shop.getAllAvailableTuningParts().isEmpty()); 
        assertEquals(4, gameEnvironment.getItemCatalogue().getLockedCarList().size()); 
        assertEquals(2, shop.getLockedCarsMap().size()); 
    }


    @Test
    void setupLockedItemsCorrectly() {
        assertEquals("Dune Drifter", shop.getLockedCarsMap().get(Course.DESERT).getName()); 
        assertEquals("Ridge Racer", shop.getLockedCarsMap().get(Course.CITY).getName()); 
        assertNull(shop.getLockedCarsMap().get(Course.MOUNTAIN)); 
        assertNull(shop.getLockedCarsMap().get(Course.COUNTRY)); 
    }

    @Test
    void setShopInventoryPopulatesShopLists() {
        shop.getCarList().clear(); 
        shop.getTuningPartList().clear(); 

        shop.setShopInventory(); 
        
        assertEquals(3, shop.getCarList().size()); 
        assertEquals(3, shop.getTuningPartList().size()); 
        
        for (Car carInShop : shop.getCarList()) { 
            assertTrue(shop.getAllAvailableCars().contains(carInShop)); 
        }
        for (TuningPart partInShop : shop.getTuningPartList()) { 
            assertTrue(shop.getAllAvailableTuningParts().contains(partInShop)); 
        }
    }

    @Test
    void setShopInventoryWithFewerThanThreeAvailableItems() {
        shop.getAllAvailableCars().clear(); 
        shop.getAllAvailableTuningParts().clear(); 

        Car testCar = new Car("Test Car", 0.5, 0.5, 0.5, 10, 1000);
        TuningPart testPart = new TuningPart("Test Part", 100, "💨", 1.1);

        shop.getAllAvailableCars().add(testCar); 
        shop.getAllAvailableTuningParts().add(testPart); 

        shop.setShopInventory(); 

        assertEquals(1, shop.getCarList().size()); 
        assertTrue(shop.getCarList().contains(testCar)); 
        assertEquals(1, shop.getTuningPartList().size()); 
        assertTrue(shop.getTuningPartList().contains(testPart)); 
    }


    @Test
    void getLockedCarsMapReturnsMap() {
        assertNotNull(shop.getLockedCarsMap()); 
    }

    @Test
    void removeLockedCar() {
        assertTrue(shop.getLockedCarsMap().containsKey(Course.DESERT)); 
        shop.removeLockedCar(Course.DESERT); 
        assertFalse(shop.getLockedCarsMap().containsKey(Course.DESERT)); 
    }

    @Test
    void removeCarFromAllAvailable() {
        Car carToRemove = shop.getAllAvailableCars().get(0); 
        assertTrue(shop.getAllAvailableCars().contains(carToRemove)); 
        shop.removeCarFromAllAvailable(carToRemove); 
        assertFalse(shop.getAllAvailableCars().contains(carToRemove)); 
    }

    @Test
    void removePartFromAllAvailable() {
        TuningPart partToRemove = shop.getAllAvailableTuningParts().get(0); 
        assertTrue(shop.getAllAvailableTuningParts().contains(partToRemove)); 
        shop.removePartFromAllAvailable(partToRemove); 
        assertFalse(shop.getAllAvailableTuningParts().contains(partToRemove)); 
    }

    @Test
    void addCarToAllAvailable() {
        Car newCar = new Car("New Unique Car", 1,1,1,1,1);
        assertFalse(shop.getAllAvailableCars().contains(newCar)); 
        shop.addCarToAllAvailable(newCar); 
        assertTrue(shop.getAllAvailableCars().contains(newCar)); 
    }

    @Test
    void addPartToAllAvailable() {
        TuningPart newPart = new TuningPart("New Unique Part", 1, "S", 1);
        assertFalse(shop.getAllAvailableTuningParts().contains(newPart)); 
        shop.addPartToAllAvailable(newPart); 
        assertTrue(shop.getAllAvailableTuningParts().contains(newPart)); 
    }
}