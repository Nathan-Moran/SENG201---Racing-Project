package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;


import javafx.event.ActionEvent;
import seng201.team0.services.ControllerLogicManager;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GarageController implements Initializable {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;
    protected Car activeCar;
    private ControllerLogicManager controllerLogicManager;

    public GarageController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.controllerLogicManager = gameEnvironment.getControllerLogicManager();
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> modelColumn;


    @FXML
    private TableColumn<Car, Integer> reliabilityColumn;

    @FXML
    private TableColumn<Car, Integer> fuelColumn;

    @FXML
    private TableColumn<Car, Double> handlingColumn;

    @FXML
    private TableColumn<Car, Double> speedColumn;


    @FXML
    private Label selectedCarFuelLabel;

    @FXML
    private Label selectedCarHandlingLabel;

    @FXML
    private Label selectedCarHandlingUpgradeLabel;

    @FXML
    private Label selectedCarModelLabel;

    @FXML
    private Label selectedCarReliabilityLabel;

    @FXML
    private Label selectedCarSpeedLabel;

    @FXML
    private Label selectedCarSpeedUpgradeLabel;


    @FXML
    void selectCar(ActionEvent event) {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            gameEnvironment.getPlayerInventory().setSelectedCar(selectedCar);;
            carTable.getSelectionModel().clearSelection();
            setGUI();
        }
    }


    @FXML
    void switchToPartsMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToScenePartsManager(event);
    }

    public void setGUI () {
//        gameEnvironment.getPlayerInventory().setSelectedCar();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGUI();
    }
}
