package seng201.team0.services;

import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Import Objects
import java.util.Random;

/**
 * Manages the core logic of a single race, including player and opponent progress,
 * fuel management, event triggers, and race completion.
 * It simulates race progression tick by tick.
 */
public class RaceManager {
    /**
     * The {@link Race} instance this manager is controlling.
     */
    private final Race race;
    /**
     * The player's {@link Car} participating in this race.
     */
    private final Car playerCar;
    /**
     * The list of {@link OpponentCar} objects in this race.
     */
    private final List<OpponentCar> opponents;
    /**
     * The current distance covered by the player's car.
     */
    private double playerDistance = 0;
    /**
     * The current fuel level of the player's car, represented as a fraction (0.0 to 1.0).
     */
    private double fuelLevel = 1.0;
    /**
     * A counter for the number of simulation ticks that have passed.
     */
    private int tickCount = 0;
    /**
     * Flag indicating if the race is currently active.
     */
    private boolean isRacing = true;
    /**
     * Flag indicating if the player is currently waiting due to an event (e.g., fuel stop, repair).
     */
    private boolean isWaiting = false;
    /**
     * The number of ticks remaining for a current wait period.
     */
    private int waitTicksRemaining = 0;
    /**
     * The effective speed of the player's car, calculated considering route attributes.
     */
    private final double speed;
    /**
     * The rate at which the player's car consumes fuel.
     */
    private final double fuelConsumptionRate;
    /**
     * The distance at which a random event might be triggered.
     */
    private final double eventTriggerDistance;
    /**
     * The distance at which a breakdown event might be triggered.
     */
    private final double breakdownTriggerDistance;
    /**
     * A {@link Random} instance for generating random events.
     */
    private final Random random = new Random();
    /**
     * The {@link RaceEvent} currently affecting the player, if any.
     */
    private RaceEvent currentEvent = null;
    /**
     * Flag to ensure the random event is triggered only once.
     */
    private boolean eventDone = false;
    /**
     * Flag to ensure the breakdown event is triggered only once.
     */
    private boolean breakdownDone = false;
    /**
     * A list of distances where fuel stops are mandated.
     */
    private final List<Double> fuelStopDistances;
    /**
     * The index of the next mandatory fuel stop to be encountered.
     */
    private int nextFuelStopIndex = 0;
    /**
     * A constant used to indicate an indefinite wait, requiring user interaction to proceed.
     */
    private static final int INDEFINITE_WAIT = 9999;
    /**
     * A string describing why the race finished (e.g., "Out of fuel!", "Finished the race!").
     */
    private String finishReason = "";
    /**
     * The tick count at which the player finished the race.
     */
    private int playerFinishTick = -1;
    /**
     * Flag indicating if the player's car has officially finished the race (either by reaching end, running out of fuel, or time).
     */
    private boolean playerFinished = false;
    /**
     * The total duration of the race in seconds.
     */
    private final double raceDurationSeconds;
    /**
     * The time elapsed in the race in seconds.
     */
    private double timeElapsedSeconds = 0;
    /**
     * Flag indicating if the race was cancelled, for example, due to severe weather.
     */
    private boolean raceCancelled = false;
    /**
     * The amount of money earned by the player in the race.
     */
    private int moneyEarned;

    // Constants for event-related wait times and costs/profits
    /**
     * The number of ticks the player must wait for a car repair.
     */
    private static final int REPAIR_WAIT_TICKS = 2;
    /**
     * The number of ticks the player must wait for a traveler interaction.
     */
    private static final int TRAVELER_WAIT_TICKS = 2;
    /**
     * The number of ticks the player must wait for refueling.
     */
    private static final int REFUEL_WAIT_TICKS = 1;
    /**
     * The cost incurred for repairing the car after a breakdown.
     */
    private static final int REPAIR_COST = 250;
    /**
     * The profit gained from picking up a traveler.
     */
    private static final int TRAVELER_PROFIT = 250;


