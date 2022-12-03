package mapsJavaFX.editFeatures;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import mapsJavaFX.ControllerMediator;

import java.lang.String;

import java.util.List;

public class EditBuildingController {
  static Stage stage;
  static Application app;
  private Tab selectedTab;
  private Building selectedBuilding;

  // edit building scene
  @FXML
  private Text currBuildingName;
  @FXML
  private TextField newBldName;
  @FXML
  private Button conEditBuild;
  @FXML
  private Button cancEditBuild;

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
    selectedTab = ControllerMediator.getInstance().getBuildingTabObject();
    if (selectedTab == null) {
      stage.close();
      return;
    }
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
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
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    String newName = newBldName.getText();
    if (!(newName.equals(""))) {
      for (Building building : allBuildings) {
        if (building.getName().equals(newName)) {
          return; // cannot have same name as another building
        }
      }
      selectedBuilding.setName(newName);
      selectedTab.setText(newName);
      stage.close();
    }
  }
}
