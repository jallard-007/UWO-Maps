package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.Application;
import maps.POILocation;

/**
 * Controller to handle actions involving the display of POIs after a search
 */
public class SearchPOIController {
  /**
   * Current application being used
   */
  private Application app;
  /**
   * A list of matching POIs according to what was entered in the search bar
   */
  @FXML
  private ListView<POILocation> matchingPOIList;
  /**
   * The text box where the search query is entered by the user
   */
  @FXML
  private TextField searchInput;

  /**
   * Called to set up the app and list of matching POIs for future use
   *
   * @param app referring to the map application
   */
  public void setApp(Application app) {
    matchingPOIList.setPlaceholder(new Label("No Matching POIs"));
    this.app = app;
    matchingPOIList.getItems().setAll(this.app.getPoiLocations());
  }

  /**
   * Method called each time a letter is entered into the serach bar
   */
  public void onSearchInputKeyPress() {
    matchingPOIList.getItems().clear();
    matchingPOIList.getItems().addAll(app.searchForPOI(searchInput.getText()));
  }

  /**
   * Event handler to dictate what to do when a user clicks on a POI in the list
   *
   * @param mouseEvent onMouseClicked event
   */
  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        if (getSelectedPOI() == null) {
          return;
        }
        navigateToPOI(getSelectedPOI());
        // Commented out to allow easy testing of goToPOI method in MainViewController
        // new POIDescriptionController(getSelectedPOI());
      }
    }
  }

  /**
   * Handles when a keyboard button is pressed. Allows the user to press enter to navigate to the
   * highlighted poi
   *
   * @param keyEvent the key event
   */
  public void onPOIListKeyPress(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      navigateToPOI(getSelectedPOI());
    }
  }

  /**
   * Gets the POI location selected by the user.
   *
   * @return selected POI location
   */
  private POILocation getSelectedPOI() {
    return matchingPOIList.getSelectionModel().getSelectedItem();
  }

  /**
   * Navigate to the map location of the selected POI.
   *
   * @param poiLocation selected POI location
   */
  public void navigateToPOI(POILocation poiLocation) {
    ControllerMediator.getInstance().mapViewControllerGoToPOI(poiLocation);
  }

  /**
   * Refreshes the list of displayed POIs in the search tab.
   */
  public void refreshList() {
    matchingPOIList.getItems().setAll(app.searchForPOI(""));
  }
}
