package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import seng201.team0.Car;
import seng201.team0.Garage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarSelectorController implements Initializable {

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;
    protected Garage startingCarsGarage;

    public CarSelectorController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        gameEnvironment.setupStarterCars();
        startingCarsGarage = gameEnvironment.getStarterCars();
    }

    @FXML
    private void chooseSelected(ActionEvent event) throws IOException {

//
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        gameEnvironment.starterCarInventory.removeCar(selectedCar);
        gameEnvironment.playerInventory.addCar(selectedCar);
        carTable.getSelectionModel().clearSelection();
//        sceneNavigator.switchToSceneMainMenu(event);
//        Integer.parseInt(modelColumn.getText());
//        gameEnvironment.getShopInventory().getCarList();
    }

    @FXML
    void getSelectedCar(MouseEvent event) {

    }

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
