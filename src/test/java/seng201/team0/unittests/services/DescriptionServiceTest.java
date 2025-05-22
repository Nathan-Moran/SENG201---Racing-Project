package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.services.DescriptionService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionServiceTest {

    @Test
    void getCarDescriptionForKnownCar() {
        String expected = "A well-rounded entry point, the Civic offers a good blend of peppy acceleration and commendable dependability, with decent fuel usage. Its cornering ability is adequate for learning the ropes. A solid choice for early events like Desert Drift or for a driver who values a reliable all-rounder.";
        assertEquals(expected, DescriptionService.getCarDescription("Honda Civic")); //
    }

    @Test
    void getCarDescriptionForAnotherKnownCar() {
        String expected = "An exotic masterpiece delivering blistering top speed and almost telepathic handling. The Ferrari 458 is a top-tier performer, capable of dominating almost any route if driven skillfully. High reliability ensures it can take the heat, but its racing pedigree means it consumes fuel quickly. A dream machine for ambitious racers.";
        assertEquals(expected, DescriptionService.getCarDescription("Ferrari")); //
    }

    @Test
    void getCarDescriptionForUnlockedCar() {
        String expected = "Unlocked by conquering the Desert, the Dune Drifter is a true handling specialist. Its modest top speed is more than offset by its incredible agility on loose surfaces, making it dance where others struggle. Good reliability and fuel economy make it a smart choice for technical desert stages.";
        assertEquals(expected, DescriptionService.getCarDescription("Dune Drifter")); //
    }


    @Test
    void getCarDescriptionForUnknownCar() {
        String expectedDefault = "Car has no description";
        assertEquals(expectedDefault, DescriptionService.getCarDescription("Unknown Car Model")); //
    }

    @Test
    void getCarDescriptionWithNullName() {
        String expectedDefault = "Car has no description";
        assertEquals(expectedDefault, DescriptionService.getCarDescription(null)); //
    }

    @Test
    void getTuningPartDescriptionForKnownPart() {
        String expected = "Boosts speed by improving engine combustion with high-octane fuel.";
        assertEquals(expected, DescriptionService.getTuningPartDescription("Ethanol")); //
    }

    @Test
    void getTuningPartDescriptionForAnotherKnownPart() {
        String expected = "Maximizes handling performance with professional-grade slick tires for ultimate track grip.";
        assertEquals(expected, DescriptionService.getTuningPartDescription("RacingWheels")); //
    }

    @Test
    void getTuningPartDescriptionForUnknownPart() {
        String expectedDefault = "Part has no description";
        assertEquals(expectedDefault, DescriptionService.getTuningPartDescription("Unknown Tuning Part")); //
    }

    @Test
    void getTuningPartDescriptionWithNullName() {
        String expectedDefault = "Part has no description";
        assertEquals(expectedDefault, DescriptionService.getTuningPartDescription(null)); //
    }

    @Test
    void staticBlockInitializesMaps() {
        // Test a few entries to ensure the static block ran.
        assertNotEquals("Car has no description", DescriptionService.getCarDescription("Honda Civic")); //
        assertNotEquals("Part has no description", DescriptionService.getTuningPartDescription("Ethanol")); //
        // Count check could be an option if exact number of entries is stable and known
        // For example, if there are exactly 10 car descriptions and 6 part descriptions
        // This would require reflection to access the map sizes or adding methods to get counts.
        // For now, checking a few known entries is sufficient to confirm initialization.
    }

    @Test
    void getCarDescriptionCaseInsensitive() {
        // HashMaps are case-sensitive by default, so this should return the default.
        String expectedDefault = "Car has no description"; //
        assertEquals(expectedDefault, DescriptionService.getCarDescription("honda civic"));
    }

    @Test
    void getTuningPartDescriptionCaseInsensitive() {
        // HashMaps are case-sensitive by default, so this should return the default.
        String expectedDefault = "Part has no description"; //
        assertEquals(expectedDefault, DescriptionService.getTuningPartDescription("ethanol"));
    }

    @Test
    void getCarDescriptionEmptyString() {
        String expectedDefault = "Car has no description"; //
        assertEquals(expectedDefault, DescriptionService.getCarDescription(""));
    }

    @Test
    void getTuningPartDescriptionEmptyString() {
        String expectedDefault = "Part has no description"; //
        assertEquals(expectedDefault, DescriptionService.getTuningPartDescription(""));
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<DescriptionService> constructor = DescriptionService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor should be private.");
        constructor.setAccessible(true);
        assertNotNull(constructor.newInstance(), "Instance should be creatable via reflection for coverage.");
    }
}