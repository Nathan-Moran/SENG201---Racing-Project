package seng201.team0.models;

public enum Course {
    DESERT("Desert", 100, new CoursePrizes(300, 200, 150), 4),
    MOUNTAIN("Mountain", 200, new CoursePrizes(600, 400, 300), 3),
    COUNTRY("Country", 300, new CoursePrizes(900, 600, 450), 4),
    CITY("City", 500, new CoursePrizes(1500, 1000, 750), 2);

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