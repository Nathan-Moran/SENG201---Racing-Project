package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
    EASY(1500, 0.6, 1),
    MEDIUM(1250, 0.4, 1.1),
    HARD(1000, 0.3, 1.2);

    private final int startBudget;
    private final double randomEventRate;
    private final double opponentSpeed;

    Difficulty(int startBudget, double randomEventRate, double opponentSpeed) {
        this.startBudget = startBudget;
        this.randomEventRate = randomEventRate;
        this.opponentSpeed = opponentSpeed;
    }

    public int getStartBudget() {
        return startBudget;
    }

    public double getRandomEventRate() {
        return randomEventRate;
    }

    public double getOpponentSpeed() {
        return opponentSpeed;
    }

}