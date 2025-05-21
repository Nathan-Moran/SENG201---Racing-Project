package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import seng201.team0.models.Car;
import seng201.team0.models.SetupCarTable;
import seng201.team0.models.SetupTuningPartTable;
import seng201.team0.models.TuningPart;
import seng201.team0.services.DescriptionService;
import seng201.team0.services.GameEnvironment;
import java.io.IOException;

public class CatalogueTwoController {
    private GameEnvironment gameEnvironment;
    private SceneNavigator sceneNavigator;
    private SetupCarTable setupCarTable;
    private SetupTuningPartTable setupTuningPartTable;

    @FXML private TableView<Car> unlockableCarTable;
    @FXML private TableColumn<Car, String> modelColumn;
    @FXML private TableColumn<Car, Integer> priceColumn;
    @FXML private TableColumn<Car, Double> speedColumn;
    @FXML private TableColumn<Car, Double> handlingColumn;
    @FXML private TableColumn<Car, String> reliabilityColumn;
    @FXML private TableColumn<Car, Integer> fuelColumn;

    @FXML private TableView<TuningPart> tuningPartTable;
    @FXML private TableColumn<TuningPart, String> partNameColumn;
    @FXML private TableColumn<TuningPart, Integer> partPriceColumn;
    @FXML private TableColumn<TuningPart, String> partStatColumn;
    @FXML private TableColumn<TuningPart, Double> partBoostColumn;

    private Object lastSelectedItem;

    public CatalogueTwoController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupCarTable = new SetupCarTable();
        this.setupTuningPartTable = new SetupTuningPartTable();
    }

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

    @FXML
    private void switchToSceneMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    @FXML
    private void switchToPageOne(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCatalogueOne(event);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}