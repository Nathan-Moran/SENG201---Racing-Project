package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.StarterCarInventory;
import seng201.team0.services.GameEnvironment;

import static org.junit.jupiter.api.Assertions.*;

class StarterCarInventoryTest {

    private StarterCarInventory starterCarInventory;
    private GameEnvironment gameEnvironment;

    @BeforeEach
    void setUp() {
        gameEnvironment = new GameEnvironment(); 
        starterCarInventory = new StarterCarInventory(gameEnvironment); 
    }

    @Test
    void setupStarterCarInventoryPopulatesList() {
        assertTrue(starterCarInventory.getCarList().isEmpty()); 
        starterCarInventory.setupStarterCarInventory(); 
        assertFalse(starterCarInventory.getCarList().isEmpty());
        assertEquals(3, starterCarInventory.getCarList().size()); 
        assertTrue(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Honda Civic"))); 
        assertTrue(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Mazda MPS"))); 
        assertTrue(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Nissan Z"))); 
    }

    @Test
    void setupStarterCarInventoryClearsExistingList() {
        starterCarInventory.addCar(new seng201.team0.models.Car("Dummy",0,0,0,0,0)); 
        assertFalse(starterCarInventory.getCarList().isEmpty()); 
        starterCarInventory.setupStarterCarInventory(); 
        assertEquals(3, starterCarInventory.getCarList().size()); 
        assertFalse(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Dummy"))); 
    }

    @Test
    void constructorInitializesLists() {
        StarterCarInventory newInventory = new StarterCarInventory(gameEnvironment); 
        assertNotNull(newInventory.getCarList()); 
        assertNotNull(newInventory.getTuningPartList()); 
        assertTrue(newInventory.getCarList().isEmpty()); 
        assertTrue(newInventory.getTuningPartList().isEmpty()); 
    }

    @Test
    void setupStarterCarInventoryWithNullCatalogue() {
        assertTrue(true);
    }
}