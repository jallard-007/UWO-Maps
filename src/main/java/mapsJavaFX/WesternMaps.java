package mapsJavaFX;

import javafx.fxml.FXMLLoader;
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
  }
}