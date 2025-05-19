package seng201.team0.unittests.services;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.ItemStorage;
import seng201.team0.models.TuningPart;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemStorageTest {

    private ItemStorage itemStorage;
    private Car car1;
    private Car car2;
    private TuningPart part1;
    private TuningPart part2;

    @BeforeEach
    void setUp() {
        itemStorage = new ConcreteItemStorage();
        car1 = new Car("Test Car 1", 100, 0.8, 0.9, 10, 1000);
        car2 = new Car("Test Car 2", 120, 0.7, 0.85, 12, 1500);
        part1 = new TuningPart("Test Part 1", 100, "StatA", 1.1);
        part2 = new TuningPart("Test Part 2", 150, "StatB", 1.2);
    }

    @Test
    void testInitialState() {
        assertTrue(itemStorage.getCarList().isEmpty());
        assertTrue(itemStorage.getTuningPartList().isEmpty());
    }

    @Test
    void testAddCar() {
        itemStorage.addCar(car1);
        ObservableList<Car> carList = itemStorage.getCarList();
        assertEquals(1, carList.size());
        assertTrue(carList.contains(car1));
    }

    @Test
    void testAddMultipleCars() {
        itemStorage.addCar(car1);
        itemStorage.addCar(car2);
        ObservableList<Car> carList = itemStorage.getCarList();
        assertEquals(2, carList.size());
        assertTrue(carList.contains(car1));
        assertTrue(carList.contains(car2));
    }

    @Test
    void testAddNullCar() {
        itemStorage.addCar(null);
        assertTrue(itemStorage.getCarList().isEmpty());
    }

    @Test
    void testAddSameCarMultipleTimes() {
        itemStorage.addCar(car1);
        itemStorage.addCar(car1);
        ObservableList<Car> carList = itemStorage.getCarList();
        assertEquals(2, carList.size());
        assertEquals(car1, carList.get(0));
        assertEquals(car1, carList.get(1));
    }

    @Test
    void testRemoveCar() {
        itemStorage.addCar(car1);
        itemStorage.addCar(car2);
        itemStorage.removeCar(car1);
        ObservableList<Car> carList = itemStorage.getCarList();
        assertEquals(1, carList.size());
        assertFalse(carList.contains(car1));
        assertTrue(carList.contains(car2));
    }

    @Test
    void testRemoveNonExistentCar() {
        itemStorage.addCar(car1);
        itemStorage.removeCar(car2);
        ObservableList<Car> carList = itemStorage.getCarList();
        assertEquals(1, carList.size());
        assertTrue(carList.contains(car1));
    }

    @Test
    void testRemoveNullCar() {
        itemStorage.addCar(car1);
        itemStorage.removeCar(null);
        ObservableList<Car> carList = itemStorage.getCarList();
        assertEquals(1, carList.size());
        assertTrue(carList.contains(car1));
    }

    @Test
    void testRemoveCarFromEmptyList() {
        itemStorage.removeCar(car1);
        assertTrue(itemStorage.getCarList().isEmpty());
    }

    @Test
    void testGetCarListReturnsObservableList() {
        assertNotNull(itemStorage.getCarList());
        assertTrue(itemStorage.getCarList() instanceof ObservableList);
    }

    @Test
    void testAddTuningPart() {
        itemStorage.addTuningPart(part1);
        ObservableList<TuningPart> partList = itemStorage.getTuningPartList();
        assertEquals(1, partList.size());
        assertTrue(partList.contains(part1));
    }

    @Test
    void testAddMultipleTuningParts() {
        itemStorage.addTuningPart(part1);
        itemStorage.addTuningPart(part2);
        ObservableList<TuningPart> partList = itemStorage.getTuningPartList();
        assertEquals(2, partList.size());
        assertTrue(partList.contains(part1));
        assertTrue(partList.contains(part2));
    }

    @Test
    void testAddNullTuningPart() {
        itemStorage.addTuningPart(null);
        assertTrue(itemStorage.getTuningPartList().isEmpty());
    }

    @Test
    void testAddSameTuningPartMultipleTimes() {
        itemStorage.addTuningPart(part1);
        itemStorage.addTuningPart(part1);
        ObservableList<TuningPart> partList = itemStorage.getTuningPartList();
        assertEquals(2, partList.size());
        assertEquals(part1, partList.get(0));
        assertEquals(part1, partList.get(1));
    }

    @Test
    void testRemoveTuningPart() {
        itemStorage.addTuningPart(part1);
        itemStorage.addTuningPart(part2);
        itemStorage.removeTuningPart(part1);
        ObservableList<TuningPart> partList = itemStorage.getTuningPartList();
        assertEquals(1, partList.size());
        assertFalse(partList.contains(part1));
        assertTrue(partList.contains(part2));
    }

    @Test
    void testRemoveNonExistentTuningPart() {
        itemStorage.addTuningPart(part1);
        itemStorage.removeTuningPart(part2);
        ObservableList<TuningPart> partList = itemStorage.getTuningPartList();
        assertEquals(1, partList.size());
        assertTrue(partList.contains(part1));
    }

    @Test
    void testRemoveNullTuningPart() {
        itemStorage.addTuningPart(part1);
        itemStorage.removeTuningPart(null);
        ObservableList<TuningPart> partList = itemStorage.getTuningPartList();
        assertEquals(1, partList.size());
        assertTrue(partList.contains(part1));
    }

    @Test
    void testRemoveTuningPartFromEmptyList() {
        itemStorage.removeTuningPart(part1);
        assertTrue(itemStorage.getTuningPartList().isEmpty());
    }

    @Test
    void testGetTuningPartListReturnsObservableList() {
        assertNotNull(itemStorage.getTuningPartList());
        assertTrue(itemStorage.getTuningPartList() instanceof ObservableList);
    }
}