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

public class EditFloorController {
  static Stage stage;
  static Application app;
  private Tab selectedTab;
  private Floor selectedFloor;
  private Building selectedBuilding;

  // edit floor scene
  @FXML
  private Text curFloorName;
  @FXML
  private TextField newFloorName;
  @FXML
  private Button saveFloorEdit;
  @FXML
  private Button cancFloorEdit;
  @FXML
  private TextField levelField;

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
    // if the edit floor stage is being intialized
    if (curFloorName != null) {
      curFloorName.setText(selectedFloor.getName());
      levelField.setText(Integer.toString(selectedFloor.getLevel()));
    }
    stage.setTitle("Edit Floor");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  /**
   * Pressing [Save Changes] button attempts to make edit changes to thefloor.
   */
  public void onSaveFloorEdit() {
    String newName = newFloorName.getText();
    if (!(newName.equals(""))) {
      // String prevName = selectedBuilding.getName();

      for (Floor floor : selectedBuilding.getFloors()) {
        if (floor.getName().equals(newName)) {
          return;
        }
        // check if the user entered an integer for the level
        try {
          int newLevel = Integer.parseInt(levelField.getText());
          // if they did and it's different
          if (newLevel != selectedFloor.getLevel()) {
            // make sure it is not the same level as another floor in the building
            for (Floor floor2 : selectedBuilding.getFloors()) {
              if (floor2.getLevel() == newLevel) {
                return;
              }
            }
            selectedFloor.setLevel(newLevel);
          }
          // if not, end the function call
        } catch (Exception e) {
          return;
        }
        selectedFloor.setName(newName);
        selectedTab.setText(newName);
        stage.close();
      }
    }
  }

  public void onCancelFloorEdit() {
    stage.close();
  }

}
