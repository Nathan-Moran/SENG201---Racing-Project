package seng201.team0;

public class Difficulty {
    private String difficulty;
    private double successRate;
    private int startBudget;
    private double randomEventRate;

    public String getDifficulty() {
        return difficulty;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getStartBudget() {
        return startBudget;
    }

    public double getRandomEventRate() {
        return randomEventRate;
    }

    public void setSuccessRate(double successRate) {
        switch (difficulty) {
            case "easy" -> this.successRate = 0.9;
            case "medium" -> this.successRate = 0.8;
            case "hard" -> this.successRate = 0.7;
        }
    }

    public void setStartBudget(int startBudget) {
        switch (difficulty) {
            case "easy" -> this.startBudget = 1000;
            case "medium" -> this.startBudget = 800;
            case "hard" -> this.startBudget = 600;
        }
    }

    public void setRandomEventRate(double randomEventRate) {
        switch (difficulty) {
            case "easy" -> this.randomEventRate = 0.6;
            case "medium" -> this.randomEventRate = 0.4;
            case "hard" -> this.randomEventRate = 0.3;
        }
    }
}
