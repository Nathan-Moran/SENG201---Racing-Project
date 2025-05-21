package seng201.team0.models;

/**
 * Represents a specific event that can occur during a race,
 * such as a fuel stop, breakdown, or environmental changes.
 * The type of event is defined by {@link RaceEventType}.
 */
public class RaceEvent {
    /**
     * The type of the race event.
     */
    private final RaceEventType type;

    /**
     * Constructs a new RaceEvent with a specified type.
     * @param type The {@link RaceEventType} of the event.
     */
    public RaceEvent(RaceEventType type) {
        this.type = type;
    }

    /**
     * Gets the type of the race event.
     * @return The {@link RaceEventType} of the event.
     */
    public RaceEventType getType() {
        return type;
    }

}