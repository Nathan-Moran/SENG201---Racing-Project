package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.Course;
import seng201.team0.models.CoursePrizes;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    void testDesertCourseAttributes() {
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
    void testMountainCourseAttributes() {
        Course mountain = Course.MOUNTAIN;
        assertEquals("Mountain", mountain.getName());
        assertEquals(200, mountain.getEntryFee());
        assertNotNull(mountain.getPrizes());
        assertEquals(800, mountain.getPrizes().getFirstPlacePrize());
        assertEquals(650, mountain.getPrizes().getSecondPlacePrize());
        assertEquals(500, mountain.getPrizes().getThirdPlacePrize());
        assertEquals(3, mountain.getNumberOfOpponents());
    }

    @Test
    void testCountryCourseAttributes() {
        Course country = Course.COUNTRY;
        assertEquals("Country", country.getName());
        assertEquals(300, country.getEntryFee());
        assertNotNull(country.getPrizes());
        assertEquals(900, country.getPrizes().getFirstPlacePrize());
        assertEquals(600, country.getPrizes().getSecondPlacePrize());
        assertEquals(450, country.getPrizes().getThirdPlacePrize());
        assertEquals(4, country.getNumberOfOpponents());
    }

    @Test
    void testCityCourseAttributes() {
        Course city = Course.CITY;
        assertEquals("City", city.getName());
        assertEquals(500, city.getEntryFee());
        assertNotNull(city.getPrizes());
        assertEquals(1500, city.getPrizes().getFirstPlacePrize());
        assertEquals(1000, city.getPrizes().getSecondPlacePrize());
        assertEquals(750, city.getPrizes().getThirdPlacePrize());
        assertEquals(2, city.getNumberOfOpponents());
    }

    @Test
    void testGetAllCourses() {
        Course[] courses = Course.values();
        assertEquals(4, courses.length);
        assertSame(Course.DESERT, Course.valueOf("DESERT"));
        assertSame(Course.MOUNTAIN, Course.valueOf("MOUNTAIN"));
        assertSame(Course.COUNTRY, Course.valueOf("COUNTRY"));
        assertSame(Course.CITY, Course.valueOf("CITY"));
    }
}