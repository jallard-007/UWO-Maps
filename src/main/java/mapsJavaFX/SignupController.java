package mapsJavaFX;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maps.Application;

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

    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Util.setControllers(controller, app);

    Stage stage = (Stage) username.getScene().getWindow();
    stage.setX(23);
    stage.setY(20);
    stage.setScene(scene);
    stage.show();
  }
}
