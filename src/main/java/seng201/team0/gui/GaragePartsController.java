package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the garage parts management screen.
 * This class allows the player to view installed and reserve tuning parts for the active car.
 * Players can install parts from their reserve to the active car or remove installed parts.
 * @author Nathan Moran
 */
public class GaragePartsController implements Initializable {

    /**
     * The game environment instance, providing access to global game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator instance, used for switching between different application scenes.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * The currently active car in the garage, on which tuning parts can be installed or uninstalled.
     */
    protected Car activeCar;
    /**
     * Helper class for setting up and configuring {@link TableView} for {@link TuningPart} objects.
     */
    private SetupTuningPartTable setupTuningPartTable;

    /**
     * TableColumn for displaying the boost value of tuning parts in the reserve inventory.
     */
    @FXML private TableColumn<TuningPart, Double> reservePartBoostColumn;
    /**
     * TableColumn for displaying the boost value of tuning parts currently installed on the active car.
     */
    @FXML private TableColumn<TuningPart, Double> installedPartBoostColumn;
    /**
     * TableColumn for displaying the name of tuning parts in the reserve inventory.
     */
    @FXML private TableColumn<TuningPart, String> reservePartNameColumn;
    /**
     * TableColumn for displaying the name of tuning parts currently installed on the active car.
     */
    @FXML private TableColumn<TuningPart, String> installedPartNameColumn;
    /**
     * TableColumn for displaying the affected stat of tuning parts in the reserve inventory.
     */
    @FXML private TableColumn<TuningPart, String> reservePartStatColumn;
    /**
     * TableColumn for displaying the affected stat of tuning parts currently installed on the active car.
     */
    @FXML private TableColumn<TuningPart, String> installedPartStatColumn;
    /**
     * TableView for displaying tuning parts that are in the player's general inventory (reserve).
     */
    @FXML private TableView<TuningPart> reserveTuningPartTable;
    /**
     * TableView for displaying tuning parts that are currently installed on the {@link #activeCar}.
     */
    @FXML private TableView<TuningPart> installedTuningPartTable;

    /**
     * Constructs a GaragePartsController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public GaragePartsController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupTuningPartTable = new SetupTuningPartTable();
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
     * Handles the action of installing a selected tuning part from the reserve parts table to the active car.
     * If a part is selected, it is installed, and both the installed and reserve parts tables are updated.
     * An alert is shown if the part is incompatible (e.g., trying to install two of the same type).
     *
     * @param event The action event triggered by the installation part button.
     */
    @FXML
    void installPart(ActionEvent event) {
        TuningPart selectedTuningPart = reserveTuningPartTable.getSelectionModel().getSelectedItem();
        if (selectedTuningPart != null) {
            if (gameEnvironment.getPlayerInventory().installTuningPart(selectedTuningPart)) {
                setupInstalledPartsTable();
                setupReservePartsTable();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incompatible Tuning Part");
                alert.setHeaderText(null);
                alert.setContentText("Cannot install two of the same tuning parts on a car");
                alert.showAndWait();
            }
        }
    }

    /**
     * Handles the action of removing a selected tuning part from the installed parts table of the active car.
     * If a part is selected, it is removed from the active car and moved back to the player's reserve inventory,
     * and both tables are subsequently updated to reflect these changes.
     *
     * @param event The action event triggered by the remove part button.
     */
    @FXML
    void removePart(ActionEvent event) {
        TuningPart selectedTuningPart = installedTuningPartTable.getSelectionModel().getSelectedItem();
        if (selectedTuningPart != null) {
            gameEnvironment.getPlayerInventory().uninstallTuningPart(selectedTuningPart);
            setupInstalledPartsTable();
            setupReservePartsTable();
        }
    }

    /**
     * Handles the action of switching back to the main garage (car selection) scene.
     *
     * @param event The action event triggered by the button to switch to the car menu.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    void switchToCarMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneGarage(event);
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets up the tables for installed and reserve tuning parts and
     * sets the active car based on the player's current selection.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupInstalledPartsTable();
        setupReservePartsTable();

        activeCar = gameEnvironment.getPlayerInventory().getSelectedCar();
    }

    /**
     * Sets up the columns for the installed tuning parts table ({@link #installedTuningPartTable}),
     * binding them to the properties of the {@link TuningPart} class and populating it with
     * parts currently installed on the active car.
     */
    protected void setupInstalledPartsTable() {
        setupTuningPartTable.setupTuningPartTable(
                installedTuningPartTable,
                installedPartNameColumn,
                null, // Price column is not applicable for installed parts display
                installedPartStatColumn,
                installedPartBoostColumn
        );
        installedTuningPartTable.setItems(gameEnvironment.getPlayerInventory().getInstalledTuningParts());
    }

    /**
     * Sets up the columns for the reserve tuning parts table ({@link #reserveTuningPartTable}),
     * binding them to the properties of the {@link TuningPart} class and populating it with
     * tuning parts in the player's general inventory (not installed on a car).
     */
    protected void setupReservePartsTable() {
        setupTuningPartTable.setupTuningPartTable(
                reserveTuningPartTable,
                reservePartNameColumn,
                null, // Price column is not applicable for reserve parts display in this context
                reservePartStatColumn,
                reservePartBoostColumn
        );
        reserveTuningPartTable.setItems(gameEnvironment.getPlayerInventory().getTuningPartList());
    }
}
