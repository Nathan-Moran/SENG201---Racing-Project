package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RaceEventType;

import static org.junit.jupiter.api.Assertions.*;

class RaceEventTypeTest {

    @Test
    void enumValuesExist() {
        assertNotNull(RaceEventType.valueOf("FUEL_STOP")); 
        assertNotNull(RaceEventType.valueOf("BREAKDOWN")); 
        assertNotNull(RaceEventType.valueOf("TRAVELER")); 
        assertNotNull(RaceEventType.valueOf("WEATHER")); 
    }

    @Test
    void numberOfEnumValuesIsCorrect() {
        assertEquals(4, RaceEventType.values().length); 
    }
}