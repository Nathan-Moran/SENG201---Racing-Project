package seng201.team0.models;

/**
 * Defines the different difficulty levels for the game, each influencing
 * the starting budget and a multiplier for car breakdown chances.
 */
public enum Difficulty {
    /**
     * Easy difficulty: Higher starting budget, lower chance of car breakdown.
     */
    EASY(1500, 0.8),
    /**
     * Medium difficulty: Balanced starting budget and breakdown chance.
     */
    MEDIUM(1250, 0.9),
    /**
     * Hard difficulty: Lower starting budget, higher chance of car breakdown.
     */
    HARD(1000, 1);

    /**
     * The initial amount of money the player starts with at this difficulty.
     */
    private final int startBudget;
    /**
     * A multiplier applied to the base breakdown chance of a car.
     * A lower value means less chance of breakdown.
     */
    private final double breakdownMultiplier;

    /**
     * Constructs a Difficulty enum constant.
     *
     * @param startBudget The initial money provided to the player.
     * @param breakdownMultiplier The multiplier for car breakdown probability.
     */
    Difficulty(int startBudget, double breakdownMultiplier) {
        this.startBudget = startBudget;
        this.breakdownMultiplier = breakdownMultiplier;
    }

    /**
     * Gets the starting budget for this difficulty level.
     * @return The initial balance for the player.
     */
    public int getStartBudget() {
        return startBudget;
    }

    /**
     * Gets the breakdown multiplier for this difficulty level.
     * This value affects the probability of a car breaking down during a race.
     * @return The breakdown multiplier.
     */
    public double getBreakdownMultiplier() {
        return breakdownMultiplier;
    }
}