package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import seng201.team0.services.GameEnvironment;
import seng201.team0.models.Car;
import seng201.team0.services.DescriptionService;
import java.io.IOException;

/**
 * Controller for the first page of the Item Catalogue. Displays a list of
 * starter cars and general shop cars, allowing the player to view their details.
 * Provides navigation to the second catalogue page and the main menu.
 */
public class CatalogueOneController {

    /**
     * The game environment, providing access to game state and services.
     */
    private final GameEnvironment gameEnvironment;
    /**
     * The scene navigator for handling transitions between different scenes.
     */
    private final SceneNavigator sceneNavigator;
    /**
     * Helper class for setting up car tables.
     */
    private final SetupCarTable setupCarTable;

    /**
     * TableView for displaying starter cars.
     */
    @FXML private TableView<Car> starterCarTable;
    /**
     * TableColumn for the model name of starter cars.
     */
    @FXML private TableColumn<Car, String> modelColumn;
    /**
     * TableColumn for the price of starter cars.
     */
    @FXML private TableColumn<Car, Integer> priceColumn;
    /**
     * TableColumn for the speed stat of starter cars.
     */
    @FXML private TableColumn<Car, String> speedColumn;
    /**
     * TableColumn for the handling stat of starter cars.
     */
    @FXML private TableColumn<Car, String> handlingColumn;
    /**
     * TableColumn for the reliability stat of starter cars.
     */
    @FXML private TableColumn<Car, String> reliabilityColumn;
    /**
     * TableColumn for the fuel economy stat of starter cars.
     */
    @FXML private TableColumn<Car, String> fuelColumn;

    /**
     * TableView for displaying general shop cars.
     */
    @FXML private TableView<Car> shopCarTable;
    /**
     * TableColumn for the model name of shop cars.
     */
    @FXML private TableColumn<Car, String> modelColumn1;
    /**
     * TableColumn for the price of shop cars.
     */
    @FXML private TableColumn<Car, Integer> priceColumn1;
    /**
     * TableColumn for the speed stat of shop cars.
     */
    @FXML private TableColumn<Car, String> speedColumn1;
    /**
     * TableColumn for the handling stat of shop cars.
     */
    @FXML private TableColumn<Car, String> handlingColumn1;
    /**
     * TableColumn for the reliability stat of shop cars.
     */
    @FXML private TableColumn<Car, String> reliabilityColumn1;
    /**
     * TableColumn for the fuel economy stat of shop cars.
     */
    @FXML private TableColumn<Car, String> fuelColumn1;

    /**
     * Stores the last selected item (Car or TuningPart) from either table.
     */
    private Object lastSelectedItem;

    /**
     * Constructs a CatalogueOneController.
     * @param gameEnvironment The current game environment.
     * @param sceneNavigator The scene navigator for scene transitions.
     */
    public CatalogueOneController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupCarTable = new SetupCarTable();
    }

    /**
     * Initializes the controller. This method is automatically called by the FXMLLoader
     * after the FXML file has been loaded. It sets up the car tables and their data,
     * and adds listeners for table selection changes.
     */
    @FXML
    public void initialize() {
        setupCarTable.setupCarTable(starterCarTable, modelColumn, priceColumn, speedColumn, handlingColumn, reliabilityColumn, fuelColumn);
        setupCarTable.setupCarTable(shopCarTable, modelColumn1, priceColumn1, speedColumn1, handlingColumn1, reliabilityColumn1, fuelColumn1);

        if (gameEnvironment != null && gameEnvironment.getItemCatalogue() != null) {
            starterCarTable.setItems(gameEnvironment.getItemCatalogue().getStarterCarPool());
            shopCarTable.setItems(gameEnvironment.getItemCatalogue().getShopCarList());
        }


        starterCarTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lastSelectedItem = newSelection;
                shopCarTable.getSelectionModel().clearSelection();
            }
        });

        shopCarTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lastSelectedItem = newSelection;
                starterCarTable.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * Displays a description of the currently selected car.
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
            }
        }
    }

    /**
     * Switches the scene back to the Main Menu.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an error occurs during FXML loading.
     */
    @FXML
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    /**
     * Switches the scene to the second page of the Item Catalogue.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an error occurs during FXML loading.
     */
    @FXML
    private void switchToPageTwo(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCatalogueTwo(event);
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