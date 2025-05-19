package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceTest {

    @Test
    void testRaceCreationAndGetters() {
        Course course = Course.DESERT;
        Route route = Route.DESERT_DRIFT;
        Difficulty difficulty = Difficulty.EASY;

        Race race = new Race(course, route, difficulty);

        assertSame(course, race.getCourse());
        assertSame(route, race.getRoute());
        assertNotNull(race.getOpponents());

        int expectedOpponentCount = course.getNumberOfOpponents();
        assertEquals(expectedOpponentCount, race.getOpponents().size());

        if (expectedOpponentCount > 0) {
            assertNotNull(race.getOpponents().get(0));
        }
    }

    @Test
    void testRaceCreationWithDifferentParameters() {
        Course course = Course.CITY;
        Route route = Route.CITY_ALLEYS;
        Difficulty difficulty = Difficulty.HARD;

        Race race = new Race(course, route, difficulty);

        assertSame(course, race.getCourse());
        assertSame(route, race.getRoute());
        assertNotNull(race.getOpponents());
        assertEquals(course.getNumberOfOpponents(), race.getOpponents().size());
    }


    @Test
    void testSetRoute() {
        Course course = Course.MOUNTAIN;
        Route initialRoute = Route.MOUNTAIN_STEEP;
        Difficulty difficulty = Difficulty.MEDIUM;
        Race race = new Race(course, initialRoute, difficulty);

        Route newRoute = Route.MOUNTAIN_CURVES;
        race.setRoute(newRoute);
        assertSame(newRoute, race.getRoute());
    }

    @Test
    void testSetCourse() {
        Course initialCourse = Course.COUNTRY;
        Route route = Route.COUNTRY_STRAIGHT;
        Difficulty difficulty = Difficulty.EASY;
        Race race = new Race(initialCourse, route, difficulty);

        Course newCourse = Course.DESERT;
        race.setCourse(newCourse);
        assertSame(newCourse, race.getCourse());
    }

    @Test
    void testSetOpponents() {
        Course course = Course.CITY;
        Route route = Route.CITY_TRAFFIC;
        Difficulty difficulty = Difficulty.HARD;
        Race race = new Race(course, route, difficulty);

        List<OpponentCar> newOpponents = new ArrayList<>();
        newOpponents.add(new OpponentCar(100.0));
        newOpponents.add(new OpponentCar(110.0));

        race.setOpponents(newOpponents);
        assertSame(newOpponents, race.getOpponents());
        assertEquals(2, race.getOpponents().size());
    }

    @Test
    void testToString() {
        Course course = Course.DESERT;
        Route route = Route.DESERT_LONG;
        Difficulty difficulty = Difficulty.EASY;
        Race race = new Race(course, route, difficulty);

        String expectedString = course.getName() + " - " + route.getRouteName();
        assertEquals(expectedString, race.toString());
    }

    @Test
    void testToStringWithDifferentRace() {
        Course course = Course.MOUNTAIN;
        Route route = Route.MOUNTAIN_CURVES;
        Difficulty difficulty = Difficulty.MEDIUM;
        Race race = new Race(course, route, difficulty);

        String expectedString = course.getName() + " - " + route.getRouteName();
        assertEquals(expectedString, race.toString());
    }

    @Test
    void testOpponentGenerationBasedOnCourse() {
        Course courseWithTwoOpponents = Course.CITY; // CITY has 2 opponents
        Route route = Route.CITY_ALLEYS;
        Difficulty difficulty = Difficulty.EASY;

        Race race = new Race(courseWithTwoOpponents, route, difficulty);
        assertEquals(2, race.getOpponents().size());

        Course courseWithFourOpponents = Course.COUNTRY; // COUNTRY has 4 opponents
        race = new Race(courseWithFourOpponents, route, difficulty);
        assertEquals(4, race.getOpponents().size());
    }
}