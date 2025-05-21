package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Difficulty;

import static org.junit.jupiter.api.Assertions.*;

public class DifficultyTest {

    @Test
    void testEasyDifficultyAttributes() {
        Difficulty easy = Difficulty.EASY;
        assertEquals(1000, easy.getStartBudget());
        assertEquals(0.6, easy.getBreakdownMultiplier());

    }

    @Test
    void testMediumDifficultyAttributes() {
        Difficulty medium = Difficulty.MEDIUM;
        assertEquals(800, medium.getStartBudget());
        assertEquals(0.4, medium.getBreakdownMultiplier());
    }

    @Test
    void testHardDifficultyAttributes() {
        Difficulty hard = Difficulty.HARD;
        assertEquals(600, hard.getStartBudget());
        assertEquals(0.3, hard.getBreakdownMultiplier());
    }

    @Test
    void testGetAllDifficulties() {
        Difficulty[] difficulties = Difficulty.values();
        assertEquals(3, difficulties.length);
        assertSame(Difficulty.EASY, Difficulty.valueOf("EASY"));
        assertSame(Difficulty.MEDIUM, Difficulty.valueOf("MEDIUM"));
        assertSame(Difficulty.HARD, Difficulty.valueOf("HARD"));
    }
}