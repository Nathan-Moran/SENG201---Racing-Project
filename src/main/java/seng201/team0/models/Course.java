package seng201.team0.models;

/**
 * Represents different race courses available in the game, each with unique characteristics.
 * Each course has a name, an entry fee, specific prize money for different placements,
 * and a defined number of opponents.
 */
public enum Course {
    /**
     * The Desert course, offering a starting challenge with moderate entry fees and prizes.
     */
    DESERT("Desert", 0, new CoursePrizes(500, 350, 250), 4),
    /**
     * The Mountain course, a step up in difficulty with higher entry fees and increased prizes.
     */
    MOUNTAIN("Mountain", 250, new CoursePrizes(1000, 750, 500), 3),
    /**
     * The Country course, providing a significant challenge with higher stakes and rewards.
     */
    COUNTRY("Country", 500, new CoursePrizes(1500, 1000, 800), 4),
    /**
     * The City course, the ultimate challenge with the highest entry fee and top prize,
     * but only prizes for 1st place.
     */
    CITY("City", 1000, new CoursePrizes(3000, 0, 0), 2);

    /**
     * The display name of the course.
     */
    private final String name;
    /**
     * The money required to enter this course's race.
     */
    private final int entryFee;
    /**
     * The prize money awarded for 1st, 2nd, and 3rd place in this course.
     */
    private final CoursePrizes prizes;
    /**
     * The number of AI opponents the player will race against on this course.
     */
    private final int numberOfOpponents;

    /**
     * Constructs a new Course enum constant.
     *
     * @param name The display name of the course.
     * @param entryFee The entry fee for the course.
     * @param prizes The {@link CoursePrizes} object detailing the prize money for placements.
     * @param numberOfOpponents The number of AI opponents in the race.
     */
    Course(String name, int entryFee, CoursePrizes prizes, int numberOfOpponents) {
        this.name = name;
        this.entryFee = entryFee;
        this.prizes = prizes;
        this.numberOfOpponents = numberOfOpponents;
    }

    /**
     * Gets the display name of the course.
     * @return The name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the entry fee for the course.
     * @return The entry fee.
     */
    public int getEntryFee() {
        return entryFee;
    }

    /**
     * Gets the prize money details for the course.
     * @return A {@link CoursePrizes} object containing the prize money for different placements.
     */
    public CoursePrizes getPrizes() {
        return prizes;
    }

    /**
     * Gets the number of opponents for this course.
     * @return The number of AI opponents.
     */
    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }
}