package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RaceEvent;
import seng201.team0.models.RaceEventType;

import static org.junit.jupiter.api.Assertions.*;

public class RaceEventTest {

    @Test
    void testRaceEventCreationAndGetType() {
        RaceEventType eventType = RaceEventType.FUEL_STOP;
        RaceEvent raceEvent = new RaceEvent(eventType);
        assertEquals(eventType, raceEvent.getType());
    }

    @Test
    void testRaceEventWithBreakdownType() {
        RaceEventType eventType = RaceEventType.BREAKDOWN;
        RaceEvent raceEvent = new RaceEvent(eventType);
        assertEquals(eventType, raceEvent.getType());
    }

    @Test
    void testRaceEventWithTravelerType() {
        RaceEventType eventType = RaceEventType.TRAVELER;
        RaceEvent raceEvent = new RaceEvent(eventType);
        assertEquals(eventType, raceEvent.getType());
    }

    @Test
    void testRaceEventWithWeatherType() {
        RaceEventType eventType = RaceEventType.WEATHER;
        RaceEvent raceEvent = new RaceEvent(eventType);
        assertEquals(eventType, raceEvent.getType());
    }

    @Test
    void testRaceEventWithNullType() {
        RaceEvent raceEvent = new RaceEvent(null);
        assertNull(raceEvent.getType());
    }
}