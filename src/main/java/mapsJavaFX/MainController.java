package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MainController {
  @FXML private TabPane mapView;
  @FXML private BorderPane searchPOI;
  @FXML private MapViewController mapViewController;
  @FXML private SearchPOIController searchPOIController;

  public SearchPOIController getSearchPOIController() {
    return searchPOIController;
  }

  public MapViewController getMapViewController() {
    return mapViewController;
  }
}