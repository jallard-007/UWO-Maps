package mapsJavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import maps.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditController implements Initializable {

  /**
   * The button that allows the user to edit the POI position.
   */
  @FXML private Button btnEditPosition;
  /**
   * List of all possible choices of POI types; base users may only may [custom] POIs
   */
  @FXML private ChoiceBox newPOIType;
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
   * Default set-up of the add/edit POI page upon entering it.
   * @param location
   * The location used to resolve relative paths for the root object, or
   * {@code null} if the location is not known.
   *
   * @param resources
   * The resources used to localize the root object, or {@code null} if
   * the root object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //Get all POI types and turn it into an array list, then turn into observable list and set values as POI type options.
    newPOIType.setItems(FXCollections.observableList(Stream.of(POIType.values()).map(POIType::name).toList()));
    newPOIType.getSelectionModel().selectLast();
    if (ControllerMediator.getInstance().getApplication().getUser().getUserType() == UserType.base){
      newPOIType.setDisable(true);
    }
  }

  /**
   * Retrieves the POI the user wants to edit
   * @param poiLocation POI location to edit
   */
  public void setPoiLocation(POILocation poiLocation){
    this.poiLocation = poiLocation;
  }

  /**
   * Saves the changes to the POI made by the user
   */
  public void onSave() {

    //Keep the original description of each POI if nothing is entered into the textbox (save for POI common names)
    if (!newRoomNum.getText().trim().isEmpty()){
      poiLocation.getPOI().setRoomNumber(newRoomNum.getText());
    }

    poiLocation.getPOI().setName(newName.getText());
    poiLocation.getPOI().setHoursOfOperation(newHours.getText());
    poiLocation.getPOI().setInformation(newInformation.getText());
    poiLocation.getPOI().setType(POIType.valueOf(
        (String) newPOIType.getSelectionModel().getSelectedItem()));

    //if after removing spaces, the textfield is empty, set attribute as null (Textfields are initialized as empty strings)
    if (newName.getText().trim().isEmpty()){
      poiLocation.getPOI().setName(null);
    }
    if (newHours.getText().trim().isEmpty()){
      poiLocation.getPOI().setHoursOfOperation(null);
    }
    if (newInformation.getText().trim().isEmpty()){
      poiLocation.getPOI().setInformation(null);
    }

    //Handle hours of operation
    try{
      poiLocation.getPOI().setCapacity(Integer.parseInt(newRoomCapacity.getText()));
    }catch (NumberFormatException e){
      //Set as null if empty or not a number
      poiLocation.getPOI().setCapacity(null);
    }finally{
      //Add POI location to list and display new POI button on map if the POI location being edited is a newly-created POI
      if (ControllerMediator.getInstance().getApplication().searchForPOI(poiLocation.toString()).isEmpty()){
        ControllerMediator.getInstance().getApplication().addPOI(poiLocation);
        POIButton poiButton = new POIButton(poiLocation);
        ControllerMediator.getInstance().addPOIButton(poiButton, poiLocation);
      }
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
   * @return the POI description popup corresponding to the POI being edited
   */
  public POIDescriptionController onCancel() {
    Stage stage = (Stage) btnCancel.getScene().getWindow();
    stage.close();
    return new POIDescriptionController(ControllerMediator.getInstance().getApplication().getUser(), poiLocation, ControllerMediator.getInstance().getApplication());
  }

  /**
   * Take the user to edit the location of the selected POI on the map.
   * @param event onAction event
   */
  public void onEdit(ActionEvent event) {
  }
}
