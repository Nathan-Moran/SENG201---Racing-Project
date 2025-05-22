package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Controller for the Icon Key screen.
 * This class manages the navigation from the icon key screen to other parts of the application,
 * such as the item catalogue or the main menu.
 *
 * @author Nathan Moran
 */
public class IconKeyController {
    private final SceneNavigator sceneNavigator;

    /**
     * Constructs an {@code IconKeyController} with the specified game environment and scene navigator.
     *
     * @param sceneNavigator  The navigator responsible for changing scenes.
     */
    public IconKeyController(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Handles the action of navigating to the item catalogue screen.
     * This method is triggered by an FXML action event.
     *
     * @param event The {@link ActionEvent} that triggered this method.
     * @throws IOException if an error occurs during scene transition.
     */
    @FXML void switchToItemCatalogue(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCatalogueOne(event);
    }

    /**
     * Handles the action of navigating back to the main menu screen.
     * This method is triggered by an FXML action event.
     *
     * @param event The {@link ActionEvent} that triggered this method.
     * @throws IOException if an error occurs during scene transition.
     */
    @FXML void switchToMainMenu(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }
}
