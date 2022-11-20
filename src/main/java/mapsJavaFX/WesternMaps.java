package mapsJavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class WesternMaps extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
    // Group root = new Group();
    Scene scene = new Scene(root);
    stage.setTitle("Western Maps");
    stage.setResizable(false);

    stage.setScene(scene);
    stage.show();
  }
}