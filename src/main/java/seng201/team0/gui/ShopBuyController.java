package seng201.team0.gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

/**
 * Controller for the shop's "buy" section.
 * This class extends {@link AbstractShopController} and handles the logic for players
 * to purchase cars and tuning parts from the shop's inventory.
 * It populates the tables with items available for sale and processes buy transactions.
 * @author Nathan Moran
 */
public class ShopBuyController extends AbstractShopController {

    /**
     * Constructs a ShopBuyController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public ShopBuyController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        super(gameEnvironment, sceneNavigator);
    }

    /**
    * Label to display the player's current amount of money.
    */
    @FXML private Label moneyLabel;

    /**
     * Loads tuning parts from the shop's inventory into the tuning part table.
     * This method is an implementation of the abstract method from {@link AbstractShopController}.
     */
    @Override
    protected void loadTuningParts() {
        tuningPartTable.setItems(gameEnvironment.getShopInventory().getTuningPartList());
    }

    /**
     * Loads cars from the shop's inventory into the car table.
     * This method is an implementation of the abstract method from {@link AbstractShopController}.
     */
    @Override
    protected void loadCars() {
        carTable.setItems(gameEnvironment.getShopInventory().getCarList());
    }

    /**
     * Handles the action of switching to the "sell" section of the shop.
     *
     * @param event The action event triggered by the button to switch to the sell shop.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    private void switchToSceneShopSell(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopSell(event);
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
     * Handles the action of buying a selected car from the shop.
     * If a car is selected and the player can afford it, the transaction is processed,
     * the player's balance is updated, and the car is added to their inventory.
     * The table selection is then cleared and the money label is updated.
     *
     * @param event The action event triggered by the "Buy Car" button.
     */
    @FXML
    void buyCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            if (gameEnvironment.getBalanceManager().notEnoughBalance(selectedCar.getPrice())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Funds");
                alert.setHeaderText(null);
                alert.setContentText("You do not have the required funds to buy this Car");
                alert.showAndWait();
            } else {
                if (!gameEnvironment.getPlayerInventory().garageFull()) {
                    gameEnvironment.getBalanceManager().buySelectedCar(selectedCar);
                    carTable.getSelectionModel().clearSelection();
                    moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Garage Full");
                    alert.setHeaderText(null);
                    alert.setContentText("Your Garage can not fit more than 4 reserved Cars, please sell a Car first");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Handles the action of buying a selected tuning part from the shop.
     * If a part is selected and the player can afford it, the transaction is processed,
     * the player's balance is updated, and the part is added to their inventory.
     * The table selection is then cleared and the money label is updated.
     *
     * @param event The action event triggered by the "Buy Part" button.
     */
    @FXML
    void buyPart(ActionEvent event) {
        TuningPart selectedPart = tuningPartTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            if (gameEnvironment.getBalanceManager().notEnoughBalance(selectedPart.getPrice())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Funds");
                alert.setHeaderText(null);
                alert.setContentText("You do not have the required funds to buy this Part");
                alert.showAndWait();
            } else {
                gameEnvironment.getBalanceManager().buySelectedPart(selectedPart);
                tuningPartTable.getSelectionModel().clearSelection();
                moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
            }
        }
    }
}