    /**
     * Constructs a new RaceManager.
     * Initializes the race with the given race details, player car, opponents,
     * calculated speed, and fuel consumption rate. It also calculates and sets up
     * fuel stop distances, event trigger distances, and race duration.
     *
     * @param race The {@link Race} object containing course and route details.
     * @param playerCar The player's {@link Car}.
     * @param opponents A {@link List} of {@link OpponentCar} objects.
     * @param speed The effective speed of the player's car for this race.
     * @param fuelConsumptionRate The fuel consumption rate of the player's car for this race.
     */
    public RaceManager(Race race, Car playerCar, List<OpponentCar> opponents, double speed, double fuelConsumptionRate) {
        this.race = race;
        this.playerCar = playerCar;
        this.opponents = opponents;
        this.speed = speed;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.fuelStopDistances = RaceCalculations.calculateFuelStopDistances(race.getRoute().getLength(), race.getRoute().getFuelStops());
        this.eventTriggerDistance = RaceCalculations.calculateEventTriggerDistance(race.getRoute().getLength());
        this.breakdownTriggerDistance = RaceCalculations.calculateBreakdownTriggerDistance(race.getRoute().getLength());
        this.raceDurationSeconds = race.getRoute().getRaceDuration();
        this.moneyEarned = 0; // Initialize earnings
    }

    /**
     * Advances the race simulation by one tick (a small unit of time).
     * This method updates opponent distances, player distance (if not waiting),
     * fuel level, checks for time limits, and triggers random/breakdown events or fuel stops.
     */
    public void advanceRaceTick() {
        if (!isRacing || raceCancelled) return; // Stop if race is over or cancelled
        tickCount++;
        updateOpponentDistances(); // Opponents always advance

        if (isWaiting) {
            waitTicksRemaining--;
            if (waitTicksRemaining <= 0) {
                isWaiting = false; // Waiting period is over
            }
            return; // Player car does not advance during wait
        }

        playerDistance += speed;
        fuelLevel -= fuelConsumptionRate;
        timeElapsedSeconds += (1.0 / 2); // Assuming 1 tick = 0.5 seconds for duration calculation

        // Check for race-ending conditions
        if (fuelLevel <= 0) {
            fuelLevel = 0; // Ensure fuel doesn't go negative
            finishRace("Out of fuel!");
            return; // End race simulation for player
        }
        if (timeElapsedSeconds >= raceDurationSeconds && !playerFinished) {
            finishRace("Time ran out!");
            return; // End race simulation for player
        }

        // Trigger random events
        if (playerDistance >= eventTriggerDistance && !eventDone) {
            maybeTriggerRandomEvent();
            eventDone = true; // Ensure it only triggers once
        }
        // Trigger breakdown event
        if (playerDistance >= breakdownTriggerDistance && !breakdownDone) {
            maybeTriggerBreakdownEvent();
            breakdownDone = true; // Ensure it only triggers once
        }

        // Handle mandatory fuel stops
        if (nextFuelStopIndex < fuelStopDistances.size() && playerDistance >= fuelStopDistances.get(nextFuelStopIndex)) {
            isWaiting = true;
            waitTicksRemaining = INDEFINITE_WAIT; // Player must interact to continue
            currentEvent = new RaceEvent(RaceEventType.FUEL_STOP);
            nextFuelStopIndex++;
        }

        // Check if player has finished the race route
        if (playerDistance >= race.getRoute().getLength() && !playerFinished) {
            playerFinished(); // Mark player as finished
        }
    }

    /**
     * Explicitly marks the player as having finished the race, if they haven't already.
     * This is called when the player crosses the finish line.
     */
    public void playerFinished() {
        if (isRacing && !playerFinished && !raceCancelled) {
            playerFinished = true;
            playerFinishTick = tickCount; // Record the tick at which player finished
            finishRace("Finished the race!"); // Set the reason for finishing
        }
    }

