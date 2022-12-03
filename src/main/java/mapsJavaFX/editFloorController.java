package mapsJavaFX;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import java.lang.String;

import java.io.IOException;
import java.util.List;

public class editFloorController {
    static Stage stage;
    static Application app;
    private Tab selectedBldTab;
    private Tab selectedTab;
    @FXML
    private Text curFloorName;
    @FXML
    private TextField newFloorName;
    @FXML
    private Button saveFloorEdit;
    @FXML
    private Button cancFloorEdit;
    private Floor selectedFloor;
    private Building selectedBuilding;


  /**
   * Called to set up the app
   * @param newApp referring to the map application
   */
  public static void setApp(Application newApp) {
    app = newApp;
  }

  
 
  /**
   * Sets stage of application
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
    selectedBldTab = ControllerMediator.getInstance().getBuildingTabObject();
    selectedTab = ControllerMediator.getInstance().getFloorTab();
    System.out.println(selectedTab.getText());
    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(selectedBldTab.getText())) {
        selectedBuilding = building;
        for(Floor floor : selectedBuilding.getFloors()){
          if (floor.getName().equals(selectedTab.getText())) {
            selectedFloor = floor;
            break;
          }
        }
      }
    }
    curFloorName.setText(selectedFloor.getName());

  }

  /**
   * Pressing [Save Changes] button takes user to input.
   */
  public void onSaveFloorEdit() {
    String newName = newFloorName.getText();
    if(!(newName.equals(""))){
      // String prevName = selectedBuilding.getName();
      
        for(Floor floor : selectedBuilding.getFloors()){
          if (floor.getName().equals(newName)) {
            return;
          }
        }
      
      selectedFloor.setName(newName);
      selectedTab.setText(newName);

    }
  }


}
