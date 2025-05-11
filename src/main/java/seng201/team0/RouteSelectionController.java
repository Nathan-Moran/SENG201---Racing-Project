//package seng201.team0;
//
//import javafx.fxml.FXML;
//import javafx.event.ActionEvent;
//import seng201.team0.gui.GameEnvironment;
//
//public class RouteSelectionController {
//
//    @FXML
//    private void onDesertDriftSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.DESERT_DRIFT);
//        startRace();
//    }
//
//    @FXML
//    private void onDesertLongSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.DESERT_LONG);
//        startRace();
//    }
//
//    @FXML
//    private void onMountainSteepSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.MOUNTAIN_STEEP);
//        startRace();
//    }
//
//    @FXML
//    private void onMountainCurvesSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.MOUNTAIN_CURVES);
//        startRace();
//    }
//
//    @FXML
//    private void onCountryStraightSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.COUNTRY_STRAIGHT);
//        startRace();
//    }
//
//    @FXML
//    private void onCountryTwistySelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.COUNTRY_TWISTY);
//        startRace();
//    }
//
//    @FXML
//    private void onCityAlleysSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.CITY_ALLEYS);
//        startRace();
//    }
//
//    @FXML
//    private void onCityTrafficSelected(ActionEvent event) {
//        GameEnvironment.setSelectedRoute(Route.CITY_TRAFFIC);
//        startRace();
//    }
//
//    private void startRace() {
//        ScreenNavigator.loadScreen("RaceScreen.fxml");
//    }
//}
