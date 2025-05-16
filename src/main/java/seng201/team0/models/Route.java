package seng201.team0.models;

public enum Route {
    DESERT_DRIFT("Desert Drift", new RouteAttributes(0.5, 1.5, 1.2), 20, 2),
    DESERT_LONG("Desert Long", new RouteAttributes(1.5, 0.6, 1), 30, 3),
    MOUNTAIN_STEEP("Mountain Steep", new RouteAttributes(1, 0.8, 1.2), 15, 1),
    MOUNTAIN_CURVES("Mountain Curves", new RouteAttributes(0.8, 1.3, 1.2), 20, 1),
    COUNTRY_STRAIGHT("Country Straight", new RouteAttributes(1.6, 0.5, 1.1), 50, 4),
    COUNTRY_TWISTY("Country Twisty", new RouteAttributes(0.8, 1.3, 1.2), 30, 3),
    CITY_ALLEYS("City Alleys", new RouteAttributes(1.2, 1.2, 1), 35, 3),
    CITY_TRAFFIC("City Cut Line", new RouteAttributes(0.4, 1, 1.5), 15, 2);

    private String routeName;
    private RouteAttributes attributes;
    private int length;
    private int fuelstops;

    Route(String routeName, RouteAttributes attributes, int length, int fuelstops) {
        this.routeName = routeName;
        this.attributes = attributes;
        this.length = length;
        this.fuelstops = fuelstops;
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
}
