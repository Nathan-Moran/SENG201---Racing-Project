package seng201.team0.models;

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
    /**
     * A map storing lists of tuning parts that are initially locked, mapped to the {@link Course}
     * whose completion unlocks them.
     */
    private Map<Course, List<TuningPart>> lockedTuningPartMap;
    List<Car> allAvailableCars;
    List<TuningPart> allAvailableTuningParts;

    /**
     * Constructs a new Shop instance.
     * Initializes the maps for locked cars and tuning parts.
     */
    public Shop() {
        super();
        this.lockedCarsMap = new HashMap<>();
        this.lockedTuningPartMap = new HashMap<>();
        this.allAvailableCars = new ArrayList<>();
        this.allAvailableTuningParts = new ArrayList<>();

        populateBaseMasterLists();
        setupLockedItems();
    }

    /**
     * Populates the master lists with standard, non-starter items.
     */
    private void populateBaseMasterLists() {
        // Standard cars available in the shop's random pool
        this.allAvailableCars.add(new Car("Toyota Supra", 0.85, 0.85, 0.80, 23, 6500));
        this.allAvailableCars.add(new Car("Mustang", 0.92, 0.78, 0.80, 19, 6800));
        this.allAvailableCars.add(new Car("Ferrari", 0.96, 0.96, 0.90, 15, 12500));
        // Add any other standard cars you want in the initial random pool here

        // Standard tuning parts
        this.allAvailableTuningParts.add(new TuningPart("Ethanol", 250, "\uD83D\uDCA8", 1.1));
        this.allAvailableTuningParts.add(new TuningPart("StreetWheels", 250, "\uD83C\uDFAE", 1.1));
        this.allAvailableTuningParts.add(new TuningPart("SuperCharger", 1000, "\uD83D\uDCA8", 1.3));
        this.allAvailableTuningParts.add(new TuningPart("SportsWheels", 1000, "\uD83C\uDFAE", 1.3));
        this.allAvailableTuningParts.add(new TuningPart("TurboKit", 2500, "\uD83D\uDCA8", 1.5));
        this.allAvailableTuningParts.add(new TuningPart("RacingWheels", 2500, "\uD83C\uDFAE", 1.5));
        // Add any other standard tuning parts
    }

    /**
     * Sets up the mappings for items that are initially locked.
     */
    private void setupLockedItems() {
        // Locked cars
        lockedCarsMap.put(Course.DESERT, new Car("Dune Drifter", 0.5, 0.8, 0.7, 22, 2200));
        lockedCarsMap.put(Course.MOUNTAIN, new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25, 2800));
        lockedCarsMap.put(Course.COUNTRY, new Car("Cliff Climber", 0.6, 0.5, 0.9, 20, 2600));
        lockedCarsMap.put(Course.CITY, new Car("Ridge Racer", 0.5, 0.9, 0.7, 28, 3000));
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

    /**
     * Gets the map of tuning parts that are currently locked, associated with the course
     * that unlocks them.
     * @return A {@link Map} where keys are {@link Course} and values are {@link List} of {@link TuningPart} objects.
     */
    public Map<Course, List<TuningPart>> getLockedTuningPartMap() {
        return lockedTuningPartMap;
    }

    /**
     * Removes a list of locked tuning parts from the map, typically after they have been unlocked and
     * moved to the main shop inventory.
     * @param course The {@link Course} associated with the locked tuning parts to be removed.
     */
    public void removeLockedTuningParts(Course course) {
        lockedTuningPartMap.remove(course);
    }
}