package seng201.team0;

public class GameEnvironment {
    private static Course selectedCourse;
    private static Route selectedRoute;
    private static Season currentSeason;

    public static void setSelectedCourse(Course course) {
        selectedCourse = course;
    }

    public static void setSelectedRoute(Route route) {
        selectedRoute = route;
    }

    public static void setCurrentSeason(Season season) {
        currentSeason = season;
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }

    public static Route getSelectedRoute() {
        return selectedRoute;
    }

    public static Season getCurrentSeason() {
        return currentSeason;
    }
}