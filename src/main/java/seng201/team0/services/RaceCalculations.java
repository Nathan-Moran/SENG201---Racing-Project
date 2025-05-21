package seng201.team0.services;

import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides static utility methods for performing various calculations related to races.
 * These calculations include effective car statistics, fuel consumption, opponent tracking,
 * and event trigger distances.
 */
public final class RaceCalculations { // Made final as it's a utility class with only static methods
    // Removed instance variable 'opponents' as all methods are static or take parameters.
    // private List<OpponentCar> opponents;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private RaceCalculations() {
        // This is a utility class, so no instances should be created.
    }

    /**
     * Calculates the effective speed of a car on a given route.
     * This combines the car's base speed and handling with the route's
     * speed and handling advantages.
     *
     * @param car The {@link Car} for which to calculate effective speed.
     * @param route The {@link Route} on which the calculation is based.
     * @return The calculated effective speed.
     */
    public static double calculateEffectiveSpeed(Car car, Route route) {
        RouteAttributes attr = route.getAttributes();

        double speed = car.getSpeed();
        double handling = car.getHandling();

        double adjustedSpeed = speed * attr.getSpeedAdvantage();
        double adjustedHandling = handling * attr.getHandlingAdvantage();

        return adjustedSpeed + adjustedHandling; // Simple sum for combined effect
    }

    /**
     * Calculates the rate at which a car consumes fuel.
     * This is the inverse of its fuel economy.
     *
     * @param car The {@link Car} for which to calculate fuel consumption rate.
     * @return The fuel consumption rate (e.g., units of fuel per tick).
     */
    public static double calculateFuelConsumptionRate(Car car) {
        // Prevent division by zero if fuel economy is somehow 0
        if (car.getFuelEconomy() == 0) {
            return Double.MAX_VALUE; // Represents infinite consumption rate
        }
        return 1.0 / car.getFuelEconomy();
    }

    /**
     * Filters a list of opponents to return only those who have reached or exceeded
     * the length of the race route.
     *
     * @param race The {@link Race} containing the opponents and route length.
     * @return A {@link List} of {@link OpponentCar} objects that have finished the race.
     */
    public static List<OpponentCar> getFinishedOpponents(Race race) {
        return race.getOpponents().stream()
                .filter(o -> o.getCurrentDistance() >= race.getRoute().getLength())
                .toList(); // Using Java 16+ toList() for immutable list
    }

    /**
     * Calculates the distances at which fuel stops should occur along a route.
     * Fuel stops are evenly distributed along the length of the route.
     *
     * @param length The total length of the route.
     * @param numStops The number of fuel stops planned for the route.
     * @return A {@link List} of doubles representing the distances for each fuel stop.
     */
    public static List<Double> calculateFuelStopDistances(double length, int numStops) {
        List<Double> stops = new ArrayList<>();
        if (numStops <= 0) {
            return stops; // No stops if numStops is 0 or less
        }
        // Distribute stops evenly, plus one for the start and one for the end
        double interval = length / (numStops + 1);
        for (int i = 1; i <= numStops; i++) {
            stops.add(interval * i);
        }
        return stops;
    }

    /**
     * Calculates the effective reliability of a car on a given route.
     * This takes into account the car's base reliability and the route's reliability advantage.
     *
     * @param car The {@link Car} for which to calculate effective reliability.
     * @param route The {@link Route} on which the calculation is based.
     * @return The calculated effective reliability.
     */
    public static double calculateEffectiveReliability(Car car, Route route) {
        RouteAttributes attr = route.getAttributes();
        return car.getReliability() * attr.getReliabilityAdvantage();
    }

    /**
     * Calculates the distance into the race where a random event might first be triggered.
     * This is set to 40% of the total route length.
     *
     * @param length The total length of the route.
     * @return The distance (in kilometers) at which a random event can be triggered.
     */
    public static double calculateEventTriggerDistance(double length) {
        return length * 0.4;
    }

    /**
     * Calculates the distance into the race where a car breakdown event might first be triggered.
     * This is set to 70% of the total route length.
     *
     * @param length The total length of the route.
     * @return The distance (in kilometers) at which a breakdown event can be triggered.
     */
    public static double calculateBreakdownTriggerDistance(double length) {
        return length * 0.7;
    }
}