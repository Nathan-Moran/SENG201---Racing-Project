package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RaceEventType;

import static org.junit.jupiter.api.Assertions.*;

class RaceEventTypeTest {

    @Test
    void enumValuesExist() {
        assertNotNull(RaceEventType.valueOf("FUEL_STOP")); //
        assertNotNull(RaceEventType.valueOf("BREAKDOWN")); //
        assertNotNull(RaceEventType.valueOf("TRAVELER")); //
        assertNotNull(RaceEventType.valueOf("WEATHER")); //
    }

    @Test
    void numberOfEnumValuesIsCorrect() {
        assertEquals(4, RaceEventType.values().length); //
    }

    // Enums usually don't have more logic to test unless they have methods or complex constructors.
    // For simple enums like this, value checking is the primary test.
}