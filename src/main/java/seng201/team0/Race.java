package seng201.team0;

public class Race {
    private Course course;
    private Route route;

    public Race(Course course, Route route) {
        this.course = course;
        this.route = route;
    }

    public Course getCourse() {
        return course;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return course.getName() + " - " + route.getRouteName();
    }
}