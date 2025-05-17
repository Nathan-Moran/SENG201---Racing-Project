package seng201.team0.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import seng201.team0.services.GameEnvironment;

import java.awt.event.MouseEvent;
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
     * Constructs a SceneNavigator with the given game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public SceneNavigator(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
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
        String title = "Shop Buy";
        String fxml = "/fxml/ShopBuyScene.fxml";
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
        String title = "Shop Sell";
        String fxml = "/fxml/ShopSellScene.fxml";
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
        String title = "Select your car";
        String fxml = "/fxml/CarSelectorScene.fxml";
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
     * Switches the current scene to the Race scene.
     * This version is intended for AWT MouseEvent, but the parameter type is java.awt.event.MouseEvent,
     * which might be a mismatch if JavaFX MouseEvent is intended elsewhere.
     *
     * @param event The AWT mouse event that triggered this navigation.
     * @throws IOException If an error occurs during FXML loading.
     * @deprecated Consider using the JavaFX MouseEvent version or clarifying usage.
     */
    public void switchToSceneRace(MouseEvent event) throws IOException {
        String title = "Race";
        String fxml = "/fxml/RaceScene.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        loader.setControllerFactory(ignoredControllerClass ->
                new RaceController(this.gameEnvironment, this)
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
        String title = "Course Selector";
        String fxml = "/fxml/courseSelector.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        // Set the controller using controller factory
        loader.setControllerFactory(ignoredControllerClass ->
                new CourseAndRouteSelectionController(this.gameEnvironment, this)
        );

        Parent parent = loader.load();

        // Get the controller and call initializeView()
        CourseAndRouteSelectionController controller = loader.getController();
        controller.initializeView();  // <- reset the view to course menu

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
        String title = "Main Menu";
        String fxml = "/fxml/MainMenuScene.fxml";
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
        String title = "Parts Manager";
        String fxml = "/fxml/GaragePartsScene.fxml";
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
        String title = "Race";
        String fxml = "/fxml/RaceScene.fxml";
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
        String title = "Race Results";
        String fxml = "/fxml/RaceFinishScene.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent parent = loader.load();

        // Get controller and pass data
        RaceFinishController controller = loader.getController();
        controller.setSceneNavigator(this);
        controller.setRaceResults(reason, placement, leaderboard, earnings);

//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(parent);
//        stage.setScene(scene);
//        stage.setTitle(title);
    }

}
