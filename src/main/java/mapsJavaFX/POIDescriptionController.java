package mapsJavaFX;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maps.POILocation;

import java.io.IOException;

public class POIDescriptionController {
  String roomNum;
  String name;
  String type;
  Integer capacity;
  String hours;
  String information;
  String POIDescription;
  public void getPOIDescription (POILocation poiLocation) {
    type = poiLocation.getPOI().getPOIType();
    roomNum = poiLocation.getPOI().getRoomNumber();
    information = poiLocation.getPOI().getInformation();
    POIDescription = "Room Number: " + roomNum + "\n";

    if (poiLocation.getPOI().getName() != null){
      name = poiLocation.getPOI().getName();
      POIDescription += "Name: " + name + "\n";
    }

    POIDescription += "Type: " + type + "\n";

    if (poiLocation.getPOI().getCapacity() != null){
      capacity = poiLocation.getPOI().getCapacity();
      POIDescription += "Room Capacity: " + capacity + "\n";
    }
    if (poiLocation.getPOI().getHoursOfOperation() != null){
      hours = poiLocation.getPOI().getHoursOfOperation();
      POIDescription += "\nHours of Operation: \n" + hours;
    }
    if (poiLocation.getPOI().getInformation() != null){
      information = poiLocation.getPOI().getInformation();
      POIDescription += "\nInformation: \n" + information;
    }


    ScrollPane scrollPane = new ScrollPane();
    Label label = new Label();
    label.setText(POIDescription);
    label.setPadding(new Insets(10));
    label.setWrapText(true);
    scrollPane.setContent(label);
    scrollPane.setFitToWidth(true);
    Scene scene = new Scene(scrollPane, 200, 300);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setMinHeight(200);
    stage.setMinWidth(300);


    //Indicate that the stage should be a popup
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();


//    FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/POIDescription.fxml"));
//    Scene scene = new Scene(fxmlLoader.load());
//    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//    stage.setScene(scene);
//    stage.show();
//    stage.centerOnScreen();

  }

//  public void displayPOIDescription(POILocation poiLocation){
//
//  }
}
