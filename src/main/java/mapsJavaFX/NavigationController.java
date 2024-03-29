package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mapsJavaFX.editFeatures.EditHelper;

import java.io.IOException;

/**
 * Control the navigation in the main application scene
 */
public class NavigationController {
  @FXML
  private BorderPane menuBar;

  /**
   * Go to help page of application
   *
   * @param event where user clicks on the Help button
   * @throws IOException if help.fxml file does not exist
   */
  public void goToHelp(ActionEvent event) throws IOException {
    changeScene(SceneHolder.helpScene, event);
  }

  /**
   * Go back to main view of application
   *
   * @param event where user clicks the back button
   * @throws IOException if mainView.fxml does not exist
   */
  public void goBack(ActionEvent event) throws IOException {
    changeScene(SceneHolder.mainScene, event);
  }

  /**
   * Logout of application and go back to log in screen
   *
   * @param event user clicks on the logout button
   * @throws IOException if login.fxml does not exist
   */
  public void logout(ActionEvent event) throws IOException {
    // confirmation message for user before they complete logging out
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    alert.initOwner(stage);
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to log out");
    alert.setContentText("Are you sure?");

    // cancel operation if user does not want to log out
    if (alert.showAndWait().get() != ButtonType.OK) {
      return;
    }

    // go back to login page if user successfully logs out
    stage.setWidth(600);
    stage.setHeight(330);
    stage.setResizable(false);
    ControllerMediator.getInstance().getApplication().save();
    changeScene(SceneHolder.loginScene, event);
  }

  /**
   * Change the scene correspondingly based on the action event
   *
   * @param scene the scene to change to
   * @param event the event which triggers the change of scene
   */
  private void changeScene(Scene scene, ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
    EditHelper.getStage().close();
  }
}
