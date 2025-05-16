package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

public class ShopSellController extends AbstractShopController {
    public ShopSellController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        super(gameEnvironment, sceneNavigator);
    }

    @Override
    protected void loadTuningParts() {
        tuningPartTable.setItems(gameEnvironment.getPlayerInventory().getTuningPartList());
    }

    @Override
    protected void loadCars() {
        carTable.setItems(gameEnvironment.getPlayerInventory().getCarList());
    }

    @FXML
    private void switchToSceneShopBuy(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopBuy(event);
    }

    @FXML
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    @FXML
    void sellCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getPlayerInventory().removeCar(selectedCar);
        gameEnvironment.getShopInventory().addCar(selectedCar);
        carTable.getSelectionModel().clearSelection();
    }

    @FXML
    void sellPart(ActionEvent event) {
        TuningPart selectedPart = tuningPartTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getPlayerInventory().removeTuningParts(selectedPart);
        gameEnvironment.getShopInventory().addTuningParts(selectedPart);
        tuningPartTable.getSelectionModel().clearSelection();
    }
}
