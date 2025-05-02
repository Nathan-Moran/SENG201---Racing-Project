package com.example.aitesting2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneGarage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/aitesting2/ShopSellScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneShop(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/aitesting2/ShopBuyScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<Car> reserveCarList = FXCollections.observableArrayList(
            new Car("Toyota Supra", 180, 6, 8, 538, 67000),
            new Car("Mustang", 250, 6, 6, 400, 89000),
            new Car("Ferrari 458", 330, 8, 7, 300, 485000)
    );


    //Garage
    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, Integer> reliabilityColumn;

    @FXML
    private TableColumn<Car, Integer> fuelColumn;

    @FXML
    private TableColumn<Car, Double> handlingColumn;

    @FXML
    private TableColumn<Car, Double> speedColumn;

    @FXML
    private TableColumn<Car, String> handlingupgradeColumn;

    @FXML
    private TableColumn<Car, String> speedupgradeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
        handlingupgradeColumn.setCellValueFactory(new PropertyValueFactory<>("handlingUpgrade"));
        speedupgradeColumn.setCellValueFactory(new PropertyValueFactory<>("speedUpgrade"));

        carTable.setItems(reserveCarList);
    }
}