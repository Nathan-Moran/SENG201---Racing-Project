package seng201.team0;

import java.util.List;

public class Race {
    private Course course;
    private Route route;
    private List<OpponentCar> opponents;

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

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setOpponents(List<OpponentCar> opponents) {
        this.opponents = opponents;
    }


    public void updateRace(double timeElapsed) {
        // Update the distance for each opponent
        for (OpponentCar opponent : opponents) {
            opponent.updateDistance(timeElapsed);
        }
    }

    public void checkRaceCompletion() {
        for (OpponentCar opponent : opponents) {
            if (opponent.getCurrentDistance() >= route.getLength()) {
                // Opponent has finished the race
                // Handle opponent's race completion (position, prize, etc.)
            }
        }
    }

        @Override
        public String toString () {
            return course.getName() + " - " + route.getRouteName();
        }
    }