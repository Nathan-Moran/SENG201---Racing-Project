package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

class TuningPartTest {

    @Test
    void constructorSetsPropertiesCorrectly() {
        String name = "Turbo";
        int price = 1500;
        String stat = "💨";
        double boost = 1.3;
        TuningPart part = new TuningPart(name, price, stat, boost); 

        assertEquals(name, part.getName()); 
        assertEquals(price, part.getPrice()); 
        assertEquals(stat, part.getStat()); 
        assertEquals(boost, part.getBoost(), 0.001); 
    }

    @Test
    void getStatReturnsCorrectStat() {
        TuningPart part = new TuningPart("Wheels", 800, "🎮", 1.15); 
        assertEquals("🎮", part.getStat()); 
    }

    @Test
    void getBoostReturnsCorrectBoost() {
        TuningPart part = new TuningPart("ECU Tune", 1200, "💨", 1.25); 
        assertEquals(1.25, part.getBoost(), 0.001); 
    }
}