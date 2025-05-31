package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeasonTest {

    private List<Race> initialRaces;
    private Race race1;
    private Race race2;

    @BeforeEach
    void setUp() {
        race1 = new Race(Course.DESERT, Route.DESERT_DRIFT, Difficulty.EASY);
        race2 = new Race(Course.MOUNTAIN, Route.MOUNTAIN_STEEP, Difficulty.MEDIUM);
        initialRaces = new ArrayList<>();
        initialRaces.add(race1);
        initialRaces.add(race2);
    }

    @Test
    void constructorSetsLengthAndRaces() {
        Season season = new Season(5, initialRaces);
        assertEquals(5, season.getLength());
        assertEquals(2, season.getRaces().size());
        assertTrue(season.getRaces().contains(race1));
        assertTrue(season.getRaces().contains(race2));
        assertEquals(0, season.getCurrentRaceIndex());
    }

    @Test
    void constructorWithNullRacesList() {
        Season season = new Season(3, null);
        assertEquals(3, season.getLength());
        assertNotNull(season.getRaces());
        assertTrue(season.getRaces().isEmpty());
        assertEquals(0, season.getCurrentRaceIndex());
    }

    @Test
    void constructorWithEmptyRacesList() {
        Season season = new Season(4, Collections.emptyList());
        assertEquals(4, season.getLength());
        assertNotNull(season.getRaces());
        assertTrue(season.getRaces().isEmpty());
    }


    @Test
    void addRaceWhenNotFull() {
        Season season = new Season(3, new ArrayList<>());
        assertEquals(0, season.getRaces().size());
        season.addRace(Course.COUNTRY, Route.COUNTRY_STRAIGHT, null, 0, Difficulty.HARD);

        assertEquals(1, season.getRaces().size());
        Race addedRace = season.getRaces().get(0);
        assertNotNull(addedRace);
        assertEquals(Course.COUNTRY, addedRace.getCourse());
        assertEquals(Route.COUNTRY_STRAIGHT, addedRace.getRoute());
        assertEquals(Difficulty.HARD, addedRace.getDifficulty());
    }

    @Test
    void addRaceWhenFull() {
        Season season = new Season(1, new ArrayList<>(List.of(race1)));
        assertEquals(1, season.getRaces().size());

        season.addRace(Course.CITY, Route.CITY_ALLEYS, null, 0, Difficulty.EASY);
        assertEquals(1, season.getRaces().size());
    }

    @Test
    void advanceToNextRaceWhenMoreRacesExist() {
        Season season = new Season(3, initialRaces);
        assertEquals(0, season.getCurrentRaceIndex());
        assertEquals(race1, season.getCurrentRace());

        season.advanceToNextRace();
        assertEquals(1, season.getCurrentRaceIndex());
        assertEquals(race2, season.getCurrentRace());
    }

    @Test
    void advanceToNextRaceAtLastRace() {
        Season season = new Season(2, initialRaces);
        season.advanceToNextRace();
        assertEquals(1, season.getCurrentRaceIndex());
        assertEquals(race2, season.getCurrentRace());

        season.advanceToNextRace();
        assertEquals(1, season.getCurrentRaceIndex());
    }

    @Test
    void advanceToNextRaceWhenNoRaces() {
        Season season = new Season(2, new ArrayList<>());
        assertEquals(0, season.getCurrentRaceIndex());
        assertNull(season.getCurrentRace());

        season.advanceToNextRace();
        assertEquals(0, season.getCurrentRaceIndex());
        assertNull(season.getCurrentRace());
    }


    @Test
    void getRacesReturnsInternalListReference() {
        Season season = new Season(5, initialRaces);
        List<Race> retrievedRaces = season.getRaces();
        assertEquals(initialRaces, retrievedRaces);

        Race newRace = new Race(Course.CITY, Route.CITY_TRAFFIC, Difficulty.HARD);
        retrievedRaces.add(newRace);
        assertEquals(3, season.getRaces().size());
    }

    @Test
    void getLengthReturnsCorrectLength() {
        Season season = new Season(7, null);
        assertEquals(7, season.getLength());
    }

    @Test
    void getCurrentRaceIndexReturnsCorrectIndex() {
        Season season = new Season(3, initialRaces);
        assertEquals(0, season.getCurrentRaceIndex());
        season.advanceToNextRace();
        assertEquals(1, season.getCurrentRaceIndex());
    }

    @Test
    void getCurrentRaceReturnsCorrectRace() {
        Season season = new Season(3, initialRaces);
        assertEquals(race1, season.getCurrentRace());
        season.advanceToNextRace();
        assertEquals(race2, season.getCurrentRace());
    }

    @Test
    void getCurrentRaceWhenIndexIsEffectivelyOutOfBounds() {
        Season emptySeason = new Season(0, new ArrayList<>());
        assertNull(emptySeason.getCurrentRace());

        Season seasonWithOneRace = new Season(1, new ArrayList<>(List.of(race1)));
        assertEquals(race1, seasonWithOneRace.getCurrentRace());
        seasonWithOneRace.advanceToNextRace();
        assertEquals(race1, seasonWithOneRace.getCurrentRace());
    }
}