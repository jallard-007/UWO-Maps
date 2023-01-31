package mapsJavaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import maps.Application;
import mapsJavaFX.editFeatures.EditHelper;

import java.io.IOException;

/**
 * The WesternMaps class is dedicated to setting up the stage, corresponding
 * scenes, and handling
 * start up and closure of the application
 */
public class WesternMaps extends javafx.application.Application {

  public static void starter(String[] args) {
    launch(args);
  }

  /**
   * starts up the application by setting up the appropriate stage and scene as
   * well as handling the
   * event in which the user closes the application
   *
   * @param stage the stage which will be used to display the scene to the user
   * @throws IOException if a fxml file does not exist
   */
  @Override
  public void start(Stage stage) throws IOException {
    // load the login UI from the fxml file, load the scene, and set the title
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
    Scene scene = new Scene(loader.load());
    SceneHolder.loginScene = scene;
    stage.setScene(scene);

    loader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
    scene = new Scene(loader.load());
    SceneHolder.mainScene = scene;
    MainController controller = loader.getController();
    SceneHolder.mainController = controller;
    Application app = new Application();
    app.loadData();
    ControllerMediator.getInstance().registerApplication(app);
    Util.setControllers(controller, app);

    loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
    scene = new Scene(loader.load());
    SceneHolder.signupScene = scene;

    loader = new FXMLLoader(getClass().getResource("/help.fxml"));
    scene = new Scene(loader.load());
    SceneHolder.helpScene = scene;
    stage.setTitle("Western Maps");

    // make it so the stage cannot be resized by the user and set the scene
    stage.setResizable(false);

    stage.show();

    stage.setOnCloseRequest(event -> {
      event.consume();
      exit(stage);
    });
  }

  /**
   * Method that prompts the user with a confirmation message when the user wants
   * to exit the
   * application.
   *
   * @param stage the stage that the user is currently on when they click the exit
   *              button.
   */
  public void exit(Stage stage) {
    stage.setFullScreen(false);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setResizable(false);
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to exit from the program");
    alert.setContentText("Are you sure?");

    if (alert.showAndWait().get() == ButtonType.OK) {
      ControllerMediator.getInstance().getApplication().save();
      System.out.println("The program was exited successfully.");
      stage.close();
      if (EditHelper.getStage() != null) {
        EditHelper.getStage().close();
      }
    }
  }
}
