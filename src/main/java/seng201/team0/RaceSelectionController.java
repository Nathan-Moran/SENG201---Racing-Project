package seng201.team0;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class RaceSelectionController {

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private ComboBox<Route> routeComboBox;

    @FXML
    private Button startRaceButton;

    @FXML
    private Label summaryLabel;

    @FXML
    private void initialize() {
        // Populate course options on startup
        courseComboBox.getItems().addAll(Course.values());

        // Listen for course selection and update routes
        courseComboBox.setOnAction(event -> onCourseSelected());
    }

    private void onCourseSelected() {
        Course selectedCourse = courseComboBox.getValue();
        if (selectedCourse != null) {
            routeComboBox.getItems().setAll(selectedCourse.getAvailableRoutes());
            routeComboBox.setValue(null); // Clear any previous route selection
        }
    }

    @FXML
    private void onStartRaceClicked() {
        Course selectedCourse = courseComboBox.getValue();
        Route selectedRoute = routeComboBox.getValue();

        if (selectedCourse == null || selectedRoute == null) {
            summaryLabel.setText("Please select both a course and a route.");
            return;
        }

        if (!selectedCourse.getAvailableRoutes().contains(selectedRoute)) {
            summaryLabel.setText("Invalid route for the selected course.");
            return;
        }

        Race race = new Race(selectedCourse, selectedRoute);
        summaryLabel.setText("Race selected: " + race.toString());
    }
}