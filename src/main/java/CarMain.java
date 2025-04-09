public class CarMain {

    public static void main(String[] args) {

        //Setup Shop
        Car Mustang = new Car("Mustang", 7, 8, 6, 3, 220000);
        Car Ferrari = new Car("Ferrari", 8, 10, 7, 5, 320000);
        Car Bugatti = new Car("Bugatti", 10, 7, 5, 1, 3900000);

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
        Car Honda = new Car("Honda", 120, 8, 9, 7, 45000);
        garage.setSelectedCar(Honda);
    }
}
