package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import maps.UserType;
import mapsJavaFX.editFeatures.EditHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Primarily used to get other controllers
 */
public class MainController {
  @FXML
  private Button addBuilding;
  @FXML
  private Button addFloor;
  @FXML
  private HBox buildingButtons;
  @FXML
  private HBox floorButtons;
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
   * Checks if a POI is being edited by checking the main layout node's class. This checks the
   * pop-up stage
   *
   * @return true if a poi is in edit mode, false otherwise
   */
  private static boolean poiBeingEdited() {
    Scene s = EditHelper.getStage().getScene();
    if (s != null && s.getRoot().getClass() == CustomPane.class) {
      EditHelper.getStage().toFront();
      return true;
    }
    return false;
  }

  /**
   * Changes the scene by loading an fxml file and applying it to the popup stage
   *
   * @param fxmlFileName the name of the fxml file
   */
  public static void changeScene(String fxmlFileName) {
    Stage stage = EditHelper.getStage();
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource(fxmlFileName));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

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
    if (!poiBeingEdited()) {
      changeScene("/addPOI.fxml");
    }
  }

  public void onAddFloor() {
    if (!poiBeingEdited()) {
      changeScene("/addFloor.fxml");
    }
  }

  public void onEditFloor() {
    if (!poiBeingEdited()) {
      changeScene("/editFloor.fxml");
    }
  }

  public void onDelFloor() {
    if (!poiBeingEdited()) {
      changeScene("/deleteFloor.fxml");
    }
  }

  public void onAddBld() {
    if (!poiBeingEdited()) {
      changeScene("/addBuilding.fxml");
    }
  }

  public void onEditBld() {
    if (!poiBeingEdited()) {
      changeScene("/editBuilding.fxml");
    }
  }

  public void onDelBld() {
    if (!poiBeingEdited()) {
      changeScene("/deleteBuilding.fxml");
    }
  }

  /**
   * Removes admin features for base users
   *
   * @param userType the current user type
   */
  public void setPerms(UserType userType) {
    if (userType == UserType.admin) {
      return;
    }
    ArrayList<Node> buttonsToSet = new ArrayList<>();
    buttonsToSet.addAll(buildingButtons.getChildren());
    buttonsToSet.addAll(floorButtons.getChildren());
    buttonsToSet.add(addBuilding);
    buttonsToSet.add(addFloor);
    for (Node button : buttonsToSet) {
      button.setDisable(true);
    }
  }
}
