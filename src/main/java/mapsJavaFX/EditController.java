package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maps.POI;
import maps.POILocation;

public class EditController {
  public static Stage stage;
  /**
   * The Save button on the editing page.
   */
  @FXML
  private Button btnSave;
  /**
   * the Cancel button on the editing page
   */
  @FXML
  private Button btnCancel;
  /**
   * the text box on the UI where the user enters the new room number.
   */
  @FXML
  private TextField newRoomNum;
  /**
   * the text box on the UI where the user enters the new common name.
   */
  @FXML
  private TextField newName;
  /**
   * the text box on the UI where the user enters the new room capacity.
   */
  @FXML
  private TextField newRoomCapacity;
  /**
   * the text box on the UI where the user enters the new hours of operation of
   * the location.
   */
  @FXML
  private TextField newHours;
  /**
   * the text box on the UI where the user enters any additional information for
   * the POI.
   */
  @FXML
  private TextArea newInformation;
  /**
   * the specific POI location that the user has selected to edit its POI.
   */
  private POILocation poiLocation;
  private POIButton poiButton;

  public static void setStage(Stage newStage) {
    stage = newStage;
  }

  /**
   * Retrieves the POI the user wants to edit
   * 
   * @param poiLocation POI location to edit
   * @param poiButton   button that corresponds to the poi
   */
  public void setPoiLocation(POILocation poiLocation, POIButton poiButton) {
    this.poiLocation = poiLocation;
    this.poiButton = poiButton;
    this.poiButton.makeDraggable();

    POI poi = poiLocation.getPOI();
    this.newRoomNum.setText(poi.getRoomNumber());
    String roomName = poi.getName();
    if (roomName != null) {
      this.newName.setText(roomName);
    }
    Integer roomCapacity = poi.getCapacity();
    if (roomCapacity != null) {
      this.newRoomCapacity.setText(roomCapacity.toString());
    }
    String hours = poi.getHoursOfOperation();
    if (hours != null) {
      this.newHours.setText(hours);
    }
    String info = poi.getInformation();
    if (info != null) {
      this.newInformation.setText(info);
    }

    stage.setOnHiding(event -> {
      this.poiButton.removeDraggable();
      stage.setScene(null);
    });
  }

  /**
   * Saves the changes to the POI made by the user
   */
  public void onSave() {

    // save position
    this.poiButton.savePosition();
    this.poiButton.removeDraggable();

    // Keep the original description of each POI if nothing is entered into the
    // textbox (save for POI common names)
    POI poi = poiLocation.getPOI();
    if (!newRoomNum.getText().trim().isEmpty()) {
      poi.setRoomNumber(newRoomNum.getText());
    }

    poi.setName(newName.getText());
    poi.setHoursOfOperation(newHours.getText());
    poi.setInformation(newInformation.getText());

    // if after removing spaces, the textfield is empty, set attribute as null
    // (Textfields are initialized as empty strings)
    if (newName.getText().trim().isEmpty()) {
      poi.setName(null);
    }
    if (newHours.getText().trim().isEmpty()) {
      poi.setHoursOfOperation(null);
    }
    if (newInformation.getText().trim().isEmpty()) {
      poi.setInformation(null);
    }

    // Handle hours of operation
    try {
      poi.setCapacity(Integer.parseInt(newRoomCapacity.getText()));
    } catch (NumberFormatException e) {
      // Set as null if empty or not a number
      poi.setCapacity(null);
    } finally {
      // Refresh both the favourites and search display to reflect the deletion.
      ControllerMediator.getInstance().refreshFavouritesList();
      ControllerMediator.getInstance().refreshSearchList();
      // exit pop-up
      stage.setScene(null);
      stage.hide();
    }
  }

  /**
   * Return to the POI's description popup if the user clicks on the [Cancel]
   * button in the editing window.
   */
  public void onCancel() {
    this.poiButton.removeDraggable();
    stage.setScene(null);
    new POIDescriptionController(this.poiButton, this.poiLocation);
  }
}
