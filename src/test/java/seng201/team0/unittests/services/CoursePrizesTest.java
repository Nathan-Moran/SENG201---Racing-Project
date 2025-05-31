package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.CoursePrizes;

import static org.junit.jupiter.api.Assertions.*;

class CoursePrizesTest {

    @Test
    void constructorSetsPrizesCorrectly() {
        CoursePrizes prizes = new CoursePrizes(1000, 500, 250); 
        assertEquals(1000, prizes.getFirstPlacePrize()); 
        assertEquals(500, prizes.getSecondPlacePrize()); 
        assertEquals(250, prizes.getThirdPlacePrize()); 
    }

    @Test
    void constructorSetsPrizesWithZeros() {
        CoursePrizes prizes = new CoursePrizes(1000, 0, 0); 
        assertEquals(1000, prizes.getFirstPlacePrize()); 
        assertEquals(0, prizes.getSecondPlacePrize()); 
        assertEquals(0, prizes.getThirdPlacePrize()); 
    }

    @Test
    void getFirstPlacePrizeReturnsCorrectValue() {
        CoursePrizes prizes = new CoursePrizes(1500, 700, 300); 
        assertEquals(1500, prizes.getFirstPlacePrize()); 
    }

    @Test
    void getSecondPlacePrizeReturnsCorrectValue() {
        CoursePrizes prizes = new CoursePrizes(1500, 700, 300); 
        assertEquals(700, prizes.getSecondPlacePrize()); 
    }

    @Test
    void getThirdPlacePrizeReturnsCorrectValue() {
        CoursePrizes prizes = new CoursePrizes(1500, 700, 300); 
        assertEquals(300, prizes.getThirdPlacePrize()); 
    }

    @Test
    void toStringFormatsCorrectly() {
        CoursePrizes prizes = new CoursePrizes(100, 50, 20); 
        String expectedString = "Prizes -> 1st: $100, 2nd: $50, 3rd: $20"; 
        assertEquals(expectedString, prizes.toString()); 
    }

    @Test
    void toStringWithZeroPrizes() {
        CoursePrizes prizes = new CoursePrizes(500, 0, 0); 
        String expectedString = "Prizes -> 1st: $500, 2nd: $0, 3rd: $0"; 
        assertEquals(expectedString, prizes.toString()); 
    }
}