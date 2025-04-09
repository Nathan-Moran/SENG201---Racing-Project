public enum Route {
    DESERT_DRIFT("Desert Fast"),
    DESERT_LONG("Desert Long"),
    MOUNTAIN_STEEP("Mountain Circuit"),
    MOUNTAIN_CURVY("Mountain Rapid"),
    COUNTRYSIDE_LONG_STRAIGHT("Countryside Straight"),
    COUNTRYSIDE_SHORT_CURVES("Countryside Twisty"),
    CITY_WIDE_LINE("City Sprint"),
    CITY_CUT_LINE("City Cross");

    private String description;
    private Course course;
    private int fuelStops;
    private double speedAdvantage;
    private double handlingAdvantage;
    private double reliabilityAdvantage;

    public Route(String description, Course course, int fuelStops) {
        this.description = description;
        this.course = course;
        this.fuelStops = fuelStops;
    }

    public double getReliabilityAdvantage() {
        return reliabilityAdvantage;
    }

    public double getSpeedAdvantage() {
        return speedAdvantage;
    }

    public double getHandlingAdvantage() {
        return handlingAdvantage;
    }


}
