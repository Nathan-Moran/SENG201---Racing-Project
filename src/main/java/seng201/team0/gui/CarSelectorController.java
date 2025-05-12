package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.Car;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CarSelectorController implements Initializable {

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public CarSelectorController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    @FXML
    private void chooseSelected(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
//        Integer.parseInt(modelColumn.getText());
//        gameEnvironment.getShopInventory().getCarList();
    }

    @FXML protected TableView<Car> carTable;

    @FXML private TableColumn<Car, String> modelColumn;

    @FXML private TableColumn<Car, Integer> priceColumn;

    @FXML private TableColumn<Car, Double> speedColumn;

    @FXML private TableColumn<Car, Double> handlingColumn;

    @FXML private TableColumn<Car, Integer> reliabilityColumn;

    @FXML private TableColumn<Car, Integer> fuelColumn;


    @FXML private TableColumn<Car, String> handlingupgradeColumn;

    @FXML private TableColumn<Car, String> speedupgradeColumn;

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
        handlingupgradeColumn.setCellValueFactory(new PropertyValueFactory<>("handlingUpgrade"));
        speedupgradeColumn.setCellValueFactory(new PropertyValueFactory<>("speedUpgrade"));
    }

    protected void loadCars() {
        carTable.getItems().clear();
    }

}
