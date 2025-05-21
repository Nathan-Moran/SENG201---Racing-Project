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
     * Initializes the maps for locked cars and tuning parts, and populates
     * the master lists of all available items.
     * @param gameEnvironment The {@link GameEnvironment} instance for accessing item catalogue.
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
     * Populates the master lists with standard, non-starter items from the item catalogue.
     * This includes all cars and tuning parts that can eventually appear in the shop,
     * whether initially available or locked.
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
     * This method associates specific cars and tuning parts with the {@link Course}
     * that needs to be completed to unlock them.
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
     * Populates the shop's initial inventory with a randomized selection of available cars and tuning parts.
     * This method is typically called at the start of a new shop session to display items for purchase.
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

    /**
     * Retrieves the master list of all cars that can potentially be available in the shop.
     * @return A {@link List} of all available {@link Car} objects.
     */
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

    /**
     * Removes a specific car from the master list of all available cars.
     * This is typically called when a car is purchased by the player.
     * @param car The {@link Car} to be removed.
     */
    public void removeCarFromAllAvailable(Car car) {
        allAvailableCars.remove(car);
    }

    /**
     * Removes a specific tuning part from the master list of all available tuning parts.
     * This is typically called when a tuning part is purchased by the player.
     * @param part The {@link TuningPart} to be removed.
     */
    public void removePartFromAllAvailable(TuningPart part) {
        allAvailableTuningParts.remove(part);
    }

    /**
     * Adds a specific car back to the master list of all available cars.
     * This is typically called when a car is sold by the player back to the shop.
     * @param car The {@link Car} to be added.
     */
    public void addCarToAllAvailable(Car car) {
        allAvailableCars.add(car);
    }

    /**
     * Adds a specific tuning part back to the master list of all available tuning parts.
     * This is typically called when a tuning part is sold by the player back to the shop.
     * @param part The {@link TuningPart} to be added.
     */
    public void addPartToAllAvailable(TuningPart part) {
        allAvailableTuningParts.add(part);
    }

    public List<TuningPart> getAllAvailableTuningParts() {
        return allAvailableTuningParts;
    }
}
