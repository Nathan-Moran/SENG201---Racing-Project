package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeasonTest {

    private Race testRace1;
    private Race testRace2;
    private Race testRace3;

    @BeforeEach
    void setUp() {
        Course course = Course.DESERT;
        Route route = Route.DESERT_DRIFT;
        Difficulty difficulty = Difficulty.EASY;
        testRace1 = new Race(course, route, difficulty);
        testRace2 = new Race(Course.MOUNTAIN, Route.MOUNTAIN_STEEP, Difficulty.MEDIUM);
        testRace3 = new Race(Course.CITY, Route.CITY_ALLEYS, Difficulty.HARD);
    }

    @Test
    void testSeasonCreationEmptyRaces() {
        Season season = new Season(5, null);
        assertEquals(5, season.getLength());
        assertTrue(season.getRaces().isEmpty());
        assertEquals(0, season.getCurrentRaceIndex());
        assertNull(season.getCurrentRace());
    }

    @Test
    void testSeasonCreationWithInitialRaces() {
        List<Race> initialRaces = new ArrayList<>();
        initialRaces.add(testRace1);
        initialRaces.add(testRace2);
        Season season = new Season(3, initialRaces);

        assertEquals(3, season.getLength());
        assertEquals(2, season.getRaces().size());
        assertTrue(season.getRaces().contains(testRace1));
        assertTrue(season.getRaces().contains(testRace2));
        assertEquals(0, season.getCurrentRaceIndex());
        assertSame(testRace1, season.getCurrentRace());
    }

    @Test
    void testAddRaceToSeason() {
        Season season = new Season(2, new ArrayList<>());
        season.addRace(Course.COUNTRY, Route.COUNTRY_STRAIGHT, new ArrayList<>(), 0, Difficulty.EASY);
        assertEquals(1, season.getRaces().size());
        assertNotNull(season.getRaces().get(0));
        assertEquals("Country", season.getRaces().get(0).getCourse().getName());
    }

    @Test
    void testAddRaceToFullSeason() {
        List<Race> initialRaces = List.of(testRace1);
        Season season = new Season(1, initialRaces);
        assertEquals(1, season.getRaces().size());

        season.addRace(Course.COUNTRY, Route.COUNTRY_TWISTY, new ArrayList<>(), 0, Difficulty.MEDIUM);
        assertEquals(1, season.getRaces().size());
    }

    @Test
    void testAdvanceToNextRace() {
        List<Race> races = List.of(testRace1, testRace2, testRace3);
        Season season = new Season(3, races);

        assertSame(testRace1, season.getCurrentRace());
        assertEquals(0, season.getCurrentRaceIndex());

        season.advanceToNextRace();
        assertSame(testRace2, season.getCurrentRace());
        assertEquals(1, season.getCurrentRaceIndex());

        season.advanceToNextRace();
        assertSame(testRace3, season.getCurrentRace());
        assertEquals(2, season.getCurrentRaceIndex());
    }

    @Test
    void testAdvanceToNextRaceAtEnd() {
        List<Race> races = List.of(testRace1, testRace2);
        Season season = new Season(2, races);

        season.advanceToNextRace();
        assertSame(testRace2, season.getCurrentRace());
        assertEquals(1, season.getCurrentRaceIndex());

        season.advanceToNextRace();
        assertSame(testRace2, season.getCurrentRace());
        assertEquals(1, season.getCurrentRaceIndex());
    }

    @Test
    void testGetCurrentRaceWhenNoRaces() {
        Season season = new Season(3, new ArrayList<>());
        assertNull(season.getCurrentRace());
    }

    @Test
    void testGetRacesReturnsCopy() {
        List<Race> initialRaces = new ArrayList<>();
        initialRaces.add(testRace1);
        Season season = new Season(2, initialRaces);
        List<Race> gotRaces = season.getRaces();
        gotRaces.add(testRace2);

        assertEquals(1, season.getRaces().size());
    }

    @Test
    void testConstructorWithZeroLength() {
        Season season = new Season(0, new ArrayList<>());
        assertEquals(0, season.getLength());
        season.addRace(Course.DESERT, Route.DESERT_DRIFT, new ArrayList<>(), 0, Difficulty.EASY);
        assertTrue(season.getRaces().isEmpty());
    }
}