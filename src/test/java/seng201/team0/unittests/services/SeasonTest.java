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
        // Use concrete Race instances
        // For simplicity, assume Difficulty.EASY for these test races
        // Opponent generation is handled by Race constructor and Route.generateOpponents
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
        Season season = new Season(3, new ArrayList<>()); // Length 3, initially 0 races
        assertEquals(0, season.getRaces().size());

        // Parameters for Race constructor. The `addRace` method in Season creates a new Race.
        // The `opponents` and `raceDuration` parameters in `Season.addRace` are not actually used
        // in the `new Race(course, route, difficulty)` call within that method.
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
        // Season with length 1, already has 1 race
        Season season = new Season(1, new ArrayList<>(List.of(race1)));
        assertEquals(1, season.getRaces().size());

        // Attempt to add another race
        season.addRace(Course.CITY, Route.CITY_ALLEYS, null, 0, Difficulty.EASY);

        assertEquals(1, season.getRaces().size()); // Should not add if full
        // The "Season is full" message is printed to System.out, which is hard to assert here.
    }

    @Test
    void advanceToNextRaceWhenMoreRacesExist() {
        Season season = new Season(3, initialRaces); // 2 initial races (race1, race2)
        assertEquals(0, season.getCurrentRaceIndex());
        assertEquals(race1, season.getCurrentRace());

        season.advanceToNextRace();
        assertEquals(1, season.getCurrentRaceIndex());
        assertEquals(race2, season.getCurrentRace());
    }

    @Test
    void advanceToNextRaceAtLastRace() {
        Season season = new Season(2, initialRaces); // 2 initial races
        season.advanceToNextRace(); // Advance to the second (last) race (index 1)
        assertEquals(1, season.getCurrentRaceIndex());
        assertEquals(race2, season.getCurrentRace());

        season.advanceToNextRace(); // Attempt to advance past the last race
        assertEquals(1, season.getCurrentRaceIndex()); // Index should not change
        // "All races completed" message printed.
    }

    @Test
    void advanceToNextRaceWhenNoRaces() {
        Season season = new Season(2, new ArrayList<>());
        assertEquals(0, season.getCurrentRaceIndex());
        assertNull(season.getCurrentRace());

        season.advanceToNextRace(); // `currentRaceIndex < races.size() - 1` (0 < -1) is false
        assertEquals(0, season.getCurrentRaceIndex()); // Index remains 0
        assertNull(season.getCurrentRace());
        // "All races completed" message printed because `currentRaceIndex` (0) is not less than `races.size() - 1` (-1)
    }


    @Test
    void getRacesReturnsInternalListReference() {
        Season season = new Season(5, initialRaces);
        List<Race> retrievedRaces = season.getRaces();
        assertEquals(initialRaces, retrievedRaces);

        // Modify the retrieved list and check if the season's list is affected
        Race newRace = new Race(Course.CITY, Route.CITY_TRAFFIC, Difficulty.HARD);
        retrievedRaces.add(newRace);
        assertEquals(3, season.getRaces().size()); // Confirms getRaces() returns a direct reference or a shallow copy that's modified
        // The Season constructor `new ArrayList<>(races)` makes a shallow copy.
        // So, the `getRaces()` returns a reference to this internal shallow copy.
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
        assertNull(emptySeason.getCurrentRace()); // currentRaceIndex = 0, races.size() = 0. (0 < 0) is false.

        Season seasonWithOneRace = new Season(1, new ArrayList<>(List.of(race1)));
        assertEquals(race1, seasonWithOneRace.getCurrentRace());
        seasonWithOneRace.advanceToNextRace(); // "All races completed..." printed. Index remains 0.
        assertEquals(race1, seasonWithOneRace.getCurrentRace()); // Still points to the first (and only) race.

        // To truly test the `null` path where `currentRaceIndex >= races.size()`
        // and `races.size()` is not 0, we'd need to manipulate `currentRaceIndex`
        // in a way that `advanceToNextRace` doesn't, or have a season where `length` allows
        // `currentRaceIndex` to go beyond the actual number of races added.
        // However, `addRace` respects `length`, and `advanceToNextRace` stops incrementing
        // when `currentRaceIndex` reaches `races.size() - 1`.
        // So, `currentRaceIndex` should not become `>= races.size()` if `races` is not empty.
        // The only natural way for `getCurrentRace()` to return null is if `races` is empty.
    }
}