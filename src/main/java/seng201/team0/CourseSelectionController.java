package seng201.team0;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import seng201.team0.gui.GameEnvironment;

public class CourseSelectionController {

    @FXML private VBox courseSelectionMenu;
    @FXML private VBox desertRouteMenu;
    @FXML private VBox mountainRouteMenu;
    @FXML private VBox countryRouteMenu;
    @FXML private VBox cityRouteMenu;

    @FXML
    private void onDesertSelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.DESERT);
        showRouteMenu(desertRouteMenu);
    }

    @FXML
    private void onMountainSelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.MOUNTAIN);
        showRouteMenu(mountainRouteMenu);
    }

    @FXML
    private void onCountrySelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.COUNTRY);
        showRouteMenu(countryRouteMenu);
    }

    @FXML
    private void onCitySelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.CITY);
        showRouteMenu(cityRouteMenu);
    }

    private void showRouteMenu(VBox routeMenu) {
        courseSelectionMenu.setVisible(false);
        courseSelectionMenu.setManaged(false);

        desertRouteMenu.setVisible(false);
        desertRouteMenu.setManaged(false);

        mountainRouteMenu.setVisible(false);
        mountainRouteMenu.setManaged(false);

        countryRouteMenu.setVisible(false);
        countryRouteMenu.setManaged(false);

        cityRouteMenu.setVisible(false);
        cityRouteMenu.setManaged(false);

        routeMenu.setVisible(true);
        routeMenu.setManaged(true);
    }
}
