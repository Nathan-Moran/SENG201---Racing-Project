package seng201.team0.models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SetupCarTable {

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

