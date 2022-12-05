package mapsJavaFX.editFeatures;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import maps.POI;
import maps.POILocation;
import maps.POIType;
import maps.UserType;
import mapsJavaFX.ControllerMediator;
import mapsJavaFX.POIButton;
import mapsJavaFX.POIDescriptionController;

import java.util.Arrays;

public class EditPOIController {
  /**
   * List of all possible choices of POI types; base users may only may [custom] POIs
   */
  @FXML
  private ChoiceBox<POIType> newPOIType;
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
   * the text box on the UI where the user enters the new hours of operation of the location.
   */
  @FXML
  private TextField newHours;
  /**
   * the text box on the UI where the user enters any additional information for the POI.
   */
  @FXML
  private TextArea newInformation;
  /**
   * the specific POI location that the user has selected to edit its POI.
   */
  private POILocation poiLocation;
  private POIButton poiButton;

  /**
   * Retrieves the POI the user wants to edit
   *
   * @param poiLocation POI location to edit
   * @param poiButton   button that corresponds to the poi
   */
  public void setPoiLocation(POILocation poiLocation, POIButton poiButton) {
    newPOIType.setItems(FXCollections.observableList(Arrays.asList(POIType.values())));

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

    if (ControllerMediator.getInstance().getApplication().getUser().getUserType()
        == UserType.base) {
      newPOIType.setDisable(true);
      newPOIType.getSelectionModel().selectLast();
    } else {
      newPOIType.getSelectionModel().select(poi.getPOIType().ordinal());
    }

    EditHelper.getStage().setOnHiding(event -> {
      this.poiButton.removeDraggable();
      EditHelper.getStage().setScene(null);
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

    // Handle POI type changes: the selected POI's floor stores its POIs by type.
    // Similarly, the POI button seen on the map are also stored by type. So both
    // storages must be updated
    POIType oldType = poi.getPOIType();
    POIType newType = newPOIType.getSelectionModel().getSelectedItem();
    ControllerMediator controllerMediator = ControllerMediator.getInstance();
    if (oldType != newType) {
      // The button must be updated before the new type is set
      poi.setType(newType);
      controllerMediator.mapViewControllerUpdateButtonStorage(oldType, newType, poiButton);
      poiLocation.getFloor().updatePOIStorage(oldType, newType, poi);
    }

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
    }
    // Add POI location to list and display new POI button on map if the POI
    // location being edited is a newly-created POI
    if (controllerMediator.getApplication().searchForPOI(poiLocation.toString()).isEmpty()) {
      controllerMediator.getApplication().addPOI(poiLocation);
      controllerMediator.mapViewControllerAddPOIButton(poiButton);
    }
    // Refresh both the favourites and search display to reflect the deletion.
    controllerMediator.favouritesControllerRefreshList();
    controllerMediator.searchPOIControllerRefreshList();
    this.poiButton.updateButtonDisplay();

    // exit pop-up
    EditHelper.getStage().hide();
  }

  /**
   * Return to the POI's description popup if the user clicks on the [Cancel] button in the editing
   * window.
   */
  public void onCancel() {
    this.poiButton.removeDraggable();
    EditHelper.getStage().setScene(null);
    new POIDescriptionController(this.poiButton, this.poiLocation);
  }
}
