package seng201.team0.services;

import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceManager {
    private Race race;
    private Car playerCar;
    private List<OpponentCar> opponents;
    private double playerDistance = 0;
    private double fuelLevel = 1.0;
    private int tickCount = 0;
    private boolean isRacing = true;
    private boolean isWaiting = false;
    private int waitTicksRemaining = 0;
    private double speed;
    private double fuelConsumptionRate;
    private double eventTriggerDistance;
    private double breakdownTriggerDistance;
    private final Random random = new Random();
    private RaceEvent currentEvent = null;
    private boolean eventDone = false;
    private boolean breakdownDone = false;
    private List<Double> fuelStopDistances;
    private int nextFuelStopIndex = 0;
    private static final int INDEFINITE_WAIT = 9999;
    private String finishReason = "";
    private int playerFinishTick = -1;
    private boolean playerFinished = false;
    private double raceDurationSeconds;
    private double timeElapsedSeconds = 0;
    private boolean raceCancelled = false;

    private static final int REPAIR_WAIT_TICKS = 2;
    private static final int TRAVELER_WAIT_TICKS = 2;
    private static final int REFUEL_WAIT_TICKS = 1;
    private static final int REPAIR_COST = 250;
    private static final int TRAVELER_PROFIT = 250;


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
    }

    public void advanceRaceTick() {
        if (!isRacing || raceCancelled) return;
        tickCount++;
        updateOpponentDistances();
        if (isWaiting) {
            waitTicksRemaining--;
            if (waitTicksRemaining <= 0) {
                isWaiting = false;
            }
            return; // Don't advance player during wait
        }
        playerDistance += speed;
        fuelLevel -= fuelConsumptionRate;
        timeElapsedSeconds += (1.0 / 2);
        if (fuelLevel <= 0) {
            fuelLevel = 0;
            finishRace("Out of fuel!");
        }
        if (timeElapsedSeconds >= raceDurationSeconds && !playerFinished) {
            finishRace("Time ran out!");
        }
        if (playerDistance >= eventTriggerDistance && !eventDone) {
            maybeTriggerRandomEvent();
            eventDone = true;
        }
        if (playerDistance >= breakdownTriggerDistance && !breakdownDone) {
            maybeTriggerBreakdownEvent();
            breakdownDone = true;
        }
        if (nextFuelStopIndex < fuelStopDistances.size() && playerDistance >= fuelStopDistances.get(nextFuelStopIndex)) {
            isWaiting = true;
            waitTicksRemaining = INDEFINITE_WAIT;
            currentEvent = new RaceEvent(RaceEventType.FUEL_STOP);
            nextFuelStopIndex++;
        }
        if (playerDistance >= race.getRoute().getLength() && !playerFinished) {
            finishRace("Finished the race!");
        }
    }

    public void playerFinished() {
        if (isRacing && !playerFinished && !raceCancelled) {
            playerFinished = true;
            playerFinishTick = tickCount;
            finishRace("Finished the race!");
        }
    }


    private void maybeTriggerRandomEvent() {
        int roll = random.nextInt(100);
        if (roll < 25) {
            currentEvent = new RaceEvent(RaceEventType.TRAVELER);
        } else if (roll < 40) {
            currentEvent = new RaceEvent(RaceEventType.WEATHER);
        } else {
            // No event occurs
            return;
        }
        isWaiting = true;
        waitTicksRemaining = INDEFINITE_WAIT; // stops game till continue
    }

    private void maybeTriggerBreakdownEvent() {
        double reliability = RaceCalculations.calculateEffectiveReliability(playerCar, race.getRoute()); // e.g., 0.85
        double breakdownChance = (1.0 - reliability) * 100; // e.g., 15.0
        int roll = random.nextInt(100); // random number between 0 and 99 inclusive
        if (roll < breakdownChance) {   // triggers breakdown with probability = breakdownChance%
            currentEvent = new RaceEvent(RaceEventType.BREAKDOWN);
            isWaiting = true;
            waitTicksRemaining = INDEFINITE_WAIT;
        }
    }

    public void clearCurrentEvent() {
        currentEvent = null;
    }

    public void updateOpponentDistances() {
        for (OpponentCar opponent : opponents) {
            opponent.advanceTick();
        }
    }

    public List<LeaderboardEntry> getLeaderboardStandings() {
        List<LeaderboardEntry> standings = new ArrayList<>();

        standings.add(new LeaderboardEntry("Player", playerDistance));
        for (int i = 0; i < opponents.size(); i++) {
            standings.add(new LeaderboardEntry("Opponent " + (i + 1), opponents.get(i).getCurrentDistance()));
        }

        standings.sort((a, b) -> Double.compare(b.getDistance(), a.getDistance())); // Sort descending

        return standings;
    }

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

    public int getPlayerPlacement() {
        List<LeaderboardEntry> standings = getLeaderboardStandings();

        for (int i = 0; i < standings.size(); i++) {
            if (standings.get(i).getName().equals("Player")) {
                return i + 1; // +1 to make it 1-based (1st place, 2nd place, etc.)
            }
        }
        return -1; // Should not happen unless "Player" is missing
    }

    public void playerWithdrawDueToBreakdown() {
        isRacing = false;
        playerFinished = true;
        finishReason = "Car broke down! You withdrew from the race.";
    }

    public void finishRace(String reason) {
        if (isRacing && !raceCancelled) {
            isRacing = false;
            finishReason = reason;
            if (!playerFinished && reason.equals("Finished the race!")) {
                playerFinished = true;
                playerFinishTick = tickCount;
            } else if (reason.equals("Time ran out!") && !playerFinished) {
                playerFinished = true; // Mark as finished due to time
                playerFinishTick = tickCount; // Record the tick count when time ran out
            }
        }
    }

    public void handleFuelStop(boolean refuel) {
        if (refuel) {
            refuel();
            setWaiting(true, REFUEL_WAIT_TICKS);
        } else {
            setWaiting(false, 0);
        }
        clearCurrentEvent();
    }

    public void handleRepair(boolean pay, GameEnvironment gameEnvironment) {
        if (pay) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() - REPAIR_COST);
            setWaiting(true, REPAIR_WAIT_TICKS);
        } else {
            playerWithdrawDueToBreakdown();
        }
        clearCurrentEvent();
    }

    public void handleTraveler(boolean pickUp, GameEnvironment gameEnvironment) {
        if (pickUp) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() + TRAVELER_PROFIT);
            setWaiting(true, TRAVELER_WAIT_TICKS);
        } else {
            setWaiting(false, 0);
        }
        clearCurrentEvent();
    }

    public void handleWeather(GameEnvironment gameEnvironment) {
        raceCancelled = true;
        isRacing = false;
        playerFinished = true;
        gameEnvironment.setRacesRemaining(gameEnvironment.getRacesRemaining() + 1);
        finishReason = "Weather has cancelled the race!";
        // No balance change or races remaining decrement here, handled in GUI
    }


    public boolean isRaceFinished() {
        return !isRacing;
    }

    public boolean isRaceCancelled() {
        return raceCancelled;
    }

    // Getters and setters for UI to read state

    public double getPlayerDistance() {
        return playerDistance;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public RaceEvent getCurrentEvent() {
        return currentEvent;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public int getPlayerFinishTick() {
        return playerFinishTick;
    }


    public int getMoneyEarned() {
        if (raceCancelled) {
            return 0; // No earnings if race is cancelled
        }
        int placement = getPlayerPlacement();
        CoursePrizes prizes = race.getCourse().getPrizes();

        return switch (placement) {
            case 1 -> prizes.getFirstPlacePrize();
            case 2 -> prizes.getSecondPlacePrize();
            case 3 -> prizes.getThirdPlacePrize();
            default -> 0;
        };
    }

    public void deductEntryFee(GameEnvironment gameEnvironment) {
        if (!raceCancelled) {
            int entryFee = race.getCourse().getEntryFee();
            gameEnvironment.setBalance(gameEnvironment.getBalance() - entryFee);
        }
    }

    public int awardPrizeMoney(GameEnvironment gameEnvironment) {
        if (playerFinished && !raceCancelled) {
            int prize = getMoneyEarned();
            gameEnvironment.setBalance(gameEnvironment.getBalance() + prize);
            return prize;
        }
        return 0;
    }

    public boolean hasPlayerFinished() {
        return playerFinished;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting, int ticks) {
        this.isWaiting = waiting;
        this.waitTicksRemaining = ticks;
    }

    public List<OpponentCar> getOpponents() {
        return opponents;
    }

    public Race getRace() {
        return race;
    }

    public Car getPlayerCar() {
        return playerCar;
    }

    public double getRaceDurationSeconds() {
        return raceDurationSeconds;
    }

    public double getTimeElapsedSeconds() {
        return timeElapsedSeconds;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void refuel() {
        fuelLevel = 1.0;
    }

    public boolean isRacing() {
        return isRacing;
    }
}