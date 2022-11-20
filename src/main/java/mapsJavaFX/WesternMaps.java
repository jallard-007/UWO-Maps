package mapsJavaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class WesternMaps extends javafx.application.Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Western Maps");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();

    stage.setOnCloseRequest(event -> {
      event.consume();
      try {
        exit(stage);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public void exit(Stage stage) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to exit from the program");
    alert.setContentText("Are you sure?");

    if (alert.showAndWait().get() == ButtonType.OK){
      System.out.println("The program was exited successfully.");
      stage.close();
    }
  }
}