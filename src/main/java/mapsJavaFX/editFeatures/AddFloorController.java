package mapsJavaFX.editFeatures;

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
import mapsJavaFX.ControllerMediator;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AddFloorController {
  private static Application app;
  private static Stage stage;
  private Tab selectedBldTab;
  private File newImage;
  private Building selectedBuilding;
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
    buildingNameNewFloor.setText(selectedBldTab.getText());

    List<Building> allBuildings = ControllerMediator.getInstance().getApplication().getBuildings();
    for (Building building : allBuildings) {
      if (building.getName().equals(selectedBldTab.getText())) {
        selectedBuilding = building;
      }
    }
    stage.setTitle("Add New Floor");
    if (!stage.isShowing()) {
      stage.show();
    }
  }

  public void onAddFloor() {
    if ((newImage == null) || (newFloorNameField.getText().equals("")) || (newLevelField.getText().equals(""))) {
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
    try {
      Files.copy(newImage.toPath(), new File(rootPath + relativePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      return; // error adding file
    }

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
  }
}
