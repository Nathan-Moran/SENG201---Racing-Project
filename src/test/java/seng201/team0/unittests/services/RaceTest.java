package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {

    private Course testCourse;
    private Route testRoute;
    private Difficulty testDifficulty;
    private Race race;

    @BeforeEach
    void setUp() {
        testCourse = Course.DESERT;
        testRoute = Route.DESERT_DRIFT;
        testDifficulty = Difficulty.EASY;

        race = new Race(testCourse, testRoute, testDifficulty);
    }

    @Test
    void constructorSetsPropertiesAndGeneratesOpponents() {
        assertEquals(testCourse, race.getCourse());
        assertEquals(testRoute, race.getRoute());
        assertEquals(testDifficulty, race.getDifficulty());
        assertNotNull(race.getOpponents());


        assertEquals(testCourse.getNumberOfOpponents(), race.getOpponents().size());

        List<OpponentCar> generatedOpponents = race.getOpponents();
        double expectedBaseSpeed = 0.8;
        for (int i = 0; i < generatedOpponents.size(); i++) {
            double expectedSpeed = expectedBaseSpeed + (i * 0.1);
            assertEquals(expectedSpeed, generatedOpponents.get(i).getSpeed(), 0.001, "Opponent " + i + " speed mismatch");
        }
    }

    @Test
    void getCourseReturnsCorrectCourse() {
        assertEquals(testCourse, race.getCourse());
    }

    @Test
    void getRouteReturnsCorrectRoute() {
        assertEquals(testRoute, race.getRoute());
    }

    @Test
    void getOpponentsReturnsCorrectList() {
        assertNotNull(race.getOpponents());
        assertEquals(testCourse.getNumberOfOpponents(), race.getOpponents().size());
    }

    @Test
    void getDifficultyReturnsCorrectDifficulty() {
        assertEquals(testDifficulty, race.getDifficulty());
    }

    @Test
    void setRouteChangesRoute() {
        Route newRoute = Route.DESERT_LONG;
        race.setRoute(newRoute);
        assertEquals(newRoute, race.getRoute());

        assertEquals(testCourse.getNumberOfOpponents(), race.getOpponents().size());
    }

    @Test
    void setCourseChangesCourse() {
        Course newCourse = Course.MOUNTAIN;
        race.setCourse(newCourse);
        assertEquals(newCourse, race.getCourse());

        assertNotEquals(newCourse.getNumberOfOpponents(), race.getOpponents().size());
        assertEquals(testCourse.getNumberOfOpponents(), race.getOpponents().size());
    }

    @Test
    void setOpponentsChangesOpponentsList() {
        List<OpponentCar> newOpponents = new ArrayList<>();
        newOpponents.add(new OpponentCar(1.0));
        newOpponents.add(new OpponentCar(1.2));
        race.setOpponents(newOpponents);
        assertEquals(newOpponents, race.getOpponents());
        assertEquals(2, race.getOpponents().size());
    }

    @Test
    void toStringFormatsCorrectly() {
        String expectedString = testCourse.getName() + " - " + testRoute.getRouteName();
        assertEquals(expectedString, race.toString());

        Course cityCourse = Course.CITY;
        Route cityRoute = Route.CITY_ALLEYS;
        Race cityRace = new Race(cityCourse, cityRoute, Difficulty.HARD);
        String cityExpectedString = cityCourse.getName() + " - " + cityRoute.getRouteName();
        assertEquals(cityExpectedString, cityRace.toString());
    }
}