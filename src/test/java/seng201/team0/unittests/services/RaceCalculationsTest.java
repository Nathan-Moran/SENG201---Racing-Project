package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.RaceCalculations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceCalculationsTest {

    private Car testCar;
    private Route testRoute;
    private List<OpponentCar> opponents;
    private Race testRace;

    @BeforeEach
    void setUp() {
        testCar = new Car("Test Car", 100, 0.8, 0.9, 10, 0);
        testRoute = Route.DESERT_DRIFT;
        opponents = new ArrayList<>();
        opponents.add(new OpponentCar(50));
        opponents.add(new OpponentCar(60));

        testRace = new Race(Course.DESERT, testRoute, Difficulty.EASY);
        testRace.setOpponents(opponents);

    }

    @Test
    void testCalculateEffectiveSpeed() {
        RouteAttributes attr = testRoute.getAttributes();
        double expectedSpeed = testCar.getSpeed() * attr.getSpeedAdvantage() +
                testCar.getHandling() * attr.getHandlingAdvantage();
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRoute), 0.001);
    }

    @Test
    void testCalculateEffectiveSpeedWithUpgrades() {
        TuningPart speedBoost = new TuningPart("SpeedUp", 100, "Speed", 1.2);
        TuningPart handlingBoost = new TuningPart("HandleUp", 100, "Handling", 1.1);
        testCar.addSpeedUpgrade(speedBoost);
        testCar.addHandlingUpgrade(handlingBoost);

        RouteAttributes attr = testRoute.getAttributes();
        double expectedSpeed = (testCar.getSpeed()) * attr.getSpeedAdvantage() +
                (testCar.getHandling()) * attr.getHandlingAdvantage();
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
            op.advanceTick(); // Assuming speed is less than length
        }
        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertTrue(finished.isEmpty());
    }

    @Test
    void testGetFinishedOpponentsSomeFinished() {
        opponents.get(0).advanceTick(); // distance 50
        opponents.get(0).advanceTick(); // distance 100, route length for DESERT_DRIFT is 20

        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertEquals(1, finished.size());
        assertTrue(finished.contains(opponents.get(0)));
        assertFalse(finished.contains(opponents.get(1)));
    }

    @Test
    void testGetFinishedOpponentsAllFinished() {
        for (int i = 0; i < testRoute.getLength(); i++) {
            for(OpponentCar op : opponents) {
                op.advanceTick();
            }
        }
        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertEquals(opponents.size(), finished.size());
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