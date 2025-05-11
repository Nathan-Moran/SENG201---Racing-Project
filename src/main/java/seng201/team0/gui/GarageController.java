package seng201.team0.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng201.team0.Car;

import java.net.URL;
import java.util.ResourceBundle;


public class GarageController implements Initializable {

    ObservableList<Car> reserveCars = FXCollections.observableArrayList(
            new Car("Toyota Supra", 180, 6, 8, 538, 67000),
            new Car("Mustang", 250, 6, 6, 400, 89000),
            new Car("Ferrari 458", 330, 8, 7, 300, 485000)
    );

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> modelColumn;

    @FXML
    private TableColumn<Car, Integer> ReliabilityColumn;

    @FXML
    private TableColumn<Car, Integer> fuelColumn;

    @FXML
    private TableColumn<Car, Double> handlingColumn;

    @FXML
    private TableColumn<Car, Double> speedColumn;

//    @FXML
//    private TableColumn<Car, String> handlingupgradeColumn;

//    @FXML
//    private TableColumn<Car, String> speedupgradeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        handlingColumn.setCellValueFactory(new PropertyValueFactory<>("handling"));
        ReliabilityColumn.setCellValueFactory(new PropertyValueFactory<>("reliability"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuelEconomy"));

        carTable.setItems(reserveCars);
    }
}
