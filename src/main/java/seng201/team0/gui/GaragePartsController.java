package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GaragePartsController implements Initializable {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;
    protected Car activeCar;

    public GaragePartsController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    @FXML
    void installPart(ActionEvent event) {
        TuningPart selectedTuningPart = tuningPartTable.getSelectionModel().getSelectedItem();
        if (selectedTuningPart != null) {
            gameEnvironment.getPlayerInventory().installTuningPart(selectedTuningPart);
            setupInstalledPartsTable();
            setupReservePartsTable();
        }
    }

    @FXML
    void removePart(ActionEvent event) {
        TuningPart selectedTuningPart = tuningPartTable1.getSelectionModel().getSelectedItem();
        if (selectedTuningPart != null) {
            gameEnvironment.getPlayerInventory().removeTuningPart(selectedTuningPart);
            setupInstalledPartsTable();
            setupReservePartsTable();
        }
    }

    @FXML
    void switchToCarMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneGarage(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupInstalledPartsTable();
        setupReservePartsTable();

        activeCar = gameEnvironment.getPlayerInventory().getSelectedCar();
    }

    protected void setupInstalledPartsTable() {
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStatColumn1.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partBoostColumn1.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable1.setItems(gameEnvironment.getPlayerInventory().getInstalledTuningParts());

    }

    protected void setupReservePartsTable() {
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partBoostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable.setItems(gameEnvironment.getPlayerInventory().getTuningPartList());
    }

    @FXML
    private TableColumn<TuningPart, Double> partBoostColumn;

    @FXML
    private TableColumn<TuningPart, Double> partBoostColumn1;

    @FXML
    private TableColumn<TuningPart, String> partNameColumn;

    @FXML
    private TableColumn<TuningPart, String> partNameColumn1;

    @FXML
    private TableColumn<TuningPart, String> partStatColumn;

    @FXML
    private TableColumn<TuningPart, String> partStatColumn1;

    @FXML
    private TableView<TuningPart> tuningPartTable;

    @FXML
    private TableView<TuningPart> tuningPartTable1;
}
