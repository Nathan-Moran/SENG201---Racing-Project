public class CarMain {

    public static void main(String[] args) {

        //Setup Shop
        Car Mustang = new Car("Mustang", 250, 6, 6, 300, 89000);
        Car Ferrari = new Car("Ferrari", 330, 8, 7, 200, 485000);
        Car Bugatti = new Car("Bugatti", 420, 7, 5, 150, 4750000);

        Shop shop = new Shop();
        shop.addCarsForPurchase(Mustang);
        shop.addCarsForPurchase(Ferrari);
        shop.addCarsForPurchase(Bugatti);

        TuningPart V4 = new TuningPart("V4", 200, "Speed", 1.1);
        TuningPart V6 = new TuningPart("V6", 300, "Speed", 1.2 );
        TuningPart V8 = new TuningPart("V8", 500, "Speed", 1.3 );

        TuningPart StreetWheel = new TuningPart("StreetWheel", 400, "Handling", 1.1 );
        TuningPart SportsWheel = new TuningPart( "SportsWheel", 500, "Handling", 1.2 );
        TuningPart RacingWheel = new TuningPart( "RacingWheel", 600, "Handling", 1.3 );

        shop.addTuningPartsForPurchase(V4);
        shop.addTuningPartsForPurchase(V6);
        shop.addTuningPartsForPurchase(V8);
        shop.addTuningPartsForPurchase(StreetWheel);
        shop.addTuningPartsForPurchase(SportsWheel);
        shop.addTuningPartsForPurchase(RacingWheel);

        //Setup Garage
        Garage garage = new Garage();
        Car Honda = new Car("Honda", 180, 7, 8, 500, 32000);
        garage.setSelectedCar(Honda);
    }
}
