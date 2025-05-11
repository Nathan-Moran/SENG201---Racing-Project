package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import java.io.IOException;

public class ShopBuyController extends AbstractShopController {

    public ShopBuyController() {
        super();
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
}
