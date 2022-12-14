package mapsJavaFX.welcomePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Application;
import mapsJavaFX.ControllerMediator;
import mapsJavaFX.MainController;
import mapsJavaFX.Util;

import java.io.IOException;

/**
 * Controls the Login page
 */
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
   * the text where the user recieves login feedback.
   */
  @FXML
  private Text loginFeedBackText;


  /**
   * Method called when the user clicks the log in button; checks the validity of their username and
   * password, and takes the user to the main view of the application
   *
   * @throws IOException if the fxml file is missing
   */
  public void goToApplication() throws IOException {
    if (username.getText().equals("")) {
      // ask user to input username
      loginFeedBackText.setText("please enter a username.");
      return;
    }
    if (password.getText().equals("")) {
      // ask user to input password
      loginFeedBackText.setText("please enter a password.");
      return;
    }
    Application app = ControllerMediator.getInstance().getApplication();
    Stage stage = (Stage) logIn.getScene().getWindow();
    if (!app.login(username.getText(), password.getText())) {
      // username / password incorrect
      loginFeedBackText.setText(
          "Invalid login credentials. Please try again or select sign up to make a new account.");
      return;
    }
    stage.close();
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Util.setControllers(controller, app);

    stage.setHeight(700);
    stage.setWidth(1200);
    stage.setResizable(true);
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
