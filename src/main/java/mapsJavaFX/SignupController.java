package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    public void goToApplication(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        // set positioning on screen
        stage.setX(23);
        stage.setY(20);

        stage.setScene(scene);
        stage.show();
    }
}
