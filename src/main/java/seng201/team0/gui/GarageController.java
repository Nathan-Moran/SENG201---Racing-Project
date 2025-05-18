package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;


import javafx.event.ActionEvent;
import seng201.team0.services.ControllerLogicManager;
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
    private final ControllerLogicManager controllerLogicManager;

    /**
     * Constructs a GarageController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public GarageController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.controllerLogicManager = gameEnvironment.getControllerLogicManager();
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
     * TableView to display the player's cars.
     */
    @FXML
    private TableView<Car> carTable;

    /**
     * TableColumn for displaying the model of the car.
     */
    @FXML
    private TableColumn<Car, String> modelColumn;

    /**
     * TableColumn for displaying the reliability of the car.
     */
    @FXML
    private TableColumn<Car, Integer> reliabilityColumn;

    /**
     * TableColumn for displaying the fuel economy of the car.
     */
    @FXML
    private TableColumn<Car, Integer> fuelColumn;

    /**
     * TableColumn for displaying the handling of the car.
     */
    @FXML
    private TableColumn<Car, Double> handlingColumn;

    /**
     * TableColumn for displaying the speed of the car.
     */
    @FXML
    private TableColumn<Car, Double> speedColumn;

    /**
     * Label to display the fuel economy of the selected car.
     */
    @FXML
    private Label selectedCarFuelLabel;

    /**
     * Label to display the handling stat of the selected car.
     */
    @FXML
    private Label selectedCarHandlingLabel;

    /**
     * Label to display the handling upgrade status of the selected car.
     */
    @FXML
    private Label selectedCarHandlingUpgradeLabel;

    /**
     * Label to display the model name of the selected car.
     */
    @FXML
    private Label selectedCarModelLabel;

    /**
     * Label to display the reliability stat of the selected car.
     */
    @FXML
    private Label selectedCarReliabilityLabel;

    /**
     * Label to display the speed stat of the selected car.
     */
    @FXML
    private Label selectedCarSpeedLabel;

    /**
     * Label to display the speed upgrade status of the selected car.
     */
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

        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));

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
