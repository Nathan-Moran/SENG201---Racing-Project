package seng201.team0.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import seng201.team0.CourseAndRouteSelectionController;
import seng201.team0.Garage;

import java.io.IOException;

public class SceneNavigator {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final GameEnvironment gameEnvironment;

    public SceneNavigator(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public void switchToSceneGarage(ActionEvent event) throws IOException {
//        GarageController garageController = new GarageController(GameEnvironment gameEnvironment);

        root = FXMLLoader.load(getClass().getResource("/fxml/GarageScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneShopBuy(ActionEvent event) throws IOException {
        ShopBuyController shopBuyController = new ShopBuyController(gameEnvironment, this);

        root = FXMLLoader.load(getClass().getResource("/fxml/ShopBuyScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneShopSell(ActionEvent event) throws IOException {
        ShopSellController shopSellController = new ShopSellController(gameEnvironment, this);

        root = FXMLLoader.load(getClass().getResource("/fxml/ShopSellScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneMenu(ActionEvent event) throws IOException {
    }

    public void switchToSceneCarSelector(ActionEvent event) throws IOException {
        CarSelectorController carSelectorController = new CarSelectorController(gameEnvironment, this);

    }

    public void switchToSceneRace(ActionEvent event) throws IOException {
        RaceController raceController = new RaceController(gameEnvironment, this);
    }

    public void switchToSceneCourseAndRoute(ActionEvent event) throws IOException {
        CourseAndRouteSelectionController courseAndRouteSelectionController = new CourseAndRouteSelectionController(gameEnvironment, this);
    }
}
