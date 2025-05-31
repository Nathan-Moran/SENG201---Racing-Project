package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.Course;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;
import seng201.team0.services.ShopService;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private GameEnvironment gameEnv;
    private ShopService shopService;
    private Car carToBuy;
    private TuningPart partToBuy;

    @BeforeEach
    void setUp() {
        gameEnv = new GameEnvironment();
        shopService = gameEnv.getShopService();

        carToBuy = new Car("Shop Special", 0.7, 0.7, 0.7, 20, 1000);
        gameEnv.getShopInventory().getCarList().add(carToBuy);
        gameEnv.getShopInventory().getAllAvailableCars().add(carToBuy);

        partToBuy = new TuningPart("Shop Part", 200, "💨", 1.1);
        gameEnv.getShopInventory().getTuningPartList().add(partToBuy);
        gameEnv.getShopInventory().getAllAvailableTuningParts().add(partToBuy);

        gameEnv.setBalance(10000);
    }

    @Test
    void notEnoughBalanceTrue() {
        gameEnv.setBalance(100);
        assertTrue(shopService.notEnoughBalance(200));
    }

    @Test
    void notEnoughBalanceFalse() {
        gameEnv.setBalance(300);
        assertFalse(shopService.notEnoughBalance(200));
    }

    @Test
    void notEnoughBalanceWithNegativePrice() {
        gameEnv.setBalance(100);
        assertTrue(shopService.notEnoughBalance(-50));
    }

    @Test
    void notEnoughBalanceWithNegativeBalance() {
        gameEnv.setBalance(-100);
        assertTrue(shopService.notEnoughBalance(50));
    }

    @Test
    void chooseStarterCarSufficientFunds() {
        gameEnv.getStarterCarInventory().setupStarterCarInventory();
        assertFalse(gameEnv.getStarterCarInventory().getCarList().isEmpty(), "Starter inventory is empty");
        Car starterCar = gameEnv.getStarterCarInventory().getCarList().get(0);
        int carPrice = starterCar.getPrice();
        int initialBalance = gameEnv.getBalance();

        shopService.chooseStarterCar(starterCar);

        assertNotNull(gameEnv.getPlayerInventory().getSelectedCar());
        assertEquals(starterCar.getName(), gameEnv.getPlayerInventory().getSelectedCar().getName());
        assertEquals(initialBalance - carPrice, gameEnv.getBalance());
        assertFalse(gameEnv.getStarterCarInventory().getCarList().contains(starterCar));
    }

    @Test
    void chooseStarterCarAsAdditionalCar() {
        gameEnv.getPlayerInventory().setStarterCar(new Car("First Car", 0.1,0.1,0.1,1,100));
        gameEnv.getStarterCarInventory().setupStarterCarInventory();
        Car secondStarterChoice = gameEnv.getStarterCarInventory().getCarList().get(0);
        int carPrice = secondStarterChoice.getPrice();
        int initialBalance = gameEnv.getBalance();
        int initialPlayerGarageSize = gameEnv.getPlayerInventory().getCarList().size();

        shopService.chooseStarterCar(secondStarterChoice);

        assertEquals("First Car", gameEnv.getPlayerInventory().getSelectedCar().getName());
        assertEquals(initialPlayerGarageSize + 1, gameEnv.getPlayerInventory().getCarList().size());
        assertTrue(gameEnv.getPlayerInventory().getCarList().stream().anyMatch(c -> c.getName().equals(secondStarterChoice.getName())));
        assertEquals(initialBalance - carPrice, gameEnv.getBalance());
    }


    @Test
    void chooseStarterCarInsufficientFunds() {
        gameEnv.getStarterCarInventory().setupStarterCarInventory();
        Car starterCar = gameEnv.getStarterCarInventory().getCarList().get(0);
        gameEnv.setBalance(starterCar.getPrice() - 1);

        shopService.chooseStarterCar(starterCar);

        assertNull(gameEnv.getPlayerInventory().getSelectedCar());
        assertEquals(starterCar.getPrice() - 1, gameEnv.getBalance());
        assertTrue(gameEnv.getStarterCarInventory().getCarList().contains(starterCar));
    }

    @Test
    void buySelectedCarSufficientFunds() {
        int initialBalance = gameEnv.getBalance();
        int carPrice = carToBuy.getPrice();
        int initialPlayerGarageSize = gameEnv.getPlayerInventory().getCarList().size();

        shopService.buySelectedCar(carToBuy);

        assertEquals(initialPlayerGarageSize + 1, gameEnv.getPlayerInventory().getCarList().size());
        assertTrue(gameEnv.getPlayerInventory().getCarList().stream().anyMatch(c -> c.getName().equals(carToBuy.getName())));
        assertEquals(initialBalance - carPrice, gameEnv.getBalance());
        assertFalse(gameEnv.getShopInventory().getCarList().contains(carToBuy));
        assertFalse(gameEnv.getShopInventory().getAllAvailableCars().contains(carToBuy));
    }

    @Test
    void buySelectedCarInsufficientFunds() {
        gameEnv.setBalance(carToBuy.getPrice() - 1);
        int initialBalance = gameEnv.getBalance();
        int initialPlayerGarageSize = gameEnv.getPlayerInventory().getCarList().size();

        shopService.buySelectedCar(carToBuy);

        assertEquals(initialPlayerGarageSize, gameEnv.getPlayerInventory().getCarList().size());
        assertEquals(initialBalance, gameEnv.getBalance());
        assertTrue(gameEnv.getShopInventory().getCarList().contains(carToBuy));
    }

    @Test
    void buySelectedPartSufficientFunds() {
        int initialBalance = gameEnv.getBalance();
        int partPrice = partToBuy.getPrice();
        int initialPlayerPartsSize = gameEnv.getPlayerInventory().getTuningPartList().size();

        shopService.buySelectedPart(partToBuy);

        assertEquals(initialPlayerPartsSize + 1, gameEnv.getPlayerInventory().getTuningPartList().size());
        assertTrue(gameEnv.getPlayerInventory().getTuningPartList().contains(partToBuy));
        assertEquals(initialBalance - partPrice, gameEnv.getBalance());
        assertFalse(gameEnv.getShopInventory().getTuningPartList().contains(partToBuy));
        assertFalse(gameEnv.getShopInventory().getAllAvailableTuningParts().contains(partToBuy));
    }

    @Test
    void sellSelectedCar() {
        Car carToSell = new Car("Player's Old Car", 0.6, 0.6, 0.6, 15, 800);
        gameEnv.getPlayerInventory().addCar(carToSell);
        int initialBalance = gameEnv.getBalance();
        int carPrice = carToSell.getPrice();
        int initialShopAllAvailableCount = gameEnv.getShopInventory().getAllAvailableCars().size();

        shopService.sellSelectedCar(carToSell);

        assertFalse(gameEnv.getPlayerInventory().getCarList().contains(carToSell));
        assertEquals(initialBalance + carPrice, gameEnv.getBalance());
        assertTrue(gameEnv.getShopInventory().getAllAvailableCars().contains(carToSell));
        assertEquals(initialShopAllAvailableCount + 1, gameEnv.getShopInventory().getAllAvailableCars().size());
    }

    @Test
    void sellSelectedPart() {
        TuningPart partToSell = new TuningPart("Player's Old Part", 150, "🎮", 1.2);
        gameEnv.getPlayerInventory().addTuningPart(partToSell);
        int initialBalance = gameEnv.getBalance();
        int partPrice = partToSell.getPrice();
        int initialShopAllAvailableCount = gameEnv.getShopInventory().getAllAvailableTuningParts().size();

        shopService.sellSelectedPart(partToSell);

        assertFalse(gameEnv.getPlayerInventory().getTuningPartList().contains(partToSell));
        assertEquals(initialBalance + partPrice, gameEnv.getBalance());
        assertTrue(gameEnv.getShopInventory().getAllAvailableTuningParts().contains(partToSell));
        assertEquals(initialShopAllAvailableCount + 1, gameEnv.getShopInventory().getAllAvailableTuningParts().size());
    }

    @Test
    void unlockNewCarsWhenCourseWon() {
        Car duneDrifter = gameEnv.getItemCatalogue().getLockedCarList().stream()
                .filter(car -> car.getName().equals("Dune Drifter"))
                .findFirst().orElse(null);
        assertNotNull(duneDrifter, "Dune Drifter not found in item catalogue's locked list for test setup.");

        assertFalse(gameEnv.getShopInventory().getCarList().contains(duneDrifter));
        assertFalse(gameEnv.getShopInventory().getAllAvailableCars().contains(duneDrifter));
        assertTrue(gameEnv.getShopInventory().getLockedCarsMap().containsKey(Course.DESERT));
        assertEquals(duneDrifter.getName(), gameEnv.getShopInventory().getLockedCarsMap().get(Course.DESERT).getName());

        gameEnv.updateHasWonCourse(Course.DESERT, 1);
        shopService.unlockNewCars();

        assertTrue(gameEnv.getShopInventory().getCarList().contains(duneDrifter) ||
                        gameEnv.getShopInventory().getAllAvailableCars().contains(duneDrifter),
                "Unlocked car not found in shop's available cars.");
        assertFalse(gameEnv.getShopInventory().getLockedCarsMap().containsKey(Course.DESERT));
    }

    @Test
    void unlockNewCarsWhenCourseNotWon() {
        Car duneDrifter = gameEnv.getItemCatalogue().getLockedCarList().stream()
                .filter(car -> car.getName().equals("Dune Drifter"))
                .findFirst().orElse(null);
        assertNotNull(duneDrifter);

        gameEnv.updateHasWonCourse(Course.DESERT, 2);
        shopService.unlockNewCars();

        assertFalse(gameEnv.getShopInventory().getCarList().contains(duneDrifter));
        assertFalse(gameEnv.getShopInventory().getAllAvailableCars().contains(duneDrifter));
        assertTrue(gameEnv.getShopInventory().getLockedCarsMap().containsKey(Course.DESERT));
    }

    @Test
    void unlockNewCarsWithNonExistentCourseInMap() {
        Course unmappedCourse = Course.MOUNTAIN;
        assertNull(gameEnv.getShopInventory().getLockedCarsMap().get(unmappedCourse));


        gameEnv.updateHasWonCourse(unmappedCourse, 1);
        int initialShopCarCount = gameEnv.getShopInventory().getCarList().size();
        int initialLockedMapSize = gameEnv.getShopInventory().getLockedCarsMap().size();

        assertDoesNotThrow(() -> shopService.unlockNewCars());
        assertEquals(initialShopCarCount, gameEnv.getShopInventory().getCarList().size());
        assertEquals(initialLockedMapSize, gameEnv.getShopInventory().getLockedCarsMap().size());
    }
}