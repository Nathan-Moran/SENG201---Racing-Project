package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.*;
import seng201.team0.services.BalanceManager;
import seng201.team0.services.GameEnvironment;

import static org.junit.jupiter.api.Assertions.*;

public class BalanceManagerTest {

    private GameEnvironment gameEnvironment;
    private BalanceManager balanceManager;
    private Car car1;
    private Car car2_expensive;
    private TuningPart part1;
    private TuningPart part2_expensive;

    @BeforeEach
    void setUp() {
        gameEnvironment = new GameEnvironment();
        balanceManager = gameEnvironment.getBalanceManager();

        car1 = new Car("Affordable Car", 100, 0.5, 0.8, 15, 1000);
        car2_expensive = new Car("Expensive Car", 200, 0.9, 0.9, 10, 5000);
        part1 = new TuningPart("Basic Part", 200, "Speed", 1.1);
        part2_expensive = new TuningPart("Premium Part", 1000, "Handling", 1.3);

        gameEnvironment.getStarterCarInventory().addCar(car1);
        gameEnvironment.getShopInventory().addCar(car2_expensive);
        gameEnvironment.getShopInventory().addTuningPart(part1);
        gameEnvironment.getShopInventory().addTuningPart(part2_expensive);

        gameEnvironment.setBalance(3000);
    }

    @Test
    void testNotEnoughBalanceTrue() {
        assertTrue(balanceManager.notEnoughBalance(3001));
    }

    @Test
    void testNotEnoughBalanceFalse() {
        assertFalse(balanceManager.notEnoughBalance(3000));
        assertFalse(balanceManager.notEnoughBalance(1000));
    }

    @Test
    void testChooseStarterCarSufficientFundsFirstCar() {
        assertNull(gameEnvironment.getSelectedCar());
        balanceManager.chooseStarterCar(car1);
        assertSame(car1, gameEnvironment.getSelectedCar());
        assertEquals(2000, gameEnvironment.getBalance());
        assertFalse(gameEnvironment.getStarterCarInventory().getCarList().contains(car1));
        assertTrue(gameEnvironment.getPlayerInventory().getCarList().isEmpty());
    }

    @Test
    void testChooseStarterCarSufficientFundsAdditionalCar() {
        Car initialPlayerCar = new Car("Initial", 50, 0.5, 0.5, 5, 100);
        gameEnvironment.getPlayerInventory().setStarterCar(initialPlayerCar);
        gameEnvironment.setBalance(2000);
        Car anotherStarter = new Car("Another", 60,0.6,0.6,6, 500);
        gameEnvironment.getStarterCarInventory().addCar(anotherStarter);


        balanceManager.chooseStarterCar(anotherStarter);

        assertEquals(initialPlayerCar, gameEnvironment.getSelectedCar());
        assertTrue(gameEnvironment.getPlayerInventory().getCarList().contains(anotherStarter));
        assertEquals(1500, gameEnvironment.getBalance());
        assertFalse(gameEnvironment.getStarterCarInventory().getCarList().contains(anotherStarter));
    }


    @Test
    void testChooseStarterCarInsufficientFunds() {
        gameEnvironment.setBalance(500);
        balanceManager.chooseStarterCar(car1);
        assertNull(gameEnvironment.getSelectedCar());
        assertEquals(500, gameEnvironment.getBalance());
        assertTrue(gameEnvironment.getStarterCarInventory().getCarList().contains(car1));
    }

    @Test
    void testChooseNullStarterCar() {
        int initialBalance = gameEnvironment.getBalance();
        balanceManager.chooseStarterCar(null);
        assertNull(gameEnvironment.getSelectedCar());
        assertEquals(initialBalance, gameEnvironment.getBalance());
    }

    @Test
    void testBuySelectedCarSufficientFunds() {
        balanceManager.buySelectedCar(car2_expensive);
        assertEquals(-2000, gameEnvironment.getBalance());
        assertTrue(gameEnvironment.getPlayerInventory().getCarList().contains(car2_expensive));
        assertFalse(gameEnvironment.getShopInventory().getCarList().contains(car2_expensive));
    }

    @Test
    void testBuySelectedCarInsufficientFunds() {
        gameEnvironment.setBalance(1000);
        balanceManager.buySelectedCar(car2_expensive);
        assertEquals(1000, gameEnvironment.getBalance());
        assertFalse(gameEnvironment.getPlayerInventory().getCarList().contains(car2_expensive));
        assertTrue(gameEnvironment.getShopInventory().getCarList().contains(car2_expensive));
    }

