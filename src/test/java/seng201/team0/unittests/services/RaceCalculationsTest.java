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
//        Car(String name, double speed, double handling, double reliability, int fuelEconomy, int price)
        testCar = new Car("Test Car", 1.0, 0.8, 0.9, 10, 0);
        testRoute = Route.DESERT_DRIFT;
        opponents = new ArrayList<>();
        opponents.add(new OpponentCar(1.0));
        opponents.add(new OpponentCar(1.1));

        testRace = new Race(Course.DESERT, testRoute, Difficulty.EASY);
        testRace.setOpponents(opponents);
    }

    @Test
    void testCalculateEffectiveSpeed() {
        RouteAttributes attr = testRoute.getAttributes();

        double expectedSpeed = (testCar.getSpeed() * attr.getSpeedAdvantage()) +
                (testCar.getHandling() * attr.getHandlingAdvantage());
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateEffectiveSpeedWithUpgrades() {
        TuningPart speedBoost = new TuningPart("SpeedUp", 100, "💨", 1.2);
        TuningPart handlingBoost = new TuningPart("HandleUp", 100, "🎮", 1.1);
        testCar.addSpeedUpgrade(speedBoost);
        testCar.addHandlingUpgrade(handlingBoost);

        RouteAttributes attr = testRoute.getAttributes();

        double expectedSpeed = (testCar.getSpeed() * attr.getSpeedAdvantage()) +
                (testCar.getHandling() * attr.getHandlingAdvantage());
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateFuelConsumptionRate() {
        double expectedRate = 1.0 / testCar.getFuelEconomy();
        assertEquals(expectedRate, RaceCalculations.calculateFuelConsumptionRate(testCar), 0.001);
    }

    @Test
    void testGetFinishedOpponentsNoneFinished() {
        for (OpponentCar op : opponents) {
            op.advanceTick();
        }
        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertTrue(finished.isEmpty(), "Expected no opponents to be finished.");
    }

    @Test
    void testGetFinishedOpponentsSomeFinished() {
        for (int i = 0; i < 20; i++) {
            opponents.get(0).advanceTick();
        }

        for (int i = 0; i < 10; i++) {
            opponents.get(1).advanceTick();
        }

        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertEquals(1, finished.size(), "Expected 1 opponent to be finished.");
        assertTrue(finished.contains(opponents.get(0)), "Opponent 0 should be in finished list.");
        assertFalse(finished.contains(opponents.get(1)), "Opponent 1 should not be in finished list.");
    }

    @Test
    void testGetFinishedOpponentsAllFinished() {
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
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(120, 2);
        assertEquals(2, stops.size());
        assertEquals(40.0, stops.get(0), 0.001);
        assertEquals(80.0, stops.get(1), 0.001);
    }

    @Test
    void testCalculateFuelStopDistancesZeroStops() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(100, 0);
        assertTrue(stops.isEmpty());
    }

    @Test
    void testCalculateEffectiveReliability() {
        RouteAttributes attr = testRoute.getAttributes();

        double expectedReliability = testCar.getReliability() * attr.getReliabilityAdvantage();
        assertEquals(expectedReliability, RaceCalculations.calculateEffectiveReliability(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateEventTriggerDistance() {
        double length = testRoute.getLength();
        assertEquals(length * 0.4, RaceCalculations.calculateEventTriggerDistance(length), 0.001);
    }

    @Test
    void testCalculateBreakdownTriggerDistance() {
        double length = testRoute.getLength();
        assertEquals(length * 0.7, RaceCalculations.calculateBreakdownTriggerDistance(length), 0.001);
    }
}