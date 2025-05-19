package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

public class TuningPartTest {

    private TuningPart tuningPart;
    private final String name = "Test Part";
    private final int price = 300;
    private final String stat = "Speed";
    private final double boost = 1.25;

    @BeforeEach
    void setUp() {
        tuningPart = new TuningPart(name, price, stat, boost);
    }

    @Test
    void testTuningPartCreation() {
        assertEquals(name, tuningPart.getName());
        assertEquals(price, tuningPart.getPrice());
        assertEquals(stat, tuningPart.getStat());
        assertEquals(boost, tuningPart.getBoost());
    }

    @Test
    void testGetStat() {
        assertEquals(stat, tuningPart.getStat());
    }

    @Test
    void testGetBoost() {
        assertEquals(boost, tuningPart.getBoost());
    }


    @Test
    void testSetNameFromPurchasable() {
        assertEquals(name, tuningPart.getName());
        tuningPart.setName("New Part Name");
        assertEquals("New Part Name", tuningPart.getName());
    }

    @Test
    void testGetPriceFromPurchasable() {
        assertEquals(price, tuningPart.getPrice());
    }

    @Test
    void testConstructorWithDifferentValues() {
        TuningPart otherPart = new TuningPart("Performance Chip", 750, "Power", 1.5);
        assertEquals("Performance Chip", otherPart.getName());
        assertEquals(750, otherPart.getPrice());
        assertEquals("Power", otherPart.getStat());
        assertEquals(1.5, otherPart.getBoost());
    }
}