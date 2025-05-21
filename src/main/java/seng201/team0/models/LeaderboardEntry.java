package seng201.team0.models;

/**
 * Represents a single entry on a race leaderboard, storing the name of the participant
 * and the distance they covered during the race.
 */
public class LeaderboardEntry {
    /**
     * The name of the participant (e.g., player's name or opponent's name).
     */
    private final String name;
    /**
     * The total distance covered by the participant in the race, in kilometers.
     */
    private final double distance;

    /**
     * Constructs a new LeaderboardEntry.
     * @param name The name of the participant.
     * @param distance The distance covered by the participant.
     */
    public LeaderboardEntry(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }

    /**
     * Gets the name of the participant for this leaderboard entry.
     * @return The participant's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the distance covered by the participant for this leaderboard entry.
     * @return The distance covered, in kilometers.
     */
    public double getDistance() {
        return distance;
    }
}