package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Building;
import mapsJavaFX.ControllerMediator;

public class EditBuildingController {
  private Tab selectedTab;
  private Building selectedBuilding;

  @FXML
  private Text currBuildingName;
  @FXML
  private TextField newBldName;

  @FXML
  public void initialize() {
    selectedTab = ControllerMediator.getInstance().getBuildingTabObject();
    Stage stage = EditHelper.getStage();
    if (selectedTab == null) {
      stage.close();
      return;
    }
    for (Building building : EditHelper.getApp().getBuildings()) {
      if (building.getName().equals(selectedTab.getText())) {
        selectedBuilding = building;
        break;
      }
    }
    if (selectedBuilding == null) {
      stage.close();
      return;
    }
    if (currBuildingName != null) {
      currBuildingName.setText(selectedBuilding.getName());
    }
    stage.setTitle("Edit Building Name");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Save Changes] button takes user to input.
   */
  public void onSaveBldEdit() {
    String newName = newBldName.getText();
    if (newName.equals("")) {
      return;
    }
    for (Building building : EditHelper.getApp().getBuildings()) {
      if (building.getName().equals(newName)) {
        return; // cannot have same name as another building
      }
    }
    selectedBuilding.setName(newName);
    selectedTab.setText(newName);
    EditHelper.getStage().close();
  }
}
