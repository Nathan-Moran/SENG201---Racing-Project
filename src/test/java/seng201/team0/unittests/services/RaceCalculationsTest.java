package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Course;
import seng201.team0.models.Difficulty;
import seng201.team0.models.OpponentCar;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.models.RouteAttributes;
import seng201.team0.models.TuningPart;
import seng201.team0.services.RaceCalculations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RaceCalculationsTest {

    private Car testCar;
    private Route testRoute;
    private List<OpponentCar> opponents;
    private Race testRace;

    @BeforeEach
    void setUp() {
        // Car(String name, double speed, double handling, double reliability, int fuelEconomy, int price)
        testCar = new Car("Test Car", 1.0, 0.8, 0.9, 10, 0); // Speed 1.0 for easier calculations
        testRoute = Route.DESERT_DRIFT; // Length 20
        opponents = new ArrayList<>();
        opponents.add(new OpponentCar(1.0)); // Opponent 0, speed 1.0
        opponents.add(new OpponentCar(1.1)); // Opponent 1, speed 1.1

        testRace = new Race(Course.DESERT, testRoute, Difficulty.EASY);
        testRace.setOpponents(opponents);
    }

    @Test
    void testCalculateEffectiveSpeed() {
        RouteAttributes attr = testRoute.getAttributes(); // DESERT_DRIFT: SpeedAdv 0.5, HandlingAdv 1.5
        // testCar: Speed 1.0, Handling 0.8
        // Expected: (1.0 * 0.5) + (0.8 * 1.5) = 0.5 + 1.2 = 1.7
        double expectedSpeed = (testCar.getSpeed() * attr.getSpeedAdvantage()) +
                (testCar.getHandling() * attr.getHandlingAdvantage());
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateEffectiveSpeedWithUpgrades() {
        // Car base: Speed 1.0, Handling 0.8
        // SpeedUpgrade: boost 1.2 => Car effective speed = 1.0 * 1.2 = 1.2
        // HandlingUpgrade: boost 1.1 => Car effective handling = 0.8 * 1.1 = 0.88
        TuningPart speedBoost = new TuningPart("SpeedUp", 100, "💨", 1.2); // Stat string "💨" for speed
        TuningPart handlingBoost = new TuningPart("HandleUp", 100, "🎮", 1.1); // Stat string "🎮" for handling
        testCar.addSpeedUpgrade(speedBoost);
        testCar.addHandlingUpgrade(handlingBoost);

        RouteAttributes attr = testRoute.getAttributes(); // DESERT_DRIFT: SpeedAdv 0.5, HandlingAdv 1.5
        // Upgraded car stats: Speed 1.2, Handling 0.88
        // Expected: (1.2 * 0.5) + (0.88 * 1.5) = 0.6 + 1.32 = 1.92
        double expectedSpeed = (testCar.getSpeed() * attr.getSpeedAdvantage()) +
                (testCar.getHandling() * attr.getHandlingAdvantage());
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateFuelConsumptionRate() {
        // testCar fuelEconomy = 10
        double expectedRate = 1.0 / testCar.getFuelEconomy();
        assertEquals(expectedRate, RaceCalculations.calculateFuelConsumptionRate(testCar), 0.001);
    }

    @Test
    void testGetFinishedOpponentsNoneFinished() {
        // Opponent speeds are 1.0 and 1.1. Route length is 20.
        // Advancing once will result in distances 1.0 and 1.1, both < 20.
        for (OpponentCar op : opponents) {
            op.advanceTick();
        }
        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertTrue(finished.isEmpty(), "Expected no opponents to be finished.");
    }

    @Test
    void testGetFinishedOpponentsSomeFinished() {
        // Route.DESERT_DRIFT length is 20.
        // Opponent 0 has speed 1.0. Need to advance 20 times to finish.
        for (int i = 0; i < 20; i++) {
            opponents.get(0).advanceTick(); // Opponent 0 distance will be 20.0
        }
        // Opponent 1 has speed 1.1. Advance it less than needed to finish.
        for (int i = 0; i < 10; i++) {
            opponents.get(1).advanceTick(); // Opponent 1 distance will be 11.0
        }

        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertEquals(1, finished.size(), "Expected 1 opponent to be finished.");
        assertTrue(finished.contains(opponents.get(0)), "Opponent 0 should be in finished list.");
        assertFalse(finished.contains(opponents.get(1)), "Opponent 1 should not be in finished list.");
    }

    @Test
    void testGetFinishedOpponentsAllFinished() {
        // Route.DESERT_DRIFT length is 20.
        // Opponent 0 (speed 1.0) needs 20 ticks.
        // Opponent 1 (speed 1.1) needs 20 / 1.1 = ~18.18 ticks, so 19 ticks.
        // Advance both by 20 ticks to ensure both are finished.
        for (int i = 0; i < 20; i++) {
            for (OpponentCar op : opponents) {
                op.advanceTick();
            }
        }
        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertEquals(opponents.size(), finished.size(), "Expected all opponents to be finished.");
    }

    @Test
    void testCalculateFuelStopDistancesOneStop() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(100, 1);
        assertEquals(1, stops.size());
        assertEquals(50.0, stops.get(0), 0.001);
    }

    @Test
    void testCalculateFuelStopDistancesMultipleStops() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(120, 2); // Intervals of 120/3 = 40
        assertEquals(2, stops.size());
        assertEquals(40.0, stops.get(0), 0.001); // 1st stop at 40
        assertEquals(80.0, stops.get(1), 0.001); // 2nd stop at 80
    }

    @Test
    void testCalculateFuelStopDistancesZeroStops() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(100, 0);
        assertTrue(stops.isEmpty());
    }

    @Test
    void testCalculateEffectiveReliability() {
        // testCar reliability = 0.9
        RouteAttributes attr = testRoute.getAttributes(); // DESERT_DRIFT: ReliabilityAdv 1.2
        // Expected: 0.9 * 1.2 = 1.08
        double expectedReliability = testCar.getReliability() * attr.getReliabilityAdvantage();
        assertEquals(expectedReliability, RaceCalculations.calculateEffectiveReliability(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateEventTriggerDistance() {
        double length = testRoute.getLength(); // DESERT_DRIFT length is 20
        assertEquals(length * 0.4, RaceCalculations.calculateEventTriggerDistance(length), 0.001); // 20 * 0.4 = 8
    }

    @Test
    void testCalculateBreakdownTriggerDistance() {
        double length = testRoute.getLength(); // DESERT_DRIFT length is 20
        assertEquals(length * 0.7, RaceCalculations.calculateBreakdownTriggerDistance(length), 0.001); // 20 * 0.7 = 14
    }
}