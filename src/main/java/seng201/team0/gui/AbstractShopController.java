package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Abstract base class for shop controllers, providing common functionality for displaying cars and tuning parts.
 * It handles the initialization of tables for cars and tuning parts.
 * @author Nathan Moran
 */
public abstract class AbstractShopController implements Initializable {
    /**
     * Label to display the player's current balance.
     */
    @FXML private Label moneyLabel;

    /**
     * TableView to display cars available in the shop or garage.
     */
    @FXML protected TableView<Car> carTable;

    /**
     * TableColumn for displaying the model of the car.
     */
    @FXML private TableColumn<Car, String> modelColumn;

    /**
     * TableColumn for displaying the price of the car.
     */
    @FXML private TableColumn<Car, Integer> priceColumn;

    /**
     * TableColumn for displaying the speed of the car.
     */
    @FXML private TableColumn<Car, Double> speedColumn;

    /**
     * TableColumn for displaying the handling of the car.
     */
    @FXML private TableColumn<Car, Double> handlingColumn;

    /**
     * TableColumn for displaying the reliability of the car.
     */
    @FXML private TableColumn<Car, Integer> reliabilityColumn;

    /**
     * TableColumn for displaying the fuel economy of the car.
     */
    @FXML private TableColumn<Car, Integer> fuelColumn;

    /**
     * TableView to display tuning parts available in the shop or player's inventory.
     */
    @FXML protected TableView<TuningPart> tuningPartTable;

    /**
     * TableColumn for displaying the name of the tuning part.
     */
    @FXML private TableColumn<TuningPart, String> partnameColumn;

    /**
     * TableColumn for displaying the price of the tuning part.
     */
    @FXML private TableColumn<TuningPart, Integer> partpriceColumn;

    /**
     * TableColumn for displaying the stat affected by the tuning part.
     */
    @FXML private TableColumn<TuningPart, String> partstatColumn;

    /**
     * TableColumn for displaying the boost value of the tuning part.
     */
    @FXML private TableColumn<TuningPart, Double> partboostColumn;

    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;

    /**
     * The scene navigator, used for switching between different scenes in the application.
     */
    protected SceneNavigator sceneNavigator;

    /**
     * Constructs an AbstractShopController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public AbstractShopController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets up the car and tuning part tables, loads data into them,
     * and updates the money label.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupCarTable();
        setupTuningPartTable();

        loadCars();
        loadTuningParts();
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
    }

    /**
     * Sets up the columns for the car table, binding them to the properties of the Car class.
     */
    protected void setupCarTable() {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
    }

    /**
     * Sets up the columns for the tuning part table, binding them to the properties of the TuningPart class.
     */
    protected void setupTuningPartTable() {
        partnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partboostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));
    }

    /**
     * Abstract method to be implemented by subclasses to load tuning parts into the tuning part table.
     * The specific source of tuning parts are determined by the subclass.
     */
    protected abstract void loadTuningParts();

    /**
     * Abstract method to be implemented by subclasses to load cars into the car table.
     * The specific source of cars are determined by the subclass.
     */
    protected abstract void loadCars();
}
