package mapsJavaFX;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import maps.Application;

public class SignupController {
  @FXML
  private AnchorPane signUp;

  public void goToApplication() throws IOException {
    System.out.println("switched");
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = (Stage) signUp.getScene().getWindow();
    stage.setX(23);
    stage.setY(20);
    stage.setScene(scene);
    stage.show();

    MainController controller = fxmlLoader.getController();
    Application app = new Application();
    try {
      app.loadData();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      System.exit(12);
    }
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    controller.getSearchPOIController().addPOIs(app.getPoiLocations());
    controller.getMapViewController().addBuildings(app.getBuildings());
  }
}
