package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;

import java.io.File;
import java.lang.String;
import javafx.stage.FileChooser;

import java.util.List;

public class editFloorController {
  static Stage stage;
  static Application app;
  private Tab selectedBldTab;
  private Tab selectedTab;
  private File newImage;

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

  // delete floor scene
  @FXML
  private Text floorName;
  @FXML
  private Text buildingName;
  @FXML
  private Button delFloorButton;

  // add floor scene
  @FXML
  private TextField newFloorNameField;
  @FXML
  private Text buildingNameNewFloor;
  @FXML
  private TextField newLevelField;
  @FXML
  private Button selectImage;
  @FXML
  private Button addFloorButton;
  @FXML
  private Text selectedImagePath;

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
    // if the edit floor stage is being intialized
    if (curFloorName != null) {
      curFloorName.setText(selectedFloor.getName());
      levelField.setText(Integer.toString(selectedFloor.getLevel()));
    }
    // else if the delete floor stage is being initialized
    else if (floorName != null) {
      floorName.setText(selectedFloor.getName());
      buildingName.setText(selectedBuilding.getName() + "?");
    }
    // else initialize for the add floor stage
    else {
      buildingNameNewFloor.setText(selectedBuilding.getName());
      newImage = null;
    }

  }

  /**
   * Pressing [Add Floor] button attempts to make new floor.
   */
  public void onAddFloorSubmit() {
    if ((newImage.getPath() == null) || (newFloorNameField.getText().equals(""))
        || (newLevelField.getText().equals(""))) {
      return; // not valid input
    }

    int newLevel = Integer.parseInt(newLevelField.getText());
    for (Floor floor : selectedBuilding.getFloors()) {
      if ((floor.getName().equals(newFloorNameField.getText())) || (floor.getLevel() == newLevel)) {
        return; // already a floor with that info
      }
    }

    // move the image file to the applicaiton directory
    String rootPath = maps.Util.getRootPath();
    String relativePath = "/appData/maps/" + selectedBuilding.getName() + "Floor" + newLevelField.getText() + ".png";
    newImage.renameTo(new File(rootPath + relativePath));

    // create the new floor
    Floor newFloor = new Floor(newLevel, newFloorNameField.getText(), relativePath);
    // and add it to the app
    app.addFloor(selectedBuilding, newFloor);
    ControllerMediator.getInstance().addFloorTab(selectedBldTab, newFloor);
    // add it to the mapview
    stage.close();

  }

  public void onSelectImage() {
    // create a file chooser
    FileChooser fileChooser = new FileChooser();
    // set the fileChooser to filter for png files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
    fileChooser.getExtensionFilters().add(extFilter);
    fileChooser.setTitle("Select Floor Image");
    // fileChooser.setInitialDirectory(new File("."));
    newImage = fileChooser.showOpenDialog(stage);
    if (newImage != null) {
      selectedImagePath.setText(newImage.getAbsolutePath() + " selected.");
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
          // if they did and its different
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
