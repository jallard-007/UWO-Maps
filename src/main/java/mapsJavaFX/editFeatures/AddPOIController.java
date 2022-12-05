package mapsJavaFX.editFeatures;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import mapsJavaFX.ControllerMediator;
import mapsJavaFX.POIButton;

import java.io.IOException;
import java.util.List;

/**
 * Controllers the addPOI window that popus up when the addPOI button is selected
 */
public class AddPOIController {
  @FXML
  private ChoiceBox<Floor> floorName;
  @FXML
  private Text buildingName;
  private Building selectedBuilding;

  /**
   * Sets up interface: gives user options to select the floor that their new POI will be associated
   * with.
   */
  @FXML
  public void initialize() {
    Stage stage = EditHelper.getStage();
    // Get current building selected in the map view
    String strSelectedBuilding = ControllerMediator.getInstance().mapViewControllerGetBuildingTab();
    if (strSelectedBuilding == null) {
      stage.close();
      return;
    }
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(strSelectedBuilding)) {
        selectedBuilding = building;
      }
    }
    buildingName.setText(selectedBuilding.getName());

    if (selectedBuilding.getFloors().isEmpty()) {
      stage.close();
      return;
    }
    floorName.setItems(FXCollections.observableList(selectedBuilding.getFloors()));
    floorName.getSelectionModel().selectFirst();
    stage.setTitle("New POI");
    if (!stage.isShowing()) {
      stage.show();
    }
    stage.centerOnScreen();
  }

  /**
   * Pressing [Continue] button takes user to input information about their new POI.
   */
  public void onContinue() {
    Floor selectedFloor = floorName.getSelectionModel().getSelectedItem();

    double x = selectedFloor.getImage().getWidth();
    double y = selectedFloor.getImage().getHeight();
    POILocation poiLocation = new POILocation(selectedBuilding, selectedFloor,
        new POI("New POI", POIType.custom, new Pair(x / 2, y / 2)));
    EditHelper.getApp().addPOI(poiLocation);
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edit.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      EditPOIController editPOIController = fxmlLoader.getController();
      POIButton poiButton = new POIButton(poiLocation);
      ControllerMediator.getInstance().mapViewControllerAddPOIButton(poiButton);
      ControllerMediator.getInstance().mapViewControllerGoToPOI(poiLocation);
      editPOIController.setPoiLocation(poiLocation, poiButton);
      EditHelper.getStage().setScene(scene);
      EditHelper.getStage().show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
