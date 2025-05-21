package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.ItemCatalogue;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

class ItemCatalogueTest {

    private ItemCatalogue itemCatalogue;

    @BeforeEach
    void setUp() {
        itemCatalogue = new ItemCatalogue(); //
    }

    @Test
    void setupCataloguePopulatesStarterCarPool() {
        assertNotNull(itemCatalogue.getStarterCarPool()); //
        assertFalse(itemCatalogue.getStarterCarPool().isEmpty()); //
        // Check for specific starter cars by name
        assertTrue(itemCatalogue.getStarterCarPool().stream().anyMatch(car -> car.getName().equals("Honda Civic"))); //
        assertTrue(itemCatalogue.getStarterCarPool().stream().anyMatch(car -> car.getName().equals("Mazda MPS"))); //
        assertTrue(itemCatalogue.getStarterCarPool().stream().anyMatch(car -> car.getName().equals("Nissan Z"))); //
        assertEquals(3, itemCatalogue.getStarterCarPool().size()); //
    }

    @Test
    void setupCataloguePopulatesShopCarList() {
        assertNotNull(itemCatalogue.getShopCarList()); //
        assertFalse(itemCatalogue.getShopCarList().isEmpty()); //
        // Check for specific shop cars by name
        assertTrue(itemCatalogue.getShopCarList().stream().anyMatch(car -> car.getName().equals("Toyota Supra"))); //
        assertTrue(itemCatalogue.getShopCarList().stream().anyMatch(car -> car.getName().equals("Mustang"))); //
        assertTrue(itemCatalogue.getShopCarList().stream().anyMatch(car -> car.getName().equals("Ferrari"))); //
        assertEquals(3, itemCatalogue.getShopCarList().size()); //
    }

    @Test
    void setupCataloguePopulatesLockedCarList() {
        assertNotNull(itemCatalogue.getLockedCarList()); //
        assertFalse(itemCatalogue.getLockedCarList().isEmpty()); //
        // Check for specific locked cars by name
        assertTrue(itemCatalogue.getLockedCarList().stream().anyMatch(car -> car.getName().equals("Dune Drifter"))); //
        assertTrue(itemCatalogue.getLockedCarList().stream().anyMatch(car -> car.getName().equals("Ridge Racer"))); //
        assertTrue(itemCatalogue.getLockedCarList().stream().anyMatch(car -> car.getName().equals("Vineyard Viper"))); //
        assertTrue(itemCatalogue.getLockedCarList().stream().anyMatch(car -> car.getName().equals("CommuterKing"))); //
        assertEquals(4, itemCatalogue.getLockedCarList().size()); //
    }

    @Test
    void setupCataloguePopulatesTuningPartList() {
        assertNotNull(itemCatalogue.getTuningPartList()); //
        assertFalse(itemCatalogue.getTuningPartList().isEmpty()); //
        // Check for specific tuning parts by name
        assertTrue(itemCatalogue.getTuningPartList().stream().anyMatch(part -> part.getName().equals("Ethanol"))); //
        assertTrue(itemCatalogue.getTuningPartList().stream().anyMatch(part -> part.getName().equals("SuperCharger"))); //
        assertTrue(itemCatalogue.getTuningPartList().stream().anyMatch(part -> part.getName().equals("TurboKit"))); //
        assertTrue(itemCatalogue.getTuningPartList().stream().anyMatch(part -> part.getName().equals("StreetWheels"))); //
        assertTrue(itemCatalogue.getTuningPartList().stream().anyMatch(part -> part.getName().equals("SportsWheels"))); //
        assertTrue(itemCatalogue.getTuningPartList().stream().anyMatch(part -> part.getName().equals("RacingWheels"))); //
        assertEquals(6, itemCatalogue.getTuningPartList().size()); //
    }

    @Test
    void getStarterCarPoolReturnsCorrectList() {
        assertNotNull(itemCatalogue.getStarterCarPool()); //
        assertEquals(3, itemCatalogue.getStarterCarPool().size()); //
    }

    @Test
    void getShopCarListReturnsCorrectList() {
        assertNotNull(itemCatalogue.getShopCarList()); //
        assertEquals(3, itemCatalogue.getShopCarList().size()); //
    }

    @Test
    void getLockedCarListReturnsCorrectList() {
        assertNotNull(itemCatalogue.getLockedCarList()); //
        assertEquals(4, itemCatalogue.getLockedCarList().size()); //
    }
}