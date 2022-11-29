package mapsJavaFX;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import maps.*;
import java.io.IOException;

/**
 * Controller to handle the popup window displaying information about a selected
 * POI.
 */
public class POIDescriptionController {
  static Application app;
  static Stage stage = new Stage();

  public static void setApp(Application newApp) {
    app = newApp;
    EditController.setStage(stage);
    stage.setMaxWidth(500);
    stage.setAlwaysOnTop(true);
  }

  /**
   * Constructor for the class, stages the popup window and sets up all its button
   * functionalities (edit, favourite, remove)
   * 
   * @param user        current user logged into the application
   * @param poiLocation the POI location selected by the user to view its
   *                    information
   * @param app         the application
   */
  public POIDescriptionController(POIButton poiButton, POILocation poiLocation) {
    Scene s = stage.getScene();
    if (s != null && s.getRoot().getClass() == AnchorPane.class) {
      // poi is being edited and cannot switch, otherwise the button will be moveable,
      // even not when editing
      return;
    }
    stage.setOnHiding(null);
    BorderPane borderPane = new BorderPane();

    // Concatenate variables to form a string Label containing the description of
    // the selected POI
    POI poi = poiLocation.getPOI();

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

    // Handling favouriting POIs
    // check if POI is already favourited
    if (app.getUser().indexOfFavourite(poiLocation) != -1) {
      btnFavouritePOI.setSelected(true);
      btnFavouritePOI.setText("Unfavourite");
    }

    btnFavouritePOI.setOnAction(event -> {
      if (btnFavouritePOI.isSelected()) {
        btnFavouritePOI.setText("Unfavourite");
        app.getUser().addFavourite(poiLocation);
        ControllerMediator.getInstance().refreshFavouritesList();
      } else {
        btnFavouritePOI.setText("Favourite");
        app.getUser().removeFavourites(poiLocation);
        ControllerMediator.getInstance().refreshFavouritesList();

      }
    });

    if (app.getUser().getUserType() != UserType.admin
        && poiLocation.getPOI().getPOIType() != POIType.custom) {
      btnDeletePOI.setDisable(true);
      btnEditPOI.setDisable(true);
    }

    // Handling deleting POIs
    btnDeletePOI.setOnAction(event -> {
      app.deletePOI(poiLocation);
      // Refresh both the favourites and search display to reflect the deletion;
      // remove the POI from the map
      ControllerMediator.getInstance().refreshFavouritesList();
      ControllerMediator.getInstance().refreshSearchList();
      ControllerMediator.getInstance().removePOIButton(poiLocation);
      // exit pop-up
      Stage stage = (Stage) btnDeletePOI.getScene().getWindow();
      stage.close();
    });

    // Handling editing POIs
    btnEditPOI.setOnAction(event -> {
      FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("/edit.fxml"));
      try {
        Scene scene = new Scene(fxmlLoader.load());
        EditController editController = fxmlLoader.getController();
        editController.setPoiLocation(poiLocation, poiButton);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });

    Scene scene = new Scene(borderPane);

    stage.setScene(scene);
    stage.setTitle(poi.getRoomNumber());
    stage.setMinHeight(200);
    stage.setMinWidth(300);

    stage.show();
    stage.centerOnScreen();
  }
}
