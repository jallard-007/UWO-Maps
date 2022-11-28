package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.POILocation;
import maps.Application;

public class FavouritesController {
  public Application app;

  @FXML
  private ListView<POILocation> favouritePOIList;

  public void setApp(Application app) {
    this.app = app;
    favouritePOIList.getItems().setAll(app.getUser().getFavourites());
  }

  public void refreshList() {
    favouritePOIList.getItems().setAll(app.getUser().getFavourites());
  }

  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        navigateToPOI(getSelectedPOI());
        new POIDescriptionController(getSelectedPOI());
      }
    }
  }

  public void navigateToPOI(POILocation poiLocation) {
    ControllerMediator.getInstance().mapViewControllerGoToPOI(poiLocation);
  }

  private POILocation getSelectedPOI() {
    return favouritePOIList.getSelectionModel().getSelectedItem();
  }
}
