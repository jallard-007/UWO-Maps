package mapsJavaFX.welcomePage;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maps.Application;
import mapsJavaFX.ControllerMediator;
import mapsJavaFX.SceneHolder;

import java.io.IOException;

/**
 * Controls the signup page
 */
public class SignupController {
  @FXML
  private TextField username;
  @FXML
  private PasswordField password;
  @FXML
  private PasswordField confirmPassword;

  public void goToApplication() throws IOException {
    if (username.getText().equals("")) {
      // ask user to input username
      return;
    }
    if (password.getText().equals("")) {
      // ask user to input password
      return;
    }
    if (confirmPassword.getText().equals("")) {
      // ask user to confirm password
      return;
    }

    if (!password.getText().equals(confirmPassword.getText())) {
      // passwords do not match
      return;
    }
    Application app = ControllerMediator.getInstance().getApplication();
    if (!app.signup(username.getText(), password.getText())) {
      return;
    }
    SceneHolder.mainController.setPerms(app.getUser().getUserType());
    ControllerMediator.getInstance()
        .registerFavouritesController(SceneHolder.mainController.getFavouritesController());
    SceneHolder.mainController.getFavouritesController().setApp(app);

    Stage stage = (Stage) username.getScene().getWindow();
    stage.close();

    stage.setHeight(700);
    stage.setWidth(1200);
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.setScene(SceneHolder.mainScene);
    stage.show();
  }
}
