package seng201.team0.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
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
     * The name of the scene to be initialised
     */
    String title;
    /**
     * The name of the fxml file to be initialised
     */
    String fxml;
    private Stage stage;
    /**
     * Constructs a SceneNavigator with the given game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public SceneNavigator(GameEnvironment gameEnvironment, Stage stage) {
        this.gameEnvironment = gameEnvironment;
        this.stage = stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Switches the current scene to the Garage scene.
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
     *
     * @param event The action event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToSceneCourseAndRoute(ActionEvent event) throws IOException {
        title = "Course Selector";
        fxml = "/fxml/courseSelector.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        // Set the controller using controller factory
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
     * Switches the current scene to the Race scene using a JavaFX MouseEvent.
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
     * Note: The stage switching part is commented out, implying the RaceFinishController might handle its own display
     * or this method is called from a context where the stage is already managed.
     *
     * @author Jamie wood
     * @param reason      The reason for the race finishing (e.g., "Finished", "Crashed").
     * @param placement   The player's placement in the race (e.g., "1st", "DNF").
     * @param leaderboard A list of strings representing the race leaderboard.
     * @param earnings    The amount of money earned from the race.
     * @throws IOException If an error occurs during FXML loading.
     */
    public void switchToRaceFinishScene(String reason, String placement, List<String> leaderboard, int earnings) throws IOException {
        title = "Race Results";
        fxml = "/fxml/RaceFinishScreen.fxml";

        // Set controller factory to inject GameEnvironment and SceneNavigator
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(ignoredClass -> new RaceFinishController(this.gameEnvironment, this));

        Parent parent = loader.load();

        // Get the controller instance created via factory
        RaceFinishController controller = loader.getController();

        // Set the race results after FXML has loaded
        controller.setRaceResults(reason, placement, leaderboard, earnings);

        // Create and show the new scene
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

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

}
