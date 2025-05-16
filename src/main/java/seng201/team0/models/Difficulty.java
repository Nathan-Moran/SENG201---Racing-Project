package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public enum Difficulty {
    EASY(1000, 0.6, 50),
    MEDIUM(800, 0.4, 60),
    HARD(600, 0.3, 70);

    private final int startBudget;
    private final double randomEventRate;
    private final int opponentSpeed;

    Difficulty(int startBudget, double randomEventRate, int opponentSpeed) {
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

    public int getOpponentSpeed() {
        return opponentSpeed;
    }

    public List<OpponentCar> generateOpponents(int count) {
        List<OpponentCar> opponents = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Calculate speed with difference of 5 km/h for each opponent
            int speed = opponentSpeed + (i * 5);
            opponents.add(new OpponentCar(speed));
        }
        return opponents;
    }
}