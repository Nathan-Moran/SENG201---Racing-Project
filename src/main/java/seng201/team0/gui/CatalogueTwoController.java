package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.DescriptionService;
import seng201.team0.services.GameEnvironment;
import java.io.IOException;

/**
 * Controller for the second page of the Item Catalogue. Displays a list of
 * unlockable cars and all tuning parts, allowing the player to view their details.
 * Provides navigation back to the first catalogue page and the main menu.
 */
public class CatalogueTwoController {
    /**
     * The game environment, providing access to game state and services.
     */
    private GameEnvironment gameEnvironment;
    /**
     * The scene navigator for handling transitions between different scenes.
     */
    private SceneNavigator sceneNavigator;
    /**
     * Helper class for setting up car tables.
     */
    private SetupCarTable setupCarTable;
    /**
     * Helper class for setting up tuning part tables.
     */
    private SetupTuningPartTable setupTuningPartTable;

    /**
     * TableView for displaying unlockable cars.
     */
    @FXML private TableView<Car> unlockableCarTable;
    /**
     * TableColumn for the model name of unlockable cars.
     */
    @FXML private TableColumn<Car, String> modelColumn;
    /**
     * TableColumn for the price of unlockable cars.
     */
    @FXML private TableColumn<Car, Integer> priceColumn;
    /**
     * TableColumn for the speed stat of unlockable cars.
     */
    @FXML private TableColumn<Car, String> speedColumn;
    /**
     * TableColumn for the handling stat of unlockable cars.
     */
    @FXML private TableColumn<Car, String> handlingColumn;
    /**
     * TableColumn for the reliability stat of unlockable cars.
     */
    @FXML private TableColumn<Car, String> reliabilityColumn;
    /**
     * TableColumn for the fuel economy stat of unlockable cars.
     */
    @FXML private TableColumn<Car, String> fuelColumn;

    /**
     * TableView for displaying tuning parts.
     */
    @FXML private TableView<TuningPart> tuningPartTable;
    /**
     * TableColumn for the name of tuning parts.
     */
    @FXML private TableColumn<TuningPart, String> partNameColumn;
    /**
     * TableColumn for the price of tuning parts.
     */
    @FXML private TableColumn<TuningPart, Integer> partPriceColumn;
    /**
     * TableColumn for the stat affected by tuning parts.
     */
    @FXML private TableColumn<TuningPart, String> partStatColumn;
    /**
     * TableColumn for the boost provided by tuning parts.
     */
    @FXML private TableColumn<TuningPart, Double> partBoostColumn;

    /**
     * Stores the last selected item (Car or TuningPart) from either table.
     */
    private Object lastSelectedItem;

    /**
     * Constructs a CatalogueTwoController.
     * @param gameEnvironment The current game environment.
     * @param sceneNavigator The scene navigator for scene transitions.
     */
    public CatalogueTwoController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupCarTable = new SetupCarTable();
        this.setupTuningPartTable = new SetupTuningPartTable();
    }

    /**
     * Initializes the controller. This method is automatically called by the FXMLLoader
     * after the FXML file has been loaded. It sets up the car and tuning part tables
     * and their data, and adds listeners for table selection changes.
     */
    @FXML
    public void initialize() {
        setupCarTable.setupCarTable(unlockableCarTable, modelColumn, priceColumn, speedColumn, handlingColumn, reliabilityColumn, fuelColumn);
        setupTuningPartTable.setupTuningPartTable(tuningPartTable, partNameColumn, partPriceColumn, partStatColumn, partBoostColumn);

        if (gameEnvironment != null && gameEnvironment.getItemCatalogue() != null) {
            unlockableCarTable.setItems(gameEnvironment.getItemCatalogue().getLockedCarList());
            tuningPartTable.setItems(gameEnvironment.getItemCatalogue().getTuningPartList());
        }


        unlockableCarTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lastSelectedItem = newSelection;
                tuningPartTable.getSelectionModel().clearSelection();
            }
        });

        tuningPartTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lastSelectedItem = newSelection;
                unlockableCarTable.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * Displays a description of the currently selected car or tuning part.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    private void getDescription(ActionEvent event) {
        if (lastSelectedItem != null) {
            if (lastSelectedItem instanceof Car) {
                Car selectedCar = (Car) lastSelectedItem;
                String carName = selectedCar.getName();
                String description = DescriptionService.getCarDescription(carName);
                String title = carName + " - Description";
                showAlert(title, description);

            } else if (lastSelectedItem instanceof TuningPart) {
                TuningPart selectedPart = (TuningPart) lastSelectedItem;
                String partName = selectedPart.getName();
                String description = DescriptionService.getTuningPartDescription(partName);
                String title = partName + " - Description";
                showAlert(title, description);
            }
        }
    }

    /**
     * Switches the scene back to the Main Menu.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an error occurs during FXML loading.
     */
    @FXML
    private void switchToSceneIconKey(ActionEvent event) throws IOException {
        sceneNavigator.switchIconKeyScene(event);
    }

    /**
     * Switches the scene to the first page of the Item Catalogue.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an error occurs during FXML loading.
     */
    @FXML
    private void switchToPageOne(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCatalogueOne(event);
    }

    /**
     * Displays an information alert dialog with a specified title and content.
     * @param title The title of the alert dialog.
     * @param content The main message content of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
