package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.ItemStorage;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteItemStorage extends ItemStorage {
    public ConcreteItemStorage() {
        super();
    }
}

class ItemStorageTest {

    private ItemStorage itemStorage;
    private Car car1;
    private Car car2;
    private TuningPart part1;
    private TuningPart part2;

    @BeforeEach
    void setUp() {
        itemStorage = new ConcreteItemStorage();
        car1 = new Car("Test Car 1", 0.5, 0.5, 0.5, 10, 1000);
        car2 = new Car("Test Car 2", 0.6, 0.6, 0.6, 12, 1200);
        part1 = new TuningPart("Test Part 1", 100, "💨", 1.1);
        part2 = new TuningPart("Test Part 2", 120, "🎮", 1.2);
    }

    @Test
    void addCarSuccessfully() {
        assertTrue(itemStorage.getCarList().isEmpty());
        itemStorage.addCar(car1);
        assertEquals(1, itemStorage.getCarList().size());
        assertTrue(itemStorage.getCarList().contains(car1));
    }

    @Test
    void addNullCar() {
        assertTrue(itemStorage.getCarList().isEmpty());
        itemStorage.addCar(null);
        assertTrue(itemStorage.getCarList().isEmpty());
    }

    @Test
    void addMultipleCars() {
        itemStorage.addCar(car1);
        itemStorage.addCar(car2);
        assertEquals(2, itemStorage.getCarList().size());
        assertTrue(itemStorage.getCarList().contains(car1));
        assertTrue(itemStorage.getCarList().contains(car2));
    }

    @Test
    void removeCarSuccessfully() {
        itemStorage.addCar(car1);
        itemStorage.addCar(car2);
        assertEquals(2, itemStorage.getCarList().size());

        itemStorage.removeCar(car1);
        assertEquals(1, itemStorage.getCarList().size());
        assertFalse(itemStorage.getCarList().contains(car1));
        assertTrue(itemStorage.getCarList().contains(car2));
    }

    @Test
    void removeNullCar() {
        itemStorage.addCar(car1);
        assertEquals(1, itemStorage.getCarList().size());
        itemStorage.removeCar(null);
        assertEquals(1, itemStorage.getCarList().size());
    }

    @Test
    void removeCarNotInList() {
        itemStorage.addCar(car1);
        assertEquals(1, itemStorage.getCarList().size());
        itemStorage.removeCar(car2);
        assertEquals(1, itemStorage.getCarList().size());
        assertTrue(itemStorage.getCarList().contains(car1));
    }

    @Test
    void getCarListReturnsCorrectList() {
        assertNotNull(itemStorage.getCarList());
        assertTrue(itemStorage.getCarList().isEmpty());
        itemStorage.addCar(car1);
        assertEquals(1, itemStorage.getCarList().size());
    }

    @Test
    void addTuningPartSuccessfully() {
        assertTrue(itemStorage.getTuningPartList().isEmpty());
        itemStorage.addTuningPart(part1);
        assertEquals(1, itemStorage.getTuningPartList().size());
        assertTrue(itemStorage.getTuningPartList().contains(part1));
    }

    @Test
    void addNullTuningPart() {
        assertTrue(itemStorage.getTuningPartList().isEmpty());
        itemStorage.addTuningPart(null);
        assertTrue(itemStorage.getTuningPartList().isEmpty());
    }

    @Test
    void addMultipleTuningParts() {
        itemStorage.addTuningPart(part1);
        itemStorage.addTuningPart(part2);
        assertEquals(2, itemStorage.getTuningPartList().size());
        assertTrue(itemStorage.getTuningPartList().contains(part1));
        assertTrue(itemStorage.getTuningPartList().contains(part2));
    }

    @Test
    void removeTuningPartSuccessfully() {
        itemStorage.addTuningPart(part1);
        itemStorage.addTuningPart(part2);
        assertEquals(2, itemStorage.getTuningPartList().size());

        itemStorage.removeTuningPart(part1);
        assertEquals(1, itemStorage.getTuningPartList().size());
        assertFalse(itemStorage.getTuningPartList().contains(part1));
        assertTrue(itemStorage.getTuningPartList().contains(part2));
    }

    @Test
    void removeNullTuningPart() {
        itemStorage.addTuningPart(part1);
        assertEquals(1, itemStorage.getTuningPartList().size());
        itemStorage.removeTuningPart(null);
        assertEquals(1, itemStorage.getTuningPartList().size());
    }

    @Test
    void removeTuningPartNotInList() {
        itemStorage.addTuningPart(part1);
        assertEquals(1, itemStorage.getTuningPartList().size());
        itemStorage.removeTuningPart(part2);
        assertEquals(1, itemStorage.getTuningPartList().size());
        assertTrue(itemStorage.getTuningPartList().contains(part1));
    }

    @Test
    void getTuningPartListReturnsCorrectList() {
        assertNotNull(itemStorage.getTuningPartList());
        assertTrue(itemStorage.getTuningPartList().isEmpty());
        itemStorage.addTuningPart(part1);
        assertEquals(1, itemStorage.getTuningPartList().size());
    }
}