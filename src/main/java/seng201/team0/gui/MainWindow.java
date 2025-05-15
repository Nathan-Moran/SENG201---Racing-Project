package seng201.team0.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

/**
 * Class starts the javaFX application window
 * @author seng201 teaching team
 */
public class MainWindow extends Application {

    /**
     * Opens the gui with the fxml content specified in resources/fxml/main.fxml
     * @param primaryStage The current fxml stage, handled by javaFX Application class
     * @throws IOException if there is an issue loading fxml file
     */
    protected SceneNavigator sceneNavigator;
    protected GameEnvironment gameEnvironment;

    public MainWindow() {
        this.gameEnvironment = new GameEnvironment();
        this.sceneNavigator = new SceneNavigator(this.gameEnvironment); // Assumes this constructor exists
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
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
     * Launches the FXML application, this must be called from another class (in this cass App.java) otherwise JavaFX
     * errors out and does not run
     * @param args command line arguments
     */
    public static void launchWrapper(String [] args) {
        launch(args);
    }

}
