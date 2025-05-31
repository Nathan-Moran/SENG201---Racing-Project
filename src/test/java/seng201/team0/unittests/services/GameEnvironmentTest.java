package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Course;
import seng201.team0.models.Difficulty;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.models.Season;
import seng201.team0.services.GameEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameEnvironmentTest {

    private GameEnvironment gameEnv;
    private Car testCar;
    private Race testRace;
    private Season testSeason;

    @BeforeEach
    void setUp() {
        gameEnv = new GameEnvironment();
        testCar = new Car("Test Car", 0.5, 0.5, 0.5, 10, 1000);
        testRace = new Race(Course.CITY, Route.CITY_ALLEYS, Difficulty.EASY);
        testSeason = new Season(5, null);
    }

    @Test
    void constructorInitializesServicesAndInventories() {
        assertNotNull(gameEnv.getItemCatalogue());
        assertNotNull(gameEnv.getShopService());
        assertNotNull(gameEnv.getControllerService());
        assertNotNull(gameEnv.getShopInventory());
        assertNotNull(gameEnv.getPlayerInventory());
        assertNotNull(gameEnv.getStarterCarInventory());
        for (Course course : Course.values()) {
            assertFalse(gameEnv.hasWonCourse(course));
        }
    }

    @Test
    void setupShopCalledInConstructor() {
        assertFalse(gameEnv.getShopInventory().getAllAvailableCars().isEmpty() ||
                        gameEnv.getShopInventory().getAllAvailableTuningParts().isEmpty(),
                "Shop master lists should be populated.");
        if (!gameEnv.getShopInventory().getAllAvailableCars().isEmpty()) {
            assertTrue(gameEnv.getShopInventory().getCarList().size() <= 3);
        }
        if (!gameEnv.getShopInventory().getAllAvailableTuningParts().isEmpty()) {
            assertTrue(gameEnv.getShopInventory().getTuningPartList().size() <= 3);
        }
    }

    @Test
    void setAndGetCurrentRace() {
        assertNull(gameEnv.getCurrentRace());
        gameEnv.setCurrentRace(testRace);
        assertEquals(testRace, gameEnv.getCurrentRace());
    }

    @Test
    void setAndGetCurrentSeason() {
        assertNull(gameEnv.getCurrentSeason());
        gameEnv.setCurrentSeason(testSeason);
        assertEquals(testSeason, gameEnv.getCurrentSeason());
    }

    @Test
    void setAndGetDifficultyAndBalance() {
        assertNull(gameEnv.getDifficulty());
        gameEnv.setDifficulty(Difficulty.EASY);
        assertEquals(Difficulty.EASY, gameEnv.getDifficulty());
        gameEnv.setBalance(5000);
        assertEquals(5000, gameEnv.getBalance());
    }

    @Test
    void setAndGetRacesRemaining() {
        assertEquals(0, gameEnv.getRacesRemaining());
        gameEnv.setRacesRemaining(10);
        assertEquals(10, gameEnv.getRacesRemaining());
    }

    @Test
    void getSelectedCourseAndRoute() {
        assertNull(gameEnv.getSelectedCourse());
        assertNull(gameEnv.getSelectedRoute());
        gameEnv.setCurrentRace(testRace);
        assertEquals(Course.CITY, gameEnv.getSelectedCourse());
        assertEquals(Route.CITY_ALLEYS, gameEnv.getSelectedRoute());
    }

    @Test
    void getSelectedCourseAndRouteWhenNoRace() {
        gameEnv.setCurrentRace(null);
        assertNull(gameEnv.getSelectedCourse());
        assertNull(gameEnv.getSelectedRoute());
    }


    @Test
    void getSelectedCarDelegatesToPlayerInventory() {
        assertNull(gameEnv.getSelectedCar());
        gameEnv.getPlayerInventory().setStarterCar(new Car(testCar));
        assertEquals(testCar.getName(), gameEnv.getSelectedCar().getName());
    }

    @Test
    void setAndGetName() {
        assertNull(gameEnv.getName());
        gameEnv.setName("Player One");
        assertEquals("Player One", gameEnv.getName());
    }

    @Test
    void setAndGetSeasonLength() {
        assertEquals(0, gameEnv.getSeasonLength());
        gameEnv.setSeasonLength(15);
        assertEquals(15, gameEnv.getSeasonLength());
    }

    @Test
    void setupStarterCarsPopulatesInventory() {
        assertTrue(gameEnv.getStarterCarInventory().getCarList().isEmpty(), "Should be empty");
        gameEnv.setupStarterCars();
        assertFalse(gameEnv.getStarterCarInventory().getCarList().isEmpty());
        assertEquals(3, gameEnv.getStarterCarInventory().getCarList().size());
    }

    @Test
    void decrementRacesRemaining() {
        gameEnv.setRacesRemaining(5);
        gameEnv.decrementRacesRemaining();
        assertEquals(4, gameEnv.getRacesRemaining());
        gameEnv.decrementRacesRemaining();
        assertEquals(3, gameEnv.getRacesRemaining());
    }

    @Test
    void decrementRacesRemainingAtZero() {
        gameEnv.setRacesRemaining(0);
        gameEnv.decrementRacesRemaining();
        assertEquals(0, gameEnv.getRacesRemaining());
    }

    @Test
    void getAveragePlacementEmpty() {
        assertEquals(0, gameEnv.getAveragePlacement(), 0.001);
    }

    @Test
    void addRacePlacementAndGetAverage() {
        gameEnv.addRacePlacement(1);
        gameEnv.addRacePlacement(3);
        gameEnv.addRacePlacement(2);
        assertEquals(2.0, gameEnv.getAveragePlacement(), 0.001);
    }

    @Test
    void getRacesCompleted() {
        gameEnv.setSeasonLength(10);
        gameEnv.setRacesRemaining(7);
        assertEquals(3, gameEnv.getRacesCompleted());
    }

    @Test
    void addAndGetTotalPrizeMoney() {
        assertEquals(0, gameEnv.getTotalPrizeMoney());
        gameEnv.addPrizeMoney(100);
        assertEquals(100, gameEnv.getTotalPrizeMoney());
        gameEnv.addPrizeMoney(150);
        assertEquals(250, gameEnv.getTotalPrizeMoney());
    }

    @Test
    void updateHasWonCourseAndCheck() {
        assertFalse(gameEnv.hasWonCourse(Course.DESERT));
        gameEnv.updateHasWonCourse(Course.DESERT, 1);
        assertTrue(gameEnv.hasWonCourse(Course.DESERT));

        assertFalse(gameEnv.hasWonCourse(Course.MOUNTAIN));
        gameEnv.updateHasWonCourse(Course.MOUNTAIN, 2);
        assertFalse(gameEnv.hasWonCourse(Course.MOUNTAIN));
    }

    @Test
    void hasWonCourseForUnmappedCourse() {
        assertFalse(gameEnv.hasWonCourse(Course.COUNTRY));
    }
}