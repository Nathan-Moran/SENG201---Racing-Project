package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RaceEvent;
import seng201.team0.models.RaceEventType;

import static org.junit.jupiter.api.Assertions.*;

class RaceEventTest {

    @Test
    void constructorSetsTypeCorrectly() {
        RaceEvent event = new RaceEvent(RaceEventType.FUEL_STOP); 
        assertEquals(RaceEventType.FUEL_STOP, event.getType()); 
    }

    @Test
    void getTypeReturnsCorrectType() {
        RaceEvent breakdownEvent = new RaceEvent(RaceEventType.BREAKDOWN); 
        assertEquals(RaceEventType.BREAKDOWN, breakdownEvent.getType()); 

        RaceEvent weatherEvent = new RaceEvent(RaceEventType.WEATHER); 
        assertEquals(RaceEventType.WEATHER, weatherEvent.getType()); 

        RaceEvent travelerEvent = new RaceEvent(RaceEventType.TRAVELER); 
        assertEquals(RaceEventType.TRAVELER, travelerEvent.getType()); 
    }

    @Test
    void checkAllEventTypesCanBeSet() {
        for (RaceEventType eventType : RaceEventType.values()) {
            RaceEvent event = new RaceEvent(eventType); 
            assertEquals(eventType, event.getType(), "Failed for type: " + eventType); 
        }
    }
}