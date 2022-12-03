package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import mapsJavaFX.ControllerMediator;

import java.util.List;

public class DeleteFloorController {
  private static Application app;
  private static Stage stage;
  private Floor selectedFloor;
  private Building selectedBuilding;
  private Tab selectedTab;

  // delete floor scene
  @FXML
  private Text floorName;
  @FXML
  private Text buildingName;
  @FXML
  private Button delFloorButton;

  /**
   * Called to set up the app
   * 
   * @param newApp referring to the map application
   */
  public static void setApp(Application newApp) {
    app = newApp;
  }

  /**
   * Sets stage of application
   * 
   * @param newStage stage to be set
   */
  public static void setStage(Stage newStage) {
    stage = newStage;
  }

  /**
   * @return current application stage
   */
  public static Stage getStage() {
    return stage;
  }

  @FXML
  public void initialize() {
    Tab selectedBldTab = ControllerMediator.getInstance().getBuildingTabObject();
    if (selectedBldTab == null) {
      stage.close();
      return;
    }
    selectedTab = ControllerMediator.getInstance().getFloorTab();
    if (selectedTab == null) {
      stage.close();
      return;
    }

    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(selectedBldTab.getText())) {
        selectedBuilding = building;
        for (Floor floor : selectedBuilding.getFloors()) {
          if (floor.getName().equals(selectedTab.getText())) {
            selectedFloor = floor;
            break;
          }
        }
        break;
      }
    }
    // else if the delete floor stage is being initialized
    if (floorName != null) {
      floorName.setText(selectedFloor.getName());
      buildingName.setText(selectedBuilding.getName() + "?");
    }
    stage.setTitle("Delete Floor");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Delete] button deletes the floor from the app.
   */
  public void onDelFloor() {
    app.deleteFloor(selectedBuilding, selectedFloor);
    ControllerMediator.getInstance().removeFloorTab(selectedTab);
    ControllerMediator.getInstance().favouritesControllerRefreshList();
    ControllerMediator.getInstance().searchPOIControllerRefreshList();
    stage.close();
  }

}
