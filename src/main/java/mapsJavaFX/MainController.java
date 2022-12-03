package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import maps.*;

import java.io.IOException;

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
  @FXML
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
      Stage stage = AddPOIController.getStage();
      stage.setTitle("New POI");
      stage.setScene(scene);
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onAddFloor(ActionEvent event) {

  }

  public void onEditFloor(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/editFloor.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = editFloorController.getStage();
      stage.setTitle("Edit Floor");
      stage.setScene(scene);
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onDelFloor(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/deleteFloor.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = editFloorController.getStage();
      stage.setTitle("Delete Floor");
      stage.setScene(scene);
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onAddBld(ActionEvent event) {

  }

  public void onEditBld(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/editBuilding.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = editBuildingController.getStage();
      stage.setTitle("Edit Building Name");
      stage.setScene(scene);
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onDelBld(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/deleteBuilding.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = editBuildingController.getStage();
      stage.setTitle("Delete Building");
      stage.setScene(scene);
      stage.showAndWait();
      stage.centerOnScreen();
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
