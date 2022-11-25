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
  public POIDescriptionController(User user, POILocation poiLocation) {
    BorderPane borderPane = new BorderPane();

    // Concatenate variables to form a string Label containing the description of the selected POI
    POI poi = poiLocation.getPOI();

    String description = "ROOM NUMBER: " + poi.getRoomNumber() + "\n";

    if (poiLocation.getPOI().getName() != null) {
      description += "NAME: " + poi.getName() + "\n";
    }

    description += "TYPE: " + poi.getPOIType() + "\n";

    if (poiLocation.getPOI().getCapacity() != null) {
      description += "ROOM CAPACITY: " + poi.getCapacity() + "\n";
    }
    if (poiLocation.getPOI().getHoursOfOperation() != null) {
      description += "\nHOURS OF OPERATION: \n" + poi.getHoursOfOperation();
    }
    if (poiLocation.getPOI().getInformation() != null) {
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
    Button btnEditPOI = new Button("Edit");
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.getButtons().addAll(btnEditPOI, btnFavouritePOI, btnDeletePOI);
    buttonBar.setPadding(new Insets(5));
    borderPane.setBottom(buttonBar);

    // Handling favouriting POIs
    // check if POI is already favourited
    if (user.indexOfFavourite(poiLocation) != -1) {
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