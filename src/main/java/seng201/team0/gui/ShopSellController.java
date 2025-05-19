package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

/**
 * Controller for the shop's "sell" section.
 * This class extends {@link AbstractShopController} and handles the logic for players
 * to sell cars and tuning parts from their inventory to the shop.
 * It populates the tables with items available for selling from the player's inventory
 * and processes sell transactions.
 * @author Nathan Moran
 */
public class ShopSellController extends AbstractShopController {

    /**
     * Constructs a ShopSellController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public ShopSellController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        super(gameEnvironment, sceneNavigator);
    }

    /**
     * Loads cars from the player's inventory into the car table for selling.
     * This method is an implementation of the abstract method from {@link AbstractShopController}.
     */
    @Override
    protected void loadTuningParts() {
        tuningPartTable.setItems(gameEnvironment.getPlayerInventory().getTuningPartList());
    }

    /**
     * Loads tuning parts from the player's inventory into the tuning part table for selling.
     * This method is an implementation of the abstract method from {@link AbstractShopController}.
     */
    @Override
    protected void loadCars() {
        carTable.setItems(gameEnvironment.getPlayerInventory().getCarList());
    }

    /**
     * Handles the action of switching to the "buy" section of the shop.
     *
     * @param event The action event triggered by the button to switch to the buy shop.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    private void switchToSceneShopBuy(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopBuy(event);
    }

    /**
     * Handles the action of switching back to the main menu scene.
     *
     * @param event The action event triggered by the button to switch to the main menu.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    /**
     * Handles the action of selling a selected car from the player's inventory.
     * If a car is selected, the transaction is processed, the player's balance is updated,
     * and the car is removed from their inventory.
     * The table selection is then cleared and the money label is updated.
     *
     * @param event The action event triggered by the "Sell Car" button.
     */
    @FXML
    void sellCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            gameEnvironment.getShopService().sellSelectedCar(selectedCar);
            carTable.getSelectionModel().clearSelection();
            moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        }
    }

    /**
     * Handles the action of selling a selected tuning part from the player's inventory.
     * If a part is selected, the transaction is processed, the player's balance is updated,
     * and the part is removed from their inventory.
     * The table selection is then cleared and the money label is updated.
     *
     * @param event The action event triggered by the "Sell Part" button.
     */
    @FXML
    void sellPart(ActionEvent event) {
        TuningPart selectedPart = tuningPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            gameEnvironment.getShopService().sellSelectedPart(selectedPart);
            tuningPartTable.getSelectionModel().clearSelection();
            moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        }
    }

    /**
     * Label to display the player's current amount of money.
     */
    @FXML
    private Label moneyLabel;
}
