package seng201.team0.models;

/**
 * Defines the various types of events that can occur during a race.
 * These events can affect the progress of the player or opponents.
 */
public enum RaceEventType {
    /**
     * Represents an event where a car needs to stop for fuel.
     */
    FUEL_STOP,
    /**
     * Represents an event where a car experiences a mechanical failure.
     */
    BREAKDOWN,
    /**
     * Represents a random event where a traveler might cause a slight delay or advantage.
     */
    TRAVELER,
    /**
     * Represents an event where weather conditions change, potentially affecting race performance.
     */
    WEATHER
}