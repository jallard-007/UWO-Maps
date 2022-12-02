package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Primarily used to get other controllers
 */
public class MainController {
  @FXML
  private Button addPOI;
  @FXML
  private StackPane mapView;
  @FXML
  private BorderPane searchPOI;
  @FXML
  private BorderPane favourites;
  @FXML
  private MapViewController mapViewController;
  @FXML
  private SearchPOIController searchPOIController;
  @FXML
  private FavouritesController favouritesController;

  /**
   * @return SearchPOIController loaded with this controller
   */
  public SearchPOIController getSearchPOIController() {
    return searchPOIController;
  }

  /**
   * @return MapViewController loaded with this controller
   */
  public MapViewController getMapViewController() {
    return mapViewController;
  }

  /**
   * @return FavouritesController loaded with this controller
   */
  public FavouritesController getFavouritesController() {
    return favouritesController;
  }

  /**
   * On add POI button event handler
   */
  public void onAddPOI() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addPOI.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = AddPOIController.getStage();
      stage.setTitle("New POI");
      stage.setScene(scene);
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
