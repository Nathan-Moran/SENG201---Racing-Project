package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.RouteAttributes;

import static org.junit.jupiter.api.Assertions.*;

class RouteAttributesTest {

    @Test
    void constructorSetsAdvantagesCorrectly() {
        double speedAdv = 1.2;
        double handlingAdv = 0.9;
        double reliabilityAdv = 1.1;
        RouteAttributes attributes = new RouteAttributes(speedAdv, handlingAdv, reliabilityAdv); 

        assertEquals(speedAdv, attributes.getSpeedAdvantage(), 0.001); 
        assertEquals(handlingAdv, attributes.getHandlingAdvantage(), 0.001); 
        assertEquals(reliabilityAdv, attributes.getReliabilityAdvantage(), 0.001); 
    }

    @Test
    void constructorWithNeutralAdvantages() {
        RouteAttributes attributes = new RouteAttributes(1.0, 1.0, 1.0); 
        assertEquals(1.0, attributes.getSpeedAdvantage(), 0.001); 
        assertEquals(1.0, attributes.getHandlingAdvantage(), 0.001); 
        assertEquals(1.0, attributes.getReliabilityAdvantage(), 0.001); 
    }

    @Test
    void getSpeedAdvantageReturnsCorrectValue() {
        RouteAttributes attributes = new RouteAttributes(1.5, 1.0, 0.8); 
        assertEquals(1.5, attributes.getSpeedAdvantage(), 0.001); 
    }

    @Test
    void getHandlingAdvantageReturnsCorrectValue() {
        RouteAttributes attributes = new RouteAttributes(1.0, 1.3, 0.7); 
        assertEquals(1.3, attributes.getHandlingAdvantage(), 0.001); 
    }

    @Test
    void getReliabilityAdvantageReturnsCorrectValue() {
        RouteAttributes attributes = new RouteAttributes(0.9, 1.0, 1.4); 
        assertEquals(1.4, attributes.getReliabilityAdvantage(), 0.001); 
    }
}