package mapsJavaFX;

import javafx.scene.Scene;
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
  public void getPOIDescription (POILocation poiLocation) {
    type = poiLocation.getPOI().getPOIType();
    roomNum = poiLocation.getPOI().getRoomNumber();
    information = poiLocation.getPOI().getInformation();
    System.out.println(type);
    System.out.println(roomNum);

    if (poiLocation.getPOI().getName() != null){
      name = poiLocation.getPOI().getName();
      System.out.println(name);
    }
    if (poiLocation.getPOI().getCapacity() != null){
      capacity = poiLocation.getPOI().getCapacity();
      System.out.println(capacity);
    }
    if (poiLocation.getPOI().getHoursOfOperation() != null){
      hours = poiLocation.getPOI().getHoursOfOperation();
      System.out.println(hours);
    }
    if (poiLocation.getPOI().getInformation() != null){
      information = poiLocation.getPOI().getInformation();
      System.out.println(information);
    }


    FlowPane flowPane = new FlowPane();
    Scene scene = new Scene(flowPane, 400, 500);
    Stage stage = new Stage();
    stage.setScene(scene);

    Text text = new Text();

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
