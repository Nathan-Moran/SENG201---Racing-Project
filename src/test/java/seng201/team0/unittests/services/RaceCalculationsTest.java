package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.RaceCalculations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RaceCalculationsTest {

    private Car testCar;
    // Routes are enums, so we can use them directly.
    private Route testRouteDesertDrift;
    private Route testRouteCityTraffic;

    @BeforeEach
    void setUp() {
        // Create a concrete Car instance
        // Car(String name, double speed, double handling, double reliability, int fuelEconomy, int price)
        testCar = new Car("Test Racer", 0.7, 0.6, 0.8, 20, 1000);
        testRouteDesertDrift = Route.DESERT_DRIFT; // SpeedAdv: 0.5, HandlingAdv: 1.5, ReliabilityAdv: 1.2
        testRouteCityTraffic = Route.CITY_TRAFFIC; // SpeedAdv: 0.4, HandlingAdv: 1.0, ReliabilityAdv: 1.5
    }

    @Test
    void calculateEffectiveSpeedCombinesCarAndRouteStats() {
        // Car: speed 0.7, handling 0.6
        // Route DESERT_DRIFT: speedAdv 0.5, handlingAdv 1.5
        // Effective speed = (0.7 * 0.5) + (0.6 * 1.5) = 0.35 + 0.90 = 1.25
        double expectedSpeed = (0.7 * 0.5) + (0.6 * 1.5);
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRouteDesertDrift), 0.001);
    }

    @Test
    void calculateEffectiveSpeedWithDifferentRoute() {
        // Car: speed 0.7, handling 0.6
        // Route CITY_TRAFFIC: speedAdv 0.4, handlingAdv 1.0
        // Effective speed = (0.7 * 0.4) + (0.6 * 1.0) = 0.28 + 0.60 = 0.88
        double expectedSpeed = (0.7 * 0.4) + (0.6 * 1.0);
        assertEquals(expectedSpeed, RaceCalculations.calculateEffectiveSpeed(testCar, testRouteCityTraffic), 0.001);
    }


    @Test
    void calculateFuelConsumptionRateNormal() {
        // testCar has fuelEconomy 20
        assertEquals(1.0 / 20.0, RaceCalculations.calculateFuelConsumptionRate(testCar), 0.00001);
    }

    @Test
    void calculateFuelConsumptionRateWithZeroEconomy() {
        Car carWithZeroFuelEcon = new Car("Gas Guzzler", 0.5, 0.5, 0.5, 0, 500);
        assertEquals(Double.MAX_VALUE, RaceCalculations.calculateFuelConsumptionRate(carWithZeroFuelEcon));
    }

    @Test
    void calculateFuelConsumptionRateWithOneEconomy() {
        Car carWithOneFuelEcon = new Car("Efficient", 0.5, 0.5, 0.5, 1, 1500);
        assertEquals(1.0, RaceCalculations.calculateFuelConsumptionRate(carWithOneFuelEcon), 0.00001);
    }


    @Test
    void getFinishedOpponentsFiltersCorrectly() {
        // Create a concrete Race
        // Race(Course course, Route route, Difficulty difficulty)
        // Opponents are generated inside Race constructor based on route and course.
        // For this test, we'll manually set opponents on a Race instance after creation for control.
        Race testRace = new Race(Course.DESERT, Route.DESERT_LONG, Difficulty.EASY); // DESERT_LONG length = 30km

        OpponentCar op1 = new OpponentCar(1.0); // Speed 1.0
        OpponentCar op2 = new OpponentCar(1.0);
        OpponentCar op3 = new OpponentCar(1.0);

        // Manually advance distances
        for(int i=0; i<35; i++) op1.advanceTick(); // op1 distance = 35.0 (finished)
        for(int i=0; i<29; i++) op2.advanceTick(); // op2 distance = 29.0 (not finished)
        for(int i=0; i<30; i++) op3.advanceTick(); // op3 distance = 30.0 (finished)


        List<OpponentCar> allOpponents = new ArrayList<>(List.of(op1, op2, op3));
        testRace.setOpponents(allOpponents); // Replace generated opponents with controlled ones

        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertEquals(2, finished.size());
        assertTrue(finished.contains(op1));
        assertTrue(finished.contains(op3));
        assertFalse(finished.contains(op2));
    }

    @Test
    void getFinishedOpponentsWithNoFinishers() {
        Race testRace = new Race(Course.MOUNTAIN, Route.MOUNTAIN_STEEP, Difficulty.MEDIUM); // MOUNTAIN_STEEP length = 15km
        OpponentCar op1 = new OpponentCar(0.5);
        for(int i=0; i<10; i++) op1.advanceTick(); // op1 distance = 5.0

        testRace.setOpponents(List.of(op1));

        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertTrue(finished.isEmpty());
    }

    @Test
    void getFinishedOpponentsWithEmptyOpponentList() {
        Race testRace = new Race(Course.CITY, Route.CITY_ALLEYS, Difficulty.HARD);
        testRace.setOpponents(new ArrayList<>()); // Empty list

        List<OpponentCar> finished = RaceCalculations.getFinishedOpponents(testRace);
        assertTrue(finished.isEmpty());
    }


    @Test
    void calculateFuelStopDistancesMultipleStops() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(100.0, 3); // Length 100, 3 stops
        // Interval = 100 / (3+1) = 25
        // Stops at 25, 50, 75
        assertEquals(3, stops.size());
        assertEquals(25.0, stops.get(0), 0.001);
        assertEquals(50.0, stops.get(1), 0.001);
        assertEquals(75.0, stops.get(2), 0.001);
    }

    @Test
    void calculateFuelStopDistancesOneStop() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(60.0, 1); // Length 60, 1 stop
        // Interval = 60 / (1+1) = 30
        // Stop at 30
        assertEquals(1, stops.size());
        assertEquals(30.0, stops.get(0), 0.001);
    }

    @Test
    void calculateFuelStopDistancesZeroStops() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(100.0, 0);
        assertTrue(stops.isEmpty());
    }

    @Test
    void calculateFuelStopDistancesNegativeStops() {
        List<Double> stops = RaceCalculations.calculateFuelStopDistances(100.0, -1);
        assertTrue(stops.isEmpty());
    }

    @Test
    void calculateEffectiveReliability() {
        // testCar reliability: 0.8
        // testRouteDesertDrift reliabilityAdv: 1.2
        // Effective reliability = 0.8 * 1.2 = 0.96
        assertEquals(0.8 * 1.2, RaceCalculations.calculateEffectiveReliability(testCar, testRouteDesertDrift), 0.001);
    }

    @Test
    void calculateEffectiveReliabilityWithDifferentRoute() {
        // testCar reliability: 0.8
        // testRouteCityTraffic reliabilityAdv: 1.5
        // Effective reliability = 0.8 * 1.5 = 1.20
        assertEquals(0.8 * 1.5, RaceCalculations.calculateEffectiveReliability(testCar, testRouteCityTraffic), 0.001);
    }


    @Test
    void calculateEventTriggerDistance() {
        assertEquals(100.0 * 0.4, RaceCalculations.calculateEventTriggerDistance(100.0), 0.001);
        assertEquals(50.0 * 0.4, RaceCalculations.calculateEventTriggerDistance(50.0), 0.001);
    }

    @Test
    void calculateBreakdownTriggerDistance() {
        assertEquals(100.0 * 0.7, RaceCalculations.calculateBreakdownTriggerDistance(100.0), 0.001);
        assertEquals(200.0 * 0.7, RaceCalculations.calculateBreakdownTriggerDistance(200.0), 0.001);
    }
}