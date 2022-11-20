package mapsJavaFX;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import maps.Application;

public class LoginController {
  @FXML
  private Button loginButton;
  @FXML
  private PasswordField password;
  @FXML
  private TextField username;
  @FXML
  private AnchorPane logIn;

  public void goToApplication(ActionEvent event) throws IOException {
    System.out.println("switched");
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/mainView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = (Stage) logIn.getScene().getWindow();
    stage.setX(23);
    stage.setY(20);
    stage.setScene(scene);
    stage.show();

    Application app = ControllerMediator.getInstance().getApplication();
    MainController controller = fxmlLoader.getController();
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    controller.getSearchPOIController().addPOIs(app.getPoiLocations());
    controller.getMapViewController().addBuildings(app.getBuildings());
  }

  public void goToSignUp(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/signup.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
