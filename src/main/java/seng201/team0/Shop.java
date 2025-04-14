package seng201.team0;

import java.util.ArrayList;

public class Shop {
    private int currency;
    ArrayList<Car> CarsforPurchase;
    ArrayList<TuningPart> TuningPartsforPurchase;
    private Garage garage;

    public Shop() {
        this.currency = 0;
        this.CarsforPurchase = new ArrayList<>();
        this.TuningPartsforPurchase = new ArrayList<>();
    }

    public void sellCar(Car car) {
        currency += car.getPrice();
        CarsforPurchase.add(car); //re-adds car to shop (might want to delete car forever)
        if (car.getSpeedUpgrade() != null) {
            currency += car.getSpeedUpgrade().getPrice();
        }
        if (car.getHandlingUpgrade() != null) {
            currency += car.getHandlingUpgrade().getPrice();
        }
        garage.removeCar(car);
    }

    public void buyCar(Car car) {
        currency -= car.getPrice();
        CarsforPurchase.remove(car);
        garage.addCar(car);
    }

    public void addCarsForPurchase(Car car) {
        CarsforPurchase.add(car);
    }

    public void addTuningPartsForPurchase(TuningPart part) {
        TuningPartsforPurchase.add(part);
    }
}
