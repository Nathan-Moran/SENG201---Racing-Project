package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.util.List;

/**
 * Handles navigation between different scenes (views) in the JavaFX application.
 * This class provides methods to load FXML files and switch the content of the primary stage.
 * It uses a {@link GameEnvironment} instance to provide necessary data and services to the controllers of the scenes.
 * @author Nathan Moran
 */
public class SceneNavigator {
    /**
     * The game environment, providing access to game state and services for controllers.
     */
    private final GameEnvironment gameEnvironment;
    /**
     * The title to be set for the stage when a new scene is initialized.
     */
    String title;
    /**
     * The path to the FXML file to be loaded for the new scene.
     */
    String fxml;
    /**
     * The primary {@link Stage} of the JavaFX application, where scenes are displayed.
     */
    private Stage stage;

    /**
     * Constructs a SceneNavigator with the given game environment and the primary stage.
     * This constructor is used to initialize the navigator with the core application components.
     *
     * @param gameEnvironment The game environment instance.
     * @param stage The primary {@link Stage} of the application.
     */
    public SceneNavigator(GameEnvironment gameEnvironment, Stage stage) {
        this.gameEnvironment = gameEnvironment;
        this.stage = stage;
    }

    /**
     * Sets the primary {@link Stage} for the scene navigator.
     * This method can be used if the stage needs to be updated or set after initial construction.
     * @param stage The {@link Stage} to be set.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Switches the current scene to the Garage scene.
     * This scene allows the player to manage their owned cars.
     *
     * @param event The action event that triggered this navigation (e.g., button click).
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneGarage(ActionEvent event) throws IOException {
        String title = "Car Manager";
        String fxml = "/fxml/GarageCarScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new GarageController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }

    /**
     * Switches the current scene to the Shop Buy scene.
     * This scene allows the player to purchase new cars and tuning parts.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneShopBuy(ActionEvent event) throws IOException {
        title = "Shop Buy";
        fxml = "/fxml/ShopBuyScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new ShopBuyController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }

    /**
     * Switches the current scene to the Shop Sell scene.
     * This scene allows the player to sell their cars and tuning parts.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneShopSell(ActionEvent event) throws IOException {
        title = "Shop Sell";
        fxml = "/fxml/ShopSellScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new ShopSellController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }

    /**
     * Switches the current scene to the Car Selector scene.
     * This scene is typically used at the beginning of the game for initial car selection.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCarSelector(ActionEvent event) throws IOException {
        title = "Select your car";
        fxml = "/fxml/CarSelectorScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new CarSelectorController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }


    /**
     * Switches the current scene to the Course and Route Selection scene.
     * This scene allows the player to choose a race course and a specific route within it.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCourseAndRoute(ActionEvent event) throws IOException {
        title = "Course Selector";
        fxml = "/fxml/courseSelector.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new CourseAndRouteSelectionController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }

    /**
     * Switches the current scene to the Main Menu scene.
     * This is the central hub for navigating to other parts of the game.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneMainMenu(ActionEvent event) throws IOException {
        title = "Main Menu";
        fxml = "/fxml/MainMenuScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new MainMenuController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }

    /**
     * Switches the current scene to the Parts Manager scene (Garage Parts).
     * This scene allows the player to install and uninstall tuning parts on their selected car.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToScenePartsManager(ActionEvent event) throws IOException {
        title = "Parts Manager";
        fxml = "/fxml/GaragePartsScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new GaragePartsController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
    }

    /**
     * Switches the current scene to the Race scene.
     * This method loads the FXML for the race simulation and sets up its controller.
     *
     * @param event The JavaFX mouse event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneRace(javafx.scene.input.MouseEvent event) throws IOException {
        title = "Race";
        fxml = "/fxml/RaceScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new RaceController(this.gameEnvironment, this)
        );
        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Switches the current scene to the Race Finish scene.
     * This method loads the RaceFinishScene.fxml, gets its controller, and passes race result data to it.
     * The stage is then updated to display the race finish scene.
     *
     * @author Jamie wood
     * @param reason The reason for the race finishing (e.g., "Finished", "Crashed").
     * @param placement The player's placement in the race (e.g., "1st", "DNF").
     * @param leaderboard A list of strings representing the race leaderboard.
     * @param earnings    The amount of money earned from the race.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToRaceFinishScene(String reason, String placement, List<String> leaderboard, int earnings) throws IOException {
        title = "Race Results";
        fxml = "/fxml/RaceFinishScreen.fxml";


        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(ignoredClass -> new RaceFinishController(this.gameEnvironment, this));

        Parent parent = loader.load();

        RaceFinishController controller = loader.getController();

        controller.setRaceResults(reason, placement, leaderboard, earnings);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Switches the current scene to the Game Finish scene.
     * This scene displays the overall results and statistics at the end of the game season.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToFinishGameScene(ActionEvent event) throws IOException {
        title = "Game Results";
        fxml = "/fxml/GameFinishScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new FinishGameController(this.gameEnvironment, this)
        );
        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Switches the current scene to the first page of the Item Catalogue.
     * This scene displays a list of starter cars and general shop cars.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCatalogueOne(ActionEvent event) throws IOException {
        title = "Item Catalogue";
        fxml = "/fxml/ItemCatalogueScene1.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new CatalogueOneController(this.gameEnvironment, this)
        );
        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Switches the current scene to the second page of the Item Catalogue.
     * This scene displays a list of unlockable cars and all tuning parts.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCatalogueTwo(ActionEvent event) throws IOException {
        title = "Item Catalogue";
        fxml = "/fxml/ItemCatalogueScene2.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new CatalogueTwoController(this.gameEnvironment, this)
        );
        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    /**
     * Switches the current scene to the Tutorial screen.
     * This scene provides instructions on how to play the game.
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneTutorial(ActionEvent event) throws IOException {
        title = "How to Play";
        fxml = "/fxml/Tutorial2Scene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new TutorialController(this) // Corrected constructor to pass gameEnvironment
        );

        Parent parent = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
