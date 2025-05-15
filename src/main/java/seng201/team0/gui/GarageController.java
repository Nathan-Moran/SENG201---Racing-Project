package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;


import javafx.event.ActionEvent;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GarageController implements Initializable {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public GarageController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
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

//    @FXML
//    private TableColumn<Car, String> handlingupgradeColumn;
//
//    @FXML
//    private TableColumn<Car, String> speedupgradeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));

        carTable.setItems(gameEnvironment.getPlayerInventory().getCarList());
    }
}
