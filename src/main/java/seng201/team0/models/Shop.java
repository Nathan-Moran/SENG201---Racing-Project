package seng201.team0.models;

public class Shop extends ItemStorage {

    public Shop() {
        super();
    }

    public void setShopInventory() {
        Car ToyotaSupra = new Car("Toyota Supra", 0.85, 0.85, 0.80, 23.0, 6500);
        Car Mustang = new Car("Mustang", 0.92, 0.78, 0.80, 19.0, 6800);
        Car Ferrari458 = new Car("Ferrari 458", 0.96, 0.96, 0.90, 15.0, 12500);

        TuningPart Ethanol = new TuningPart("Ethanol", 500, "\uD83D\uDCA8", 1.2);
        TuningPart SuperCharger = new TuningPart("SuperCharger", 750, "\uD83D\uDCA8", 1.5);
        TuningPart TurboKit = new TuningPart("TurboKit", 1750, "\uD83D\uDCA8", 1.8);

        TuningPart StreetWheels = new TuningPart("StreetWheels", 400, "\uD83C\uDFAE", 1.2);
        TuningPart SportWheels = new TuningPart("SportsWheels", 800, "\uD83C\uDFAE", 1.5);
        TuningPart RacingWheels = new TuningPart("RacingWheels", 1250, "\uD83C\uDFAE", 1.8);


        Car DuneDrifter = new Car("Dune Drifter", 0.5, 0.8, 0.7, 22.0, 2200);
        Car SandstormStrider = new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25.0, 2800);
        Car CliffClimber = new Car("Cliff Climber", 0.6, 0.5, 0.9, 20.0, 2600);
        Car RidgeRacer = new Car("Ridge Racer", 0.5, 0.9, 0.7, 28.0, 3000);
        Car FarmersFlyer = new Car("Farmers Flyer", 0.9, 0.4, 0.9, 30.0, 3800);
        Car VineyardViper = new Car("Vineyard Viper", 0.6, 0.8, 0.8, 24.0, 3000);
        Car AlleyCat = new Car("Alley Cat", 0.8, 0.5, 0.7, 23.0, 2600);
        Car CommuterKing = new Car("Commuter King", 0.4, 0.7, 0.9, 28.0, 3300);


        addCar(ToyotaSupra);
        addCar(Mustang);
        addCar(Ferrari458);

        addTuningPart(Ethanol);
        addTuningPart(SuperCharger);
        addTuningPart(TurboKit);
        addTuningPart(StreetWheels);
        addTuningPart(SportWheels);
        addTuningPart(RacingWheels);

    }
}