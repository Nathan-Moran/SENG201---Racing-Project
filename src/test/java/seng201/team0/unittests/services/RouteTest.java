package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.OpponentCar;
import seng201.team0.models.Route;
import seng201.team0.models.RouteAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    @Test
    void desertDriftRouteHasCorrectProperties() {
        Route route = Route.DESERT_DRIFT;
        assertEquals("Desert Drift", route.getRouteName()); 
        assertNotNull(route.getAttributes()); 
        assertEquals(0.5, route.getAttributes().getSpeedAdvantage(), 0.001); 
        assertEquals(1.5, route.getAttributes().getHandlingAdvantage(), 0.001); 
        assertEquals(1.2, route.getAttributes().getReliabilityAdvantage(), 0.001); 
        assertEquals(20, route.getLength()); 
        assertEquals(2, route.getFuelStops());
        assertEquals(15, route.getRaceDuration(), 0.001); 
    }

    @Test
    void cityTrafficRouteHasCorrectProperties() {
        Route route = Route.CITY_TRAFFIC;
        assertEquals("City Cut Line", route.getRouteName());
        assertNotNull(route.getAttributes()); 
        assertEquals(0.4, route.getAttributes().getSpeedAdvantage(), 0.001); 
        assertEquals(1.0, route.getAttributes().getHandlingAdvantage(), 0.001); 
        assertEquals(1.5, route.getAttributes().getReliabilityAdvantage(), 0.001); 
        assertEquals(15, route.getLength()); 
        assertEquals(2, route.getFuelStops()); 
        assertEquals(10, route.getRaceDuration(), 0.001); 
    }


    @Test
    void generateOpponentsCreatesCorrectNumberOfOpponents() {
        Route route = Route.DESERT_LONG;
        int opponentCount = 3;
        List<OpponentCar> opponents = route.generateOpponents(opponentCount); 
        assertEquals(opponentCount, opponents.size()); 
    }

    @Test
    void generateOpponentsCreatesOpponentsWithIncrementingSpeed() {
        Route route = Route.MOUNTAIN_STEEP;
        int opponentCount = 4;
        List<OpponentCar> opponents = route.generateOpponents(opponentCount); 

        assertNotNull(opponents); 
        assertEquals(opponentCount, opponents.size()); 

        double baseSpeed = 0.9;
        for (int i = 0; i < opponentCount; i++) {
            double expectedSpeed = baseSpeed + (i * 0.1);
            assertEquals(expectedSpeed, opponents.get(i).getSpeed(), 0.001, "Opponent " + i + " speed mismatch"); 
        }
    }

    @Test
    void generateOpponentsWithZeroCount() {
        Route route = Route.COUNTRY_STRAIGHT; 
        List<OpponentCar> opponents = route.generateOpponents(0); 
        assertNotNull(opponents); 
        assertTrue(opponents.isEmpty()); 
    }


    @Test
    void getRouteNameReturnsCorrectName() {
        assertEquals("Desert Drift", Route.DESERT_DRIFT.getRouteName()); 
    }

    @Test
    void getAttributesReturnsCorrectAttributes() {
        RouteAttributes expectedAttributes = new RouteAttributes(0.5, 1.5, 1.2);
        RouteAttributes actualAttributes = Route.DESERT_DRIFT.getAttributes(); 
        assertEquals(expectedAttributes.getSpeedAdvantage(), actualAttributes.getSpeedAdvantage(), 0.001); 
        assertEquals(expectedAttributes.getHandlingAdvantage(), actualAttributes.getHandlingAdvantage(), 0.001); 
        assertEquals(expectedAttributes.getReliabilityAdvantage(), actualAttributes.getReliabilityAdvantage(), 0.001); 
    }

    @Test
    void getLengthReturnsCorrectLength() {
        assertEquals(20, Route.DESERT_DRIFT.getLength()); 
        assertEquals(50, Route.COUNTRY_STRAIGHT.getLength()); 
    }

    @Test
    void getFuelStopsReturnsCorrectNumber() {
        assertEquals(2, Route.DESERT_DRIFT.getFuelStops()); 
        assertEquals(4, Route.COUNTRY_STRAIGHT.getFuelStops()); 
    }

    @Test
    void getRaceDurationReturnsCorrectDuration() {
        assertEquals(15, Route.DESERT_DRIFT.getRaceDuration(), 0.001); 
        assertEquals(20, Route.COUNTRY_STRAIGHT.getRaceDuration(), 0.001); 
    }

    @Test
    void ensureAllEnumValuesAreCovered() {
        assertEquals(8, Route.values().length);
    }
}