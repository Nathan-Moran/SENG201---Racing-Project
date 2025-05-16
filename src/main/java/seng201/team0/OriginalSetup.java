package seng201.team0;

import seng201.team0.models.Car;
import seng201.team0.models.Garage;
import seng201.team0.models.Shop;
import seng201.team0.models.TuningPart;

public class OriginalSetup {

    public static void main(String[] args) {

        //Setup Shop
        Car ToyotaSupra = new Car("Toyota Supra", 180, 6, 8, 538, 67000);
        Car Mustang = new Car("Mustang", 250, 6, 6, 400, 89000);
        Car Ferrari458 = new Car("Ferrari 458", 330, 8, 7, 300, 485000);


        //Car Bugatti = new Car("Bugatti", 420, 7, 5, 150, 4750000);

        Shop shop = new Shop();
        shop.addCarsForPurchase(ToyotaSupra);
        shop.addCarsForPurchase(Mustang);
        shop.addCarsForPurchase(Ferrari458);
        //shop.addCarsForPurchase(Bugatti);

        TuningPart Ethanol = new TuningPart("Ethanol", 4000, "Speed", 1.2);
        TuningPart SuperCharger = new TuningPart("SuperCharger", 14000, "Speed", 1.5);
        TuningPart TurboKit = new TuningPart("TurboKit", 21000, "Speed", 1.8);

        TuningPart StreetTyres = new TuningPart("StreetWheels", 2500, "Handling", 1.2);
        TuningPart SportsTyres = new TuningPart("SportsWheels", 6400, "Handling", 1.5);
        TuningPart RacingTyres = new TuningPart("RacingWheels", 10000, "Handling", 1.8);

        shop.addTuningPartsForPurchase(Ethanol);
        shop.addTuningPartsForPurchase(SuperCharger);
        shop.addTuningPartsForPurchase(TurboKit);
        shop.addTuningPartsForPurchase(StreetTyres);
        shop.addTuningPartsForPurchase(SportsTyres);
        shop.addTuningPartsForPurchase(RacingTyres);

        //Setup Garage
        Garage garage = new Garage();
        Car HondaCivicR = new Car("Honda Civic R", 180, 7, 7, 500, 32000);
        Car ToyotaGRCorolla  =  new Car("Toyota Corolla", 180, 7, 8, 500, 32000);
        Car MazdaMPS = new Car("Mazda MPS", 180, 6, 7, 500, 32000);
        Car NissanZ = new Car("Nissan Z", 180, 7, 8, 500, 32000);

        //garage.setSelectedCar(Honda);
    }
}
