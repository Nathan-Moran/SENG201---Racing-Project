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
        // GameEnvironment constructor creates ItemCatalogue, ShopService, ControllerService, Shop, and StarterCarInventory
        gameEnvironment = new GameEnvironment(); //
        shop = gameEnvironment.getShopInventory(); //
    }

    @Test
    void constructorInitializesLockedMapsAndLists() {
        assertNotNull(shop.getLockedCarsMap()); //
        // Based on ItemCatalogue and Shop's setupLockedItems
        // Note: The switch in setupLockedItems has cases like "Sandstorm Strider", "Cliff Climber"
        // which are not in the ItemCatalogue's lockedCarListInternal.
        // This means the lockedCarsMap will only contain entries for cars that *are* in both.
        // ItemCatalogue adds: "Dune Drifter", "Ridge Racer", "Vineyard Viper", "CommuterKing"
        // Shop setupLockedItems maps: "Dune Drifter" to DESERT, "Ridge Racer" to CITY.
        // "Sandstorm Strider" and "Cliff Climber" are in the switch but not in lockedCarList.
        // "Vineyard Viper" and "CommuterKing" are in lockedCarList but not in switch.
        // So, only 2 cars should be in lockedCarsMap after setup.
        assertEquals(2, shop.getLockedCarsMap().size()); //
        assertTrue(shop.getLockedCarsMap().containsKey(Course.DESERT)); //
        assertEquals("Dune Drifter", shop.getLockedCarsMap().get(Course.DESERT).getName()); //
        assertTrue(shop.getLockedCarsMap().containsKey(Course.CITY)); //
        assertEquals("Ridge Racer", shop.getLockedCarsMap().get(Course.CITY).getName()); //

        assertNotNull(shop.getAllAvailableCars()); //
        // allAvailableCars should be populated from ItemCatalogue's shopCarListInternal initially
        assertEquals(3, shop.getAllAvailableCars().size()); //
        assertTrue(shop.getAllAvailableCars().stream().anyMatch(c -> c.getName().equals("Toyota Supra"))); //

        assertNotNull(shop.getAllAvailableTuningParts()); //
        // allAvailableTuningParts should be populated from ItemCatalogue's getTuningPartList
        assertEquals(6, shop.getAllAvailableTuningParts().size()); //
        assertTrue(shop.getAllAvailableTuningParts().stream().anyMatch(p -> p.getName().equals("Ethanol"))); //
    }

    @Test
    void populateBaseMasterListsCorrectly() {
        // This is implicitly tested by the constructor test, as constructor calls it.
        // We can add a specific check though.
        // Assuming ItemCatalogue is setup correctly by GameEnvironment
        assertFalse(shop.getAllAvailableCars().isEmpty()); //
        assertFalse(shop.getAllAvailableTuningParts().isEmpty()); //
        // lockedCarList in shop should contain the 4 locked cars from catalogue
        assertEquals(4, gameEnvironment.getItemCatalogue().getLockedCarList().size()); // Check if this test refers to the private field in Shop
        // It seems this test should refer to the size of the local lockedCarList in Shop.
        // However, `shop.lockedCarList` is private.
        // The effect of `populateBaseMasterLists` on `lockedCarList` is internal to `setupLockedItems`.
        // The public effect is on `lockedCarsMap`.
        // The local `lockedCarList` in `Shop` should have 4 cars from the catalogue's `lockedCarListInternal`.
        // We verify this by checking the size of `shop.getLockedCarsMap()` which depends on `lockedCarList`
        // and the filtering in `setupLockedItems`.
        // As established, Dune Drifter and Ridge Racer make it to the map.
        // Vineyard Viper and Commuter King are in lockedCarListInternal but not in the switch.
        assertEquals(2, shop.getLockedCarsMap().size()); //
    }


    @Test
    void setupLockedItemsCorrectly() {
        // This is also implicitly tested by the constructor.
        // Specifically checks the mapping.
        assertEquals("Dune Drifter", shop.getLockedCarsMap().get(Course.DESERT).getName()); //
        assertEquals("Ridge Racer", shop.getLockedCarsMap().get(Course.CITY).getName()); //
        assertNull(shop.getLockedCarsMap().get(Course.MOUNTAIN)); // "Sandstorm Strider" is not in ItemCatalogue's initial locked list
        assertNull(shop.getLockedCarsMap().get(Course.COUNTRY)); // "Cliff Climber" is not in ItemCatalogue's initial locked list
    }

    @Test
    void setShopInventoryPopulatesShopLists() {
        shop.getCarList().clear(); //
        shop.getTuningPartList().clear(); //

        shop.setShopInventory(); //

        // Cars: Min(3, allAvailableCars.size()) which is Min(3,3) = 3
        assertEquals(3, shop.getCarList().size()); //
        // Parts: Min(3, allAvailableTuningParts.size()) which is Min(3,6) = 3
        assertEquals(3, shop.getTuningPartList().size()); //

        // Check they are from the allAvailable lists
        for (Car carInShop : shop.getCarList()) { //
            assertTrue(shop.getAllAvailableCars().contains(carInShop)); //
        }
        for (TuningPart partInShop : shop.getTuningPartList()) { //
            assertTrue(shop.getAllAvailableTuningParts().contains(partInShop)); //
        }
    }

    @Test
    void setShopInventoryWithFewerThanThreeAvailableItems() {
        shop.getAllAvailableCars().clear(); //
        shop.getAllAvailableTuningParts().clear(); //

        Car testCar = new Car("Test Car", 0.5, 0.5, 0.5, 10, 1000);
        TuningPart testPart = new TuningPart("Test Part", 100, "💨", 1.1);

        shop.getAllAvailableCars().add(testCar); //
        shop.getAllAvailableTuningParts().add(testPart); //

        shop.setShopInventory(); //

        assertEquals(1, shop.getCarList().size()); //
        assertTrue(shop.getCarList().contains(testCar)); //
        assertEquals(1, shop.getTuningPartList().size()); //
        assertTrue(shop.getTuningPartList().contains(testPart)); //
    }


    @Test
    void getLockedCarsMapReturnsMap() {
        assertNotNull(shop.getLockedCarsMap()); //
        // Content checked in constructorInitializesLockedMapsAndLists
    }

    @Test
    void removeLockedCar() {
        assertTrue(shop.getLockedCarsMap().containsKey(Course.DESERT)); //
        shop.removeLockedCar(Course.DESERT); //
        assertFalse(shop.getLockedCarsMap().containsKey(Course.DESERT)); //
    }

    @Test
    void removeCarFromAllAvailable() {
        Car carToRemove = shop.getAllAvailableCars().get(0); //
        assertTrue(shop.getAllAvailableCars().contains(carToRemove)); //
        shop.removeCarFromAllAvailable(carToRemove); //
        assertFalse(shop.getAllAvailableCars().contains(carToRemove)); //
    }

    @Test
    void removePartFromAllAvailable() {
        TuningPart partToRemove = shop.getAllAvailableTuningParts().get(0); //
        assertTrue(shop.getAllAvailableTuningParts().contains(partToRemove)); //
        shop.removePartFromAllAvailable(partToRemove); //
        assertFalse(shop.getAllAvailableTuningParts().contains(partToRemove)); //
    }

    @Test
    void addCarToAllAvailable() {
        Car newCar = new Car("New Unique Car", 1,1,1,1,1);
        assertFalse(shop.getAllAvailableCars().contains(newCar)); //
        shop.addCarToAllAvailable(newCar); //
        assertTrue(shop.getAllAvailableCars().contains(newCar)); //
    }

    @Test
    void addPartToAllAvailable() {
        TuningPart newPart = new TuningPart("New Unique Part", 1, "S", 1);
        assertFalse(shop.getAllAvailableTuningParts().contains(newPart)); //
        shop.addPartToAllAvailable(newPart); //
        assertTrue(shop.getAllAvailableTuningParts().contains(newPart)); //
    }
}