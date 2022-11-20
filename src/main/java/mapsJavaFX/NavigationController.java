package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maps.Application;
import java.io.IOException;

public class NavigationController {
  public void goToLogin(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    changeScene(scene, event);
  }

  public void goToProfile(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/profile.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    changeScene(scene, event);
  }

  public void goToHelp(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/help.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    changeScene(scene, event);
  }

  public void goBack(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    MainController controller = fxmlLoader.getController();
    Application app = ControllerMediator.getInstance().getApplication();
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    controller.getSearchPOIController().addPOIs(app.getPoiLocations());
    controller.getMapViewController().addBuildings(app.getBuildings());
    changeScene(scene, event);
  }

  private void changeScene(Scene scene, ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();

  }
}
