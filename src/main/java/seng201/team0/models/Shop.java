package seng201.team0.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop extends ItemStorage {
    private Map<Course, Car> lockedCarsMap = new HashMap<>();
    private Map<Course, List<TuningPart>> lockedTuningPartMap = new HashMap<>();

    public Shop() {
        super();
        this.lockedCarsMap = new HashMap<>();
        this.lockedTuningPartMap = new HashMap<>();
    }

    public void setShopInventory() {
        addTuningPart(new TuningPart("Ethanol", 250, "\uD83D\uDCA8", 1.1));
        addTuningPart(new TuningPart("StreetWheels", 250, "\uD83C\uDFAE", 1.1));
        addCar(new Car("Toyota Supra", 0.85, 0.85, 0.80, 23.0, 6500));
        addCar(new Car("Mustang", 0.92, 0.78, 0.80, 19.0, 6800));
        addCar(new Car("Ferrari 458", 0.96, 0.96, 0.90, 15.0, 12500));
        lockedTuningPartMap.put(Course.DESERT, (List<TuningPart>) Arrays.asList(
                new TuningPart("SuperCharger", 1000, "\uD83D\uDCA8", 1.3), new TuningPart("SportsWheels", 1000, "\uD83C\uDFAE", 1.3)
        ));
        lockedTuningPartMap.put(Course.CITY, (List<TuningPart>) Arrays.asList(
                new TuningPart("TurboKit", 2500, "\uD83D\uDCA8", 1.5), new TuningPart("RacingWheels", 2500, "\uD83C\uDFAE", 1.5)
        ));
        lockedCarsMap.put(Course.DESERT, new Car("Dune Drifter", 0.5, 0.8, 0.7, 22.0, 2200)); // Unlocked by Desert Course
        lockedCarsMap.put(Course.MOUNTAIN, new Car("Sandstorm Strider", 0.8, 0.5, 0.8, 25.0, 2800)); // Unlocked by Mountain Course
        lockedCarsMap.put(Course.COUNTRY, new Car("Cliff Climber", 0.6, 0.5, 0.9, 20.0, 2600)); // Unlocked by Country Course
        lockedCarsMap.put(Course.CITY, new Car("Ridge Racer", 0.5, 0.9, 0.7, 28.0, 3000)); // Unlocked by City Course
    }

    public Map<Course, Car> getLockedCarsMap() {
        return lockedCarsMap;
    }

    public void removeLockedCar(Course course) {
        lockedCarsMap.remove(course);
    }

    public Map<Course, List<TuningPart>> getLockedTuningPartMap() {
        return lockedTuningPartMap;
    }

    public void removeLockedTuningParts(Course course) {
        lockedTuningPartMap.remove(course);
    }
}