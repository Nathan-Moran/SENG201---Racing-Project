package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.OpponentCar;
import seng201.team0.models.Route;
import seng201.team0.models.RouteAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {

    @Test
    void testDesertDriftAttributes() {
        Route route = Route.DESERT_DRIFT;
        assertEquals("Desert Drift", route.getRouteName());
        assertNotNull(route.getAttributes());
        assertEquals(0.5, route.getAttributes().getSpeedAdvantage());
        assertEquals(1.5, route.getAttributes().getHandlingAdvantage());
        assertEquals(1.2, route.getAttributes().getReliabilityAdvantage());
        assertEquals(20, route.getLength());
        assertEquals(2, route.getFuelStops());
    }

    @Test
    void testCityTrafficAttributes() {
        Route route = Route.CITY_TRAFFIC;
        assertEquals("City Cut Line", route.getRouteName());
        assertNotNull(route.getAttributes());
        assertEquals(0.4, route.getAttributes().getSpeedAdvantage());
        assertEquals(1.0, route.getAttributes().getHandlingAdvantage());
        assertEquals(1.5, route.getAttributes().getReliabilityAdvantage());
        assertEquals(15, route.getLength());
        assertEquals(2, route.getFuelStops());
    }

    @Test
    void testGenerateOpponents() {
        Route route = Route.DESERT_LONG; // opponentSpeed = 0.9
        int count = 3;
        List<OpponentCar> opponents = route.generateOpponents(count);

        assertEquals(count, opponents.size());
        assertNotNull(opponents.get(0));
        assertEquals(0.9, opponents.get(0).getSpeed(), 0.001);
        assertNotNull(opponents.get(1));
        assertEquals(0.9 + 0.1, opponents.get(1).getSpeed(), 0.001);
        assertNotNull(opponents.get(2));
        assertEquals(0.9 + 0.2, opponents.get(2).getSpeed(), 0.001);
    }

    @Test
    void testGenerateZeroOpponents() {
        Route route = Route.MOUNTAIN_STEEP;
        List<OpponentCar> opponents = route.generateOpponents(0);
        assertTrue(opponents.isEmpty());
    }

    @Test
    void testGenerateOneOpponent() {
        Route route = Route.COUNTRY_STRAIGHT; // opponentSpeed = 1.3
        List<OpponentCar> opponents = route.generateOpponents(1);
        assertEquals(1, opponents.size());
        assertEquals(1.3, opponents.get(0).getSpeed(), 0.001);
    }


    @Test
    void testGetAllRoutes() {
        Route[] routes = Route.values();
        assertEquals(8, routes.length); // Based on the enum definition
        assertSame(Route.DESERT_DRIFT, Route.valueOf("DESERT_DRIFT"));
        // Add more checks if necessary for all enum values
    }

    @Test
    void testRouteAttributesContent() {
        RouteAttributes attributes = Route.MOUNTAIN_CURVES.getAttributes();
        assertEquals(0.8, attributes.getSpeedAdvantage());
        assertEquals(1.3, attributes.getHandlingAdvantage());
        assertEquals(1.2, attributes.getReliabilityAdvantage());
    }
}