package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.OpponentCar;

import static org.junit.jupiter.api.Assertions.*;

class OpponentCarTest {

    @Test
    void constructorSetsSpeedAndInitialDistance() {
        double initialSpeed = 50.5;
        OpponentCar car = new OpponentCar(initialSpeed); 

        assertEquals(initialSpeed, car.getSpeed(), 0.001); 
        assertEquals(0, car.getCurrentDistance(), 0.001);
    }

    @Test
    void getSpeedReturnsCorrectSpeed() {
        OpponentCar car = new OpponentCar(60.0); 
        assertEquals(60.0, car.getSpeed(), 0.001); 
    }

    @Test
    void getCurrentDistanceReturnsInitialDistance() {
        OpponentCar car = new OpponentCar(55.0); 
        assertEquals(0, car.getCurrentDistance(), 0.001); 
    }

    @Test
    void advanceTickIncreasesCurrentDistanceBySpeed() {
        double speed = 10.2;
        OpponentCar car = new OpponentCar(speed); 
        assertEquals(0, car.getCurrentDistance(), 0.001); 

        car.advanceTick(); 
        assertEquals(speed, car.getCurrentDistance(), 0.001); 

        car.advanceTick(); 
        assertEquals(speed * 2, car.getCurrentDistance(), 0.001); 
    }

    @Test
    void advanceTickMultipleTimes() {
        double speed = 5.0;
        OpponentCar car = new OpponentCar(speed); 

        for (int i = 1; i <= 5; i++) {
            car.advanceTick(); 
            assertEquals(speed * i, car.getCurrentDistance(), 0.001); 
        }
    }
}