package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.GameEnvironment;
import seng201.team0.services.RaceManager;

import java.util.ArrayList;
import java.util.List;

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
    private final double PLAYER_MGR_FUEL_RATE = 0.01; // Lasts 100 ticks

    private static final int KNOWN_REPAIR_COST = 250;

    // Helper to advance race ticks.
    private void simulateRaceTicksAdvancingPlayer(RaceManager manager, int targetPlayerMovementTicks, GameEnvironment envContext, String testMode) {
        int playerMovementTicksAchieved = 0;
        int totalSimulationTicks = 0;
        // Safety break based on total simulation ticks, not just movement ticks, to prevent true infinite loops.
        // If events cause many fixed waits, total ticks can be higher.
        int maxSafetyBreakTicks = targetPlayerMovementTicks * 50; // e.g., 10 * 50 = 500 ticks

        while (playerMovementTicksAchieved < targetPlayerMovementTicks && !manager.isRaceFinished() && totalSimulationTicks < maxSafetyBreakTicks) {
            totalSimulationTicks++;
            boolean playerWasWaitingBeforeEventCheck = manager.isWaiting();
            RaceEvent currentEventForHandling = manager.getCurrentEvent();

            // --- Test Helper Intervention for Indefinite Waits ---
            if (playerWasWaitingBeforeEventCheck && currentEventForHandling != null) {
                RaceEventType eventType = currentEventForHandling.getType();
                switch (eventType) {
                    case FUEL_STOP:
                        manager.handleFuelStop(true); // Assumes refuel, sets fixed wait
                        break;
                    case BREAKDOWN:
                        if (envContext.getBalance() >= KNOWN_REPAIR_COST) {
                            manager.handleRepair(true, envContext); // Sets fixed wait
                        } else {
                            manager.handleRepair(false, envContext); // Ends race
                        }
                        break;
                    case TRAVELER:
                        manager.handleTraveler(false, envContext); // Assumes decline, may set fixed wait or no wait
                        break;
                    case WEATHER:
                        if ("ignoreWeather".equals(testMode)) {
                            manager.clearCurrentEvent();      // Critical: Clear the event
                            manager.setWaiting(false, 0); // Critical: Ensure player is not stuck
                        } else {
                            manager.handleWeather(envContext); // Normal cancellation
                        }
                        break;
                }
            }
            // --- End Test Helper Intervention ---

            // Check if player is eligible to move *before* calling advanceRaceTick
            // This is because advanceRaceTick itself will handle fixed waits internally.
            boolean playerEligibleToMoveInThisRmTick = !manager.isWaiting();
            double playerDistBeforeAdvanceTick = manager.getPlayerDistance();

            manager.advanceRaceTick(); // Processes one game tick

            double playerDistAfterAdvanceTick = manager.getPlayerDistance();

            // Count as a "player movement tick" if the player was eligible to move
            // (not in an indefinite or fixed wait at the start of RaceManager's internal movement phase)
            // AND their distance actually increased.
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

    @BeforeEach
    void setUp() {
        playerCar = new Car("Player TestCar", 0.7, 0.6, 0.95, 50, 1500);
        testRace = new Race(TEST_COURSE, TEST_ROUTE, TEST_DIFFICULTY);
        opponents = new ArrayList<>(testRace.getOpponents());

        if (opponents.isEmpty()) {
            OpponentCar fallbackOpponent = new OpponentCar(0.1);
            opponents.add(fallbackOpponent);
            testRace.setOpponents(opponents);
        }
        // OpponentCar firstOpponent = opponents.get(0); // Not strictly needed if not used directly

        raceManager = new RaceManager(testRace, playerCar, opponents, PLAYER_MGR_SPEED, PLAYER_MGR_FUEL_RATE);

        gameEnvironment = new GameEnvironment();
        gameEnvironment.setName("JUnit Player");
        gameEnvironment.setDifficulty(TEST_DIFFICULTY);
        gameEnvironment.setBalance(10000);
        gameEnvironment.getPlayerInventory().setStarterCar(playerCar);
        gameEnvironment.setCurrentRace(testRace);
        raceManager.deductEntryFee(gameEnvironment); // TEST_COURSE (DESERT) has 0 entry fee.
    }

    @Test
    void getMoneyEarnedForFirstPlace() {
        // Player needs 10 effective movement ticks to finish (20km route / 2km/tick speed).
        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        // Verify race outcome basics
        assertTrue(raceManager.hasPlayerFinished(), "Player should have finished. Dist: " + raceManager.getPlayerDistance() + " Reason: " + raceManager.getFinishReason() + " Cancelled: " + raceManager.isRaceCancelled());
        assertEquals("Finished the race!", raceManager.getFinishReason(), "Finish reason should indicate normal finish.");
        assertFalse(raceManager.isRaceCancelled(), "Race should not be cancelled in this test scenario.");
        assertEquals(1, raceManager.getPlayerPlacement(), "Player placement should be 1st.");

        // Capture GameEnvironment balance before outcome processing for GE balance check later
        int balanceBeforeOutcomeProcessing = gameEnvironment.getBalance();
        int expectedPrize = TEST_COURSE.getPrizes().getFirstPlacePrize();

        // Process the race outcome. This updates RaceManager.moneyEarned and GameEnvironment.balance
        raceManager.processRaceOutcome(gameEnvironment);

        // Now, check RaceManager's moneyEarned. It should include the prize money.
        assertEquals(expectedPrize, raceManager.getMoneyEarned(),
                "RaceManager's getMoneyEarned() should reflect the first place prize after outcome processing. RaceCancelled=" + raceManager.isRaceCancelled() + ", Placement=" + raceManager.getPlayerPlacement());

        // Verify GameEnvironment balance after processing the outcome
        assertEquals(balanceBeforeOutcomeProcessing + expectedPrize, gameEnvironment.getBalance(),
                "GameEnvironment balance incorrect after processing outcome.");
    }


    @Test
    void raceFinishesWhenTimeRunsOut() {
        RaceManager timeTestManager = new RaceManager(testRace, playerCar, opponents, 0.01, 0.0001);
        // DESERT_DRIFT duration: 900s. Ticks = 1800.
        simulateRaceTicksAdvancingPlayer(timeTestManager, 1801, gameEnvironment, "allowWeather");

        assertTrue(timeTestManager.isRaceFinished(), "Race should be finished.");
        assertTrue(timeTestManager.hasPlayerFinished(), "PlayerFinished flag should be true on time out (as per RaceManager logic).");

        if ("Weather has cancelled the race!".equals(timeTestManager.getFinishReason())) {
            assertTrue(true, "Race was cancelled by weather, a valid outcome for this long simulation.");
        } else {
            assertEquals("Time ran out!", timeTestManager.getFinishReason(), "Race should ideally end because time ran out.");
            if ("Time ran out!".equals(timeTestManager.getFinishReason())) {
                assertTrue(timeTestManager.getFuelLevel() > 0.000001, "Fuel should not have run out if time was primary.");
                assertTrue(timeTestManager.getPlayerDistance() < TEST_ROUTE.getLength(), "Player should not have finished by distance if time was primary.");
            }
        }
    }

    @Test
    void playerFinishesRaceByReachingDistanceFirst() {
        simulateRaceTicksAdvancingPlayer(raceManager, 10, gameEnvironment, "ignoreWeather");

        assertTrue(raceManager.hasPlayerFinished(), "Player should have finished by distance. Reason: " + raceManager.getFinishReason());
        assertTrue(raceManager.isRaceFinished(), "Race should be marked as finished.");
        assertEquals("Finished the race!", raceManager.getFinishReason(), "Finish reason mismatch.");
        // Check player finish tick, allowing for some variance due to event handling fixed waits
        assertTrue(raceManager.getPlayerFinishTick() >= 10 && raceManager.getPlayerFinishTick() < 10 + (TEST_ROUTE.getFuelStops() * 1 + 2*2 + 2*2), // Max wait: 2 fuel stops (1 tick each) + 1 breakdown (2 ticks) + 1 traveler (2 ticks)
                "Player finish tick (" + raceManager.getPlayerFinishTick() + ") was not around the expected 10 effective ticks for distance.");
        assertEquals(1, raceManager.getPlayerPlacement(), "Player placement should be 1st.");
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

        RaceManager cityManager = new RaceManager(cityRace, playerCar, cityRace.getOpponents(), 3.0, 0.0001);

        cityManager.deductEntryFee(cityGameEnv);
        assertEquals(5000 - cityCourse.getEntryFee(), cityGameEnv.getBalance(), "Balance incorrect after entry fee deduction.");

        simulateRaceTicksAdvancingPlayer(cityManager, 12, cityGameEnv, "ignoreWeather"); // CITY_ALLEYS length 35km. Speed 3km/tick -> ~12 ticks

        assertTrue(cityManager.hasPlayerFinished(), "Player should have finished the City race by distance. Reason: " + cityManager.getFinishReason());
        if ("Finished the race!".equals(cityManager.getFinishReason())) {
            cityManager.processRaceOutcome(cityGameEnv);
            assertEquals(1, cityManager.getPlayerPlacement(), "Player should be in 1st place for City race.");
            int prizeCalculatedByManager = cityManager.getMoneyEarned();
            assertEquals(cityCourse.getPrizes().getFirstPlacePrize(), prizeCalculatedByManager, "RaceManager's getMoneyEarned() calculation is incorrect.");
            assertEquals(5000 - cityCourse.getEntryFee() + prizeCalculatedByManager, cityGameEnv.getBalance(),
                    "GameEnvironment final balance is incorrect.");
        } else {
            fail("Player did not finish by distance as expected. Finish Reason: " + cityManager.getFinishReason() + ", Cancelled: " + cityManager.isRaceCancelled());
        }
    }
}