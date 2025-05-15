package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractShopController implements Initializable {
    //Garage and Sellable Cars
    @FXML protected TableView<Car> carTable;

    @FXML private TableColumn<Car, String> modelColumn;

    @FXML private TableColumn<Car, Integer> priceColumn;

    @FXML private TableColumn<Car, Double> speedColumn;

    @FXML private TableColumn<Car, Double> handlingColumn;

    @FXML private TableColumn<Car, Integer> reliabilityColumn;

    @FXML private TableColumn<Car, Integer> fuelColumn;


    @FXML private TableColumn<Car, String> handlingupgradeColumn;

    @FXML private TableColumn<Car, String> speedupgradeColumn;

    //Tuning Parts
    @FXML protected TableView<TuningPart> tuningPartTable;

    @FXML private TableColumn<TuningPart, String> partnameColumn;

    @FXML private TableColumn<TuningPart, Integer> partpriceColumn;

    @FXML private TableColumn<TuningPart, String> partstatColumn;

    @FXML private TableColumn<TuningPart, Double> partboostColumn;


    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public AbstractShopController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCarTable();
        setupTuningPartTable();

        loadCars();
        loadTuningParts();
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

    protected void setupTuningPartTable() {
        partnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partboostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));
    }

    protected abstract void loadTuningParts();
    protected abstract void loadCars();
}
