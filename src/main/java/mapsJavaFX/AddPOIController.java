package mapsJavaFX;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;

import java.io.IOException;
import java.util.List;

public class AddPOIController {
  static Stage stage;
  static Application app;
  @FXML
  private ChoiceBox<Floor> floorName;
  @FXML
  private Text buildingName;
  private Building selectedBuilding;

  public static void setApp(Application newApp) {
    app = newApp;
  }

  public static void setStage(Stage newStage) {
    stage = newStage;
  }

  public static Stage getStage() {
    return stage;
  }

  @FXML
  public void initialize() {
    // Get current building selected in the map view
    String strSelectedBuilding = ControllerMediator.getInstance().getBuildingTab();
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(strSelectedBuilding)) {
        selectedBuilding = building;
      }
    }
    buildingName.setText(selectedBuilding.getName());

    // Set choicebox options
    floorName.setItems(FXCollections.observableList(selectedBuilding.getFloors()));
    floorName.getSelectionModel().selectFirst();
  }

  public void onContinue(ActionEvent event) {
    Floor selectedFloor = floorName.getSelectionModel().getSelectedItem();

    Double x = selectedFloor.getImage().getWidth();
    Double y = selectedFloor.getImage().getHeight();
    POILocation poiLocation = new POILocation(selectedBuilding, selectedFloor,
        new POI("", POIType.custom, new Pair(x / 2, y / 2)));
    app.addPOI(poiLocation);
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/edit.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      EditController editController = fxmlLoader.getController();
      POIButton poiButton = new POIButton(poiLocation);
      ControllerMediator.getInstance().addPOIButton(poiButton, poiLocation);
      ControllerMediator.getInstance().mapViewControllerGoToPOI(poiLocation);
      editController.setPoiLocation(poiLocation, poiButton);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
