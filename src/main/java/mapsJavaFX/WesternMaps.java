package mapsJavaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

import maps.Application;

public class WesternMaps extends javafx.application.Application {

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The method that initiates the start of the application.
   * @param stage the stage to set and show when the application is first started.
   * @throws IOException if the fxml file is missing
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Western Maps");
    stage.setResizable(false);
    stage.setScene(scene);
    Application app = new Application();
    app.loadData();
    ControllerMediator.getInstance().registerApplication(app);
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
