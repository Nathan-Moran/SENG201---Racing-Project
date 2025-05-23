package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Difficulty;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyTest {

    @Test
    void easyDifficultyHasCorrectProperties() {
        Difficulty easy = Difficulty.EASY;
        assertEquals(1000, easy.getStartBudget()); //
        assertEquals(0.8, easy.getBreakdownMultiplier(), 0.001); //
    }

    @Test
    void mediumDifficultyHasCorrectProperties() {
        Difficulty medium = Difficulty.MEDIUM;
        assertEquals(750, medium.getStartBudget()); //
        assertEquals(0.9, medium.getBreakdownMultiplier(), 0.001); //
    }

    @Test
    void hardDifficultyHasCorrectProperties() {
        Difficulty hard = Difficulty.HARD;
        assertEquals(500, hard.getStartBudget()); //
        assertEquals(1.0, hard.getBreakdownMultiplier(), 0.001); //
    }

    @Test
    void getStartBudgetReturnsCorrectValue() {
        assertEquals(1000, Difficulty.EASY.getStartBudget()); //
        assertEquals(750, Difficulty.MEDIUM.getStartBudget()); //
        assertEquals(500, Difficulty.HARD.getStartBudget()); //
    }

    @Test
    void getBreakdownMultiplierReturnsCorrectValue() {
        assertEquals(0.8, Difficulty.EASY.getBreakdownMultiplier(), 0.001); //
        assertEquals(0.9, Difficulty.MEDIUM.getBreakdownMultiplier(), 0.001); //
        assertEquals(1.0, Difficulty.HARD.getBreakdownMultiplier(), 0.001); //
    }

    @Test
    void ensureAllEnumValuesAreTested() {
        assertEquals(3, Difficulty.values().length, "Ensure all Difficulty enum values are covered in tests."); //
    }
}