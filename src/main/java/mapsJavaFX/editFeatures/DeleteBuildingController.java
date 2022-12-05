package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Building;
import mapsJavaFX.ControllerMediator;

import java.util.List;

public class DeleteBuildingController {
  private Tab selectedTab;
  private Building selectedBuilding;
  @FXML
  private Text buildingName;

  @FXML
  public void initialize() {
    selectedTab = ControllerMediator.getInstance().getBuildingTabObject();
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(selectedTab.getText())) {
        selectedBuilding = building;
        break;
      }
    }
    Stage stage = EditHelper.getStage();
    if (selectedBuilding == null) {
      stage.close();
      return;
    }
    buildingName.setText(selectedBuilding.getName());
    stage.setTitle("Delete Building");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Delete] button deletes the buildinng from the app.
   */
  public void onDelBuilding() {
    EditHelper.getApp().deleteBuilding(selectedBuilding);
    ControllerMediator.getInstance().removeTab(selectedTab);
    ControllerMediator.getInstance().favouritesControllerRefreshList();
    ControllerMediator.getInstance().searchPOIControllerRefreshList();
    EditHelper.getStage().close();
  }
}
