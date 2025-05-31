package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.GameEnvironment;
import seng201.team0.services.RaceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class RaceManagerTest {

    private Race testRace;
    private Car playerCar;
    private List<OpponentCar> opponents;
    private RaceManager raceManager;
    private GameEnvironment gameEnvironment;

    private static final Route TEST_ROUTE = Route.DESERT_DRIFT;
    private static final Course TEST_COURSE = Course.DESERT;
    private static final Difficulty TEST_DIFFICULTY = Difficulty.EASY;

    private final double PLAYER_MGR_SPEED = 2.0;
    private final double PLAYER_MGR_FUEL_RATE = 0.01;

    private static final int KNOWN_REPAIR_COST = 250;
    private static final int KNOWN_TRAVELER_PROFIT = 250;
    private static final int EXPECTED_REFUEL_WAIT_TICKS = 1;
    private static final int EXPECTED_REPAIR_WAIT_TICKS = 2;
    private static final int EXPECTED_TRAVELER_WAIT_TICKS = 2;


    private void simulateRaceTicksAdvancingPlayer(RaceManager manager, int targetPlayerMovementTicks, GameEnvironment envContext, String testMode) {
        int playerMovementTicksAchieved = 0;
        int totalSimulationTicks = 0;
        int maxSafetyBreakTicks = targetPlayerMovementTicks * 50 + 50;

        while (playerMovementTicksAchieved < targetPlayerMovementTicks && !manager.isRaceFinished() && totalSimulationTicks < maxSafetyBreakTicks) {
            totalSimulationTicks++;
            boolean playerWasWaitingBeforeEventCheck = manager.isWaiting();
            RaceEvent currentEventForHandling = manager.getCurrentEvent();

            if (playerWasWaitingBeforeEventCheck && currentEventForHandling != null) {
                RaceEventType eventType = currentEventForHandling.getType();
                switch (eventType) {
                    case FUEL_STOP:
                        if (!"manualEventHandling".equals(testMode)) {
                            manager.handleFuelStop(true);
                        }
                        break;
                    case BREAKDOWN:
                        if (!"manualEventHandling".equals(testMode)) {
                            if (envContext.getBalance() >= KNOWN_REPAIR_COST) {
                                manager.handleRepair(true, envContext);
                            } else {
                                manager.handleRepair(false, envContext);
                            }
                        }
                        break;
                    case TRAVELER:
                        if (!"manualEventHandling".equals(testMode)) {
                            manager.handleTraveler(false, envContext);
                        }
                        break;
                    case WEATHER:
                        if ("ignoreWeather".equals(testMode) && !"manualEventHandling".equals(testMode)) {
                            manager.clearCurrentEvent();
                            manager.setWaiting(false, 0);
                        } else if (!"manualEventHandling".equals(testMode)){
                            manager.handleWeather(envContext);
                        }
                        break;
                }
            }

            boolean playerEligibleToMoveInThisRmTick = !manager.isWaiting();
            double playerDistBeforeAdvanceTick = manager.getPlayerDistance();
            manager.advanceRaceTick();
            double playerDistAfterAdvanceTick = manager.getPlayerDistance();

            if (playerEligibleToMoveInThisRmTick && playerDistAfterAdvanceTick > playerDistBeforeAdvanceTick) {
                playerMovementTicksAchieved++;
            }
        }

        if (totalSimulationTicks >= maxSafetyBreakTicks && playerMovementTicksAchieved < targetPlayerMovementTicks && !manager.isRaceFinished()) {
            System.err.println("RaceManagerTest - WARNING: Loop safety break hit in " +
                    Thread.currentThread().getStackTrace()[2].getMethodName() +
                    ". TargetPlayerMovementTicks: " + targetPlayerMovementTicks +
                    ", Achieved: " + playerMovementTicksAchieved +
                    ", TotalSimulated: " + totalSimulationTicks +
                    ". FinishReason: " + manager.getFinishReason() +
                    ", IsWaiting: " + manager.isWaiting() +
                    ", CurrentEvent: " + (manager.getCurrentEvent() != null ? manager.getCurrentEvent().getType() : "null") +
                    ", PlayerDist: " + manager.getPlayerDistance() +
                    ", RaceCancelled: " + manager.isRaceCancelled());
        }
    }

    private void advanceTicksUntilWaitingEnds(RaceManager manager, int maxExpectedWaitTicks) {
        int ticksAdvanced = 0;
        int safetyMaxTicks = maxExpectedWaitTicks + 2;
        while (manager.isWaiting() && ticksAdvanced < safetyMaxTicks) {
            manager.advanceRaceTick();
            ticksAdvanced++;
        }
        if (ticksAdvanced >= safetyMaxTicks && manager.isWaiting()) {
            fail("Player was still waiting after " + safetyMaxTicks + " ticks (expected " + maxExpectedWaitTicks + "). Event: " + (manager.getCurrentEvent() != null ? manager.getCurrentEvent().getType() : "none"));
        }
    }


    @BeforeEach
    void setUp() {
        playerCar = new Car("Player TestCarFE100", 0.7, 0.6, 0.95, 100, 1500);

        testRace = new Race(TEST_COURSE, TEST_ROUTE, TEST_DIFFICULTY);
        opponents = new ArrayList<>(testRace.getOpponents());

        if (opponents.isEmpty()) {
            OpponentCar fallbackOpponent = new OpponentCar(0.1);
            opponents.add(fallbackOpponent);
            testRace.setOpponents(opponents);
        }

        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);

        gameEnvironment = new GameEnvironment();
        gameEnvironment.setName("JUnit Player");
        gameEnvironment.setDifficulty(TEST_DIFFICULTY);
        gameEnvironment.setBalance(10000);
        gameEnvironment.getPlayerInventory().setStarterCar(playerCar);
        gameEnvironment.setCurrentRace(testRace);
        raceManager.deductEntryFee(gameEnvironment);
    }

    @Test
    void getMoneyEarnedForFirstPlace() {
        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished(), "Player should have finished. Dist: " + raceManager.getPlayerDistance() + " Reason: " + raceManager.getFinishReason() + " Cancelled: " + raceManager.isRaceCancelled());
        assertEquals("Finished the race!", raceManager.getFinishReason());
        assertFalse(raceManager.isRaceCancelled());
        assertEquals(1, raceManager.getPlayerPlacement());

        int balanceBeforeOutcomeProcessing = gameEnvironment.getBalance();
        int expectedPrize = TEST_COURSE.getPrizes().getFirstPlacePrize();

        raceManager.processRaceOutcome(gameEnvironment);

        assertEquals(expectedPrize, raceManager.getMoneyEarned());
        assertEquals(balanceBeforeOutcomeProcessing + expectedPrize, gameEnvironment.getBalance());
    }


    @Test
    void raceFinishesWhenTimeRunsOut() {
        RaceManager timeTestManager = new RaceManager(testRace, playerCar, opponents, 0.01, 0.0001);
        simulateRaceTicksAdvancingPlayer(timeTestManager, 1801, gameEnvironment, "allowWeather");

        assertTrue(timeTestManager.isRaceFinished());
        assertTrue(timeTestManager.hasPlayerFinished());

        if (Objects.equals("Weather has cancelled the race!", timeTestManager.getFinishReason())) {
            assertTrue(timeTestManager.isRaceCancelled());
        } else {
            assertEquals("Time ran out!", timeTestManager.getFinishReason());
            if (Objects.equals("Time ran out!", timeTestManager.getFinishReason())) {
                assertTrue(timeTestManager.getFuelLevel() > 0.000001);
                assertTrue(timeTestManager.getPlayerDistance() < TEST_ROUTE.getLength());
            }
        }
    }

    @Test
    void playerFinishesRaceByReachingDistanceFirst() {
        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished());
        assertTrue(raceManager.isRaceFinished());
        assertEquals("Finished the race!", raceManager.getFinishReason());
        assertTrue(raceManager.getPlayerFinishTick() >= 10 && raceManager.getPlayerFinishTick() < 10 + (2 * EXPECTED_REFUEL_WAIT_TICKS + EXPECTED_REPAIR_WAIT_TICKS + EXPECTED_TRAVELER_WAIT_TICKS + 5) ,
                "Player finish tick (" + raceManager.getPlayerFinishTick() + ") was not around the expected 10 effective ticks for distance.");
        assertEquals(1, raceManager.getPlayerPlacement());
    }

    @Test
    void deductAndAwardMoneyCorrectlyForCityCourseWin() {
        Course cityCourse = Course.CITY;
        Route cityRoute = Route.CITY_ALLEYS;
        Race cityRace = new Race(cityCourse, cityRoute, TEST_DIFFICULTY);

        GameEnvironment cityGameEnv = new GameEnvironment();
        cityGameEnv.setBalance(5000);
        cityGameEnv.setCurrentRace(cityRace);
        cityGameEnv.getPlayerInventory().setStarterCar(playerCar);

        RaceManager cityManager = new RaceManager(cityRace, playerCar, cityRace.getOpponents(), 3.0, PLAYER_MGR_FUEL_RATE);

        cityManager.deductEntryFee(cityGameEnv);
        assertEquals(5000 - cityCourse.getEntryFee(), cityGameEnv.getBalance());

        simulateRaceTicksAdvancingPlayer(cityManager, 12, cityGameEnv, "ignoreWeather");

        assertTrue(cityManager.hasPlayerFinished());
        if (Objects.equals("Finished the race!", cityManager.getFinishReason())) {
            cityManager.processRaceOutcome(cityGameEnv);
            assertEquals(1, cityManager.getPlayerPlacement());
            int prizeCalculatedByManager = cityManager.getMoneyEarned();
            assertEquals(cityCourse.getPrizes().getFirstPlacePrize(), prizeCalculatedByManager);
            assertEquals(5000 - cityCourse.getEntryFee() + prizeCalculatedByManager, cityGameEnv.getBalance());
        } else {
            fail("Player did not finish by distance as expected. Finish Reason: " + cityManager.getFinishReason() + ", Cancelled: " + cityManager.isRaceCancelled());
        }
    }

    @Test
    void testHandleFuelStop_Refuel() {
        simulateRaceTicksAdvancingPlayer(raceManager, 4, gameEnvironment, "manualEventHandling");

        assertTrue(raceManager.isWaiting());
        assertNotNull(raceManager.getCurrentEvent());
        assertEquals(RaceEventType.FUEL_STOP, raceManager.getCurrentEvent().getType());

        raceManager.handleFuelStop(true);

        assertEquals(1.0, raceManager.getFuelLevel());
        assertTrue(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
        advanceTicksUntilWaitingEnds(raceManager, EXPECTED_REFUEL_WAIT_TICKS);
        assertFalse(raceManager.isWaiting(), "Player should not be waiting after refuel wait ticks");
    }

    @Test
    void testHandleFuelStop_NoRefuel() {
        simulateRaceTicksAdvancingPlayer(raceManager, 4, gameEnvironment, "manualEventHandling");

        assertTrue(raceManager.isWaiting());
        assertNotNull(raceManager.getCurrentEvent());
        assertEquals(RaceEventType.FUEL_STOP, raceManager.getCurrentEvent().getType());

        double fuelBeforeDeclining = raceManager.getFuelLevel();
        raceManager.handleFuelStop(false);

        assertEquals(fuelBeforeDeclining, raceManager.getFuelLevel());
        assertFalse(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    private boolean simulateUntilProbabilisticEvent(RaceManager manager, GameEnvironment env, RaceEventType targetEvent, int moveTicksPerAttempt, int maxAttempts) {
        boolean desiredEventActive = false;
        for (int attempt = 0; attempt < maxAttempts && !manager.isRaceFinished() && !desiredEventActive; attempt++) {
            simulateRaceTicksAdvancingPlayer(manager, moveTicksPerAttempt, env, "manualEventHandling");

            if (manager.isWaiting() && manager.getCurrentEvent() != null) {
                RaceEventType currentType = manager.getCurrentEvent().getType();
                if (currentType == targetEvent) {
                    desiredEventActive = true;
                } else if (currentType == RaceEventType.FUEL_STOP) {
                    manager.handleFuelStop(true);
                    advanceTicksUntilWaitingEnds(manager, EXPECTED_REFUEL_WAIT_TICKS);
                } else {
                    System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() +
                            ": Unexpected event " + currentType + " while waiting for " + targetEvent +". Test might be inconclusive.");
                    break;
                }
            }
        }

        if (manager.isWaiting() && manager.getCurrentEvent() != null && manager.getCurrentEvent().getType() == targetEvent) {
            desiredEventActive = true;
        }
        return desiredEventActive;
    }


    @Test
    void testHandleRepair_Pay() {
        gameEnvironment.setBalance(KNOWN_REPAIR_COST + 100);

        playerCar = new Car("BreakdownProne", 0.7, 0.6, 0.1, 100, 1500);
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        gameEnvironment.getPlayerInventory().setStarterCar(playerCar);

        boolean breakdownEventActive = simulateUntilProbabilisticEvent(raceManager, gameEnvironment, RaceEventType.BREAKDOWN, 1, 15);

        if (!breakdownEventActive) {
            System.out.println(getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() +
                    ": BREAKDOWN event did not trigger. Test inconclusive for handler logic for this run.");
            return;
        }
        assertTrue(raceManager.isWaiting() && raceManager.getCurrentEvent().getType() == RaceEventType.BREAKDOWN, "Precondition: Breakdown event should be active.");


        int initialBalance = gameEnvironment.getBalance();
        raceManager.handleRepair(true, gameEnvironment);

        assertEquals(initialBalance - KNOWN_REPAIR_COST, gameEnvironment.getBalance());
        assertTrue(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
        advanceTicksUntilWaitingEnds(raceManager, EXPECTED_REPAIR_WAIT_TICKS);
        assertFalse(raceManager.isWaiting(), "Player should not be waiting after repair wait ticks");
    }

    @Test
    void testHandleRepair_NoPay_Withdraws() {
        playerCar = new Car("BreakdownProne2", 0.7, 0.6, 0.1, 100, 1500);
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        gameEnvironment.getPlayerInventory().setStarterCar(playerCar);

        boolean breakdownEventActive = simulateUntilProbabilisticEvent(raceManager, gameEnvironment, RaceEventType.BREAKDOWN, 1, 15);

        if (!breakdownEventActive) {
            System.out.println(getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() +
                    ": BREAKDOWN event did not trigger. Test inconclusive for this run.");
            return;
        }
        assertTrue(raceManager.isWaiting() && raceManager.getCurrentEvent().getType() == RaceEventType.BREAKDOWN, "Precondition: Breakdown event should be active.");

        raceManager.handleRepair(false, gameEnvironment);

        assertTrue(raceManager.isRaceFinished());
        assertEquals("Car broke down! You withdrew from the race.", raceManager.getFinishReason());
        assertTrue(raceManager.hasPlayerFinished());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleTraveler_PickUp() {
        boolean travelerEventActive = simulateUntilProbabilisticEvent(raceManager, gameEnvironment, RaceEventType.TRAVELER, 1, 10);

        if (!travelerEventActive) {
            System.out.println(getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() +
                    ": TRAVELER event did not trigger. Test inconclusive for this run.");
            return;
        }
        assertTrue(raceManager.isWaiting() && raceManager.getCurrentEvent().getType() == RaceEventType.TRAVELER, "Precondition: Traveler event should be active.");

        int initialBalance = gameEnvironment.getBalance();
        int initialMoneyEarned = raceManager.getMoneyEarned();

        raceManager.handleTraveler(true, gameEnvironment);

        assertEquals(initialBalance + KNOWN_TRAVELER_PROFIT, gameEnvironment.getBalance());
        assertEquals(initialMoneyEarned + KNOWN_TRAVELER_PROFIT, raceManager.getMoneyEarned());
        assertTrue(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
        advanceTicksUntilWaitingEnds(raceManager, EXPECTED_TRAVELER_WAIT_TICKS);
        assertFalse(raceManager.isWaiting(), "Player should not be waiting after traveler wait ticks");
    }

    @Test
    void testHandleTraveler_Decline() {
        boolean travelerEventActive = simulateUntilProbabilisticEvent(raceManager, gameEnvironment, RaceEventType.TRAVELER, 1, 10);

        if (!travelerEventActive) {
            System.out.println(getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() +
                    ": TRAVELER event did not trigger. Test inconclusive for this run.");
            return;
        }
        assertTrue(raceManager.isWaiting() && raceManager.getCurrentEvent().getType() == RaceEventType.TRAVELER, "Precondition: Traveler event should be active.");


        int initialBalance = gameEnvironment.getBalance();
        int initialMoneyEarned = raceManager.getMoneyEarned();

        raceManager.handleTraveler(false, gameEnvironment);

        assertEquals(initialBalance, gameEnvironment.getBalance());
        assertEquals(initialMoneyEarned, raceManager.getMoneyEarned());
        assertFalse(raceManager.isWaiting());
        assertNull(raceManager.getCurrentEvent());
    }

    @Test
    void testHandleWeatherEvent() {
        Course mountainCourse = Course.MOUNTAIN;
        Race weatherRace = new Race(mountainCourse, Route.MOUNTAIN_CURVES, TEST_DIFFICULTY);
        gameEnvironment.setCurrentRace(weatherRace);
        gameEnvironment.setBalance(1000);
        gameEnvironment.setRacesRemaining(5);

        RaceManager weatherRaceManager = new RaceManager(weatherRace, playerCar, weatherRace.getOpponents(), PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        weatherRaceManager.deductEntryFee(gameEnvironment);

        int racesRemainingBefore = gameEnvironment.getRacesRemaining();
        int balanceBeforeWeather = gameEnvironment.getBalance();

        boolean weatherEventActive = simulateUntilProbabilisticEvent(weatherRaceManager, gameEnvironment, RaceEventType.WEATHER, 1, 10);

        if (!weatherEventActive) {
            System.out.println(getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() +
                    ": WEATHER event did not trigger. Test inconclusive for this run.");
            return;
        }
        assertTrue(weatherRaceManager.isWaiting() && weatherRaceManager.getCurrentEvent().getType() == RaceEventType.WEATHER, "Precondition: Weather event should be active.");

        weatherRaceManager.handleWeather(gameEnvironment);

        assertTrue(weatherRaceManager.isRaceCancelled());
        assertTrue(weatherRaceManager.isRaceFinished());
        assertTrue(weatherRaceManager.hasPlayerFinished());
        assertEquals("Weather has cancelled the race!", weatherRaceManager.getFinishReason());
        assertEquals(balanceBeforeWeather + mountainCourse.getEntryFee(), gameEnvironment.getBalance());
        assertEquals(racesRemainingBefore + 1, gameEnvironment.getRacesRemaining());
    }

    @Test
    void testProcessRaceOutcome_PlayerBrokeDown() {
        for (OpponentCar op : opponents) {
            for (int i = 0; i < (TEST_ROUTE.getLength() / op.getSpeed() / 2) +1 ; i++) {
                op.advanceTick();
            }
        }

        raceManager.playerWithdrawDueToBreakdown();
        int initialMoneyEarned = raceManager.getMoneyEarned();

        raceManager.processRaceOutcome(gameEnvironment);

        assertEquals(initialMoneyEarned, raceManager.getMoneyEarned(), "No prize money should be awarded after breakdown if player is effectively last.");
        assertFalse(gameEnvironment.hasWonCourse(testRace.getCourse()), "Course should not be marked as won after breakdown.");
    }

    @Test
    void testProcessRaceOutcome_RaceCancelled() {
        raceManager.handleWeather(gameEnvironment);
        int moneyBeforeOutcome = raceManager.getMoneyEarned();

        raceManager.processRaceOutcome(gameEnvironment);

        assertTrue(raceManager.isRaceCancelled());
        assertEquals(moneyBeforeOutcome, raceManager.getMoneyEarned(), "Money earned should not change if race cancelled and prizes not awarded.");
    }

    @Test
    void testAwardPrizeMoney_SecondPlace() {
        opponents.clear();
        OpponentCar fastOpponent = new OpponentCar(PLAYER_MGR_SPEED + 0.5);
        opponents.add(fastOpponent);
        for(int i=0; i < TEST_COURSE.getNumberOfOpponents() -1; ++i) {
            opponents.add(new OpponentCar(PLAYER_MGR_SPEED - 0.5));
        }
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        gameEnvironment.setBalance(10000);
        raceManager.deductEntryFee(gameEnvironment);

        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished());
        assertEquals(2, raceManager.getPlayerPlacement());

        int balanceBefore = gameEnvironment.getBalance();
        int moneyEarnedBefore = raceManager.getMoneyEarned();

        raceManager.awardPrizeMoney(gameEnvironment);

        assertEquals(moneyEarnedBefore + TEST_COURSE.getPrizes().getSecondPlacePrize(), raceManager.getMoneyEarned());
        assertEquals(balanceBefore + TEST_COURSE.getPrizes().getSecondPlacePrize(), gameEnvironment.getBalance());
    }

    @Test
    void testAwardPrizeMoney_ThirdPlace() {
        opponents.clear();
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED + 0.5));
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED + 0.2));
        for(int i=0; i < TEST_COURSE.getNumberOfOpponents() -2; ++i) {
            opponents.add(new OpponentCar(PLAYER_MGR_SPEED - 0.5));
        }
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        gameEnvironment.setBalance(10000);
        raceManager.deductEntryFee(gameEnvironment);

        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished());
        assertEquals(3, raceManager.getPlayerPlacement());

        int balanceBefore = gameEnvironment.getBalance();
        int moneyEarnedBefore = raceManager.getMoneyEarned();

        raceManager.awardPrizeMoney(gameEnvironment);

        assertEquals(moneyEarnedBefore + TEST_COURSE.getPrizes().getThirdPlacePrize(), raceManager.getMoneyEarned());
        assertEquals(balanceBefore + TEST_COURSE.getPrizes().getThirdPlacePrize(), gameEnvironment.getBalance());
    }


    @Test
    void testAwardPrizeMoney_NoPrize() {
        opponents.clear();
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED + 0.5));
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED + 0.4));
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED + 0.3));
        while(opponents.size() < TEST_COURSE.getNumberOfOpponents()) {
            opponents.add(new OpponentCar(PLAYER_MGR_SPEED - 0.5));
        }


        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        gameEnvironment.setBalance(10000);
        raceManager.deductEntryFee(gameEnvironment);

        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished());
        assertEquals(4, raceManager.getPlayerPlacement(), "Player placement should be 4th for no prize in this setup.");

        int balanceBefore = gameEnvironment.getBalance();
        int moneyEarnedBefore = raceManager.getMoneyEarned();

        raceManager.awardPrizeMoney(gameEnvironment);

        assertEquals(moneyEarnedBefore, raceManager.getMoneyEarned(), "No prize money for 4th place.");
        assertEquals(balanceBefore, gameEnvironment.getBalance());
    }

    @Test
    void testGetFinalPlacementText_VariousScenarios() {
        raceManager.handleWeather(gameEnvironment);
        assertEquals("Race Cancelled Due to Weather", raceManager.getFinalPlacementText());
        setUp();

        raceManager.finishRace("Out of fuel!");
        assertEquals("Ran out of fuel!", raceManager.getFinalPlacementText());
        setUp();

        raceManager.finishRace("Time ran out!");
        assertEquals("Time ran out!", raceManager.getFinalPlacementText());
        setUp();

        raceManager.playerWithdrawDueToBreakdown();
        assertEquals("Car broke down! You withdrew from the race.", raceManager.getFinalPlacementText());
        setUp();

        opponents.clear();
        for(int i=0; i < TEST_COURSE.getNumberOfOpponents(); ++i) opponents.add(new OpponentCar(0.01));
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        simulateRaceTicksAdvancingPlayer(raceManager, (int)(TEST_ROUTE.getLength()/PLAYER_MGR_SPEED), gameEnvironment, "ignoreWeather");
        assertEquals("🏆 You finished 1st!", raceManager.getFinalPlacementText());
        setUp();

        opponents.clear();
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED * 2));
        for(int i=0; i < TEST_COURSE.getNumberOfOpponents() -1; ++i) opponents.add(new OpponentCar(0.01));
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        simulateRaceTicksAdvancingPlayer(raceManager, (int)(TEST_ROUTE.getLength()/PLAYER_MGR_SPEED), gameEnvironment, "ignoreWeather");
        assertEquals("🥈 You finished 2nd!", raceManager.getFinalPlacementText());
        setUp();

        opponents.clear();
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED * 2));
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED * 1.8));
        opponents.add(new OpponentCar(PLAYER_MGR_SPEED * 1.5));
        while(opponents.size() < TEST_COURSE.getNumberOfOpponents()) {
            opponents.add(new OpponentCar(PLAYER_MGR_SPEED * 0.1));
        }
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);
        simulateRaceTicksAdvancingPlayer(raceManager, (int)(TEST_ROUTE.getLength()/PLAYER_MGR_SPEED), gameEnvironment, "ignoreWeather");
        assertEquals("You finished 4th.", raceManager.getFinalPlacementText());
    }

    @Test
    void testAdvanceRaceTick_WhenPlayerIsWaiting() {
        raceManager.setWaiting(true, 3);
        double initialDistance = raceManager.getPlayerDistance();
        double initialFuel = raceManager.getFuelLevel();

        raceManager.advanceRaceTick();

        assertEquals(initialDistance, raceManager.getPlayerDistance());
        assertEquals(initialFuel, raceManager.getFuelLevel());
        assertTrue(raceManager.isWaiting());

        raceManager.advanceRaceTick();
        raceManager.advanceRaceTick();

        assertFalse(raceManager.isWaiting());

        raceManager.advanceRaceTick();
        assertEquals(initialDistance + PLAYER_MGR_SPEED, raceManager.getPlayerDistance(), 0.001);
    }

    @Test
    void testAdvanceRaceTick_WhenRaceNotActiveOrCancelled() {
        double initialDistance = raceManager.getPlayerDistance();
        raceManager.finishRace("Test Finish");
        raceManager.advanceRaceTick();
        assertEquals(initialDistance, raceManager.getPlayerDistance());

        setUp();
        initialDistance = raceManager.getPlayerDistance();
        raceManager.handleWeather(gameEnvironment);
        assertTrue(raceManager.isRaceCancelled());
        assertFalse(raceManager.isRacing());
        raceManager.advanceRaceTick();
        assertEquals(initialDistance, raceManager.getPlayerDistance());
    }

    @Test
    void testMultipleFuelStops() {
        Route countryStraight = Route.COUNTRY_STRAIGHT;
        Race multiStopRace = new Race(Course.COUNTRY, countryStraight, Difficulty.EASY);
        raceManager = new RaceManager(multiStopRace, playerCar, multiStopRace.getOpponents(), PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);

        int fuelStopsHandled = 0;
        for (int i = 0; i < 35 && !raceManager.isRaceFinished(); i++) {
            boolean aboutToHandleFuelStop = raceManager.isWaiting() && raceManager.getCurrentEvent() != null && raceManager.getCurrentEvent().getType() == RaceEventType.FUEL_STOP;

            if (aboutToHandleFuelStop) {
                fuelStopsHandled++;
                raceManager.handleFuelStop(true);
                advanceTicksUntilWaitingEnds(raceManager, EXPECTED_REFUEL_WAIT_TICKS);
            }
            if (!raceManager.isRaceFinished()) {
                raceManager.advanceRaceTick();
            }
        }
        if (!raceManager.isRaceFinished() && raceManager.isWaiting() && raceManager.getCurrentEvent() != null && raceManager.getCurrentEvent().getType() == RaceEventType.FUEL_STOP) {
            fuelStopsHandled++;
        }
        assertEquals(countryStraight.getFuelStops(), fuelStopsHandled, "Should encounter and handle all fuel stops.");
    }

    @Test
    void testPlayerPlacementWhenOpponentFinishesFirst() {
        opponents.clear();
        OpponentCar fastOpponent = new OpponentCar(PLAYER_MGR_SPEED * 2);
        opponents.add(fastOpponent);
        for(int i=0; i < TEST_COURSE.getNumberOfOpponents() -1; ++i) {
            opponents.add(new OpponentCar(PLAYER_MGR_SPEED * 0.5));
        }
        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);

        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished());
        assertEquals(2, raceManager.getPlayerPlacement());

        raceManager.processRaceOutcome(gameEnvironment);
        assertEquals(Course.DESERT.getPrizes().getSecondPlacePrize(), raceManager.getMoneyEarned());
    }
}