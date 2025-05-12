package seng201.team0;

import java.util.List;

public class Race {
    private Course course;
    private Route route;
    private double raceDuration;
    private List<OpponentCar> opponents;

    public Race(Course course, Route route, List<OpponentCar> opponents, double raceDuration) {
        this.course = course;
        this.route = route;
        this.opponents = opponents;
        this.raceDuration = raceDuration;

    }

    public Course getCourse() {
        return course;
    }

    public Route getRoute() {
        return route;
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