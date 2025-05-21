package seng201.team0.models;

/**
 * Represents the prize money awarded for different placements in a race course.
 * This class stores the prize amounts for 1st, 2nd, and 3rd place.
 */
public class CoursePrizes {
    /**
     * The prize money awarded for finishing in 1st place.
     */
    private final int firstPlacePrize;
    /**
     * The prize money awarded for finishing in 2nd place.
     */
    private final int secondPlacePrize;
    /**
     * The prize money awarded for finishing in 3rd place.
     */
    private final int thirdPlacePrize;

    /**
     * Constructs a new CoursePrizes object with specified prize amounts.
     *
     * @param firstPlacePrize The prize for 1st place.
     * @param secondPlacePrize The prize for 2nd place.
     * @param thirdPlacePrize The prize for 3rd place.
     */
    public CoursePrizes(int firstPlacePrize, int secondPlacePrize, int thirdPlacePrize) {
        this.firstPlacePrize = firstPlacePrize;
        this.secondPlacePrize = secondPlacePrize;
        this.thirdPlacePrize = thirdPlacePrize;
    }

    /**
     * Gets the prize money for 1st place.
     * @return The 1st place prize amount.
     */
    public int getFirstPlacePrize() {
        return firstPlacePrize;
    }

    /**
     * Gets the prize money for 2nd place.
     * @return The 2nd place prize amount.
     */
    public int getSecondPlacePrize() {
        return secondPlacePrize;
    }

    /**
     * Gets the prize money for 3rd place.
     * @return The 3rd place prize amount.
     */
    public int getThirdPlacePrize() {
        return thirdPlacePrize;
    }

    /**
     * Returns a string representation of the course prizes.
     * @return A formatted string showing prizes for 1st, 2nd, and 3rd place.
     */
    @Override
    public String toString() {
        return "Prizes -> 1st: $" + firstPlacePrize +
                ", 2nd: $" + secondPlacePrize +
                ", 3rd: $" + thirdPlacePrize;
    }
}