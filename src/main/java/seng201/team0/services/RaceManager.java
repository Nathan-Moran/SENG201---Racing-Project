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



    public RaceManager(Race race, Car playerCar, List<OpponentCar> opponents, double speed, double fuelConsumptionRate) {
        this.race = race;
        this.playerCar = playerCar;
        this.opponents = opponents;
        this.speed = speed;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.fuelStopDistances = RaceCalculations.calculateFuelStopDistances(race.getRoute().getLength(), race.getRoute().getFuelStops());
        this.eventTriggerDistance = RaceCalculations.calculateEventTriggerDistance(race.getRoute().getLength());
        this.breakdownTriggerDistance = RaceCalculations.calculateBreakdownTriggerDistance(race.getRoute().getLength());
    }

    public void advanceRaceTick() {
        if (!isRacing) return;
        tickCount++;
        if (isWaiting) {
            waitTicksRemaining--;
            if (waitTicksRemaining <= 0) {
                isWaiting = false;
            }
            return; // Don't advance player during wait
        }
        playerDistance += speed;
        fuelLevel -= fuelConsumptionRate;
        if (fuelLevel <= 0) {
            fuelLevel = 0;
        }

        updateOpponentDistances();
        if (playerDistance >= eventTriggerDistance && !eventDone) {
            maybeTriggerRandomEvent();
        }
        if (playerDistance >= breakdownTriggerDistance && !breakdownDone) {
            maybeTriggerBreakdownEvent();
        }
        if (nextFuelStopIndex < fuelStopDistances.size() && playerDistance >= fuelStopDistances.get(nextFuelStopIndex)) {
            isWaiting = true;
            waitTicksRemaining = INDEFINITE_WAIT;
            currentEvent = new RaceEvent(RaceEventType.FUEL_STOP);
            nextFuelStopIndex++;
        }
        if (playerDistance >= race.getRoute().getLength()) {
            finishRace();
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
        double reliability = RaceCalculations.calculateEffectiveReliability(playerCar, race.getRoute()); // For example, 0.85 means 85% reliable
        double breakdownChance = (1.0 - reliability) * 100; // More reliable → lower chance
        int roll = random.nextInt(100);
        if (roll < 50) {
            currentEvent = new RaceEvent(RaceEventType.BREAKDOWN);
            isWaiting = true;
            waitTicksRemaining = INDEFINITE_WAIT;
        }
        breakdownDone = true;

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

    private void finishRace() {
        isRacing = false;
        if (fuelLevel <= 0) {
            finishReason = "Out of fuel!";
        } else {
            finishReason = "Finished the race!";
        }
        if (!playerFinished) {
            playerFinished = true;
            playerFinishTick = tickCount;
        }
    }

    public boolean isRaceFinished() {
        return !isRacing;
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
        int placement = getPlayerPlacement();
        CoursePrizes prizes = race.getCourse().getPrizes();

        return switch (placement) {
            case 1 -> prizes.getFirstPlacePrize();
            case 2 -> prizes.getSecondPlacePrize();
            case 3 -> prizes.getThirdPlacePrize();
            default -> 0;
        };
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

    public int getTickCount() {
        return tickCount;
    }

    public void refuel() {
        fuelLevel = 1.0;
    }
}
