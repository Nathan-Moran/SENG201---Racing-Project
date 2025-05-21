package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single season of the game, consisting of a fixed number of races.
 * It manages the progression through these races.
 */
public class Season {
    /**
     * The total number of races in this season.
     */
    private final int length;
    /**
     * A list of {@link Race} objects that make up the season.
     */
    private final List<Race> races;
    /**
     * The index of the current race being played or about to be played in the season.
     * Starts at 0 for the first race.
     */
    private int currentRaceIndex = 0;


    /**
     * Constructs a new Season instance.
     *
     * @param length The total number of races planned for this season.
     * @param races An initial list of {@link Race} objects to populate the season.
     * If null, an empty list is used.
     */
    public Season(int length, List<Race> races) {
        this.length = length;
        this.races = races != null ? new ArrayList<>(races) : new ArrayList<>();
    }

    /**
     * Adds a new race to the season, provided the season has not reached its maximum length.
     *
     * @param course The {@link Course} for the new race.
     * @param route The {@link Route} for the new race.
     * @param opponents A list of {@link OpponentCar} objects for the new race.
     * @param raceDuration The estimated duration of the race (though this parameter might not be directly used here if Race itself calculates it).
     * @param difficulty The {@link Difficulty} setting for the race.
     */
    public void addRace(Course course, Route route, List<OpponentCar> opponents, double raceDuration, Difficulty difficulty) {
        if (races.size() < length) {
            races.add(new Race(course, route, difficulty));
        } else {
            System.out.println("Season is full. Cannot add more races.");
        }
    }

    /**
     * Advances the season to the next race.
     * If all races are completed, a message is printed to the console.
     */
    public void advanceToNextRace() {
        if (currentRaceIndex < races.size() - 1) {
            currentRaceIndex++;
        } else {
            System.out.println("All races completed for the season.");
        }
    }

    /**
     * Gets the full list of races planned for this season.
     * @return A {@link List} of {@link Race} objects.
     */
    public List<Race> getRaces() {
        return races;
    }

    /**
     * Gets the total declared length (number of races) of this season.
     * @return The maximum number of races in the season.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the index of the currently active race in the season.
     * @return The zero-based index of the current race.
     */
    public int getCurrentRaceIndex() {
        return currentRaceIndex;
    }

    /**
     * Gets the current {@link Race} object based on {@link #currentRaceIndex}.
     * Returns null if the index is out of bounds (e.g., all races completed).
     * @return The current Race, or null if no more races are available.
     */
    public Race getCurrentRace() {
        if (currentRaceIndex < races.size()) {
            return races.get(currentRaceIndex);
        }
        return null;
    }

}