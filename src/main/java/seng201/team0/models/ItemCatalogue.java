package seng201.team0.models;

public class ItemCatalogue extends ItemStorage{
    public ItemCatalogue() {
        super();
        setupCatalogue();
    }

    public void setupCatalogue() {
        addCar(new Car("Honda Civic R", 0.6, 0.5, 0.7, 20, 1000));
        addCar(new Car("Mazda MPS", 0.5, 0.7, 0.7, 20, 1000));
        addCar(new Car("Nissan Z", 0.5, 0.6, 0.8, 20, 1000));

        addCar(new Car("Toyota Supra", 0.85, 0.85, 0.80, 23.0, 6500));
        addCar(new Car("Mustang", 0.92, 0.78, 0.80, 19.0, 6800));
        addCar(new Car("Ferrari 458", 0.96, 0.96, 0.90, 15.0, 12500));

        addTuningPart(new TuningPart("Ethanol", 250, "\uD83D\uDCA8", 1.1));
        addTuningPart(new TuningPart("SuperCharger", 1000, "\uD83D\uDCA8", 1.3));
        addTuningPart(new TuningPart("TurboKit", 2500, "\uD83D\uDCA8", 1.5));
        addTuningPart(new TuningPart("StreetWheels", 250, "\uD83C\uDFAE", 1.1));
        addTuningPart(new TuningPart("SportsWheels", 1000, "\uD83C\uDFAE", 1.3));
        addTuningPart(new TuningPart("RacingWheels", 2500, "\uD83C\uDFAE", 1.5));

        addCar(new Car("Dune Drifter", 0.5, 0.8, 0.7, 22.0, 2200));         //Desert Drift
        addCar(new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25.0, 2800));    //Desert Long
        addCar(new Car("Cliff Climber", 0.6, 0.5, 0.9, 20.0, 2600));        //Mountain Steep
        addCar(new Car("Ridge Racer", 0.5, 0.9, 0.7, 28.0, 3000));          //Mountain Curves
        addCar(new Car("Farmers Flyer", 0.9, 0.4, 0.9, 30.0, 3800));        //Country Straight
        addCar(new Car("Vineyard Viper", 0.6, 0.8, 0.8, 24.0, 3000));       //Country Twist
        addCar(new Car("Alley Cat", 0.8, 0.5, 0.7, 23.0, 2600));            //City Alleys
        addCar(new Car("Commuter King", 0.4, 0.7, 0.9, 28.0, 3300));        //City Traffic
    }
}
