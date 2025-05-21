package seng201.team0.models;

import java.util.List;

/**
 * Represents a single race in the game, defined by its course, route,
 * participating opponents, and the active difficulty setting.
 * A race is where the player competes against AI-controlled cars.
 */
public class Race {
    /**
     * The {@link Course} on which this race takes place.
     */
    private Course course;
    /**
     * The specific {@link Route} selected within the course for this race.
     */
    private Route route;
    /**
     * A list of {@link OpponentCar} objects that the player will compete against.
     */
    private List<OpponentCar> opponents;
    /**
     * The {@link Difficulty} level set for the current game season, influencing various race parameters.
     */
    private final Difficulty difficulty;

    /**
     * Constructs a new Race instance.
     * Opponents are generated based on the chosen route and the course's specified number of opponents.
     *
     * @param course The {@link Course} for the race.
     * @param route The {@link Route} for the race.
     * @param difficulty The {@link Difficulty} setting for the current game.
     */
    public Race(Course course, Route route, Difficulty difficulty) {
        this.course = course;
        this.route = route;
        this.difficulty = difficulty;
        // Generate opponents based on the route's method and the course's required number of opponents
        this.opponents = route.generateOpponents(course.getNumberOfOpponents());
    }

    /**
     * Gets the {@link Course} of this race.
     * @return The course.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Gets the {@link Route} of this race.
     * @return The route.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Gets the list of {@link OpponentCar} objects in this race.
     * @return A list of opponent cars.
     */
    public List<OpponentCar> getOpponents() {
        return opponents;
    }

    /**
     * Sets the {@link Route} for this race.
     * @param route The new route to set.
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Sets the {@link Course} for this race.
     * @param course The new course to set.
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Sets the list of {@link OpponentCar} objects for this race.
     * @param opponents The new list of opponents to set.
     */
    public void setOpponents(List<OpponentCar> opponents) {
        this.opponents = opponents;
    }

    /**
     * Returns a string representation of the race, combining the course and route names.
     * @return A string in the format "Course Name - Route Name".
     */
    @Override
    public String toString() {
        return course.getName() + " - " + route.getRouteName();
    }

    /**
     * Gets the {@link Difficulty} level for this race.
     * @return The difficulty level.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }
}