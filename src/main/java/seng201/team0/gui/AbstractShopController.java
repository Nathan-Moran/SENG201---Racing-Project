package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.Car;
import seng201.team0.models.SetupCarTable;
import seng201.team0.models.SetupTuningPartTable;
import seng201.team0.models.TuningPart;
import seng201.team0.services.GameEnvironment;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Abstract base class for shop controllers, providing common functionality for displaying cars and tuning parts.
 * It handles the initialization of tables for cars and tuning parts.
 * @author Nathan Moran
 */
public abstract class AbstractShopController implements Initializable {
    @FXML private Label moneyLabel;
    @FXML protected TableView<Car> carTable;
    @FXML private TableColumn<Car, String> modelColumn;
    @FXML private TableColumn<Car, Integer> priceColumn;
    @FXML private TableColumn<Car, String> speedColumn;
    @FXML private TableColumn<Car, String> handlingColumn;
    @FXML private TableColumn<Car, String> reliabilityColumn;
    @FXML private TableColumn<Car, String> fuelColumn;
    @FXML protected TableView<TuningPart> tuningPartTable;
    @FXML private TableColumn<TuningPart, String> partNameColumn;
    @FXML private TableColumn<TuningPart, Integer> partPriceColumn;
    @FXML private TableColumn<TuningPart, String> partStatColumn;
    @FXML private TableColumn<TuningPart, Double> partBoostColumn;

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    private SetupCarTable setupCarTable;
    private SetupTuningPartTable setupTuningPartTable;

    /**
     * Constructs an AbstractShopController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public AbstractShopController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.setupCarTable = new SetupCarTable();
        this.setupTuningPartTable = new SetupTuningPartTable();
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
        if (carTable != null) {
            setupCarTable.setupCarTable(
                    carTable,
                    modelColumn,
                    priceColumn,
                    speedColumn,
                    handlingColumn,
                    reliabilityColumn,
                    fuelColumn
            );
        }
    }

    /**
     * Sets up the columns for the tuning part table, binding them to the properties of the TuningPart class.
     */
    protected void setupTuningPartTable() {
        setupTuningPartTable.setupTuningPartTable(
                tuningPartTable,
                partNameColumn,
                partPriceColumn,
                partStatColumn,
                partBoostColumn
        );
    }

    /**
     * Abstract method to be implemented by subclasses to load tuning parts into the tuning part table.
     * The specific source of tuning parts is determined by the subclass.
     */
    protected abstract void loadTuningParts();

    /**
     * Abstract method to be implemented by subclasses to load cars into the car table.
     * The specific source of cars is determined by the subclass.
     */
    protected abstract void loadCars();
}
