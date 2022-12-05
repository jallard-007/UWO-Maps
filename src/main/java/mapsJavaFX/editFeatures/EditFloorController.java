package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Building;
import maps.Floor;
import mapsJavaFX.ControllerMediator;

public class EditFloorController {
  private Tab selectedTab;
  private Floor selectedFloor;
  private Building selectedBuilding;

  // edit floor scene
  @FXML
  private Text curFloorName;
  @FXML
  private TextField newFloorName;
  @FXML
  private TextField levelField;

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

    curFloorName.setText(selectedFloor.getName());
    levelField.setText(Integer.toString(selectedFloor.getLevel()));
    stage.setTitle("Edit Floor");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Save Changes] button attempts to make edit changes to the floor.
   */
  public void onSaveFloorEdit() {
    String newName = newFloorName.getText();
    if (newName.equals("")) {
      return;
    }
    int newLevel;
    try {
      newLevel = Integer.parseInt(levelField.getText());
    } catch (Exception ignored) {
      return;
    }

    for (Floor floor : selectedBuilding.getFloors()) {
      if (floor.getName().equals(newName) && !floor.equals(selectedFloor)) {
        return; // already a floor with that name
      }
    }
    if (newLevel != selectedFloor.getLevel()) {
      for (Floor floor2 : selectedBuilding.getFloors()) {
        if (floor2.getLevel() == newLevel) {
          return;
        }
      }
    }

    selectedFloor.setLevel(newLevel);
    selectedFloor.setName(newName);
    selectedTab.setText(newName);
    EditHelper.getStage().close();
  }

  public void onCancelFloorEdit() {
    EditHelper.getStage().close();
  }
}
