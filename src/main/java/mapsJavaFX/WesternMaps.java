package mapsJavaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maps.Application;

import java.io.IOException;

public class WesternMaps extends javafx.application.Application {
  @Override
  public void start(Stage stage)throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(WesternMaps.class.getResource("/mapsJavaFX/searchPOI.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    SearchPOIController controller = fxmlLoader.getController();
    Application app = new Application();
    try {
      app.loadData();
    }
    catch(IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      System.exit(12);
    }
    controller.setData(app);
    stage.setTitle("Western Maps");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}