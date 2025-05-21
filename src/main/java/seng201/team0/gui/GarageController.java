package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;


import javafx.event.ActionEvent;
import seng201.team0.models.SetupCarTable;
import seng201.team0.services.ControllerService;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the garage screen.
 * This class allows the player to view their cars, select an active car,
 * and navigate to the parts manager or back to the main menu.
 * It displays details of the currently selected car.
 * @author Nathan Moran
 */
public class GarageController implements Initializable {
    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator, used for switching between different scenes in the application.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * The currently active car selected by the player in the garage.
     */
    protected Car activeCar;
    /**
     * Manager for handling controller-specific logic, such as label formatting.
     */
    private final ControllerService controllerLogicManager;

    private SetupCarTable setupCarTable;

    /**
     * Constructs a GarageController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public GarageController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupCarTable = new SetupCarTable();
        this.controllerLogicManager = gameEnvironment.getControllerService();
    }


    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }


    @FXML
    private TableView<Car> carTable;


    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, String> reliabilityColumn;


    @FXML
    private TableColumn<Car, String> fuelColumn;


    @FXML
    private TableColumn<Car, String> handlingColumn;


    @FXML
    private TableColumn<Car, String> speedColumn;


    @FXML
    private Label selectedCarFuelLabel;


    @FXML
    private Label selectedCarHandlingLabel;


    @FXML
    private Label selectedCarHandlingUpgradeLabel;


    @FXML
    private Label selectedCarModelLabel;


    @FXML
    private Label selectedCarReliabilityLabel;


    @FXML
    private Label selectedCarSpeedLabel;


    @FXML
    private Label selectedCarSpeedUpgradeLabel;

    @FXML
    private TextField renameCarTextField;


    /**
     * Handles the action of selecting a car from the table.
     * If a car is selected, it is set as the player's currently active car,
     * the table selection is cleared, and the GUI is updated to reflect the new selection.
     *
     * @param event The action event triggered by selecting a car from the table.
     */
    @FXML
    void selectCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            gameEnvironment.getPlayerInventory().setSelectedCar(selectedCar);
            carTable.getSelectionModel().clearSelection();
            setGUI();
        }
    }

    @FXML
    void renameCar(ActionEvent event) {
        Car selectedCar = gameEnvironment.getSelectedCar();
        if (selectedCar != null) {
            String name = renameCarTextField.getText();
            renameCarTextField.setPromptText(controllerLogicManager.nameChecker(name));
            if (!renameCarTextField.getPromptText().equals("Valid Name")) {
                renameCarTextField.setStyle("-fx-prompt-text-fill: red;");
                renameCarTextField.clear();
            } else {
                renameCarTextField.clear();
                selectedCar.setCustomName(name);
                selectedCarModelLabel.setText(selectedCar.getCustomName());
            }
        }
    }

    /**
     * Handles the action of switching to the parts manager scene.
     *
     * @param event The action event triggered by the button to switch to the parts' manager.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    void switchToPartsMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToScenePartsManager(event);
    }
    /**
     * Sets up and updates the GUI elements on the garage screen.
     * This includes populating the car table with the player's cars and
     * updating the labels to display the stats of the currently selected active car.
     */
    public void setGUI () {
        this.activeCar = gameEnvironment.getPlayerInventory().getSelectedCar();

        if (carTable != null) {
            setupCarTable.setupCarTable(
                    carTable,
                    modelColumn,
                    null,
                    speedColumn,
                    handlingColumn,
                    reliabilityColumn,
                    fuelColumn
            );
        }

        carTable.setItems(gameEnvironment.getPlayerInventory().getCarList());

        selectedCarFuelLabel.setText(controllerLogicManager.setLabels("Fuel"));
        selectedCarModelLabel.setText(controllerLogicManager.setLabels("Model"));
        selectedCarHandlingLabel.setText(controllerLogicManager.setLabels("Handling"));
        selectedCarSpeedLabel.setText(controllerLogicManager.setLabels("Speed"));
        selectedCarReliabilityLabel.setText(controllerLogicManager.setLabels("Reliability"));
        selectedCarHandlingUpgradeLabel.setText(controllerLogicManager.setLabels("HandlingUpgrade"));
        selectedCarSpeedUpgradeLabel.setText(controllerLogicManager.setLabels("SpeedUpgrade"));
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method calls {@link #setGUI()} to populate and update the display.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGUI();
    }
}
