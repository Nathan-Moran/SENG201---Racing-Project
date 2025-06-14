package seng201.team0.gui;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.models.TuningPart;

/**
 * A utility class responsible for setting up and configuring a JavaFX {@link TableView}
 * for displaying {@link TuningPart} objects. It binds the columns of the table to
 * specific properties of the TuningPart class using {@link PropertyValueFactory}.
 */
public class SetupTuningPartTable {

    /**
     * Configures the columns of a {@link TableView} to display tuning part properties.
     * Each {@link TableColumn} is associated with a corresponding property name from the {@link TuningPart} class.
     * If a column is null, it is skipped, allowing for flexible table configurations where not all tuning part properties are displayed.
     *
     * @param tuningPartTable The {@link TableView} instance to be set up.
     * @param partNameColumn The column for the tuning part's name. Can be {@code null} if not needed.
     * @param partPriceColumn The column for the tuning part's price. Can be {@code null} if not needed.
     * @param partStatColumn The column for the stat (e.g., "💨" for speed, "🎮" for handling) the part affects. Can be {@code null} if not needed.
     * @param partBoostColumn The column for the boost multiplier the part provides. Can be {@code null} if not needed.
     */
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
