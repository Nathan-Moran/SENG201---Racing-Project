package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public enum     Route {
    DESERT_DRIFT("Desert Drift", new RouteAttributes(0.5, 1.5, 1.2), 20, 2, 0.8, 15),
    DESERT_LONG("Desert Long", new RouteAttributes(1.5, 0.6, 1), 30, 3, 0.8, 20),
    MOUNTAIN_STEEP("Mountain Steep", new RouteAttributes(1, 0.8, 1.2), 15, 1, 1, 10),
    MOUNTAIN_CURVES("Mountain Curves", new RouteAttributes(0.8, 1.3, 1.2), 20, 1, 1, 12),
    COUNTRY_STRAIGHT("Country Straight", new RouteAttributes(1.6, 0.5, 1.1), 50, 4, 1, 20),
    COUNTRY_TWISTY("Country Twisty", new RouteAttributes(0.8, 1.3, 1.2), 30, 3, 1, 15),
    CITY_ALLEYS("City Alleys", new RouteAttributes(1.2, 1.2, 1), 35, 3, 1.3, 15),
    CITY_TRAFFIC("City Cut Line", new RouteAttributes(0.4, 1, 1.5), 15, 2, 1.3, 10);

    private final String routeName;
    private final RouteAttributes attributes;
    private final int length;
    private final int fuelstops;
    private final double opponentSpeed;
    private final double duration;


    Route(String routeName, RouteAttributes attributes, int length, int fuelstops, double opponentSpeed, double duration) {
        this.routeName = routeName;
        this.attributes = attributes;
        this.length = length;
        this.fuelstops = fuelstops;
        this.opponentSpeed = opponentSpeed;
        this.duration = duration;
    }

    public List<OpponentCar> generateOpponents(int count) {
        List<OpponentCar> opponents = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double speed = opponentSpeed + (i * 0.1);
            opponents.add(new OpponentCar(speed));
        }
        return opponents;
    }

    public String getRouteName() {
        return routeName;
    }

    public RouteAttributes getAttributes() {
        return attributes;
    }

    public int getLength() {
        return length;
    }

    public int getFuelStops() {
        return fuelstops;
    }

    public double getRaceDuration() {
        return duration;
    }
}
