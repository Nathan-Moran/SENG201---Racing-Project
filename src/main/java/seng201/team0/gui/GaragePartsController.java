package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator, used for switching between different scenes in the application.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * The currently active car for which parts are being managed.
     */
    protected Car activeCar;

    /**
     * Constructs a GaragePartsController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public GaragePartsController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
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
     *
     * @param event The action event triggered by the installation part button.
     */
    @FXML
    void installPart(ActionEvent event) {
        TuningPart selectedTuningPart = tuningPartTable.getSelectionModel().getSelectedItem();
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
     * If a part is selected, it is removed and moved to reserve, and both tables are updated.
     *
     * @param event The action event triggered by the remove part button.
     */
    @FXML
    void removePart(ActionEvent event) {
        TuningPart selectedTuningPart = tuningPartTable1.getSelectionModel().getSelectedItem();
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
     * Sets up the columns for the installed tuning parts table (tuningPartTable1),
     * binding them to the properties of the TuningPart class and populating it with
     * parts currently installed on the active car.
     */
    protected void setupInstalledPartsTable() {
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStatColumn1.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partBoostColumn1.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable1.setItems(gameEnvironment.getPlayerInventory().getInstalledTuningParts());
    }

    /**
     * Sets up the columns for the reserve tuning parts table (tuningPartTable),
     * binding them to the properties of the TuningPart class and populating it with
     * tuning parts in the player's general inventory (not installed).
     */
    protected void setupReservePartsTable() {
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partBoostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable.setItems(gameEnvironment.getPlayerInventory().getTuningPartList());
    }

    /**
     * TableColumn for displaying the boost value of tuning parts in the reserve table.
     */
    @FXML
    private TableColumn<TuningPart, Double> partBoostColumn;
    /**
     * TableColumn for displaying the boost value of tuning parts in the installed parts table.
     */
    @FXML
    private TableColumn<TuningPart, Double> partBoostColumn1;
    /**
     * TableColumn for displaying the name of tuning parts in the reserve table.
     */
    @FXML
    private TableColumn<TuningPart, String> partNameColumn;
    /**
     * TableColumn for displaying the name of tuning parts in the installed parts table.
     */
    @FXML
    private TableColumn<TuningPart, String> partNameColumn1;
    /**
     * TableColumn for displaying the stat affected by tuning parts in the reserve table.
     */
    @FXML
    private TableColumn<TuningPart, String> partStatColumn;
    /**
     * TableColumn for displaying the stat affected by tuning parts in the installed parts table.
     */
    @FXML
    private TableColumn<TuningPart, String> partStatColumn1;
    /**
     * TableView to display the player's reserve tuning parts.
     */
    @FXML
    private TableView<TuningPart> tuningPartTable;
    /**
     * TableView to display the tuning parts currently installed on the active car.
     */
    @FXML
    private TableView<TuningPart> tuningPartTable1;
}
