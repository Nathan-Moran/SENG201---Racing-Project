package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupInstalledPartsTable();
    }

    protected void setupInstalledPartsTable() {

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

    @FXML
    void installPart(ActionEvent event) {

    }

    @FXML
    void removePart(ActionEvent event) {

    }

    @FXML
    void switchToCarMenu(ActionEvent event) {

    }

}
