package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;  
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
  private Button editBld;
  @FXML 
  Application app;
  @FXML
  private ButtonBar poiBar;
  @FXML
  private ButtonBar floorBar;
  @FXML
  private ButtonBar bldBar;


  public SearchPOIController getSearchPOIController() {
    return searchPOIController;
  }

  public MapViewController getMapViewController() {
    return mapViewController;
  }

  public FavouritesController getFavouritesController() {
    return favouritesController;
  }
  @FXML
  public void onEditBuild(ActionEvent event) {
    event.consume();
    System.out.println("all good");
    
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
  public void setApp(Application app) {
    this.app = app;
    //making certain buttons unclickable for base users
    if (this.app.getUser().getUserType() == UserType.base){
      System.out.println("being called");
      ObservableList<Node> buttonsToSet = FXCollections.observableArrayList();
      buttonsToSet = poiBar.getButtons();
      buttonsToSet.addAll(floorBar.getButtons());
      buttonsToSet.addAll(bldBar.getButtons());
      for (Node button:buttonsToSet){
        if (!(button.getId().equals("addPOI"))){
          button.setDisable(true);
          System.out.println(button.getId());
        }

      }
      
    }
  }

}
