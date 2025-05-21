package seng201.team0.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import seng201.team0.models.ItemCatalogue; // Ensure this is imported
import seng201.team0.services.GameEnvironment;
import seng201.team0.models.Car;
import seng201.team0.models.SetupCarTable;
import seng201.team0.services.DescriptionService;
import java.io.IOException;
import java.util.List;

public class CatalogueOneController {

    private final GameEnvironment gameEnvironment;
    private final SceneNavigator sceneNavigator;
    private final SetupCarTable setupCarTable;

    @FXML private TableView<Car> starterCarTable;
    @FXML private TableColumn<Car, String> modelColumn;
    @FXML private TableColumn<Car, Integer> priceColumn;
    @FXML private TableColumn<Car, String> speedColumn;
    @FXML private TableColumn<Car, String> handlingColumn;
    @FXML private TableColumn<Car, String> reliabilityColumn;
    @FXML private TableColumn<Car, String> fuelColumn;

    @FXML private TableView<Car> shopCarTable;
    @FXML private TableColumn<Car, String> modelColumn1;
    @FXML private TableColumn<Car, Integer> priceColumn1;
    @FXML private TableColumn<Car, String> speedColumn1;
    @FXML private TableColumn<Car, String> handlingColumn1;
    @FXML private TableColumn<Car, String> reliabilityColumn1;
    @FXML private TableColumn<Car, String> fuelColumn1;

    private Object lastSelectedItem;

    public CatalogueOneController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupCarTable = new SetupCarTable();
    }

    @FXML
    public void initialize() {
        setupCarTable.setupCarTable(starterCarTable, modelColumn, priceColumn, speedColumn, handlingColumn, reliabilityColumn, fuelColumn);
        setupCarTable.setupCarTable(shopCarTable, modelColumn1, priceColumn1, speedColumn1, handlingColumn1, reliabilityColumn1, fuelColumn1);

        if (gameEnvironment != null && gameEnvironment.getItemCatalogue() != null) {
            starterCarTable.setItems(gameEnvironment.getItemCatalogue().getCarList());
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

    @FXML
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    @FXML
    private void switchToPageTwo(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCatalogueTwo(event);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}