package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import java.io.IOException;

public class ShopSellController extends AbstractShopController {
    public ShopSellController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
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
    private void switchToSceneShopBuy(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopBuy(event);
    }

    @FXML
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMenu(event);
    }
}
