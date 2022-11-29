package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.POILocation;
import maps.Application;

/**
 * Controller to handle actions involving the display of the favourites list.
 */
public class FavouritesController {
  /**
   * Current application being used
   */
  public Application app;

  /**
   * A list of favourited POIs
   */
  @FXML
  private ListView<POILocation> favouritePOIList;

  /**
   * Called to set up the app and favourites list for future use
   * @param app referring to the map application
   */
  public void setApp(Application app) {
    this.app = app;
    favouritePOIList.getItems().setAll(app.getUser().getFavourites());
  }

  /**
   * Method used to refresh the list of favourited POIs being displayed in the [Favourites] tab of the application
   */
  public void refreshList() {
    favouritePOIList.getItems().setAll(app.getUser().getFavourites());
  }

  /**
   * Event handler to dictate what to do when a user clicks on a POI in the list
   * @param mouseEvent onMouseClicked event
   */
  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        navigateToPOI(getSelectedPOI());
        new POIDescriptionController(getSelectedPOI());
      }
    }
  }

  /**
   * Method that takes the user to the area on the map where the POI location they selected is located
   * @param poiLocation the selected POI location
   */
  public void navigateToPOI(POILocation poiLocation) {
    ControllerMediator.getInstance().mapViewControllerGoToPOI(poiLocation);
  }

  /**
   * Gets the POI location selected by the user.
   * @return selected POI location
   */
  private POILocation getSelectedPOI() {
    return favouritePOIList.getSelectionModel().getSelectedItem();
  }
}
