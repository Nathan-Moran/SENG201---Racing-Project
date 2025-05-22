package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.util.List;

/**
 * Manages scene transitions within the JavaFX application.
 * This class is responsible for loading FXML views, initializing their controllers,
 * and displaying them on the primary application stage. It utilizes a
 * {@link GameEnvironment} to provide shared game data and services to controllers.
 *
 * @author Nathan Moran
 */
public class SceneNavigator {

    /**
     * The central game environment, providing controllers with access to game state and services.
     */
    private final GameEnvironment gameEnvironment;

    /**
     * The primary stage of the application where scenes are rendered.
     */
    private final Stage stage;

    /**
     * A functional interface for creating controller instances.
     * Implementations are responsible for providing a new controller instance,
     * typically initialized with the {@link GameEnvironment} and this {@link SceneNavigator}.
     */
    @FunctionalInterface
    private interface ControllerCreator {
        Object create(GameEnvironment gameEnv, SceneNavigator navigator);
    }

    /**
     * Constructs a {@code SceneNavigator} associated with the given game environment and primary stage.
     *
     * @param gameEnvironment The application's {@link GameEnvironment}.
     * @param stage The primary {@link Stage} for displaying scenes.
     */
    public SceneNavigator(GameEnvironment gameEnvironment, Stage stage) {
        this.gameEnvironment = gameEnvironment;
        this.stage = stage;
    }

    /**
     * Loads an FXML file, instantiates its controller, and displays the new scene on the primary stage.
     * This is a private helper method to centralize scene switching logic.
     *
     * @param fxmlPath The resource path to the FXML file.
     * @param sceneTitle The title to be set for the stage.
     * @param controllerFactory A {@link ControllerCreator} implementation that provides the controller instance.
     * @param callShow Specifies whether {@link Stage#show()} should be invoked after setting the scene.
     * @throws IOException If an error occurs during FXML loading.
     */
    private void loadAndSwitchScene(String fxmlPath, String sceneTitle,
                                    ControllerCreator controllerFactory, boolean callShow) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(controllerClass -> controllerFactory.create(this.gameEnvironment, this));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        this.stage.setScene(scene);
        this.stage.setTitle(sceneTitle);
        if (callShow) {
            this.stage.show();
        }
    }

    /**
     * Switches to the Garage scene, where the player manages their cars.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneGarage(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/GarageCarScene.fxml", "Car Manager",
                GarageController::new, false);
    }

    /**
     * Switches to the Shop Buy scene, allowing the player to purchase items.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneShopBuy(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/ShopBuyScene.fxml", "Shop Buy",
                ShopBuyController::new, false);
    }

    /**
     * Switches to the Shop Sell scene, allowing the player to sell items.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneShopSell(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/ShopSellScene.fxml", "Shop Sell",
                ShopSellController::new, false);
    }

    /**
     * Switches to the Car Selector scene for initial car selection.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCarSelector(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/CarSelectorScene.fxml", "Select your car",
                CarSelectorController::new, false);
    }

    /**
     * Switches to the Course and Route Selection scene.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCourseAndRoute(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/courseSelector.fxml", "Course Selector",
                CourseAndRouteSelectionController::new, false);
    }

    /**
     * Switches to the Main Menu scene, the central navigation hub.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneMainMenu(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/MainMenuScene.fxml", "Main Menu",
                MainMenuController::new, false);
    }

    /**
     * Switches to the Parts Manager scene for car part management.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToScenePartsManager(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/GaragePartsScene.fxml", "Parts Manager",
                GaragePartsController::new, false);
    }

    /**
     * Switches to the Race scene for the race simulation.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link MouseEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneRace(MouseEvent event) throws IOException {
        loadAndSwitchScene("/fxml/RaceScene.fxml", "Race",
                RaceController::new, true);
    }


    /**
     * Switches to the Game Finish scene to display end-of-game results.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToFinishGameScene(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/GameFinishScene.fxml", "Game Results",
                FinishGameController::new, true);
    }

    /**
     * Switches to the first page of the Item Catalogue.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCatalogueOne(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/ItemCatalogueScene1.fxml", "Item Catalogue",
                CatalogueOneController::new, true);
    }

    /**
     * Switches to the second page of the Item Catalogue.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCatalogueTwo(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/ItemCatalogueScene2.fxml", "Item Catalogue",
                CatalogueTwoController::new, true);
    }

    /**
     * Switches to the Tutorial screen.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     * Note: Controller instantiation assumes {@link TutorialController} accepts
     * {@code GameEnvironment} and {@code SceneNavigator}, consistent with other controllers.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneTutorial(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/TutorialScene.fxml", "How to Play",
                (gameEnv, navigator) -> new TutorialController(navigator), true);

    }

    /**
     * Switches to the Icon Key screen, which provides a legend for in-game icons.
     * The {@code event} parameter is standard for FXML event handlers;
     * stage management is handled internally by this navigator using its primary stage.
     *
     * @param event The {@link ActionEvent} that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchIconKeyScene(ActionEvent event) throws IOException {
        loadAndSwitchScene("/fxml/IconKeyScene.fxml", "Icon Key",
                (gameEnv, navigator) -> new IconKeyController(navigator),
                false);
    }

    /**
     * Switches to the Race Finish scene to display race results.
     * This method loads the scene, retrieves its controller ({@link RaceFinishController}),
     * and populates it with race outcome data before displaying it on the primary stage.
     *
     * @param reason The cause of the race's conclusion (e.g., "Finished", "Crashed").
     * @param placement The player's final standing in the race (e.g., "1st", "DNF").
     * @param leaderboard A list of strings detailing the race leaderboard.
     * @param earnings The currency amount awarded to the player for the race.
     * @throws IOException If an error occurs during FXML loading.
     * @author Jamie wood
     */
    public void switchToRaceFinishScene(String reason, String placement, List<String> leaderboard, int earnings) throws IOException {
        String sceneTitle = "Race Results";
        String fxmlPath = "/fxml/RaceFinishScreen.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(ignoredClass -> new RaceFinishController(this.gameEnvironment, this));

        Parent parent = loader.load();
        RaceFinishController controller = loader.getController();
        controller.setRaceResults(reason, placement, leaderboard, earnings);

        Scene scene = new Scene(parent);
        this.stage.setScene(scene);
        this.stage.setTitle(sceneTitle);
        this.stage.show();
    }
}