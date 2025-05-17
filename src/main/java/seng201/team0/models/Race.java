package seng201.team0.models;

import java.util.List;

public class Race {
    private Course course;
    private Route route;
    private List<OpponentCar> opponents;
    private Difficulty difficulty;  // Add a field for difficulty

    // Modify the constructor to accept difficulty
    public Race(Course course, Route route, Difficulty difficulty) {
        this.course = course;
        this.route = route;
        this.difficulty = difficulty;
        this.opponents = route.generateOpponents(course.getNumberOfOpponents());  // Generate opponents based on the course's number of opponents
    }

    public Course getCourse() {
        return course;
    }

    public Route getRoute() {
        return route;
    }

    public List<OpponentCar> getOpponents() {
        return opponents;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setOpponents(List<OpponentCar> opponents) {
        this.opponents = opponents;
    }

    @Override
    public String toString() {
        return course.getName() + " - " + route.getRouteName();
    }
}