    /**
     * Attempts to trigger a random event (Traveler or Weather) based on probability.
     * If an event is triggered, the player enters a waiting state.
     */
    private void maybeTriggerRandomEvent() {
        int roll = random.nextInt(100); // 0-99
        if (roll < 25) { // 25% chance for Traveler
            currentEvent = new RaceEvent(RaceEventType.TRAVELER);
        } else if (roll < 40) { // 15% chance for Weather (total 40% chance for any random event)
            currentEvent = new RaceEvent(RaceEventType.WEATHER);
        } else {
            // No event occurs (60% chance)
            return;
        }
        isWaiting = true;
        waitTicksRemaining = INDEFINITE_WAIT; // Player must interact to proceed
    }

    /**
     * Attempts to trigger a car breakdown event based on the car's effective reliability
     * and the game's difficulty breakdown multiplier.
     * If a breakdown occurs, the player enters a waiting state.
     */
    private void maybeTriggerBreakdownEvent() {
        double reliability = RaceCalculations.calculateEffectiveReliability(playerCar, race.getRoute());
        // Breakdown chance inversely proportional to reliability, adjusted by difficulty
        double breakdownChance = (1.0 - reliability) * 100 * race.getDifficulty().getBreakdownMultiplier();
        int roll = random.nextInt(100); // 0-99

        if (roll < breakdownChance) { // Trigger breakdown if roll is within breakdown chance
            currentEvent = new RaceEvent(RaceEventType.BREAKDOWN);
            isWaiting = true;
            waitTicksRemaining = INDEFINITE_WAIT; // Player must interact to proceed
        }
    }

    /**
     * Clears the {@link #currentEvent}, signaling that the event has been handled.
     */
    public void clearCurrentEvent() {
        currentEvent = null;
    }

    /**
     * Updates the distance traveled by all opponent cars for the current tick.
     */
    public void updateOpponentDistances() {
        for (OpponentCar opponent : opponents) {
            opponent.advanceTick();
        }
    }

    /**
     * Generates a sorted list of {@link LeaderboardEntry} objects, including the player and all opponents.
     * The list is sorted in descending order of distance covered.
     * @return A sorted {@link List} of leaderboard entries.
     */
    public List<LeaderboardEntry> getLeaderboardStandings() {
        List<LeaderboardEntry> standings = new ArrayList<>();

        standings.add(new LeaderboardEntry("Player", playerDistance));
        for (int i = 0; i < opponents.size(); i++) {
            standings.add(new LeaderboardEntry("Opponent " + (i + 1), opponents.get(i).getCurrentDistance()));
        }

        // Sort the standings by distance in descending order
        standings.sort((a, b) -> Double.compare(b.getDistance(), a.getDistance()));

        return standings;
    }

    /**
     * Returns a list of formatted strings representing the current leaderboard standings.
     * @return A {@link List} of strings, each showing a position, name, and distance.
     */
    public List<String> getLeaderboardStrings() {
        List<LeaderboardEntry> standings = getLeaderboardStandings();
        List<String> leaderboardStrings = new ArrayList<>();

        for (int i = 0; i < standings.size(); i++) {
            LeaderboardEntry entry = standings.get(i);
            String position = (i + 1) + ". " + entry.getName() + " - " + (int) entry.getDistance() + " km";
            leaderboardStrings.add(position);
        }

        return leaderboardStrings;
    }

    /**
     * Determines the player's current placement in the race based on distance.
     * @return The player's 1-based placement (e.g., 1 for 1st place), or -1 if the player is not found.
     */
    public int getPlayerPlacement() {
        List<LeaderboardEntry> standings = getLeaderboardStandings();

        for (int i = 0; i < standings.size(); i++) {
            if (standings.get(i).getName().equals("Player")) {
                return i + 1; // +1 to convert from 0-based index to 1-based placement
            }
        }
        return -1; // Should theoretically not happen if player is always on leaderboard
    }

