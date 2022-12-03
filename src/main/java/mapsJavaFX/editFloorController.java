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

public class editFloorController {
  static Stage stage;
  static Application app;
  private Tab selectedBldTab;
  private Tab selectedTab;

    //edit floor scene
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

  // delete floor scene
  @FXML
  private Text floorName;
  @FXML
  private Text buildingName;
  @FXML
  private Button delFloorButton;

  private Floor selectedFloor;
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
    selectedBldTab = ControllerMediator.getInstance().getBuildingTabObject();
    selectedTab = ControllerMediator.getInstance().getFloorTab();
    System.out.println(selectedTab.getText());
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
      }
    }
    //if the edit floor stage is being intialized
    if (curFloorName != null){
      curFloorName.setText(selectedFloor.getName());
      levelField.setText(Integer.toString(selectedFloor.getLevel()));
    }
    //otherwise the delete floor stage is being initialized
    else{
      floorName.setText(selectedFloor.getName());
      buildingName.setText(selectedBuilding.getName() + "?");
    }

  }

  /**
   * Pressing [Save Changes] button takes user to input.
   */
  public void onSaveFloorEdit() {
    String newName = newFloorName.getText();
    if (!(newName.equals(""))) {
      // String prevName = selectedBuilding.getName();

      for (Floor floor : selectedBuilding.getFloors()) {
        if (floor.getName().equals(newName)) {
          return;
        }
      //check if the user entered an integer for the level
      try {
        int newLevel = Integer.parseInt(levelField.getText());
        //if they did and its different
        if (newLevel != selectedFloor.getLevel()){
          //make sure it is not the same level as another floor in the building
          for(Floor floor2: selectedBuilding.getFloors()){
            if(floor2.getLevel() == newLevel){
              return;
            }
          }
          selectedFloor.setLevel(newLevel);
        }
        //if not, end the function call
      } catch (Exception e) {
        return;
      }
      selectedFloor.setName(newName);

      selectedTab.setText(newName);
      stage.close();

    }
  }
  }
  public void onCancelFloorEdit(){
    stage.close();
  }
   /**
   * Pressing [Delete] button deletes the floor from the app.
   */
  public void onDelFloor() {
    app.deleteFloor(selectedBuilding, selectedFloor);
    ControllerMediator.getInstance().removeFloorTab(selectedTab);
    ControllerMediator.getInstance().refreshFavouritesList();
    ControllerMediator.getInstance().refreshSearchList();
    stage.close();
  }

}
