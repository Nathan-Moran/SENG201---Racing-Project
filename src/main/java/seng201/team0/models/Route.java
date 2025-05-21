package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific route within a {@link Course}, defining its unique characteristics,
 * such as length, number of fuel stops, opponent speed, and race duration.
 * Each route has specific attributes that affect car performance.
 */
public enum Route {
    /**
     * A desert route focusing on drifting, with specific speed, handling, and reliability advantages.
     */
    DESERT_DRIFT("Desert Drift", new RouteAttributes(0.5, 1.5, 1.2), 20, 2, 0.8, 15),
    /**
     * A long desert route, emphasizing speed.
     */
    DESERT_LONG("Desert Long", new RouteAttributes(1.5, 0.6, 1), 30, 3, 0.8, 20),
    /**
     * A steep mountain route, favoring reliability and handling.
     */
    MOUNTAIN_STEEP("Mountain Steep", new RouteAttributes(1, 0.8, 1.2), 15, 1, 1, 10),
    /**
     * A curvy mountain route, where handling is crucial.
     */
    MOUNTAIN_CURVES("Mountain Curves", new RouteAttributes(0.8, 1.3, 1.2), 20, 1, 1, 12),
    /**
     * A straight country road, ideal for high speeds.
     */
    COUNTRY_STRAIGHT("Country Straight", new RouteAttributes(1.6, 0.5, 1.1), 50, 4, 1, 20),
    /**
     * A twisting country road, where handling is important.
     */
    COUNTRY_TWISTY("Country Twisty", new RouteAttributes(0.8, 1.3, 1.2), 30, 3, 1, 15),
    /**
     * City alleys, requiring good handling and reliability.
     */
    CITY_ALLEYS("City Alleys", new RouteAttributes(1.2, 1.2, 1), 35, 3, 1.3, 15),
    /**
     * City traffic route, testing handling and reliability in congested areas.
     */
    CITY_TRAFFIC("City Cut Line", new RouteAttributes(0.4, 1, 1.5), 15, 2, 1.3, 10);

    /**
     * The display name of the route.
     */
    private final String routeName;
    /**
     * The {@link RouteAttributes} detailing how car stats are affected on this route.
     */
    private final RouteAttributes attributes;
    /**
     * The total length of the route in kilometers.
     */
    private final int length;
    /**
     * The number of mandatory fuel stops required on this route.
     */
    private final int fuelstops;
    /**
     * The base speed of AI opponents on this route.
     */
    private final double opponentSpeed;
    /**
     * The estimated duration of the race on this route, in minutes.
     */
    private final double duration;

    /**
     * Constructs a new Route enum constant.
     *
     * @param routeName The display name of the route.
     * @param attributes The {@link RouteAttributes} for this route.
     * @param length The length of the route in kilometers.
     * @param fuelstops The number of fuel stops required.
     * @param opponentSpeed The base speed for opponents on this route.
     * @param duration The estimated duration of the race in minutes.
     */
    Route(String routeName, RouteAttributes attributes, int length, int fuelstops, double opponentSpeed, double duration) {
        this.routeName = routeName;
        this.attributes = attributes;
        this.length = length;
        this.fuelstops = fuelstops;
        this.opponentSpeed = opponentSpeed;
        this.duration = duration;
    }

    /**
     * Generates a list of {@link OpponentCar} objects for a race on this route.
     * Opponent speeds are incrementally increased based on the base opponent speed of this route.
     *
     * @param count The number of opponents to generate.
     * @return A {@link List} of {@link OpponentCar} instances.
     */
    public List<OpponentCar> generateOpponents(int count) {
        List<OpponentCar> opponents = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double speed = opponentSpeed + (i * 0.1);
            opponents.add(new OpponentCar(speed));
        }
        return opponents;
    }

    /**
     * Gets the display name of the route.
     * @return The name of the route.
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * Gets the {@link RouteAttributes} associated with this route.
     * These attributes define how different car stats are advantaged or disadvantaged on this route.
     * @return The route's attributes.
     */
    public RouteAttributes getAttributes() {
        return attributes;
    }

    /**
     * Gets the total length of the route.
     * @return The length of the route in kilometers.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the number of fuel stops required on this route.
     * @return The number of fuel stops.
     */
    public int getFuelStops() {
        return fuelstops;
    }

    /**
     * Gets the estimated duration of a race on this route.
     * @return The duration of the race in minutes.
     */
    public double getRaceDuration() {
        return duration;
    }
}