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

public class SceneNavigator {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final GameEnvironment gameEnvironment;

    public SceneNavigator(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public void switchToSceneGarage(ActionEvent event) throws IOException {
        String title = "Garage";
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

    public void switchToScenePartsMenu(ActionEvent event) throws IOException {
        String title = "Parts Menu";
        String fxml = "/fxml/GaragePartsScene.fxml";
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


    public void switchToSceneCarSelector(ActionEvent event) throws IOException {
//        CarSelectorController carSelectorController = new CarSelectorController(gameEnvironment, this);
//
//        root = FXMLLoader.load(getClass().getResource("/fxml/CarSelectorScene.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

        //AI CODE
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

    public void switchToSceneCourseAndRoute(ActionEvent event) throws IOException {
        CourseAndRouteSelectionController courseAndRouteSelectionController = new CourseAndRouteSelectionController(gameEnvironment, this);

        String title = "Course Selector";
        String fxml = "/fxml/courseSelector.fxml";
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

    }
}
