public enum Route {
    DESERT_DRIFT("Desert Drift", new RouteAttributes(0.5, 1.5, 1.2), 20),
    DESERT_LONG("Desert Long", new RouteAttributes(1.5, 0.6, 1), 30),
    MOUNTAIN_STEEP("Mountain Steep", new RouteAttributes(1, 1, 1.2), 15),
    MOUNTAIN_CURVY("Mountain Curvy", new RouteAttributes(0.8, 1.3, 1.2), 20),
    COUNTRYSIDE_STRAIGHT("Countryside Straight", new RouteAttributes(1.6, 0.5, 1.1), 50),
    COUNTRYSIDE_TWISTY("Countryside Twisty", new RouteAttributes(0.8, 1.3, 1.2), 30),
    CITY_WIDE_LINE("City Wide Line", new RouteAttributes(1.2, 0.8, 1), 35),
    CITY_CUT_LINE("City Cut Line", new RouteAttributes(0.8, 1.2, 1), 34);

    private String routeName;
    private RouteAttributes attributes;
    private int length;

    Route(String routeName, RouteAttributes attributes, int length) {
        this.routeName = routeName;
        this.attributes = attributes;
        this.length = length;
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

    public int calculateFuelStops(Car car) {
        double economy = car.getFuelEconomy(); // e.g., 10 means 10 km per fuel unit
        int rangePerTank = (int) economy;
        return (int) Math.ceil((double) this.length / rangePerTank) - 1;

    }
}
