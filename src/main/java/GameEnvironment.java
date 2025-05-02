public class GameEnvironment {
    private static Course selectedCourse;
    private static Route selectedRoute;

    public static void setSelectedCourse(Course course) {
        selectedCourse = course;
    }

    public static void setSelectedRoute(Route route) {
        selectedRoute = route;
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }

    public static Route getSelectedRoute() {
        return selectedRoute;
    }
}