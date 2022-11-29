package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Control the navigation throughout the entire application
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
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/help.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    changeScene(scene, event);
  }

  /**
   * Go back to main view of application
   *
   * @param event where user clicks the back button
   * @throws IOException if mainView.fxml does not exist
   */
  public void goBack(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Util.setControllers(controller, ControllerMediator.getInstance().getApplication());
    changeScene(scene, event);
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
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to log out");
    alert.setContentText("Are you sure?");

    // cancel operation if user does not want to log out
    if (alert.showAndWait().get() != ButtonType.OK) {
      return;
    }

    // go back to login page if user successfully logs out
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    ControllerMediator.getInstance().getApplication().save();
    changeScene(scene, event);
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
  }
}
