package seng201.team0.unittests.services;

import org.junit.jupiter.api.Test;
import seng201.team0.models.OpponentCar;

import static org.junit.jupiter.api.Assertions.*;

public class OpponentCarTest {

    @Test
    void testOpponentCarCreation() {
        double initialSpeed = 60.0;
        OpponentCar opponent = new OpponentCar(initialSpeed);

        assertEquals(initialSpeed, opponent.getSpeed());
        assertEquals(0.0, opponent.getCurrentDistance());
    }

    @Test
    void testAdvanceTick() {
        double speed = 50.0;
        OpponentCar opponent = new OpponentCar(speed);

        opponent.advanceTick();
        assertEquals(speed, opponent.getCurrentDistance());

        opponent.advanceTick();
        assertEquals(speed * 2, opponent.getCurrentDistance());
    }

    @Test
    void testAdvanceTickMultipleTimes() {
        double speed = 70.5;
        OpponentCar opponent = new OpponentCar(speed);
        int ticks = 5;
        for (int i = 0; i < ticks; i++) {
            opponent.advanceTick();
        }
        assertEquals(speed * ticks, opponent.getCurrentDistance(), 0.001);
    }

    @Test
    void testOpponentCarWithZeroSpeed() {
        OpponentCar opponent = new OpponentCar(0.0);
        assertEquals(0.0, opponent.getSpeed());
        assertEquals(0.0, opponent.getCurrentDistance());

        opponent.advanceTick();
        assertEquals(0.0, opponent.getCurrentDistance());
    }
}