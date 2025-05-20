package seng201.team0.models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SetupTuningPartTable {

    public void setupTuningPartTable(
            TableView<TuningPart> tuningPartTable,
            TableColumn<TuningPart, String> partNameColumn,
            TableColumn<TuningPart, Integer> partPriceColumn,
            TableColumn<TuningPart, String> partStatColumn,
            TableColumn<TuningPart, Double> partBoostColumn) {

        if (partNameColumn != null) {
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        if (partPriceColumn != null) {
            partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        if (partStatColumn != null) {
            partStatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        }
        if (partBoostColumn != null) {
            partBoostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));
        }
    }
}
