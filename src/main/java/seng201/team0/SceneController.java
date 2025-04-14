package seng201.team0;

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

    ObservableList<Car> reserveCarList = FXCollections.observableArrayList(
            new Car("Toyota Supra", 180, 6, 8, 538, 67000),
            new Car("Mustang", 250, 6, 6, 400, 89000),
            new Car("Ferrari 458", 330, 8, 7, 300, 485000)
    );

    ObservableList<TuningPart> buyableTuningParts = FXCollections.observableArrayList(
            new TuningPart("Ethanol", 4000, "\uD83D\uDCA8", 1.2),
            new TuningPart("SuperCharger", 14000, "\uD83D\uDCA8", 1.5),
            new TuningPart("TurboKit", 21000, "\uD83D\uDCA8", 1.8),

            new TuningPart("StreetWheels", 2500, "\uD83C\uDFAE", 1.2),
            new TuningPart("SportsWheels", 6400, "\uD83C\uDFAE", 1.5),
            new TuningPart("RacingWheels", 10000, "\uD83C\uDFAE", 1.8)
    );


    //Garage and Sellable Cars
    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, Integer> priceColumn;

    @FXML
    private TableColumn<Car, Double> speedColumn;

    @FXML
    private TableColumn<Car, Double> handlingColumn;

    @FXML
    private TableColumn<Car, Integer> reliabilityColumn;

    @FXML
    private TableColumn<Car, Integer> fuelColumn;


    @FXML
    private TableColumn<Car, String> handlingupgradeColumn;

    @FXML
    private TableColumn<Car, String> speedupgradeColumn;

    //Tuning Parts
    @FXML
    private TableView<TuningPart> tuningPartTable;

    @FXML
    private TableColumn<TuningPart, String> partnameColumn;

    @FXML
    private TableColumn<TuningPart, Integer> partpriceColumn;

    @FXML
    private TableColumn<TuningPart, String> partstatColumn;

    @FXML
    private TableColumn<TuningPart, Double> partboostColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));
        handlingupgradeColumn.setCellValueFactory(new PropertyValueFactory<>("handlingUpgrade"));
        speedupgradeColumn.setCellValueFactory(new PropertyValueFactory<>("speedUpgrade"));

        carTable.setItems(reserveCarList);


        partnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partboostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable.setItems(buyableTuningParts);
    }
}