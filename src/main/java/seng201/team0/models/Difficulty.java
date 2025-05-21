package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
    EASY(1500, 0.6),
    MEDIUM(1250, 0.4),
    HARD(1000, 0.3);

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