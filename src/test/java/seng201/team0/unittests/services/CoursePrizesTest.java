package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.CoursePrizes;

import static org.junit.jupiter.api.Assertions.*;

public class CoursePrizesTest {

    @Test
    void testCoursePrizesConstructorAndGetters() {
        CoursePrizes prizes = new CoursePrizes(1000, 500, 250);
        assertEquals(1000, prizes.getFirstPlacePrize());
        assertEquals(500, prizes.getSecondPlacePrize());
        assertEquals(250, prizes.getThirdPlacePrize());
    }

    @Test
    void testCoursePrizesWithZeroValues() {
        CoursePrizes prizes = new CoursePrizes(0, 0, 0);
        assertEquals(0, prizes.getFirstPlacePrize());
        assertEquals(0, prizes.getSecondPlacePrize());
        assertEquals(0, prizes.getThirdPlacePrize());
    }

    @Test
    void testToString() {
        CoursePrizes prizes = new CoursePrizes(100, 50, 25);
        String expectedString = "Prizes -> 1st: $100, 2nd: $50, 3rd: $25";
        assertEquals(expectedString, prizes.toString());
    }

    @Test
    void testToStringWithDifferentValues() {
        CoursePrizes prizes = new CoursePrizes(1500, 1000, 750);
        String expectedString = "Prizes -> 1st: $1500, 2nd: $1000, 3rd: $750";
        assertEquals(expectedString, prizes.toString());
    }
}