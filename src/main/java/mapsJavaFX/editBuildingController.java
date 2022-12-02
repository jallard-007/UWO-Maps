package mapsJavaFX;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.*;
import java.lang.String;

import java.io.IOException;
import java.util.List;

public class editBuildingController {
    static Stage stage;
    static Application app;

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
   * @param newApp referring to the map application
   */
  @FXML
  public void setCurr(String currName) {
    currBuildingName.setText(currName);
  }

  /**
   * Sets stage of application
   * @param newStage stage to be set
   */
  public void setStage(Stage newStage) {
    stage = newStage;
  }

  /**
   * @return current application stage
   */
  public Stage getStage() {
    return stage;
  }

    // public void editBuildingController(String currName){
    //     currBuildingName.setText(currName);
    // }

}
