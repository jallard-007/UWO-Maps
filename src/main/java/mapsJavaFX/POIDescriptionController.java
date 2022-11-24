package mapsJavaFX;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maps.POILocation;

import java.io.IOException;

public class POIDescriptionController {
  //  @FXML private Label label;
//  @FXML private ScrollPane scrollPane;
  String roomNum;
  String name;
  String type;
  Integer capacity;
  String hours;
  String information;
  String POIDescription;

  public void getPOIDescription(POILocation poiLocation) throws IOException {
    BorderPane borderPane = new BorderPane();

    //Concatenate variables to form a string Label containing the description of the selected POI

    type = poiLocation.getPOI().getPOIType();
    roomNum = poiLocation.getPOI().getRoomNumber();
    information = poiLocation.getPOI().getInformation();
    POIDescription = "ROOM NUMBER: " + roomNum + "\n";

    if (poiLocation.getPOI().getName() != null) {
      name = poiLocation.getPOI().getName();
      POIDescription += "NAME: " + name + "\n";
    }

    POIDescription += "TYPE: " + type + "\n";

    if (poiLocation.getPOI().getCapacity() != null) {
      capacity = poiLocation.getPOI().getCapacity();
      POIDescription += "ROOM CAPACITY: " + capacity + "\n";
    }
    if (poiLocation.getPOI().getHoursOfOperation() != null) {
      hours = poiLocation.getPOI().getHoursOfOperation();
      POIDescription += "\nHOURS OF OPERATION: \n" + hours;
    }
    if (poiLocation.getPOI().getInformation() != null) {
      information = poiLocation.getPOI().getInformation();
      POIDescription += "\nADDITIONAL INFORMATION: \n" + information;
    }

    Label label = new Label();
    label.setText(POIDescription);
    label.setPadding(new Insets(10));
    label.setWrapText(true);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(label);
    scrollPane.setFitToWidth(true);
    borderPane.setCenter(scrollPane);


    //Create button bar to hold POI options (delete, edit, favourite)

    Button btnDeletePOI = new Button("Delete");
    ToggleButton btnFavouritePOI = new ToggleButton("Favourite");
    Button btnEditPOI = new Button("Edit");
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(btnEditPOI, btnFavouritePOI, btnDeletePOI);
    buttonBar.setPadding(new Insets(5));
    borderPane.setBottom(buttonBar);

    //Handling favouriting POIs
    FavouritesController favouritesController = new FavouritesController();
    //check if POI is already favourited
    if (favouritesController.searchFavourites(poiLocation) != -1){
      btnFavouritePOI.setSelected(true);
      btnFavouritePOI.setText("Unfavourite");
    }
    btnFavouritePOI.setOnAction(event -> {
      if (btnFavouritePOI.isSelected()){
        btnFavouritePOI.setText("Unfavourite");
        try {
          favouritesController.setFavourites(poiLocation);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }else{
        btnFavouritePOI.setText("Favourite");
        try {
          favouritesController.removeFavourites(poiLocation);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    Scene scene = new Scene(borderPane, 200, 300);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle(roomNum);
    stage.setMinHeight(200);
    stage.setMinWidth(300);
    //Indicate that the stage should be a popup
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
    stage.centerOnScreen();

  }
}
