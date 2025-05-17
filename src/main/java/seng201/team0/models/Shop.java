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