    @Test
    void testBuyNullCar() {
        int initialBalance = gameEnvironment.getBalance();
        balanceManager.buySelectedCar(null);
        assertEquals(initialBalance, gameEnvironment.getBalance());
    }


    @Test
    void testBuySelectedPartSufficientFunds() {
        balanceManager.buySelectedPart(part1);
        assertEquals(2800, gameEnvironment.getBalance());
        assertTrue(gameEnvironment.getPlayerInventory().getTuningPartList().contains(part1));
        assertFalse(gameEnvironment.getShopInventory().getTuningPartList().contains(part1));
    }

    @Test
    void testBuySelectedPartInsufficientFunds() {
        gameEnvironment.setBalance(100);
        balanceManager.buySelectedPart(part1);
        assertEquals(100, gameEnvironment.getBalance());
        assertFalse(gameEnvironment.getPlayerInventory().getTuningPartList().contains(part1));
        assertTrue(gameEnvironment.getShopInventory().getTuningPartList().contains(part1));
    }

    @Test
    void testBuyNullPart() {
        int initialBalance = gameEnvironment.getBalance();
        balanceManager.buySelectedPart(null);
        assertEquals(initialBalance, gameEnvironment.getBalance());
    }

    @Test
    void testSellSelectedCar() {
        gameEnvironment.getPlayerInventory().addCar(car1);
        int initialCarCountInPlayer = gameEnvironment.getPlayerInventory().getCarList().size();
        int initialCarCountInShop = gameEnvironment.getShopInventory().getCarList().size();

        balanceManager.sellSelectedCar(car1);

        assertEquals(4000, gameEnvironment.getBalance());
        assertEquals(initialCarCountInPlayer - 1, gameEnvironment.getPlayerInventory().getCarList().size());
        assertFalse(gameEnvironment.getPlayerInventory().getCarList().contains(car1));
        assertEquals(initialCarCountInShop + 1, gameEnvironment.getShopInventory().getCarList().size());
        assertTrue(gameEnvironment.getShopInventory().getCarList().contains(car1));
    }

    @Test
    void testSellNullCar() {
        int initialBalance = gameEnvironment.getBalance();
        int initialPlayerCarCount = gameEnvironment.getPlayerInventory().getCarList().size();
        int initialShopCarCount = gameEnvironment.getShopInventory().getCarList().size();

        balanceManager.sellSelectedCar(null);

        assertEquals(initialBalance, gameEnvironment.getBalance());
        assertEquals(initialPlayerCarCount, gameEnvironment.getPlayerInventory().getCarList().size());
        assertEquals(initialShopCarCount, gameEnvironment.getShopInventory().getCarList().size());
    }


    @Test
    void testSellSelectedPart() {
        gameEnvironment.getPlayerInventory().addTuningPart(part1);
        int initialPartCountInPlayer = gameEnvironment.getPlayerInventory().getTuningPartList().size();
        int initialPartCountInShop = gameEnvironment.getShopInventory().getTuningPartList().size();

        balanceManager.sellSelectedPart(part1);

        assertEquals(3200, gameEnvironment.getBalance());
        assertEquals(initialPartCountInPlayer -1, gameEnvironment.getPlayerInventory().getTuningPartList().size());
        assertFalse(gameEnvironment.getPlayerInventory().getTuningPartList().contains(part1));
        assertEquals(initialPartCountInShop + 1, gameEnvironment.getShopInventory().getTuningPartList().size());
        assertTrue(gameEnvironment.getShopInventory().getTuningPartList().contains(part1));
    }

    @Test
    void testSellNullPart() {
        int initialBalance = gameEnvironment.getBalance();
        int initialPlayerPartCount = gameEnvironment.getPlayerInventory().getTuningPartList().size();
        int initialShopPartCount = gameEnvironment.getShopInventory().getTuningPartList().size();

        balanceManager.sellSelectedPart(null);

        assertEquals(initialBalance, gameEnvironment.getBalance());
        assertEquals(initialPlayerPartCount, gameEnvironment.getPlayerInventory().getTuningPartList().size());
        assertEquals(initialShopPartCount, gameEnvironment.getShopInventory().getTuningPartList().size());
    }
}