    /**
     * Handles the scenario where the player withdraws from the race due to a breakdown.
     * Sets race state to finished and updates the finish reason.
     */
    public void playerWithdrawDueToBreakdown() {
        isRacing = false;
        playerFinished = true; // Player's race is considered finished
        finishReason = "Car broke down! You withdrew from the race.";
    }

    /**
     * Concludes the race simulation and sets the reason for finishing.
     * Ensures the race state transitions correctly to "not racing".
     *
     * @param reason A string describing why the race concluded (e.g., "Finished the race!", "Out of fuel!").
     */
    public void finishRace(String reason) {
        if (isRacing && !raceCancelled) {
            isRacing = false;
            finishReason = reason;
            // Ensure playerFinished and playerFinishTick are set correctly
            if (!playerFinished && reason.equals("Finished the race!")) {
                playerFinished = true;
                playerFinishTick = tickCount;
            } else if (reason.equals("Time ran out!") && !playerFinished) {
                playerFinished = true;
                playerFinishTick = tickCount;
            } else if (reason.equals("Out of fuel!") && !playerFinished) {
                playerFinished = true;
                playerFinishTick = tickCount;
            }
        }
    }

    /**
     * Handles the outcome of a fuel stop event.
     * If {@code refuel} is true, the player's fuel level is restored and a waiting period is initiated.
     * Otherwise, the waiting state is simply cleared.
     *
     * @param refuel {@code true} if the player chooses to refuel, {@code false} otherwise.
     */
    public void handleFuelStop(boolean refuel) {
        if (refuel) {
            refuel(); // Refill fuel
            setWaiting(true, REFUEL_WAIT_TICKS); // Short wait for refueling
        } else {
            setWaiting(false, 0); // No refueling, no wait
        }
        clearCurrentEvent(); // Event handled
    }

