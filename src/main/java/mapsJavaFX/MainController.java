package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import maps.POIType;

public class MainController {
  @FXML
  private StackPane mapView;
  @FXML
  private BorderPane searchPOI;
  @FXML
  private BorderPane favourites;
  @FXML
  private ListView<POIType> types;
  @FXML
  private MapViewController mapViewController;
  @FXML
  private SearchPOIController searchPOIController;
  @FXML
  private FavouritesController favouritesController;
  @FXML
  private POIDescriptionController poiDescripController;
  @FXML
  private TypesPOIController typesController;

  public POIDescriptionController getPOIDescriptionController() {
    return poiDescripController;
  }

  public SearchPOIController getSearchPOIController() {
    return searchPOIController;
  }

  public MapViewController getMapViewController() {
    return mapViewController;
  }

  public FavouritesController getFavouritesController() {
    return favouritesController;
  }

  public TypesPOIController getPOITypesController() {
    return typesController;
  }
}
