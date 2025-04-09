import java.util.List;

public enum Course {
    DESERT("Desert Rally", 1.2, 0.8, 1.0, 100, 300, List.of(Route.DESERT_FAST, Route.DESERT_LONG)),
    MOUNTAIN("Mountain Pass", 0.9, 1.5, 1.1, 200, 600, List.of(Route.MOUNTAIN_CIRCUIT, Route.MOUNTAIN_RAPID)),
    COUNTRYSIDE("Country Straight", 1.1, 1.0, 0.9, 300, 900, List.of(Route.COUNTRYSIDE_STRAIGHT, Route.COUNTRYSIDE_TWISTY)),
    CITY("City Track", 1.5, 1.2, 0.5, 500, 1500, List.of(Route.CITY_SPRINT, Route.CITY_CROSS));

    private final String name;
    private final double speed;
    private final double handling;
    private final double reliability;
    private final int price;
    private final int entryFee;
    private final List<Route> availableRoutes;

    Course(String name, double speed, double handling, double reliability, int price, int entryFee, List<Route> availableRoutes) {
        this.name = name;
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.price = price;
        this.entryFee = entryFee;
        this.availableRoutes = availableRoutes;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHandling() {
        return handling;
    }

    public double getReliability() {
        return reliability;
    }

    public int getPrice() {
        return price;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public List<Route> getAvailableRoutes() {
        return availableRoutes;
    }
}