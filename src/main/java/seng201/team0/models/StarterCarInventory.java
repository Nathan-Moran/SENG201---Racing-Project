package seng201.team0.models;

public class StarterCarInventory extends ItemStorage {
    public StarterCarInventory() {
        super();
    }

    public void setupStarterCarInventory() {
        Car HondaCivicR = new Car("Honda Civic R", 0.6, 0.5, 0.7, 20, 1000);
//        Car ToyotaGRCorolla  =  new Car("Toyota Corolla", 180, 7, 8, 500, 32000);
        Car MazdaMPS = new Car("Mazda MPS", 0.5, 0.7, 0.7, 20, 1000);
        Car NissanZ = new Car("Nissan Z", 0.5, 0.6, 0.8, 20, 1000);

        addCar(HondaCivicR);
        addCar(MazdaMPS);
        addCar(NissanZ);
    }
}
