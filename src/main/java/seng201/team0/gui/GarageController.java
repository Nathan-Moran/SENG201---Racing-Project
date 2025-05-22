package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import seng201.team0.models.Car;

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
     * The game environment instance, providing access to global game state and services.
     */
    private final GameEnvironment gameEnvironment;
    /**
     * The scene navigator instance, used for switching between different application scenes.
     */
    private final SceneNavigator sceneNavigator;
    /**
     * The ControllerService instance, providing various UI-related logic and validation services.
     */
    private final ControllerService controllerLogicManager;
    /**
     * Helper class for setting up and configuring {@link TableView} for {@link Car} objects.
     */
    private final SetupCarTable setupCarTable;

    /**
     * TableView for displaying the player's collection of cars.
     */
    @FXML private TableView<Car> carTable;
    /**
     * TableColumn for displaying the model name of cars in the table.
     */
    @FXML private TableColumn<Car, String> modelColumn;
    /**
     * TableColumn for displaying the reliability stat of cars in the table.
     */
    @FXML private TableColumn<Car, String> reliabilityColumn;
    /**
     * TableColumn for displaying the fuel economy stat of cars in the table.
     */
    @FXML private TableColumn<Car, String> fuelColumn;
    /**
     * TableColumn for displaying the handling stat of cars in the table.
     */
    @FXML private TableColumn<Car, String> handlingColumn;
    /**
     * TableColumn for displaying the speed stat of cars in the table.
     */
    @FXML private TableColumn<Car, String> speedColumn;
    /**
     * Label for displaying the fuel economy of the currently selected car.
     */
    @FXML private Label selectedCarFuelLabel;
    /**
     * Label for displaying the handling stat of the currently selected car.
     */
    @FXML private Label selectedCarHandlingLabel;
    /**
     * Label for displaying the handling upgrade applied to the currently selected car.
     */
    @FXML private Label selectedCarHandlingUpgradeLabel;
    /**
     * Label for displaying the model name (or custom name) of the currently selected car.
     */
    @FXML private Label selectedCarModelLabel;
    /**
     * Label for displaying the reliability stat of the currently selected car.
     */
    @FXML private Label selectedCarReliabilityLabel;
    /**
     * Label for displaying the speed stat of the currently selected car.
     */
    @FXML private Label selectedCarSpeedLabel;
    /**
     * Label for displaying the speed upgrade applied to the currently selected car.
     */
    @FXML private Label selectedCarSpeedUpgradeLabel;
    /**
     * TextField for entering a custom name to rename the currently selected car.
     */
    @FXML private TextField renameCarTextField;

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

    /**
     * Handles the action of switching to the main menu scene.
     *
     * @param event The action event triggered by the button to switch to the main menu.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

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

    /**
     * Handles the action of renaming the currently selected car.
     * It validates the entered name using {@link ControllerService#nameChecker(String)}.
     * If the name is valid, the car's custom name is updated, and the UI is refreshed.
     * Otherwise, an error message is displayed in the text field.
     *
     * @param event The action event triggered by the rename car button or pressing Enter in the text field.
     */
    @FXML
    public void renameCar(ActionEvent event) {
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
    public void setGUI() {
        if (carTable != null) {
            setupCarTable.setupCarTable(
                    carTable,
                    modelColumn,
                    null, // No price column in garage car table
                    speedColumn,
                    handlingColumn,
                    reliabilityColumn,
                    fuelColumn
            );
        }

        if (carTable != null) {
            carTable.setItems(gameEnvironment.getPlayerInventory().getCarList());
        }

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
