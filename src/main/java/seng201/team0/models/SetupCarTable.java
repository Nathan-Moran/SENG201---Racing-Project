package seng201.team0.models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SetupCarTable {

    public void setupCarTable(
            TableView<Car> carTable,
            TableColumn<Car, String> modelColumn,
            TableColumn<Car, Integer> priceColumn,
            TableColumn<Car, Double> speedColumn,
            TableColumn<Car, Double> handlingColumn,
            TableColumn<Car, Double> reliabilityColumn,
            TableColumn<Car, Double> fuelColumn) {

        if (modelColumn != null) {
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (priceColumn != null) {
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        if (speedColumn != null) {
            speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        }
        if (handlingColumn != null) {
            handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        }
        if (reliabilityColumn != null) {
            reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        }
        if (fuelColumn != null) {
            fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
        }
    }
}

