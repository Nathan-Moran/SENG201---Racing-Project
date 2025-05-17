package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;
import seng201.team0.models.StarterCarInventory;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the car selector screen.
 * This class allows the player to choose their starting car at the beginning of the game.
 * It displays a list of available starter cars and updates the player's inventory and balance upon selection.
 * @author Nathan Moran
 */
public class CarSelectorController implements Initializable {

    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator, used for switching between different scenes in the application.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * The inventory of starter cars available for the player to choose from.
     */
    protected StarterCarInventory startingCarsGarage;

    /**
     * Constructs a CarSelectorController with the given game environment and scene navigator.
     * It initializes the starter cars within the game environment.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public CarSelectorController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        gameEnvironment.setupStarterCars();
        startingCarsGarage = gameEnvironment.getStarterCars();
    }

    /**
     * Handles the action of choosing a selected car from the table.
     * If a car is selected, it is set as the player's starter car, and the player's balance is updated.
     * The selection in the table is then cleared.
     *
     * @param event The action event triggered by selecting a car.
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void chooseSelected(ActionEvent event) throws IOException {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            gameEnvironment.getBalanceManager().chooseStarterCar(selectedCar);
            carTable.getSelectionModel().clearSelection();
            moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        }
    }

    /**
     * Handles the action of switching to the main menu scene.
     * This action is only allowed if the player has selected a starter car.
     * Any leftover starter cars are stored before switching scenes.
     *
     * @param event The action event triggered by the button to switch to the main menu.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        if (gameEnvironment.getPlayerInventory().getSelectedCar() != null) {
            gameEnvironment.getControllerLogicManager().storeLeftOverCars(startingCarsGarage);
            sceneNavigator.switchToSceneMainMenu(event);
        }
    }

    /**
     * Label to display the player's current amount of money.
     */
    @FXML
    private Label moneyLabel;

    /**
     * TableView to display the available starter cars.
     */
    @FXML protected TableView<Car> carTable;

    /**
     * TableColumn for displaying the model of the car.
     */
    @FXML private TableColumn<Car, String> modelColumn;

    /**
     * TableColumn for displaying the price of the car.
     */
    @FXML private TableColumn<Car, Integer> priceColumn;

    /**
     * TableColumn for displaying the speed of the car.
     */
    @FXML private TableColumn<Car, Double> speedColumn;

    /**
     * TableColumn for displaying the handling of the car.
     */
    @FXML private TableColumn<Car, Double> handlingColumn;

    /**
     * TableColumn for displaying the reliability of the car.
     */
    @FXML private TableColumn<Car, Integer> reliabilityColumn;

    /**
     * TableColumn for displaying the fuel economy of the car.
     */
    @FXML private TableColumn<Car, Integer> fuelColumn;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets up the car table, loads the starter cars into it, and updates the money label.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCarTable();
        loadCars();
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));

    }

    /**
     * Sets up the columns for the car table, binding them to the properties of the Car class.
     */
    protected void setupCarTable() {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
    }

    /**
     * Loads the starter cars from the `startingCarsGarage` into the car table.
     */
    protected void loadCars() {
        carTable.setItems(startingCarsGarage.getCarList());
    }

}
