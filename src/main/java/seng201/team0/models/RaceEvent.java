package seng201.team0.models;

public class RaceEvent {
    private final RaceEventType type;

    public RaceEvent(RaceEventType type) {
        this.type = type;
    }

    public RaceEventType getType() {
        return type;
    }

}
