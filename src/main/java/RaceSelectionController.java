import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;

public class RaceSelectionController {

    @FXML private VBox courseSelectionMenu;
    @FXML private VBox desertRouteMenu;
    @FXML private VBox mountainRouteMenu;
    @FXML private VBox countryRouteMenu;
    @FXML private VBox cityRouteMenu;

    @FXML
    private void onDesertSelected(ActionEvent event) {
        showRouteMenu(desertRouteMenu);
    }

    @FXML
    private void onMountainSelected(ActionEvent event) {
        showRouteMenu(mountainRouteMenu);
    }

    @FXML
    private void onCountrySelected(ActionEvent event) {
        showRouteMenu(countryRouteMenu);
    }

    @FXML
    private void onCitySelected(ActionEvent event) {
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

        // Show the selected one
        routeMenu.setVisible(true);
        routeMenu.setManaged(true);
    }
}