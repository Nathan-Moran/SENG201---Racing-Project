public enum Route {
    DESERT_DRIFT("Desert Drift", 2, new RouteAttributes(0.5, 1.5, 1.2)),
    DESERT_LONG("Desert Long",3 , new RouteAttributes(1.5, 0.6, 1)),
    MOUNTAIN_STEEP("Mountain Steep", 2, new RouteAttributes(1, 1, 1.2)),
    MOUNTAIN_CURVY("Mountain Curvy", 2, new RouteAttributes(0.8, 1.3, 1.2)),
    COUNTRYSIDE_STRAIGHT("Countryside Straight", 4, new RouteAttributes(1.6, 0.5, 1.1)),
    COUNTRYSIDE_TWISTY("Countryside Twisty", 3, new RouteAttributes(0.8, 1.3, 1.2)),
    CITY_WIDE_LINE("City Wide Line", 5, new RouteAttributes(1.2, 0.8, 1)),
    CITY_CUT_LINE("City Cut Line", 5, new RouteAttributes(0.8, 1.2, 1));

    private String routeName;
    private int fuelStops;
    private RouteAttributes attributes;

    Route(String routeName, int fuelStops, RouteAttributes attributes) {
        this.routeName = routeName;
        this.fuelStops = fuelStops;
        this.attributes = attributes;
    }

    public RouteAttributes getAttributes() {
        return attributes;
    }

}
