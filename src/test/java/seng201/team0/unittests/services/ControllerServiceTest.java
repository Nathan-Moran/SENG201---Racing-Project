package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Car;
import seng201.team0.models.StarterCarInventory;
import seng201.team0.models.TuningPart;
import seng201.team0.services.ControllerService;
import seng201.team0.services.GameEnvironment;

import static org.junit.jupiter.api.Assertions.*;

class ControllerServiceTest {

    private GameEnvironment gameEnvironment;
    private ControllerService controllerService;
    private Car testCar;

    @BeforeEach
    void setUp() {
        gameEnvironment = new GameEnvironment();
        controllerService = new ControllerService(gameEnvironment);
        testCar = new Car("Test Model", 0.7, 0.6, 0.8, 25, 2000);

        gameEnvironment.getPlayerInventory().setStarterCar(new Car(testCar));
    }

    @Test
    void storeLeftOverCarsMovesCarsToShop() {
        StarterCarInventory starterInventory = gameEnvironment.getStarterCarInventory();
        starterInventory.setupStarterCarInventory();


        assertTrue(starterInventory.getCarList().size() > 0);
        int starterCarCount = starterInventory.getCarList().size();
        int initialShopAllAvailableCars = gameEnvironment.getShopInventory().getAllAvailableCars().size();

        controllerService.storeLeftOverCars(starterInventory);

        assertEquals(0, starterInventory.getCarList().size());

        assertEquals(initialShopAllAvailableCars + starterCarCount, gameEnvironment.getShopInventory().getAllAvailableCars().size());

        assertTrue(gameEnvironment.getShopInventory().getCarList().size() <= 3);
        if (!gameEnvironment.getShopInventory().getAllAvailableCars().isEmpty()) {
            assertFalse(gameEnvironment.getShopInventory().getCarList().isEmpty());
        }
    }

    @Test
    void storeLeftOverCarsWithEmptyStarterInventory() {
        StarterCarInventory starterInventory = gameEnvironment.getStarterCarInventory();
        starterInventory.getCarList().clear();

        int initialShopAllCarsCount = gameEnvironment.getShopInventory().getAllAvailableCars().size();

        controllerService.storeLeftOverCars(starterInventory);

        assertEquals(0, starterInventory.getCarList().size());
        assertEquals(initialShopAllCarsCount, gameEnvironment.getShopInventory().getAllAvailableCars().size());
        if (initialShopAllCarsCount > 0) {
            assertTrue(gameEnvironment.getShopInventory().getCarList().size() > 0 && gameEnvironment.getShopInventory().getCarList().size() <= Math.min(3, initialShopAllCarsCount) );
        } else {
            assertTrue(gameEnvironment.getShopInventory().getCarList().isEmpty());
        }
    }


    @Test
    void setLabelsForSpeed() {
        assertEquals(gameEnvironment.getPlayerInventory().getSelectedCar().getSpeedString(), controllerService.setLabels("Speed"));
    }

    @Test
    void setLabelsForFuel() {
        assertEquals(gameEnvironment.getPlayerInventory().getSelectedCar().getFuelEconomyString(), controllerService.setLabels("Fuel"));
    }

    @Test
    void setLabelsForHandling() {
        assertEquals(gameEnvironment.getPlayerInventory().getSelectedCar().getHandlingPercent(), controllerService.setLabels("Handling"));
    }

    @Test
    void setLabelsForModelDefaultName() {
        assertEquals(gameEnvironment.getPlayerInventory().getSelectedCar().getName(), controllerService.setLabels("Model"));
    }

    @Test
    void setLabelsForModelCustomName() {
        gameEnvironment.getPlayerInventory().getSelectedCar().setCustomName("My Ride");
        assertEquals("My Ride", controllerService.setLabels("Model"));
    }

    @Test
    void setLabelsForReliability() {
        assertEquals(gameEnvironment.getPlayerInventory().getSelectedCar().getReliabilityPercent(), controllerService.setLabels("Reliability"));
    }

    @Test
    void setLabelsForSpeedUpgradeEmpty() {
        assertEquals("-", controllerService.setLabels("SpeedUpgrade"));
    }

    @Test
    void setLabelsForSpeedUpgradePresent() {
        TuningPart speedPart = new TuningPart("Turbo", 100, "💨", 1.2);
        gameEnvironment.getPlayerInventory().getSelectedCar().addSpeedUpgrade(speedPart);
        assertEquals("Turbo", controllerService.setLabels("SpeedUpgrade"));
    }

    @Test
    void setLabelsForHandlingUpgradeEmpty() {
        assertEquals("-", controllerService.setLabels("HandlingUpgrade"));
    }

    @Test
    void setLabelsForHandlingUpgradePresent() {
        TuningPart handlingPart = new TuningPart("Spoilers", 100, "🎮", 1.1);
        gameEnvironment.getPlayerInventory().getSelectedCar().addHandlingUpgrade(handlingPart);
        assertEquals("Spoilers", controllerService.setLabels("HandlingUpgrade"));
    }

    @Test
    void setLabelsForUnknownStat() {
        assertEquals("-", controllerService.setLabels("UnknownStat"));
    }

    @Test
    void setLabelsWhenNoCarSelected() {
        GameEnvironment freshGameEnv = new GameEnvironment();
        ControllerService freshControllerService = new ControllerService(freshGameEnv);
        assertEquals("-", freshControllerService.setLabels("Speed"));
    }

    @Test
    void nameCheckerValidName() {
        assertEquals("Valid Name", controllerService.nameChecker("Player123"));
    }

    @Test
    void nameCheckerTooShort() {
        assertEquals("Name must be between 3 and 15 characters", controllerService.nameChecker("Pl"));
    }

    @Test
    void nameCheckerTooLong() {
        assertEquals("Name must be between 3 and 15 characters", controllerService.nameChecker("ThisNameIsWayTooLongForIt"));
    }

    @Test
    void nameCheckerInvalidCharacters() {
        assertEquals("Name must not contain special characters", controllerService.nameChecker("Player!"));
    }

    @Test
    void nameCheckerInvalidCharactersAndTooShort() {
        assertEquals("Name must be between 3 and 15 characters", controllerService.nameChecker("P!"));
    }
    @Test
    void nameCheckerInvalidCharactersAndTooLong() {
        assertEquals("Name must be between 3 and 15 characters", controllerService.nameChecker("ThisIsAVeryLongNameWith!"));
    }


    @Test
    void nameCheckerExactlyThreeCharactersValid() {
        assertEquals("Valid Name", controllerService.nameChecker("Abc"));
    }

    @Test
    void nameCheckerExactlyFifteenCharactersValid() {
        assertEquals("Valid Name", controllerService.nameChecker("ValidNameIs15Ch"));
    }

    @Test
    void nameCheckerEmptyNameIsInvalid() {
        assertEquals("Name must be between 3 and 15 characters", controllerService.nameChecker(""));
    }
}