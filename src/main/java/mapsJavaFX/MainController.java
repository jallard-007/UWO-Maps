package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maps.Building;
import maps.POILocation;

import java.io.IOException;
import java.util.List;

public class MainController {
  public Button addPOI;
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


  public SearchPOIController getSearchPOIController() {
    return searchPOIController;
  }

  public MapViewController getMapViewController() {
    return mapViewController;
  }

  public FavouritesController getFavouritesController() {
    return favouritesController;
  }


  public void onAddPOI(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/addPOI.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      AddPOIController addPOIController = fxmlLoader.getController();
      Stage stage = new Stage();
      stage.setTitle("New POI");
      stage.setScene(scene);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
