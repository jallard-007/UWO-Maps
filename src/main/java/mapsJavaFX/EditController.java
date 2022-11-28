package mapsJavaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maps.POILocation;

public class EditController {
  /**
   * The Save button on the editing page.
   */
  @FXML private  Button btnSave;
  /**
   * the Cancel button on the editing page
   */
  @FXML private Button btnCancel;
  /**
   * the text box on the UI where the user enters the new room number.
   */
  @FXML private TextField newRoomNum;
  /**
   * the text box on the UI where the user enters the new common name.
   */
  @FXML private TextField newName;
  /**
   * the text box on the UI where the user enters the new room capacity.
   */
  @FXML private TextField newRoomCapacity;
  /**
   * the text box on the UI where the user enters the new hours of operation of the location.
   */
  @FXML private TextField newHours;
  /**
   * the text box on the UI where the user enters any additional information for the POI.
   */
  @FXML private TextArea newInformation;
  /**
   * the specific POI location that the user has selected to edit its POI.
   */
  private POILocation poiLocation;

  /**
   * Retrieves the POI the user wants to edit
   * @param poiLocation POI location to edit
   */
  public void setPoiLocation(POILocation poiLocation){
    this.poiLocation = poiLocation;
  }

  /**
   * Saves the changes to the POI made by the user
   * @param event button onAction event
   */
  public void onSave(ActionEvent event) {

    //Keep the original description of each POI if nothing is entered into the textbox (save for POI common names)
    if (!newRoomNum.getText().trim().isEmpty()){
      poiLocation.getPOI().setRoomNumber(newRoomNum.getText());
    }

    //TODO: figure out what to do for these fields lol
    if (!newName.getText().trim().isEmpty()) {
      poiLocation.getPOI().setName(newName.getText());
    }else{
      poiLocation.getPOI().setName(null);
    }
    if (!newHours.getText().trim().isEmpty()){
      poiLocation.getPOI().setHoursOfOperation(newHours.getText());
    }
    if (!newInformation.getText().trim().isEmpty()){
      poiLocation.getPOI().setInformation(newInformation.getText());
    }
    try{
      poiLocation.getPOI().setCapacity(Integer.parseInt(newRoomCapacity.getText()));
    }catch (NumberFormatException e){
      //Not a number
    }finally{
      //Refresh both the favourites and search display to reflect the deletion.
      ControllerMediator.getInstance().refreshFavouritesList();
      ControllerMediator.getInstance().refreshSearchList();
      //exit pop-up
      Stage stage = (Stage) btnSave.getScene().getWindow();
      stage.close();
    }
  }

  /**
   * Return to the POI's description popup if the user clicks on the [Cancel] button in the editing window.
   *
   * @param event button onAction event
   * @return the POI description popup corresponding to the POI being edited
   */
  public POIDescriptionController onCancel(ActionEvent event) {
    Stage stage = (Stage) btnCancel.getScene().getWindow();
    stage.close();
    return new POIDescriptionController(ControllerMediator.getInstance().getApplication().getUser(), poiLocation, ControllerMediator.getInstance().getApplication());

  }
}
