package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Building;
import mapsJavaFX.ControllerMediator;

public class AddBuildingController {
  public Text buildingNameNewFloor;
  @FXML
  private TextField newBuildingNameField;

  @FXML
  public void initialize() {
    Stage stage = EditHelper.getStage();
    stage.setTitle("Add a New Building");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Add Building] button tries to add the building to the app.
   */
  public void onAddBuilding() {
    String newName = newBuildingNameField.getText();
    if (newName.equals("")) {
      return;
    }
    for (Building building : EditHelper.getApp().getBuildings()) {
      if (building.getName().equals(newName)) {
        return;
      }
    }
    Building building = new Building(newName);
    EditHelper.getApp().addBuilding(building);
    ControllerMediator.getInstance().addBuildingTab(building);
    EditHelper.getStage().close();
  }
}
