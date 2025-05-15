package seng201.team0.models;

public class RouteAttributes {
    private double speedAdvantage;
    private double handlingAdvantage;
    private double reliabilityAdvantage;

    public RouteAttributes(double speedAdvantage, double handlingAdvantage, double reliabilityAdvantage) {
        this.speedAdvantage = speedAdvantage;
        this.handlingAdvantage = handlingAdvantage;
        this.reliabilityAdvantage = reliabilityAdvantage;
    }

    public double getSpeedAdvantage() {
        return speedAdvantage;
    }

    public double getHandlingAdvantage() {
        return handlingAdvantage;
    }

    public double getReliabilityAdvantage() {
        return reliabilityAdvantage;
    }
}