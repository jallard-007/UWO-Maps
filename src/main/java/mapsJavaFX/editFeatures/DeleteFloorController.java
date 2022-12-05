package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Building;
import maps.Floor;
import mapsJavaFX.ControllerMediator;

public class DeleteFloorController {
  private Floor selectedFloor;
  private Building selectedBuilding;
  private Tab selectedTab;

  @FXML
  private Text floorName;
  @FXML
  private Text buildingName;

  @FXML
  public void initialize() {
    Tab selectedBldTab = ControllerMediator.getInstance().getBuildingTabObject();
    Stage stage = EditHelper.getStage();
    if (selectedBldTab == null) {
      stage.close();
      return;
    }
    selectedTab = ControllerMediator.getInstance().getFloorTab();
    if (selectedTab == null) {
      stage.close();
      return;
    }

    for (Building building : EditHelper.getApp().getBuildings()) {
      if (building.getName().equals(selectedBldTab.getText())) {
        selectedBuilding = building;
        break;
      }
    }
    for (Floor floor : selectedBuilding.getFloors()) {
      if (floor.getName().equals(selectedTab.getText())) {
        selectedFloor = floor;
        break;
      }
    }

    floorName.setText(selectedFloor.getName());
    buildingName.setText(selectedBuilding.getName() + "?");
    stage.setTitle("Delete Floor");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Delete] button deletes the floor from the app.
   */
  public void onDelFloor() {
    EditHelper.getApp().deleteFloor(selectedBuilding, selectedFloor);
    ControllerMediator.getInstance().removeFloorTab(selectedTab);
    ControllerMediator.getInstance().favouritesControllerRefreshList();
    ControllerMediator.getInstance().searchPOIControllerRefreshList();
    EditHelper.getStage().close();
  }
}
