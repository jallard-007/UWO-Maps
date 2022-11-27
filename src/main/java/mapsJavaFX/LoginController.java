package mapsJavaFX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import maps.Application;

public class LoginController {
  @FXML
  private Button loginButton;
  @FXML
  private PasswordField password;
  @FXML
  private TextField username;
  @FXML
  private AnchorPane logIn;

  public void goToApplication(ActionEvent event) throws IOException {
    if (username.getText().equals("")) {
      // ask user to input username
      return;
    }
    if (password.getText().equals("")) {
      // ask user to input password
      return;
    }
    Application app = ControllerMediator.getInstance().getApplication();
    if (!app.login(username.getText(), password.getText())) {
      // username / password incorrect
      return;
    }
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Util.setControllers(controller, app);

    Stage stage = (Stage) logIn.getScene().getWindow();
    stage.setHeight(700);
    stage.setWidth(1200);
    stage.centerOnScreen();
    stage.setScene(scene);
    stage.show();
  }

  public void goToSignUp(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/signup.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
