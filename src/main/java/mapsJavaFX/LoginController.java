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
  /**
   * the login button on the log in page.
   */
  @FXML
  private Button loginButton;
  /**
   * the text box where the user enters their password.
   */
  @FXML
  private PasswordField password;
  /**
   * the text box where the user enters their username.
   */
  @FXML
  private TextField username;
  /**
   * anchor pane containing the entire UI of the log in page.
   */
  @FXML
  private AnchorPane logIn;

  /**
   * Method called when the user clicks the log in button; checks the validity of their username and password, and takes the user to the main view of the application
   * @throws IOException if the fxml file is missing
   */
  public void goToApplication() throws IOException {
    if (username.getText().equals("")) {
      // ask user to input username
      return;
    }
    if (password.getText().equals("")) {
      // ask user to input password
      return;
    }
    Application app = ControllerMediator.getInstance().getApplication();
    Stage stage = (Stage) logIn.getScene().getWindow();
    if (!app.login(username.getText(), password.getText())) {
      // username / password incorrect
      return;
    }
    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Util.setControllers(controller, app);


    stage.setHeight(700);
    stage.setWidth(1200);
    stage.centerOnScreen();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Go to sign up page from login page
   *
   * @param event user clicks on signup button on login page
   * @throws IOException if signup.fxml does not exist
   */
  public void goToSignUp(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/signup.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
