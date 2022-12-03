package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import java.lang.String;

import java.util.List;

public class editBuildingController {
  static Stage stage;
  static Application app;
  private Tab selectedTab;

  // edit building scene
  @FXML
  private Text currBuildingName;
  @FXML
  private TextField newBldName;
  @FXML
  private Button conEditBuild;
  @FXML
  private Button cancEditBuild;

  // delete building scene
  @FXML
  private Button delBuildingButton;
  @FXML
  private Text buildingName;

  private Building selectedBuilding;

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
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(selectedTab.getText())) {
        selectedBuilding = building;
        break;
      }
    }
    if (currBuildingName != null) {
      currBuildingName.setText(selectedBuilding.getName());
    } else {
      buildingName.setText(selectedBuilding.getName());
    }
  }

  /**
   * Pressing [Save Changes] button takes user to input.
   */
  public void onSaveBldEdit() {
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    String newName = newBldName.getText();
    if (!(newName.equals(""))) {
      // String prevName = selectedBuilding.getName();
      for (Building building : allBuildings) {
        if (building.getName().equals(newName)) {
          return;
        }
      }
      selectedBuilding.setName(newName);
      selectedTab.setText(newName);
      stage.close();

    }
  }

  /**
   * Pressing [Delete] button deletes the buildinng from the app.
   */
  public void onDelBuilding() {
    app.deleteBuilding(selectedBuilding);
    ControllerMediator.getInstance().removeTab(selectedTab);
    ControllerMediator.getInstance().refreshFavouritesList();
    ControllerMediator.getInstance().refreshSearchList();
    stage.close();

  }

}
