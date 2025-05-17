package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;
import seng201.team0.models.Garage;
import seng201.team0.models.StarterCarInventory;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CarSelectorController implements Initializable {

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;
    protected StarterCarInventory startingCarsGarage;

    public CarSelectorController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        gameEnvironment.setupStarterCars();
        startingCarsGarage = gameEnvironment.getStarterCars();
    }

    @FXML
    private void chooseSelected(ActionEvent event) throws IOException {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        gameEnvironment.getBalanceManager().chooseStarterCar(selectedCar);
        carTable.getSelectionModel().clearSelection();
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
    }

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        if (gameEnvironment.getPlayerInventory().getSelectedCar() != null) {
            gameEnvironment.getControllerLogicManager().storeLeftOverCars(startingCarsGarage);
            sceneNavigator.switchToSceneMainMenu(event);
        }
    }


    @FXML
    private Label moneyLabel;

    @FXML protected TableView<Car> carTable;

    @FXML private TableColumn<Car, String> modelColumn;

    @FXML private TableColumn<Car, Integer> priceColumn;

    @FXML private TableColumn<Car, Double> speedColumn;

    @FXML private TableColumn<Car, Double> handlingColumn;

    @FXML private TableColumn<Car, Integer> reliabilityColumn;

    @FXML private TableColumn<Car, Integer> fuelColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCarTable();
        loadCars();
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));

    }

    protected void setupCarTable() {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
    }

    protected void loadCars() {
        carTable.setItems(startingCarsGarage.getCarList());
    }

}
