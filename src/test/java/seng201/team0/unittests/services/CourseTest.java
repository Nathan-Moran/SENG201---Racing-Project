package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Course;
import seng201.team0.models.CoursePrizes;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void desertCourseHasCorrectProperties() {
        Course desert = Course.DESERT;
        assertEquals("Desert", desert.getName()); 
        assertEquals(0, desert.getEntryFee()); 
        assertNotNull(desert.getPrizes()); 
        assertEquals(500, desert.getPrizes().getFirstPlacePrize()); 
        assertEquals(350, desert.getPrizes().getSecondPlacePrize()); 
        assertEquals(250, desert.getPrizes().getThirdPlacePrize()); 
        assertEquals(4, desert.getNumberOfOpponents()); 
    }

    @Test
    void mountainCourseHasCorrectProperties() {
        Course mountain = Course.MOUNTAIN;
        assertEquals("Mountain", mountain.getName()); 
        assertEquals(250, mountain.getEntryFee()); 
        assertNotNull(mountain.getPrizes()); 
        assertEquals(1000, mountain.getPrizes().getFirstPlacePrize()); 
        assertEquals(750, mountain.getPrizes().getSecondPlacePrize()); 
        assertEquals(500, mountain.getPrizes().getThirdPlacePrize()); 
        assertEquals(3, mountain.getNumberOfOpponents()); 
    }

    @Test
    void countryCourseHasCorrectProperties() {
        Course country = Course.COUNTRY;
        assertEquals("Country", country.getName()); 
        assertEquals(500, country.getEntryFee()); 
        assertNotNull(country.getPrizes()); 
        assertEquals(1500, country.getPrizes().getFirstPlacePrize()); 
        assertEquals(1000, country.getPrizes().getSecondPlacePrize()); 
        assertEquals(800, country.getPrizes().getThirdPlacePrize()); 
        assertEquals(4, country.getNumberOfOpponents()); 
    }

    @Test
    void cityCourseHasCorrectProperties() {
        Course city = Course.CITY;
        assertEquals("City", city.getName()); 
        assertEquals(1000, city.getEntryFee()); 
        assertNotNull(city.getPrizes()); 
        assertEquals(3000, city.getPrizes().getFirstPlacePrize()); 
        assertEquals(0, city.getPrizes().getSecondPlacePrize()); 
        assertEquals(0, city.getPrizes().getThirdPlacePrize()); 
        assertEquals(2, city.getNumberOfOpponents()); 
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Desert", Course.DESERT.getName()); 
    }

    @Test
    void getEntryFeeReturnsCorrectFee() {
        assertEquals(250, Course.MOUNTAIN.getEntryFee()); 
    }

    @Test
    void getPrizesReturnsCorrectPrizesObject() {
        CoursePrizes expectedPrizes = new CoursePrizes(500, 350, 250);
        CoursePrizes actualPrizes = Course.DESERT.getPrizes(); 
        assertEquals(expectedPrizes.getFirstPlacePrize(), actualPrizes.getFirstPlacePrize()); 
        assertEquals(expectedPrizes.getSecondPlacePrize(), actualPrizes.getSecondPlacePrize()); 
        assertEquals(expectedPrizes.getThirdPlacePrize(), actualPrizes.getThirdPlacePrize()); 
    }

    @Test
    void getNumberOfOpponentsReturnsCorrectNumber() {
        assertEquals(2, Course.CITY.getNumberOfOpponents()); 
    }

    @Test
    void ensureAllEnumValuesAreTested() {
        assertEquals(4, Course.values().length);
    }
}