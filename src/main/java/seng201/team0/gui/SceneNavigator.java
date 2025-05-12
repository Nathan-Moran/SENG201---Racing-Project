package seng201.team0.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneNavigator {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneGarage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/GarageScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneShopBuy(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ShopBuyScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchToSceneShopSell(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ShopSellScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneMenu(ActionEvent event) throws IOException {
    }

    public void switchToSceneRace(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RaceScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
