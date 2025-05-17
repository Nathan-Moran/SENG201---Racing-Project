package seng201.team0.models;

public class LeaderboardEntry {
    private final String name;
    private final double distance;

    public LeaderboardEntry(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }
}
