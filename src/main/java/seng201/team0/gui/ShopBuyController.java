package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

public class ShopBuyController extends AbstractShopController {

    public ShopBuyController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        super(gameEnvironment, sceneNavigator);
    }

    @FXML private Label moneyLabel;

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
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    @FXML
    void buyCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getBalanceManager().buySelectedCar(selectedCar);
        carTable.getSelectionModel().clearSelection();
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));

    }

    @FXML
    void buyPart(ActionEvent event) {
        TuningPart selectedPart = tuningPartTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getBalanceManager().buySelectedPart(selectedPart);
        tuningPartTable.getSelectionModel().clearSelection();
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
    }
}
