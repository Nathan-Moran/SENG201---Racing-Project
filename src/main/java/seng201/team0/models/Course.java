package seng201.team0.models;

public enum Course {
    DESERT("Desert", 0, new CoursePrizes(500, 350, 250), 4),
    MOUNTAIN("Mountain", 250, new CoursePrizes(1000, 750, 500), 3),
    COUNTRY("Country", 500, new CoursePrizes(1500, 1000, 800), 4),
    CITY("City", 1000, new CoursePrizes(3000, 0, 0), 2);

    private String name;
    private int entryFee;
    private CoursePrizes prizes;
    private int numberOfOpponents;

    Course(String name, int entryFee, CoursePrizes prizes, int numberOfOpponents) {
        this.name = name;
        this.entryFee = entryFee;
        this.prizes = prizes;
        this.numberOfOpponents = numberOfOpponents;
    }

    public String getName() {
        return name;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public CoursePrizes getPrizes() {
        return prizes;
    }

    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }
}