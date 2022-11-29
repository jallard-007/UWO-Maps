package mapsJavaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

import maps.Application;

/**
 * The WesternMaps class is dedicated to setting up the stage, corresponding scenes, and handling
 * start up and closure of the application
 */
public class WesternMaps extends javafx.application.Application {

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * starts up the application by setting up the appropriate stage and scene as well as handling the
   * event in which the user closes the application
   *
   * @param stage the stage which will be used to display the scene to the user
   * @throws IOException if a fxml file does not exist
   */
  @Override
  public void start(Stage stage) throws IOException {
    // load the login UI from the fxml file, load the scene, and set the title
    FXMLLoader loginLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
    Scene scene = new Scene(loginLoader.load());
    stage.setTitle("Western Maps");

    // make it so the stage cannot be resized by the user and set the scene
    stage.setResizable(false);
    stage.setScene(scene);

    Application app = new Application();
    app.loadData();
    ControllerMediator.getInstance().registerApplication(app);
    stage.setResizable(true);
    stage.show();

    stage.setOnCloseRequest(event -> {
      event.consume();
      exit(stage);
    });
  }

  /** Method that prompts the user with a confirmation message when the user wants to exit the application.
   * @param stage the stage that the user is currently on when they click the exit button.
   */
  public void exit(Stage stage) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to exit from the program");
    alert.setContentText("Are you sure?");

    if (alert.showAndWait().get() == ButtonType.OK) {
      ControllerMediator.getInstance().getApplication().save();
      System.out.println("The program was exited successfully.");
      stage.close();
    }
  }
}