    /**
     * Handles the outcome of a breakdown event.
     * If {@code pay} is true, the player pays for repairs, incurs a waiting period, and the car is fixed.
     * Otherwise, the player withdraws from the race due to the breakdown.
     *
     * @param pay {@code true} if the player chooses to pay for repairs, {@code false} otherwise.
     * @param gameEnvironment The {@link GameEnvironment} to update player balance.
     */
    public void handleRepair(boolean pay, GameEnvironment gameEnvironment) {
        if (pay) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() - REPAIR_COST); // Deduct cost
            setWaiting(true, REPAIR_WAIT_TICKS); // Wait for repair
        } else {
            playerWithdrawDueToBreakdown(); // Player withdraws
        }
        clearCurrentEvent(); // Event handled
    }

    /**
     * Handles the outcome of a traveler event.
     * If {@code pickUp} is true, the player gains profit and incurs a waiting period.
     * Otherwise, no action is taken besides clearing the event.
     *
     * @param pickUp {@code true} if the player chooses to pick up the traveler, {@code false} otherwise.
     * @param gameEnvironment The {@link GameEnvironment} to update player balance.
     */
    public void handleTraveler(boolean pickUp, GameEnvironment gameEnvironment) {
        if (pickUp) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() + TRAVELER_PROFIT); // Add profit
            moneyEarned += TRAVELER_PROFIT; // Track money earned
            setWaiting(true, TRAVELER_WAIT_TICKS); // Wait for traveler interaction
        } else {
            setWaiting(false, 0); // No interaction, no wait
        }
        clearCurrentEvent(); // Event handled
    }

    /**
     * Handles a weather event, leading to the cancellation of the race.
     * The player's entry fee is refunded, and the race count is adjusted.
     *
     * @param gameEnvironment The {@link GameEnvironment} to update balance and race count.
     */
    public void handleWeather(GameEnvironment gameEnvironment) {
        raceCancelled = true;
        isRacing = false;
        playerFinished = true; // Race is finished due to cancellation
        int entryFee = race.getCourse().getEntryFee();
        gameEnvironment.setBalance(gameEnvironment.getBalance() + entryFee); // Refund entry fee
        gameEnvironment.setRacesRemaining(gameEnvironment.getRacesRemaining() + 1); // Race doesn't count against remaining
        finishReason = "Weather has cancelled the race!";
    }

    /**
     * Concludes the race and processes all post-race updates.
     * This includes awarding prize money, updating win/loss status for the course,
     * unlocking new parts, and updating overall game statistics.
     * This method encapsulates all the logic previously in RaceController.finishRace()
     * that dealt with game environment updates.
     *
     * @param gameEnvironment The {@link GameEnvironment} to update.
     */
    public void processRaceOutcome(GameEnvironment gameEnvironment) {
        // Award prize money if the race wasn't cancelled
        if (!raceCancelled) {
            awardPrizeMoney(gameEnvironment);
        }

        // Determine final placement and update game environment
        int finalPlacement = getFinalPlacement();

        gameEnvironment.updateHasWonCourse(race.getCourse(), finalPlacement);
        gameEnvironment.getShopService().unlockNewCars(); // Corrected to unlockNewCars based on ShopService
        gameEnvironment.addRacePlacement(finalPlacement);
        gameEnvironment.addPrizeMoney(moneyEarned); // Add total earnings for the race
    }

    private int getFinalPlacement() {
        int finalPlacement;
        if (Objects.equals(finishReason, "Car broke down! You withdrew from the race.") ||
                Objects.equals(finishReason, "Weather has cancelled the race!") ||
                Objects.equals(finishReason, "Out of fuel!") ||
                Objects.equals(finishReason, "Time ran out!")) {
            finalPlacement = opponents.size() + 1; // Last place or indicates withdrawal/cancellation
        } else {
            finalPlacement = getPlayerPlacement();
        }
        return finalPlacement;
    }

    /**
     * Awards prize money to the player based on their final placement in the race.
     * Prize money is added to the total money earned.
     * @param gameEnvironment The game environment to update the player's balance.
     */
    public void awardPrizeMoney(GameEnvironment gameEnvironment) {
        int prize;
        int placement = getPlayerPlacement();

        // Assuming prize money depends on placement for the current race's course
        prize = switch (placement) {
            case 1 -> race.getCourse().getPrizes().getFirstPlacePrize();
            case 2 -> race.getCourse().getPrizes().getSecondPlacePrize();
            case 3 -> race.getCourse().getPrizes().getThirdPlacePrize();
            default -> 0;
        };
        gameEnvironment.setBalance(gameEnvironment.getBalance() + prize);
        moneyEarned += prize; // Accumulate total earnings from race
    }

    /**
     * Generates the final placement text for the race results screen.
     * This method encapsulates the complex string formatting logic.
     * @return A formatted string describing the player's race outcome.
     */
    public String getFinalPlacementText() {
        if (Objects.equals(finishReason, "Weather has cancelled the race!")) {
            return "Race Cancelled Due to Weather";
        } else if (Objects.equals(finishReason, "Out of fuel!")) {
            return "Ran out of fuel!";
        } else if (Objects.equals(finishReason, "Time ran out!")) {
            return "Time ran out!";
        } else if (Objects.equals(finishReason, "Car broke down! You withdrew from the race.")) {
            return "Car broke down! You withdrew from the race."; // Specific text for withdrawal
        } else if (Objects.equals(finishReason, "Finished the race!")) {
            int placement = getPlayerPlacement();
            return switch (placement) {
                case 1 -> "🏆 You finished 1st!";
                case 2 -> "🥈 You finished 2nd!";
                case 3 -> "🥉 You finished 3rd!";
                default -> "You finished " + placement + "th.";
            };
        }
        return "Race Over";
    }


    /**
     * Checks if the race has concluded (either by finishing, withdrawal, or cancellation).
     * @return {@code true} if the race is finished, {@code false} otherwise.
     */
    public boolean isRaceFinished() {
        return !isRacing;
    }

    /**
     * Checks if the race was cancelled (e.g., due to severe weather).
     * @return {@code true} if the race was cancelled, {@code false} otherwise.
     */
    public boolean isRaceCancelled() {
        return raceCancelled;
    }

    // --- Getters for UI to read state ---

    /**
     * Gets the player's current distance traveled in the race.
     * @return The player's distance.
     */
    public double getPlayerDistance() {
        return playerDistance;
    }

    /**
     * Gets the current fuel level of the player's car.
     * @return The fuel level as a fraction (0.0 to 1.0).
     */
    public double getFuelLevel() {
        return fuelLevel;
    }

    /**
     * Gets the list of opponent cars.
     * @return A list of {@link OpponentCar} objects.
     */
    public List<OpponentCar> getOpponents() {
        return opponents;
    }

    /**
     * Gets the current event affecting the player.
     * @return The {@link RaceEvent} object, or null if no event is active.
     */
    public RaceEvent getCurrentEvent() {
        return currentEvent;
    }

    /**
     * Checks if the player is currently in a waiting state due to an event.
     * @return {@code true} if waiting, {@code false} otherwise.
     */
    public boolean isWaiting() {
        return isWaiting;
    }

    /**
     * Gets the total duration of the race in seconds.
     * @return The race duration.
     */
    public double getRaceDurationSeconds() {
        return raceDurationSeconds;
    }

    /**
     * Gets the time elapsed in the race in seconds.
     * @return The elapsed time.
     */
    public double getTimeElapsedSeconds() {
        return timeElapsedSeconds;
    }

    /**
     * Gets the reason the race finished.
     * @return A string describing the finish reason.
     */
    public String getFinishReason() {
        return finishReason;
    }

    /**
     * Gets the total money earned by the player in the race, including event profits and prize money.
     * @return The total money earned.
     */
    public int getMoneyEarned() {
        return moneyEarned;
    }

    /**
     * Refuels the player's car to full.
     */
    private void refuel() {
        fuelLevel = 1.0;
    }

    /**
     * Sets the waiting state for the player.
     * @param waiting True to set waiting, false to clear.
     * @param ticks The number of ticks to wait if setting to waiting.
     */
    public void setWaiting(boolean waiting, int ticks) {
        this.isWaiting = waiting;
        this.waitTicksRemaining = ticks;
    }

    // Existing methods like deductEntryFee, calculateFuelStopDistances, etc.
    // should remain in RaceManager or RaceCalculations as appropriate.

    /**
     * Deducts the entry fee for the current race from the game environment balance.
     * @param gameEnvironment The {@link GameEnvironment} to update the balance.
     */
    public void deductEntryFee(GameEnvironment gameEnvironment) {
        int entryFee = race.getCourse().getEntryFee();
        gameEnvironment.setBalance(gameEnvironment.getBalance() - entryFee);
    }

    /**
     * Gets the current {@link Race} object being managed.
     * @return The {@link Race} instance.
     */
    public Race getRace() {
        return race;
    }

    /**
     * Checks if the race simulation is currently ongoing.
     * @return {@code true} if the race is active, {@code false} otherwise.
     */
    public boolean isRacing() {
        return isRacing;
    }

    /**
     * Gets the player's car participating in this race.
     * @return The {@link Car} object representing the player's car.
     */
    public Car getPlayerCar() {
        return playerCar;
    }

    /**
     * Checks if the player has officially finished the race (either by reaching the end, running out of fuel, or time).
     * This flag is set by {@link #playerFinished()} or {@link #finishRace(String)} under relevant conditions.
     * @return {@code true} if the player has finished, {@code false} otherwise.
     */
    public boolean hasPlayerFinished() {
        return playerFinished;
    }

    /**
     * Gets the tick count at which the player officially finished the race.
     * This value is set when {@link #playerFinished()} is called or when the race ends due to time/fuel
     * and the player is marked as finished.
     * @return The tick count when the player finished, or -1 if the player has not yet finished.
     */
    public int getPlayerFinishTick() {
        return playerFinishTick;
    }
}
