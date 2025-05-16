package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

public class ShopBuyController extends AbstractShopController {

    public ShopBuyController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        super(gameEnvironment, sceneNavigator);
    }

    @Override
    protected void loadTuningParts() {
        tuningPartTable.setItems(gameEnvironment.getShopInventory().getTuningPartList());
    }

    @Override
    protected void loadCars() {
        carTable.setItems(gameEnvironment.getShopInventory().getCarList());
    }

    @FXML
    private void switchToSceneShopSell(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopSell(event);
    }

    @FXML
    void buySelected(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getShopInventory().removeCar(selectedCar);
        gameEnvironment.getPlayerInventory().addCar(selectedCar);
        carTable.getSelectionModel().clearSelection();
    }

    @FXML
    void buyCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getShopInventory().removeCar(selectedCar);
        gameEnvironment.getPlayerInventory().addCar(selectedCar);
        carTable.getSelectionModel().clearSelection();
    }

    @FXML
    void buyPart(ActionEvent event) {
        TuningPart selectedPart = tuningPartTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getShopInventory().removeTuningParts(selectedPart);
        gameEnvironment.getPlayerInventory().addTuningParts(selectedPart);
        tuningPartTable.getSelectionModel().clearSelection();

    }
}
