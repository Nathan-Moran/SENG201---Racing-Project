package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.LeaderboardEntry;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardEntryTest {

    @Test
    void testLeaderboardEntryCreationAndGetters() {
        String name = "Player One";
        double distance = 150.75;
        LeaderboardEntry entry = new LeaderboardEntry(name, distance);

        assertEquals(name, entry.getName());
        assertEquals(distance, entry.getDistance());
    }

    @Test
    void testLeaderboardEntryWithZeroDistance() {
        String name = "Player Two";
        double distance = 0.0;
        LeaderboardEntry entry = new LeaderboardEntry(name, distance);

        assertEquals(name, entry.getName());
        assertEquals(distance, entry.getDistance());
    }

    @Test
    void testLeaderboardEntryWithEmptyName() {
        String name = "";
        double distance = 100.0;
        LeaderboardEntry entry = new LeaderboardEntry(name, distance);

        assertEquals(name, entry.getName());
        assertEquals(distance, entry.getDistance());
    }

    @Test
    void testLeaderboardEntryWithNullName() {
        String name = null;
        double distance = 50.5;
        LeaderboardEntry entry = new LeaderboardEntry(name, distance);

        assertNull(entry.getName());
        assertEquals(distance, entry.getDistance());
    }
}