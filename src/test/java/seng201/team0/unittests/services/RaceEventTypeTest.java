package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RaceEventType;

import static org.junit.jupiter.api.Assertions.*;

public class RaceEventTypeTest {

    @Test
    void testEnumValuesExist() {
        assertNotNull(RaceEventType.valueOf("FUEL_STOP"));
        assertNotNull(RaceEventType.valueOf("BREAKDOWN"));
        assertNotNull(RaceEventType.valueOf("TRAVELER"));
        assertNotNull(RaceEventType.valueOf("WEATHER"));
    }

    @Test
    void testNumberOfEnumValues() {
        assertEquals(4, RaceEventType.values().length);
    }

    @Test
    void testEnumToString() {
        assertEquals("FUEL_STOP", RaceEventType.FUEL_STOP.toString());
        assertEquals("BREAKDOWN", RaceEventType.BREAKDOWN.toString());
        assertEquals("TRAVELER", RaceEventType.TRAVELER.toString());
        assertEquals("WEATHER", RaceEventType.WEATHER.toString());
    }

    @Test
    void testEnumOrdinal() {
        assertEquals(0, RaceEventType.FUEL_STOP.ordinal());
        assertEquals(1, RaceEventType.BREAKDOWN.ordinal());
        assertEquals(2, RaceEventType.TRAVELER.ordinal());
        assertEquals(3, RaceEventType.WEATHER.ordinal());
    }
}