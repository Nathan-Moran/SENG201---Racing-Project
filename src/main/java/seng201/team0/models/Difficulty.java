package seng201.team0.models;

public enum Difficulty {
    EASY(1500, 0.8),
    MEDIUM(1250, 0.9),
    HARD(1000, 1);

    private final int startBudget;
    private final double breakdownMultiplier;

    Difficulty(int startBudget, double breakdownMultiplier) {
        this.startBudget = startBudget;
        this.breakdownMultiplier = breakdownMultiplier;
    }

    public int getStartBudget() {
        return startBudget;
    }

    public double getBreakdownMultiplier() {
        return breakdownMultiplier;
    }
}