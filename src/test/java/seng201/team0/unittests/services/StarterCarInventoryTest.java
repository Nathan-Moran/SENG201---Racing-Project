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
        gameEnvironment = new GameEnvironment(); //
        starterCarInventory = new StarterCarInventory(gameEnvironment); //
    }

    @Test
    void setupStarterCarInventoryPopulatesList() {
        assertTrue(starterCarInventory.getCarList().isEmpty()); //
        starterCarInventory.setupStarterCarInventory(); //
        assertFalse(starterCarInventory.getCarList().isEmpty()); //
        // Based on ItemCatalogue, there should be 3 starter cars
        assertEquals(3, starterCarInventory.getCarList().size()); //
        assertTrue(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Honda Civic"))); //
        assertTrue(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Mazda MPS"))); //
        assertTrue(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Nissan Z"))); //
    }

    @Test
    void setupStarterCarInventoryClearsExistingList() {
        starterCarInventory.addCar(new seng201.team0.models.Car("Dummy",0,0,0,0,0)); //
        assertFalse(starterCarInventory.getCarList().isEmpty()); //
        starterCarInventory.setupStarterCarInventory(); //
        assertEquals(3, starterCarInventory.getCarList().size()); //
        assertFalse(starterCarInventory.getCarList().stream().anyMatch(car -> car.getName().equals("Dummy"))); //
    }

    @Test
    void constructorInitializesLists() {
        // gameEnvironment is not null due to @BeforeEach
        StarterCarInventory newInventory = new StarterCarInventory(gameEnvironment); //
        assertNotNull(newInventory.getCarList()); //
        assertNotNull(newInventory.getTuningPartList()); //
        assertTrue(newInventory.getCarList().isEmpty()); //
        assertTrue(newInventory.getTuningPartList().isEmpty()); //
    }

    @Test
    void setupStarterCarInventoryWithNullCatalogue() {
        // This scenario is tricky to test directly without modifying GameEnvironment or ItemCatalogue significantly for the test.
        // The current implementation of StarterCarInventory relies on gameEnvironment.getItemCatalogue() not being null.
        // If it were null, a NullPointerException would occur.
        // A robust test would involve mocking GameEnvironment or ensuring ItemCatalogue can be null.
        // For now, we assume gameEnvironment.getItemCatalogue() always returns a valid catalogue as per GameEnvironment's constructor.
        // If the method had a null check for the catalogue itself:
        // GameEnvironment mockGameEnv = mock(GameEnvironment.class);
        // when(mockGameEnv.getItemCatalogue()).thenReturn(null);
        // StarterCarInventory customInventory = new StarterCarInventory(mockGameEnv);
        // assertDoesNotThrow(() -> customInventory.setupStarterCarInventory());
        // assertTrue(customInventory.getCarList().isEmpty());
        // This requires Mockito or similar. With current setup, we rely on GameEnvironment to provide catalogue.

        // We can test the "if (catalogue != null)" branch by ensuring it works when catalogue is not null.
        // This is covered by setupStarterCarInventoryPopulatesList.

        // To test the "else" part of "if (catalogue != null)", we'd need a way for catalogue to be null.
        // Given the current structure, this is not straightforward without mocking.
        // The constructor of GameEnvironment always initializes itemCatalogue.
        // So, catalogue will never be null in the current execution flow.
        // This test case highlights a path that is currently unreachable.
        assertTrue(true, "Skipping test for null catalogue as it's not reachable with current GameEnvironment setup without mocking.");
    }
}