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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    Car Toyota = new Car("Toyota Supra", 180, 6, 8, 538, 67000);
    Car Mustang = new Car("Mustang", 250, 6, 6, 400, 89000);
    Car Ferrari = new Car("Ferrari 458", 330, 8, 7, 300, 485000);

    TuningPart Ethanol = new TuningPart("Ethanol", 4000, "\uD83D\uDCA8", 1.2);
    TuningPart SuperCharger = new TuningPart("SuperCharger", 14000, "\uD83D\uDCA8", 1.5);
    TuningPart TurboKit = new TuningPart("TurboKit", 21000, "\uD83D\uDCA8", 1.8);

    TuningPart StreetWheels = new TuningPart("StreetWheels", 2500, "\uD83C\uDFAE", 1.2);
    TuningPart SportWheels = new TuningPart("SportsWheels", 6400, "\uD83C\uDFAE", 1.5);
    TuningPart RacingWheels = new TuningPart("RacingWheels", 10000, "\uD83C\uDFAE", 1.8);

    Garage ShopList = new Garage();
    Garage inventoryList = new Garage();


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

    //Garage and Sellable Cars
    @FXML private TableView<Car> carTable;

    @FXML private TableColumn<Car, String> modelColumn;

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
        ShopList.addCar(Toyota);
        ShopList.addCar(Mustang);
        ShopList.addCar(Ferrari);

        ShopList.addTuningParts(Ethanol);
        ShopList.addTuningParts(SuperCharger);
        ShopList.addTuningParts(TurboKit);
        ShopList.addTuningParts(StreetWheels);
        ShopList.addTuningParts(SportWheels);
        ShopList.addTuningParts(RacingWheels);



    }
}