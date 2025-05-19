package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RouteAttributes;

import static org.junit.jupiter.api.Assertions.*;

public class RouteAttributesTest {

    @Test
    void testRouteAttributesCreationAndGetters() {
        double speedAdv = 1.2;
        double handlingAdv = 0.8;
        double reliabilityAdv = 1.0;
        RouteAttributes attributes = new RouteAttributes(speedAdv, handlingAdv, reliabilityAdv);

        assertEquals(speedAdv, attributes.getSpeedAdvantage());
        assertEquals(handlingAdv, attributes.getHandlingAdvantage());
        assertEquals(reliabilityAdv, attributes.getReliabilityAdvantage());
    }

    @Test
    void testRouteAttributesWithZeroValues() {
        RouteAttributes attributes = new RouteAttributes(0.0, 0.0, 0.0);
        assertEquals(0.0, attributes.getSpeedAdvantage());
        assertEquals(0.0, attributes.getHandlingAdvantage());
        assertEquals(0.0, attributes.getReliabilityAdvantage());
    }

    @Test
    void testRouteAttributesWithVariedValues() {
        RouteAttributes attributes = new RouteAttributes(1.5, 1.0, 0.5);
        assertEquals(1.5, attributes.getSpeedAdvantage());
        assertEquals(1.0, attributes.getHandlingAdvantage());
        assertEquals(0.5, attributes.getReliabilityAdvantage());
    }
}