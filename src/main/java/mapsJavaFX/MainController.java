package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import maps.*;
import mapsJavaFX.editFeatures.AddPOIController;

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
  Application app;
  @FXML
  private ButtonBar addBar;
  @FXML
  private ButtonBar floorBar;
  @FXML
  private ButtonBar bldBar;
  @FXML
  private Button addBld;
  @FXML
  private Button editBld;
  @FXML
  private Button delBld;
  @FXML
  private Button addFloor;
  @FXML
  private Button editFloor;
  @FXML
  private Button delFloor;

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
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();

        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addPOI.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onAddFloor() {
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();
        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addFloor.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onEditFloor() {
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();

        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editFloor.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onDelFloor() {
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();

        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/deleteFloor.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onAddBld() {
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();

        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addBuilding.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onEditBld() {
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();

        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/editBuilding.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onDelBld() {
    try {
      Stage stage = AddPOIController.getStage();
      Scene s = stage.getScene();
      if (s != null && s.getRoot().getClass() == AnchorPane.class) {
        // poi is being edited and cannot switch, otherwise the button will be movable,
        // even not when editing
        stage.toFront();

        return;
      }
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/deleteBuilding.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setApp(Application app) {
    this.app = app;
    // making certain buttons unclickable for base users
    if (this.app.getUser().getUserType() == UserType.base) {
      System.out.println("being called");
      ObservableList<Node> buttonsToSet = FXCollections.observableArrayList();
      buttonsToSet = addBar.getButtons();
      buttonsToSet.addAll(floorBar.getButtons());
      buttonsToSet.addAll(bldBar.getButtons());
      for (Node button : buttonsToSet) {
        if (!(button.getId().equals("addPOI"))) {
          button.setDisable(true);
          System.out.println(button.getId());
        }

      }

    }
  }

}
