package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {

  @FXML
  private AnchorPane menuBar;

  public void goToProfile(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/profile.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    changeScene(scene, event);
  }

  public void goToHelp(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/help.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    changeScene(scene, event);
  }

  public void goBack(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Util.setControllers(controller, ControllerMediator.getInstance().getApplication());
    changeScene(scene, event);
  }


  public void logout(ActionEvent event) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to log out");
    alert.setContentText("Are you sure?");

    if (alert.showAndWait().get() != ButtonType.OK) {
      return;
    }
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    ControllerMediator.getInstance().getApplication().save();
    changeScene(scene, event);
  }

  private void changeScene(Scene scene, ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }
}
