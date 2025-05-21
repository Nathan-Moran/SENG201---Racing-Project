package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private final int length;
    private final List<Race> races;
    private int currentRaceIndex = 0;


    public Season(int length, List<Race> races) {
        this.length = length;
        this.races = races != null ? new ArrayList<>(races) : new ArrayList<>();
    }

    public void addRace(Course course, Route route, List<OpponentCar> opponents, double raceDuration, Difficulty difficulty) {
        if (races.size() < length) {
            races.add(new Race(course, route, difficulty));
        } else {
            System.out.println("Season is full.");
        }
    }

    public void advanceToNextRace() {
        if (currentRaceIndex < races.size() - 1) {
            currentRaceIndex++;
        } else {
            System.out.println("All races completed.");
        }
    }

    public List<Race> getRaces() {
        return races;
    }

    public int getLength() {
        return length;
    }

    public int getCurrentRaceIndex() {
        return currentRaceIndex;
    }

    public Race getCurrentRace() {
        if (currentRaceIndex < races.size()) {
            return races.get(currentRaceIndex);
        }
        return null;
    }

}