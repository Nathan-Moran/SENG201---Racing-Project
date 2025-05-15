package seng201.team0.services;

import seng201.team0.models.*;

import java.util.List;

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

    public RaceManager(Race race, Car playerCar, List<OpponentCar> opponents, double speed, double fuelConsumptionRate) {
        this.race = race;
        this.playerCar = playerCar;
        this.opponents = opponents;
        this.speed = speed;
        this.fuelConsumptionRate = fuelConsumptionRate;
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
        if (fuelLevel < 0) fuelLevel = 0;

        if (playerDistance >= race.getRoute().getLength() || fuelLevel <= 0) {
            finishRace();
        }
    }

    private void finishRace() {
        isRacing = false;
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
