package seng201.team0.models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * A utility class responsible for setting up and configuring a JavaFX {@link TableView}
 * for displaying {@link Car} objects. It binds the columns of the table to
 * specific properties of the Car class using {@link PropertyValueFactory}.
 */
public class SetupCarTable {

    /**
     * Configures the columns of a {@link TableView} to display car properties.
     * Each {@link TableColumn} is associated with a corresponding property name from the {@link Car} class.
     * If a column is null, it is skipped.
     *
     * @param carTable The {@link TableView} instance to be set up.
     * @param modelColumn The column for the car's model/name.
     * @param priceColumn The column for the car's price.
     * @param speedColumn The column for the car's speed attribute.
     * @param handlingColumn The column for the car's handling attribute.
     * @param reliabilityColumn The column for the car's reliability percentage.
     * @param fuelColumn The column for the car's fuel economy.
     */
    public void setupCarTable(
            TableView<Car> carTable,
            TableColumn<Car, String> modelColumn,
            TableColumn<Car, Integer> priceColumn,
            TableColumn<Car, String> speedColumn,
            TableColumn<Car, String> handlingColumn,
            TableColumn<Car, String> reliabilityColumn,
            TableColumn<Car, String> fuelColumn) {

        if (modelColumn != null) {
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (priceColumn != null) {
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        if (speedColumn != null) {
            speedColumn.setCellValueFactory(new PropertyValueFactory<>("speedString"));
        }
        if (handlingColumn != null) {
            handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handlingPercent"));
        }
        if (reliabilityColumn != null) {
            reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliabilityPercent"));
        }
        if (fuelColumn != null) {
            fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomyString"));
        }
    }
}

