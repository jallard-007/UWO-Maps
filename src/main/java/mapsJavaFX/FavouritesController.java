package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.User;
import maps.POILocation;

public class FavouritesController {
  public User user;

  @FXML
  private ListView<POILocation> favouritePOIList;

  public void setUser(User user) {
    this.user = user;
    favouritePOIList.getItems().addAll(user.getFavourites());
  }

  public void addFavourite(POILocation poiLocation) {
    favouritePOIList.getItems().add(poiLocation);
  }

  public void removeFavourite(POILocation poiLocation) {
    favouritePOIList.getItems().remove(poiLocation);
  }

  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        navigateToPOI(getSelectedPOI());
        new POIDescriptionController(user, getSelectedPOI());
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
