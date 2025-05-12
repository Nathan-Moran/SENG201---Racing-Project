package seng201.team0.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    protected SceneNavigator sceneNavigator;
    protected GameEnvironment gameEnvironment;

    public Main() {
        this.gameEnvironment = new GameEnvironment();
        this.sceneNavigator = new SceneNavigator(gameEnvironment);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ShopSellScene.fxml"));
        primaryStage.setTitle("Shop - Sell");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}