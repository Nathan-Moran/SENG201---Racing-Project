package seng201.team0.models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * A utility class responsible for setting up and configuring a JavaFX {@link TableView}
 * for displaying {@link TuningPart} objects. It binds the columns of the table to
 * specific properties of the TuningPart class using {@link PropertyValueFactory}.
 */
public class SetupTuningPartTable {

    /**
     * Configures the columns of a {@link TableView} to display tuning part properties.
     * Each {@link TableColumn} is associated with a corresponding property name from the {@link TuningPart} class.
     * If a column is null, it is skipped.
     *
     * @param tuningPartTable The {@link TableView} instance to be set up.
     * @param partNameColumn The column for the tuning part's name.
     * @param partPriceColumn The column for the tuning part's price.
     * @param partStatColumn The column for the stat (e.g., "💨" for speed, "🎮" for handling) the part affects.
     * @param partBoostColumn The column for the boost multiplier the part provides.
     */
    public void setupTuningPartTable(
            TableView<TuningPart> tuningPartTable,
            TableColumn<TuningPart, String> partNameColumn,
            TableColumn<TuningPart, Integer> partPriceColumn,
            TableColumn<TuningPart, String> partStatColumn, // Assuming stat is a String (emoji)
            TableColumn<TuningPart, Double> partBoostColumn) {

        // Use PropertyValueFactory to link table columns to TuningPart object properties
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