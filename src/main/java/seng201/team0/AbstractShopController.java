package seng201.team0;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractShopController implements Initializable {
    //Garage and Sellable Cars
    @FXML private TableView<Car> carTable;

    @FXML private TableColumn<Car, String> modelColumn;

    @FXML private TableColumn<Car, Integer> priceColumn;

    @FXML private TableColumn<Car, Double> speedColumn;

    @FXML private TableColumn<Car, Double> handlingColumn;

    @FXML private TableColumn<Car, Integer> reliabilityColumn;

    @FXML private TableColumn<Car, Integer> fuelColumn;


    @FXML private TableColumn<Car, String> handlingupgradeColumn;

    @FXML private TableColumn<Car, String> speedupgradeColumn;

    //Tuning Parts
    @FXML private TableView<TuningPart> tuningPartTable;

    @FXML private TableColumn<TuningPart, String> partnameColumn;

    @FXML private TableColumn<TuningPart, Integer> partpriceColumn;

    @FXML private TableColumn<TuningPart, String> partstatColumn;

    @FXML private TableColumn<TuningPart, Double> partboostColumn;

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

        carTable.setItems(ShopList.getCarList());


        partnameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partpriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstatColumn.setCellValueFactory(new PropertyValueFactory<>("stat"));
        partboostColumn.setCellValueFactory(new PropertyValueFactory<>("boost"));

        tuningPartTable.setItems(ShopList.getTuningPartList());
    }
}
