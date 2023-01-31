package mapsJavaFX.welcomePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import maps.Application;
import mapsJavaFX.ControllerMediator;
import mapsJavaFX.SceneHolder;

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
   * Method called when the user clicks the log in button; checks the validity of
   * their username and
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
    SceneHolder.mainController.setPerms(app.getUser().getUserType());
    ControllerMediator.getInstance()
        .registerFavouritesController(SceneHolder.mainController.getFavouritesController());
    SceneHolder.mainController.getFavouritesController().setApp(app);

    stage.setHeight(700);
    stage.setWidth(1200);
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.setScene(SceneHolder.mainScene);
    stage.show();
  }

  /**
   * Go to sign up page from login page
   *
   * @param event user clicks on signup button on login page
   * @throws IOException if signup.fxml does not exist
   */
  public void goToSignUp(ActionEvent event) throws IOException {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(SceneHolder.signupScene);
    stage.show();
  }
}
