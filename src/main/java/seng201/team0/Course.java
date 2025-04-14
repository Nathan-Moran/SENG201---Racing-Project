package seng201.team0;

import java.util.List;

public enum Course {
    DESERT("Desert Rally", new CourseAttributes(1.2, 0.8, 1.0), 100, 300, List.of(Route.DESERT_DRIFT, Route.DESERT_LONG)),
    MOUNTAIN("Mountain Pass", new CourseAttributes(0.9, 1.5, 1.1), 200, 600, List.of(Route.MOUNTAIN_STEEP, Route.MOUNTAIN_CURVY)),
    COUNTRYSIDE("Country Straight", new CourseAttributes(1.1, 1.0, 0.9), 300, 900, List.of(Route.COUNTRYSIDE_STRAIGHT, Route.COUNTRYSIDE_TWISTY)),
    CITY("City Track", new CourseAttributes(1.5, 1.2, 0.5), 500, 1500, List.of(Route.CITY_WIDE_LINE, Route.CITY_CUT_LINE));

    private String name;
    private CourseAttributes attributes;
    private int entryFee;
    private int prizeMoney;
    private List<Route> availableRoutes;

    Course(String name, CourseAttributes attributes, int entryFee, int prizeMoney, List<Route> availableRoutes) {
        this.name = name;
        this.attributes = attributes;
        this.entryFee = entryFee;
        this.prizeMoney = prizeMoney;
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

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public List<Route> getAvailableRoutes() {
        return availableRoutes;
    }
}