package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.GameEnvironment;
import seng201.team0.services.RaceCalculations;
import seng201.team0.services.RaceManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceManagerTest {

    private Race race;
    private Car playerCar;
    private List<OpponentCar> opponents;
    private RaceManager raceManager;
    private GameEnvironment gameEnvironment;
    private double carSpeed;
    private double carFuelConsumptionRate;

    private static final int DEFAULT_CAR_PRICE = 1000;
    private static final int REPAIR_WAIT_TICKS_CONST = 2;
    private static final int TRAVELER_WAIT_TICKS_CONST = 2;
    private static final int REFUEL_WAIT_TICKS_CONST = 2;
    private static final int REPAIR_COST_CONST = 500;
    private static final int TRAVELER_PROFIT_CONST = 500;


    @BeforeEach
    void setUp() {
        Course course = Course.DESERT;
        Route route = Route.DESERT_DRIFT;
        Difficulty difficulty = Difficulty.EASY;
        race = new Race(course, route, difficulty);

        playerCar = new Car("Player Racer", 10, 0.8, 0.95, 100, DEFAULT_CAR_PRICE);
        opponents = new ArrayList<>();
        opponents.add(new OpponentCar(8));
        opponents.add(new OpponentCar(9));
        race.setOpponents(opponents);

        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, route);
        carFuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(playerCar);

        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, carFuelConsumptionRate);
        gameEnvironment = new GameEnvironment();
        gameEnvironment.setBalance(10000);
    }

    @Test
    void testInitialRaceManagerState() {
        assertEquals(0, raceManager.getPlayerDistance());
        assertEquals(1.0, raceManager.getFuelLevel());
        assertEquals(0, raceManager.getTickCount());
        assertFalse(raceManager.isRaceFinished());
        assertFalse(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
        assertEquals("", raceManager.getFinishReason());
        assertFalse(raceManager.hasPlayerFinished());
    }

    @Test
    void testAdvanceRaceTickPlayerMovementAndFuel() {
        raceManager.advanceRaceTick();
        assertEquals(carSpeed, raceManager.getPlayerDistance());
        assertEquals(1.0 - carFuelConsumptionRate, raceManager.getFuelLevel(), 0.0001);
        assertEquals(1, raceManager.getTickCount());
        for(OpponentCar op : opponents) {
            assertEquals(op.getSpeed(), op.getCurrentDistance());
        }
    }

    @Test
    void testPlayerRunsOutOfFuel() {
        playerCar = new Car("Fuel Hog", 10, 0.8, 0.95, 1, DEFAULT_CAR_PRICE);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, race.getRoute());
        carFuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(playerCar);
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, carFuelConsumptionRate);

        for (int i = 0; i < 2; i++) {
            if(!raceManager.isRaceFinished()) raceManager.advanceRaceTick();
        }
        assertTrue(raceManager.isRaceFinished());
        assertEquals("Out of fuel!", raceManager.getFinishReason());
        assertTrue(raceManager.hasPlayerFinished());
    }

    @Test
    void testPlayerFinishesRaceByDistance() {
        playerCar = new Car("Speedy", race.getRoute().getLength() / 2.0 + 1 , 0.8, 0.95, 1000, DEFAULT_CAR_PRICE);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, race.getRoute());
        carFuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(playerCar);
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, carFuelConsumptionRate);

        for(int i=0; i<3; ++i) {
            if(!raceManager.isRaceFinished()) raceManager.advanceRaceTick();
        }

        assertTrue(raceManager.isRaceFinished());
        assertEquals("Finished the race!", raceManager.getFinishReason());
        assertTrue(raceManager.hasPlayerFinished());
        assertTrue(raceManager.getPlayerFinishTick() > 0);
    }

    @Test
    void testFuelStopEventTrigger() {
        Route routeWithStops = Route.DESERT_DRIFT;
        playerCar = new Car("Steady", 1, 0.8, 0.95, 1000, DEFAULT_CAR_PRICE);
        race = new Race(Course.DESERT, routeWithStops, Difficulty.EASY);
        race.setOpponents(opponents);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, routeWithStops);
        carFuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(playerCar);
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, carFuelConsumptionRate);

        List<Double> stopDistances = RaceCalculations.calculateFuelStopDistances(routeWithStops.getLength(), routeWithStops.getFuelStops());
        double firstStopDistance = stopDistances.get(0);

        while(raceManager.getPlayerDistance() < firstStopDistance && !raceManager.isRaceFinished() && raceManager.getTickCount() < routeWithStops.getLength() * 2) {
            raceManager.advanceRaceTick();
        }
        if(!raceManager.isRaceFinished() && raceManager.getPlayerDistance() >= firstStopDistance) {
            if(!raceManager.isWaiting()){
                raceManager.advanceRaceTick();
            }
        }


        assertTrue(raceManager.isWaiting());
        assertNotNull(raceManager.getCurrentEvent());
        assertEquals(RaceEventType.FUEL_STOP, raceManager.getCurrentEvent().getType());
    }

    @Test
    void testHandleFuelStopRefuel() {
        raceManager.setWaiting(true, 5000);
        raceManager.handleFuelStop(true);
        assertEquals(1.0, raceManager.getFuelLevel());
        assertTrue(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleFuelStopNoRefuel() {
        raceManager.setWaiting(true, 5000);
        raceManager.refuel();
        raceManager.advanceRaceTick();
        double fuelBefore = raceManager.getFuelLevel();

        raceManager.handleFuelStop(false);
        assertEquals(fuelBefore, raceManager.getFuelLevel());
        assertFalse(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleRepairPay() {
        gameEnvironment.setBalance(1000);
        raceManager.setWaiting(true, 5000);
        raceManager.handleRepair(true, gameEnvironment);
        assertEquals(1000 - REPAIR_COST_CONST, gameEnvironment.getBalance());
        assertTrue(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleRepairNoPay() {
        raceManager.setWaiting(true, 5000);
        raceManager.handleRepair(false, gameEnvironment);
        assertTrue(raceManager.isRaceFinished());
        assertEquals("Car broke down! You withdrew from the race.", raceManager.getFinishReason());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleTravelerPickUp() {
        gameEnvironment.setBalance(1000);
        raceManager.setWaiting(true, 5000);
        raceManager.handleTraveler(true, gameEnvironment);
        assertEquals(1000 + TRAVELER_PROFIT_CONST, gameEnvironment.getBalance());
        assertTrue(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleTravelerNoPickUp() {
        raceManager.setWaiting(true, 5000);
        raceManager.handleTraveler(false, gameEnvironment);
        assertFalse(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleWeather() {
        gameEnvironment.setBalance(1000);
        int entryFee = race.getCourse().getEntryFee();
        raceManager.handleWeather(gameEnvironment);
        assertEquals(1000 - entryFee, gameEnvironment.getBalance());
        assertNull(raceManager.getCurrentEvent());
    }


    @Test
    void testLeaderboardAndPlacement() {
        raceManager.advanceRaceTick();
        raceManager.advanceRaceTick();
        opponents.get(0).advanceTick(); opponents.get(0).advanceTick(); opponents.get(0).advanceTick();

        List<LeaderboardEntry> standings = raceManager.getLeaderboardStandings();
        assertEquals(3, standings.size());
        assertEquals("Opponent 1", standings.get(0).getName());
        assertEquals("Player", standings.get(1).getName());

        assertEquals(2, raceManager.getPlayerPlacement());

        List<String> strings = raceManager.getLeaderboardStrings();
        assertEquals(3, strings.size());
        assertTrue(strings.get(0).startsWith("1. Opponent 1"));
    }

    @Test
    void testMoneyEarnedFirstPlace() {
        playerCar = new Car("Winner", race.getRoute().getLength() * 2, 1,1,1000, DEFAULT_CAR_PRICE);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, race.getRoute());
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, 1.0/1000);
        raceManager.advanceRaceTick();
        raceManager.finishRace();

        int prize = race.getCourse().getPrizes().getFirstPlacePrize();
        assertEquals(prize, raceManager.getMoneyEarned());
    }


    @Test
    void testDeductEntryFee() {
        int initialBalance = gameEnvironment.getBalance();
        int entryFee = race.getCourse().getEntryFee();
        raceManager.deductEntryFee(gameEnvironment);
        assertEquals(initialBalance - entryFee, gameEnvironment.getBalance());
    }

    @Test
    void testAwardPrizeMoneyPlayerFinished() {
        playerCar = new Car("Winner", race.getRoute().getLength() * 2, 1,1,1000, DEFAULT_CAR_PRICE);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, race.getRoute());
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, 1.0/1000);
        raceManager.advanceRaceTick();
        raceManager.finishRace();


        int initialBalance = gameEnvironment.getBalance();
        int prize = race.getCourse().getPrizes().getFirstPlacePrize();
        int awarded = raceManager.awardPrizeMoney(gameEnvironment);
        assertEquals(prize, awarded);
        assertEquals(initialBalance + prize, gameEnvironment.getBalance());
    }

    @Test
    void testAwardPrizeMoneyPlayerNotFinished() {
        int initialBalance = gameEnvironment.getBalance();
        int awarded = raceManager.awardPrizeMoney(gameEnvironment);
        assertEquals(0, awarded);
        assertEquals(initialBalance, gameEnvironment.getBalance());
    }

    @Test
    void testRandomEventTriggerTravelerOrWeather() {
        playerCar = new Car("EventSeeker", 1, 0.8, 0.95, 1000, DEFAULT_CAR_PRICE);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, race.getRoute());
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, 0.001);

        double eventTriggerDist = RaceCalculations.calculateEventTriggerDistance(race.getRoute().getLength());
        while (raceManager.getPlayerDistance() < eventTriggerDist && !raceManager.isRaceFinished() && raceManager.getTickCount() < race.getRoute().getLength() * 2) {
            raceManager.advanceRaceTick();
        }
        if(!raceManager.isRaceFinished() && raceManager.getPlayerDistance() >= eventTriggerDist) {
            if(!raceManager.isWaiting() && raceManager.getCurrentEvent() == null){
                raceManager.advanceRaceTick();
            }
        }

        if (raceManager.getCurrentEvent() != null) {
            assertTrue(raceManager.getCurrentEvent().getType() == RaceEventType.TRAVELER ||
                    raceManager.getCurrentEvent().getType() == RaceEventType.WEATHER);
            assertTrue(raceManager.isWaiting());
        }
    }

    @Test
    void testBreakdownEventTrigger() {
        playerCar = new Car("Unlucky", 1, 0.8, 0.01, 1000, DEFAULT_CAR_PRICE);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, race.getRoute());
        raceManager = new RaceManager(race, playerCar, race.getOpponents(), carSpeed, 0.001);

        double breakdownTriggerDist = RaceCalculations.calculateBreakdownTriggerDistance(race.getRoute().getLength());
        while (raceManager.getPlayerDistance() < breakdownTriggerDist && !raceManager.isRaceFinished() && raceManager.getTickCount() < race.getRoute().getLength()*2) {
            raceManager.advanceRaceTick();
        }
        if(!raceManager.isRaceFinished() && raceManager.getPlayerDistance() >= breakdownTriggerDist) {
            if(!raceManager.isWaiting() && raceManager.getCurrentEvent() == null){
                raceManager.advanceRaceTick();
            }
        }

        if (raceManager.getCurrentEvent() != null && raceManager.getCurrentEvent().getType() == RaceEventType.BREAKDOWN) {
            assertTrue(raceManager.isWaiting());
        } else if (raceManager.getCurrentEvent() == null) {
        }
    }

    @Test
    void testWaitTicksFunctionality() {
        raceManager.setWaiting(true, 3);
        assertTrue(raceManager.isWaiting());

        raceManager.advanceRaceTick();
        assertTrue(raceManager.isWaiting());
        assertEquals(0, raceManager.getPlayerDistance());

        raceManager.advanceRaceTick();
        assertTrue(raceManager.isWaiting());

        raceManager.advanceRaceTick();
        assertFalse(raceManager.isWaiting());

        raceManager.advanceRaceTick();
        assertTrue(raceManager.getPlayerDistance() > 0);
    }

    @Test
    void testRefuelMethod() {
        raceManager.advanceRaceTick();
        raceManager.advanceRaceTick();
        assertNotEquals(1.0, raceManager.getFuelLevel());
        raceManager.refuel();
        assertEquals(1.0, raceManager.getFuelLevel());
    }

    @Test
    void testPlayerWithdrawDueToBreakdownMethod() {
        assertFalse(raceManager.isRaceFinished());
        assertFalse(raceManager.hasPlayerFinished());
        assertEquals("", raceManager.getFinishReason());

        raceManager.playerWithdrawDueToBreakdown();

        assertTrue(raceManager.isRaceFinished());
        assertTrue(raceManager.hasPlayerFinished());
        assertEquals("Car broke down! You withdrew from the race.", raceManager.getFinishReason());
    }

    @Test
    void testGettersForCoverage() {
        assertNotNull(raceManager.getOpponents());
        assertNotNull(raceManager.getRace());
        assertNotNull(raceManager.getPlayerCar());
    }

    @Test
    void testClearCurrentEvent() {
        Route routeWithStops = Route.DESERT_DRIFT;
        playerCar = new Car("Eventful Car", 1, 0.8, 0.95, 1000, DEFAULT_CAR_PRICE);
        race = new Race(Course.DESERT, routeWithStops, Difficulty.EASY);
        carSpeed = RaceCalculations.calculateEffectiveSpeed(playerCar, routeWithStops);
        raceManager = new RaceManager(race, playerCar, opponents, carSpeed, 0.001);

        double firstStopDistance = RaceCalculations.calculateFuelStopDistances(routeWithStops.getLength(), routeWithStops.getFuelStops()).get(0);
        while(raceManager.getPlayerDistance() < firstStopDistance && !raceManager.isRaceFinished() && raceManager.getTickCount() < routeWithStops.getLength() * 2) {
            raceManager.advanceRaceTick();
        }
        if(!raceManager.isRaceFinished() && raceManager.getPlayerDistance() >= firstStopDistance) {
            if(!raceManager.isWaiting()){
                raceManager.advanceRaceTick();
            }
        }

        assertNotNull(raceManager.getCurrentEvent());
        raceManager.clearCurrentEvent();
        assertNull(raceManager.getCurrentEvent());
    }

}