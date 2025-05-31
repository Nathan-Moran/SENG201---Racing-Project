package seng201.team0.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

/**
 * Class starts the JavaFX application window. This is the main entry point
 * for the graphical user interface of the application.
 * @author seng201 teaching team
 */
public class MainWindow extends Application {

    /**
     * Default constructor for the MainWindow class.
     * This constructor is implicitly provided by Java if no other constructors are defined.
     * As this is the main application class, direct instantiation is typically not intended,
     * as the JavaFX runtime handles the creation of the application instance.
     */
    public MainWindow() {
    }

    /**
     * The {@link SceneNavigator} instance responsible for managing and
     * switching between different scenes (views) in the application.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * The {@link GameEnvironment} instance, which holds the global game state
     * and provides access to various game services.
     */
    protected GameEnvironment gameEnvironment;

    /**
     * Opens the GUI with the FXML content specified in resources/fxml/StartingScreen.fxml.
     * This method is the entry point for the JavaFX application, called by the JavaFX runtime.
     * It initializes the game environment and scene navigator, then loads and displays the starting screen.
     * @param primaryStage The current FXML stage, handled by JavaFX Application class.
     * @throws IOException if there is an issue loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.gameEnvironment = new GameEnvironment();
        this.sceneNavigator = new SceneNavigator(this.gameEnvironment, primaryStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartingScreen.fxml"));
        loader.setControllerFactory(ignoredControllerClass ->
                new StartMenuController(this.gameEnvironment, this.sceneNavigator)
        );

        Parent root = loader.load();

        primaryStage.setTitle("Start Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Launches the FXML application. This method serves as a wrapper for the
     * standard {@code Application.launch()} method, allowing it to be called
     * from another class (e.g., an {@code App.java} or {@code Main.java} class)
     * without JavaFX errors.
     * @param args command line arguments passed to the application.
     */
    public static void launchWrapper(String [] args) {
        launch(args);
    }

}
