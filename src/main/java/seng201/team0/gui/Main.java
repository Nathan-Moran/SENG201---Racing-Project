//package seng201.team0.gui;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//    protected SceneNavigator sceneNavigator;
//    protected GameEnvironment gameEnvironment;
//
//    public Main() {
//        this.gameEnvironment = new GameEnvironment();
//        this.sceneNavigator = new SceneNavigator(gameEnvironment);
//    }
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartingScreen.fxml"));
//        primaryStage.setTitle("Start Menu");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}


//AI STUFF
package seng201.team0.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    protected SceneNavigator sceneNavigator;
    protected GameEnvironment gameEnvironment;

    public Main() {
        this.gameEnvironment = new GameEnvironment();
        this.sceneNavigator = new SceneNavigator(this.gameEnvironment); // Assumes this constructor exists
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartingScreen.fxml"));

        // WARNING: This simplified factory IGNORES the 'controllerClass' parameter.
        // It assumes it will ONLY be called when StartMenuController is needed.
        // This is generally NOT robust or recommended practice.
        loader.setControllerFactory(ignoredControllerClass ->
                new StartMenuController(this.gameEnvironment, this.sceneNavigator)
        );

        Parent root = loader.load();

        primaryStage.setTitle("Start Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}