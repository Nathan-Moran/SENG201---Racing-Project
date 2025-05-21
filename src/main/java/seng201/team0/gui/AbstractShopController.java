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
 * It handles the initialization of tables for cars and tuning parts, and manages common dependencies.
 * Subclasses are responsible for implementing how cars and tuning parts are loaded into these tables.
 * @author Nathan Moran
 */
public abstract class AbstractShopController implements Initializable {
    /**
     * Label displaying the player's current money balance.
     */
    @FXML private Label moneyLabel;
    /**
     * TableView for displaying a list of cars. This table is protected to allow subclasses to access it.
     */
    @FXML protected TableView<Car> carTable;
    /**
     * TableColumn for the model name of cars in the car table.
     */
    @FXML private TableColumn<Car, String> modelColumn;
    /**
     * TableColumn for the price of cars in the car table.
     */
    @FXML private TableColumn<Car, Integer> priceColumn;
    /**
     * TableColumn for the speed stat of cars in the car table.
     */
    @FXML private TableColumn<Car, String> speedColumn;
    /**
     * TableColumn for the handling stat of cars in the car table.
     */
    @FXML private TableColumn<Car, String> handlingColumn;
    /**
     * TableColumn for the reliability stat of cars in the car table.
     */
    @FXML private TableColumn<Car, String> reliabilityColumn;
    /**
     * TableColumn for the fuel economy stat of cars in the car table.
     */
    @FXML private TableColumn<Car, String> fuelColumn;
    /**
     * TableView for displaying a list of tuning parts. This table is protected to allow subclasses to access it.
     */
    @FXML protected TableView<TuningPart> tuningPartTable;
    /**
     * TableColumn for the name of tuning parts in the tuning part table.
     */
    @FXML private TableColumn<TuningPart, String> partNameColumn;
    /**
     * TableColumn for the price of tuning parts in the tuning part table.
     */
    @FXML private TableColumn<TuningPart, Integer> partPriceColumn;
    /**
     * TableColumn for the stat affected by tuning parts in the tuning part table.
     */
    @FXML private TableColumn<TuningPart, String> partStatColumn;
    /**
     * TableColumn for the boost provided by tuning parts in the tuning part table.
     */
    @FXML private TableColumn<TuningPart, Double> partBoostColumn;

    /**
     * The game environment instance, providing access to global game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator instance, used for switching between different application scenes.
     */
    protected SceneNavigator sceneNavigator;

    /**
     * Helper class for setting up and configuring {@link TableView} for {@link Car} objects.
     */
    private SetupCarTable setupCarTable;
    /**
     * Helper class for setting up and configuring {@link TableView} for {@link TuningPart} objects.
     */
    private SetupTuningPartTable setupTuningPartTable;

    /**
     * Constructs an AbstractShopController with the given game environment and scene navigator.
     * Initializes helper classes for table setup.
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
     * This method sets up the car and tuning part tables by calling their respective
     * setup methods, then loads initial data into them using abstract methods that
     * must be implemented by concrete subclasses, and finally updates the money label.
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
     * Sets up the columns for the car table ({@link #carTable}), binding them to the properties of the {@link Car} class.
     * This method uses the {@link SetupCarTable} helper to configure the table columns.
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
     * Sets up the columns for the tuning part table ({@link #tuningPartTable}), binding them to the properties of the {@link TuningPart} class.
     * This method uses the {@link SetupTuningPartTable} helper to configure the table columns.
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
     * Abstract method to be implemented by subclasses to load tuning parts into the tuning part table ({@link #tuningPartTable}).
     * The specific source of tuning parts (e.g., shop inventory, player inventory) is determined by the subclass.
     */
    protected abstract void loadTuningParts();

    /**
     * Abstract method to be implemented by subclasses to load cars into the car table ({@link #carTable}).
     * The specific source of cars (e.g., shop inventory, player inventory) is determined by the subclass.
     */
    protected abstract void loadCars();
}
