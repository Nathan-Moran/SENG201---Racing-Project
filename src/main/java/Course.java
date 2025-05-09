import java.util.List;

public enum Course {
    DESERT("Desert", new CourseAttributes(1.2, 0.8, 1.0), 100, new CoursePrizes(300, 200, 150), List.of(Route.DESERT_DRIFT, Route.DESERT_LONG)),
    MOUNTAIN("Mountain", new CourseAttributes(0.9, 1.5, 1.1), 200, new CoursePrizes(600, 400, 300), List.of(Route.MOUNTAIN_STEEP, Route.MOUNTAIN_CURVES)),
    COUNTRY("Country", new CourseAttributes(1.1, 1.0, 0.9), 300, new CoursePrizes(900, 600, 450), List.of(Route.COUNTRY_STRAIGHT, Route.COUNTRY_TWISTY)),
    CITY("City", new CourseAttributes(1.5, 1.2, 0.5), 500, new CoursePrizes(1500, 1000, 750), List.of(Route.CITY_ALLEYS, Route.CITY_TRAFFIC));

    private String name;
    private CourseAttributes attributes;
    private int entryFee;
    private CoursePrizes prizes;
    private List<Route> availableRoutes;

    Course(String name, CourseAttributes attributes, int entryFee, CoursePrizes prizes, List<Route> availableRoutes) {
        this.name = name;
        this.attributes = attributes;
        this.entryFee = entryFee;
        this.prizes = prizes;
        this.availableRoutes = availableRoutes;
    }

    public String getName() {
        return name;
    }

    public CourseAttributes getAttributes() {
        return attributes;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public CoursePrizes getPrizes() {
        return prizes;
    }

    public List<Route> getAvailableRoutes() {
        return availableRoutes;
    }
}