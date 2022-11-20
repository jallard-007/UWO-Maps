package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import maps.Application;

import java.io.IOException;

public class NavigationController {

  @FXML  private AnchorPane menuBar;

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
    Stage stage = (Stage) menuBar.getScene().getWindow();
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


  public void logout(ActionEvent event) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Logout");
    alert.setHeaderText("You're about to log out");
    alert.setContentText("Are you sure?");

    if (alert.showAndWait().get() == ButtonType.OK){
      FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/login.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      changeScene(scene, event);
    }
  }
  private void changeScene(Scene scene, ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    stage.centerOnScreen();
  }
}
