package seng201.team0.models;

import seng201.team0.services.GameEnvironment;

import java.util.*;

/**
 * Represents the in-game shop where players can buy and sell cars and tuning parts.
 * It extends {@link ItemStorage} to manage the general inventory of items available for sale.
 * Additionally, it manages specific cars and tuning parts that are initially locked
 * and become available upon completing certain courses.
 */
public class Shop extends ItemStorage {
    /**
     * A map storing cars that are initially locked, mapped to the {@link Course}
     * whose completion unlocks them.
     */
    private Map<Course, Car> lockedCarsMap;

    private List<Car> allAvailableCars;
    private List<TuningPart> allAvailableTuningParts;
    private GameEnvironment gameEnvironment;
    private ArrayList<Car> lockedCarList;

    /**
     * Constructs a new Shop instance.
     * Initializes the maps for locked cars and tuning parts.
     */
    public Shop(GameEnvironment gameEnvironment) {
        super();
        this.gameEnvironment = gameEnvironment;
        this.lockedCarsMap = new HashMap<>();
        this.allAvailableCars = new ArrayList<>();
        this.allAvailableTuningParts = new ArrayList<>();
        this.lockedCarList = new ArrayList<>();

        populateBaseMasterLists();
        setupLockedItems();
    }

    /**
     * Populates the master lists with standard, non-starter items.
     */
    private void populateBaseMasterLists() {
        ItemCatalogue catalogue = gameEnvironment.getItemCatalogue();
        if (catalogue != null) {
            this.allAvailableCars.clear();
            for (Car shopCar : catalogue.getShopCarList()) {
                this.allAvailableCars.add(shopCar);
            }

            this.allAvailableTuningParts.clear();
            for (TuningPart part : catalogue.getTuningPartList()) {
                this.allAvailableTuningParts.add(part);
            }
            this.lockedCarList.clear();
            for (Car lockedCar : catalogue.getLockedCarList()) {
                this.lockedCarList.add(lockedCar);
            }
        }
    }

    /**
     * Sets up the mappings for items that are initially locked.
     */
    private void setupLockedItems() {

        this.lockedCarsMap.clear();

        for (Car carFromPool : this.lockedCarList) {
            switch (carFromPool.getName()) {
                case "Dune Drifter":
                    this.lockedCarsMap.put(Course.DESERT, carFromPool);
                    break;
                case "Sandstorm Strider":
                    this.lockedCarsMap.put(Course.MOUNTAIN, carFromPool);
                    break;
                case "Cliff Climber":
                    this.lockedCarsMap.put(Course.COUNTRY, carFromPool);
                    break;
                case "Ridge Racer":
                    this.lockedCarsMap.put(Course.CITY, carFromPool);
                    break;
            }
        }
    }

    /**
     * Populates the shop's initial inventory with available cars and tuning parts.
     * This method also sets up the mapping for locked items that will be unlocked
     * by winning specific courses.
     */
    public void setShopInventory() {
        getCarList().clear();
        getTuningPartList().clear();

        Collections.shuffle(allAvailableCars);
        int carsToDisplayCount = Math.min(3, allAvailableCars.size());
        for (int i = 0; i < carsToDisplayCount; i++) {
            super.addCar(allAvailableCars.get(i));
        }

        Collections.shuffle(allAvailableTuningParts);
        int partsToDisplayCount = Math.min(3, allAvailableTuningParts.size());
        for (int i = 0; i < partsToDisplayCount; i++) {
            super.addTuningPart(allAvailableTuningParts.get(i));
        }
    }

    public List<Car> getAllAvailableCars() {
        return allAvailableCars;
    }

    /**
     * Gets the map of cars that are currently locked, associated with the course
     * that unlocks them.
     * @return A {@link Map} where keys are {@link Course} and values are {@link Car} objects.
     */
    public Map<Course, Car> getLockedCarsMap() {
        return lockedCarsMap;
    }

    /**
     * Removes a specific locked car from the map, typically after it has been unlocked and
     * moved to the main shop inventory.
     * @param course The {@link Course} associated with the locked car to be removed.
     */
    public void removeLockedCar(Course course) {
        lockedCarsMap.remove(course);
    }

    public void removeCarFromAllAvailable(Car car) {
        allAvailableCars.remove(car);
    }

    public void removePartFromAllAvailable(TuningPart part) {
        allAvailableTuningParts.remove(part);
    }

    public void addCarToAllAvailable(Car car) {
        allAvailableCars.add(car);
    }

    public void addPartToAllAvailable(TuningPart part) {
        allAvailableTuningParts.add(part);
    }

    public List<TuningPart> getAllAvailableTuningParts() {
        return allAvailableTuningParts;
    }
}