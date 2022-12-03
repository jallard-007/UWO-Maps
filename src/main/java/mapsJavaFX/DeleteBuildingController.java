package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;

import java.util.List;

public class DeleteBuildingController {
  static Stage stage;
  static Application app;
  private Tab selectedTab;
  private Building selectedBuilding;
  @FXML
  private Text buildingName;

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
    if (selectedBuilding == null) {
      stage.close();
      return;
    }
    buildingName.setText(selectedBuilding.getName());
    if (!stage.isShowing()) {
      stage.showAndWait();
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
