package seng201.team0.models;

public class Shop extends ItemStorage {

    public Shop() {
        super();
    }

    public void setShopInventory() {
        Car Toyota = new Car("Toyota Supra", 0.5, 0.5, 0.8, 15, 2000);
//        Car Mustang = new Car("Mustang", 250, 6, 6, 400, 89000);
        Car Ferrari = new Car("Ferrari 458", 1, 0.7, 0.7, 10, 3000);

        TuningPart Ethanol = new TuningPart("Ethanol", 500, "\uD83D\uDCA8", 1.2);
        TuningPart SuperCharger = new TuningPart("SuperCharger", 750, "\uD83D\uDCA8", 1.5);
        TuningPart TurboKit = new TuningPart("TurboKit", 1750, "\uD83D\uDCA8", 1.8);

        TuningPart StreetWheels = new TuningPart("StreetWheels", 400, "\uD83C\uDFAE", 1.2);
        TuningPart SportWheels = new TuningPart("SportsWheels", 800, "\uD83C\uDFAE", 1.5);
        TuningPart RacingWheels = new TuningPart("RacingWheels", 1250, "\uD83C\uDFAE", 1.8);

        // Starter Cars (for reference)
        Car HondaCivicR = new Car("Honda Civic R", 0.6, 0.5, 0.7, 20.0, 1000);
        Car MazdaMPS = new Car("Mazda MPS", 0.5, 0.7, 0.7, 20.0, 1000);
        Car NissanZ = new Car("Nissan Z", 0.5, 0.6, 0.8, 20.0, 1000);


        Car DuneDrifter = new Car("Dune Drifter", 0.5, 0.8, 0.7, 22.0, 1800);            // DESERT_DRIFT
        Car SandstormStrider = new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25.0, 2200); // DESERT_LONG


        Car CliffClimber = new Car("Cliff Climber", 0.6, 0.5, 0.9, 20.0, 2500);         // MOUNTAIN_STEEP
        Car RidgeRacer = new Car("Ridge Racer", 0.5, 0.9, 0.7, 28.0, 2800);             // MOUNTAIN_CURVES


        Car FarmersFlyer = new Car("Farmers Flyer", 0.9, 0.4, 0.9, 30.0, 3500);         // COUNTRY_STRAIGHT
        Car VineyardViper = new Car("Vineyard Viper", 0.6, 0.8, 0.8, 24.0, 3000);       // COUNTRY_TWISTY


        Car AlleyCat = new Car("Alley Cat", 0.8, 0.5, 0.7, 23.0, 3300);                 // CITY_ALLEYS
        Car CommuterKing = new Car("Commuter King", 0.4, 0.7, 0.9, 28.0, 3800);         // CITY_TRAFFIC


        addCar(Toyota);
//        addCar(Mustang);
        addCar(Ferrari);

        addTuningPart(Ethanol);
        addTuningPart(SuperCharger);
        addTuningPart(TurboKit);
        addTuningPart(StreetWheels);
        addTuningPart(SportWheels);
        addTuningPart(RacingWheels);

    }
}