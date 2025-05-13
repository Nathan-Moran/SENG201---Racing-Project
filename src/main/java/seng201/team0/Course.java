package seng201.team0;

import java.util.List;

public enum Course {
    DESERT("Desert", new CourseAttributes(1.2, 0.8, 1.0), 100, new CoursePrizes(300, 200, 150), 4),
    MOUNTAIN("Mountain", new CourseAttributes(0.9, 1.5, 1.1), 200, new CoursePrizes(600, 400, 300), 3),
    COUNTRY("Country", new CourseAttributes(1.1, 1.0, 0.9), 300, new CoursePrizes(900, 600, 450), 4),
    CITY("City", new CourseAttributes(1.5, 1.2, 0.5), 500, new CoursePrizes(1500, 1000, 750), 2);

    private String name;
    private CourseAttributes attributes;
    private int entryFee;
    private CoursePrizes prizes;
    private int numberOfOpponents;

    Course(String name, CourseAttributes attributes, int entryFee, CoursePrizes prizes, int numberOfOpponents) {
        this.name = name;
        this.attributes = attributes;
        this.entryFee = entryFee;
        this.prizes = prizes;
        this.numberOfOpponents = numberOfOpponents;
    }

    public String getName() {
        return name;
    }

    public CourseAttributes getAttributes() {
        return attributes;
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