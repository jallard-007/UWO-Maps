package mapsJavaFX;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import maps.POI;
import maps.POILocation;
import maps.User;

public class POIDescriptionController {
  public POIDescriptionController(User user, POILocation poiLocation, POI POI) {
    BorderPane borderPane = new BorderPane();

    // Concatenate variables to form a string Label containing the description of the selected POI
    POI poi;
    if (poiLocation != null) {
      poi = poiLocation.getPOI();
    } else {
      poi = POI;
    }

    String description = "ROOM NUMBER: " + poi.getRoomNumber() + "\n";

    if (poi.getName() != null) {
      description += "NAME: " + poi.getName() + "\n";
    }

    description += "TYPE: " + poi.getPOIType() + "\n";

    if (poi.getCapacity() != null) {
      description += "ROOM CAPACITY: " + poi.getCapacity() + "\n";
    }
    if (poi.getHoursOfOperation() != null) {
      description += "\nHOURS OF OPERATION: \n" + poi.getHoursOfOperation();
    }
    if (poi.getInformation() != null) {
      description += "\nADDITIONAL INFORMATION: \n" + poi.getInformation();
    }

    Label label = new Label();
    label.setText(description);
    label.setPadding(new Insets(10));
    label.setWrapText(true);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(label);
    scrollPane.setFitToWidth(true);
    borderPane.setCenter(scrollPane);

    // Create button bar to hold POI options (delete, edit, favourite)
    Button btnDeletePOI = new Button("Delete");
    ToggleButton btnFavouritePOI = new ToggleButton("Favourite");
    btnFavouritePOI.setPrefWidth(100);
    ToggleButton btnEditPOI = new ToggleButton("Edit");

    ButtonBar buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(btnEditPOI, btnFavouritePOI, btnDeletePOI);
    buttonBar.setPadding(new Insets(5));
    borderPane.setBottom(buttonBar);


    // btnEditPOI.setOnAction(event -> {
    // if (btnEditPOI.isSelected()) {
    // btnEditPOI.setText("Cancel");

    // TextField rmNum = new TextField("Room Number: ");
    // TextField name = new TextField("Room Name: ");
    // scrollPane.setContent(rmNum);
    // borderPane.setCenter(scrollPane);
    // // user.addFavourite(poiLocation);
    // // ControllerMediator.getInstance().refreshFavouritesList();
    // } else {
    // btnEditPOI.setText("Edit");
    // scrollPane.setContent(label);
    // scrollPane.setFitToWidth(true);
    // borderPane.setCenter(scrollPane);
    // }
    // });

    btnDeletePOI.setOnAction(event -> {
      if (btnDeletePOI.isPressed() && poiLocation != null) {
        poiLocation.removePOI();
        ControllerMediator.getInstance().refreshPOIList();
      }
    });

    // Handling favouriting POIs
    // check if POI is already favourited
    if (user.indexOfFavourite(poiLocation) != -1 && poiLocation != null) {
      btnFavouritePOI.setSelected(true);
      btnFavouritePOI.setText("Unfavourite");
    }


    btnFavouritePOI.setOnAction(event -> {
      if (btnFavouritePOI.isSelected()) {
        btnFavouritePOI.setText("Unfavourite");
        user.addFavourite(poiLocation);
        ControllerMediator.getInstance().refreshFavouritesList();
      } else {
        btnFavouritePOI.setText("Favourite");
        user.removeFavourites(poiLocation);
        ControllerMediator.getInstance().refreshFavouritesList();

      }
    });

    Scene scene = new Scene(borderPane, 200, 300);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle(poi.getRoomNumber());
    stage.setMinHeight(200);
    stage.setMinWidth(300);
    // Indicate that the stage should be a popup
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.showAndWait();
    stage.centerOnScreen();
  }
}
