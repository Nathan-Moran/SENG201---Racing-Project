package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.GameEnvironment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameEnvironmentTest {

    private GameEnvironment gameEnv;

    @BeforeEach
    void setUp() {
        gameEnv = new GameEnvironment();
    }

    @Test
    void testInitialBalance() {
        assertEquals(3000, gameEnv.getBalance());
    }

    @Test
    void testShopSetup() {
        assertFalse(gameEnv.getShopInventory().getCarList().isEmpty());
        assertFalse(gameEnv.getShopInventory().getTuningPartList().isEmpty());
    }

    @Test
    void testSetAndGetCurrentRace() {
        assertNull(gameEnv.getCurrentRace());
        Race race = new Race(Course.DESERT, Route.DESERT_DRIFT, Difficulty.EASY);
        gameEnv.setCurrentRace(race);
        assertSame(race, gameEnv.getCurrentRace());
    }

    @Test
    void testSetAndGetCurrentSeason() {
        assertNull(gameEnv.getCurrentSeason());
        List<Race> races = new ArrayList<>();
        Season season = new Season(5, races);
        gameEnv.setCurrentSeason(season);
        assertSame(season, gameEnv.getCurrentSeason());
    }

    @Test
    void testSetAndGetDifficulty() {
        assertNull(gameEnv.getDifficulty());
        gameEnv.setDifficulty(Difficulty.MEDIUM);
        assertEquals(Difficulty.MEDIUM, gameEnv.getDifficulty());
    }

    @Test
    void testSetAndGetRacesRemaining() {
        assertEquals(0, gameEnv.getRacesRemaining()); // Default or initial state might be 0
        gameEnv.setRacesRemaining(10);
        assertEquals(10, gameEnv.getRacesRemaining());
    }

    @Test
    void testDecrementRacesRemaining() {
        gameEnv.setRacesRemaining(5);
        gameEnv.decrementRacesRemaining();
        assertEquals(4, gameEnv.getRacesRemaining());
        gameEnv.decrementRacesRemaining();
        assertEquals(3, gameEnv.getRacesRemaining());
    }

    @Test
    void testDecrementRacesRemainingAtZero() {
        gameEnv.setRacesRemaining(0);
        gameEnv.decrementRacesRemaining();
        assertEquals(0, gameEnv.getRacesRemaining());
    }

    @Test
    void testGetSelectedCourseAndRouteNoCurrentRace() {
        assertNull(gameEnv.getSelectedCourse());
        assertNull(gameEnv.getSelectedRoute());
    }

    @Test
    void testGetSelectedCourseAndRouteWithCurrentRace() {
        Course course = Course.CITY;
        Route route = Route.CITY_ALLEYS;
        Race race = new Race(course, route, Difficulty.HARD);
        gameEnv.setCurrentRace(race);
        assertSame(course, gameEnv.getSelectedCourse());
        assertSame(route, gameEnv.getSelectedRoute());
    }

    @Test
    void testGetSelectedCar() {
        assertNull(gameEnv.getSelectedCar());
        Car car = new Car("Test Car", 100, 0.5,0.8,10,1000);
        gameEnv.getPlayerInventory().setStarterCar(car);
        assertSame(car, gameEnv.getSelectedCar());
    }

    @Test
    void testSetAndGetName() {
        assertNull(gameEnv.getName());
        gameEnv.setName("Player One");
        assertEquals("Player One", gameEnv.getName());
    }

    @Test
    void testSetAndGetSeasonLength() {
        assertEquals(0, gameEnv.getSeasonLength());
        gameEnv.setSeasonLength(15);
        assertEquals(15, gameEnv.getSeasonLength());
    }

    @Test
    void testGetManagers() {
        assertNotNull(gameEnv.getShopService());
        assertNotNull(gameEnv.getControllerService());
    }

    @Test
    void testSetupStarterCars() {
        assertTrue(gameEnv.getStarterCarInventory().getCarList().isEmpty());
        gameEnv.setupStarterCars();
        assertFalse(gameEnv.getStarterCarInventory().getCarList().isEmpty());
        assertTrue(gameEnv.getStarterCarInventory().getCarList().size() > 0);
    }

    @Test
    void testGetInventories() {
        assertNotNull(gameEnv.getPlayerInventory());
        assertNotNull(gameEnv.getShopInventory());
        assertNotNull(gameEnv.getStarterCarInventory());
    }

    @Test
    void testSetBalanceToSpecificValue() {
        gameEnv.setBalance(5000);
        assertEquals(5000, gameEnv.getBalance());
        gameEnv.setBalance(1500);
        assertEquals(1500, gameEnv.getBalance());
    }

    @Test
    void testSetBalanceResetsToStartingBalance() {
        gameEnv.setBalance(500);
        assertEquals(500, gameEnv.getBalance());
    }
}