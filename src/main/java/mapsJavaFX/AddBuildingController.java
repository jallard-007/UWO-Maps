package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import java.lang.String;

public class AddBuildingController {
  static Stage stage;
  static Application app;
  public Text buildingNameNewFloor;

  // add building scene
  @FXML
  private Button addBuildingButton;
  @FXML
  private TextField newBuildingNameField;

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

  /**
   * Pressing [Add Building] button tries to add the building to the app.
   */
  public void onAddBuilding() {
    String newName = newBuildingNameField.getText();
    if (!(newName.equals(""))) {
      for (Building building : app.getBuildings()) {
        if (building.getName().equals(newName)) {
          return;
        }
      }
      Building building = new Building(newName);
      app.addBuilding(building);
      ControllerMediator.getInstance().addBuildingTab(building);
      stage.close();
    }
    return;
  }
}
