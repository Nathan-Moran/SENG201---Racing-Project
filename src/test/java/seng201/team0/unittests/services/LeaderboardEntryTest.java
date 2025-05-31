package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.LeaderboardEntry;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardEntryTest {

    @Test
    void constructorSetsPropertiesCorrectly() {
        String name = "Player1";
        double distance = 150.75;
        LeaderboardEntry entry = new LeaderboardEntry(name, distance); 

        assertEquals(name, entry.getName()); 
        assertEquals(distance, entry.getDistance(), 0.001); 
    }

    @Test
    void constructorWithZeroDistance() {
        String name = "Player2";
        double distance = 0.0;
        LeaderboardEntry entry = new LeaderboardEntry(name, distance); 

        assertEquals(name, entry.getName()); 
        assertEquals(distance, entry.getDistance(), 0.001); 
    }

    @Test
    void getNameReturnsCorrectName() {
        LeaderboardEntry entry = new LeaderboardEntry("TestPlayer", 100.0); 
        assertEquals("TestPlayer", entry.getName()); 
    }

    @Test
    void getDistanceReturnsCorrectDistance() {
        LeaderboardEntry entry = new LeaderboardEntry("TestPlayer", 123.45); 
        assertEquals(123.45, entry.getDistance(), 0.001); 
    }
}