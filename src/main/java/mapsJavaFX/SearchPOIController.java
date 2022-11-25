package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.POILocation;
import maps.User;

import java.util.ArrayList;
import java.util.List;

public class SearchPOIController {
  private List<POILocation> poiLocations;
  private User user;
  @FXML
  private Label myLabel;
  @FXML
  private ListView<POILocation> matchingPOIList;
  @FXML
  private TextField searchInput;

  private List<POILocation> searchForPOI(String searchText) {
    searchText = searchText.toLowerCase();
    List<POILocation> matchingPOIs = new ArrayList<>();
    for (POILocation poiLocation : this.poiLocations) {
      if (poiLocation.toString().toLowerCase().contains(searchText)) {
        matchingPOIs.add(poiLocation);
      }
    }
    return matchingPOIs;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setPOIs(List<POILocation> poiLocations) {
    matchingPOIList.setPlaceholder(new Label("No Matching POIs"));
    this.poiLocations = poiLocations;
    matchingPOIList.getItems().setAll(this.poiLocations);
  }

  public void onSearchInputKeyPress() {
    matchingPOIList.getItems().clear();
    matchingPOIList.getItems().addAll(searchForPOI(searchInput.getText()));
  }

  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        navigateToPOI(getSelectedPOI());
        new POIDescriptionController(user, getSelectedPOI());
      }
    }
  }

  public void onPOIListKeyPress(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      navigateToPOI(getSelectedPOI());
    }
  }

  private POILocation getSelectedPOI() {
    return matchingPOIList.getSelectionModel().getSelectedItem();
  }

  public void navigateToPOI(POILocation poiLocation) {
    ControllerMediator.getInstance().mapViewControllerGoToPOI(poiLocation);
  }
}
