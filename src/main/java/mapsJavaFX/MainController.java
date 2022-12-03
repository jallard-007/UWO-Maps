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
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onAddFloor(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/addFloor.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = AddFloorController.getStage();
      stage.setTitle("Add New Floor");
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onEditFloor(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/editFloor.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = EditFloorController.getStage();
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onDelFloor(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/deleteFloor.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = DeleteFloorController.getStage();
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onAddBld(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/addBuilding.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = AddBuildingController.getStage();
      stage.setScene(scene);
      stage.setTitle("Add a New Building");
      stage.showAndWait();
      stage.centerOnScreen();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void onEditBld(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/editBuilding.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = EditBuildingController.getStage();
      stage.setScene(scene);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void onDelBld(ActionEvent event) {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/deleteBuilding.fxml"));
    try {
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = DeleteBuildingController.getStage();
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
