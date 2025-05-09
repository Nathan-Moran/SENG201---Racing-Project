package seng201.team0;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopBuyController implements Initializable{

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

    public

    Garage inventoryList = new Garage();
    Garage ShopList = new Garage();


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
        ShopList.addCar(Toyota);
        ShopList.addCar(Mustang);
        ShopList.addCar(Ferrari);

        ShopList.addTuningParts(Ethanol);
        ShopList.addTuningParts(SuperCharger);
        ShopList.addTuningParts(TurboKit);
        ShopList.addTuningParts(StreetWheels);
        ShopList.addTuningParts(SportWheels);
        ShopList.addTuningParts(RacingWheels);

        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        reliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));

        carTable.setItems(ShopList.getCarList());


        partnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partboostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable.setItems(ShopList.getTuningPartList());

    }
}