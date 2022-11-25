package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.Application;
import maps.POILocation;

public class SearchPOIController {
  private Application app;
  @FXML
  private Label myLabel;
  @FXML
  private ListView<POILocation> matchingPOIList;
  @FXML
  private TextField searchInput;

  public void setApp(Application app) {
    matchingPOIList.setPlaceholder(new Label("No Matching POIs"));
    this.app = app;
    matchingPOIList.getItems().setAll(this.app.getPoiLocations());
  }

  public void onSearchInputKeyPress() {
    matchingPOIList.getItems().clear();
    matchingPOIList.getItems().addAll(app.searchForPOI(searchInput.getText()));
  }

  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        navigateToPOI(getSelectedPOI());
        new POIDescriptionController(app.getUser(), getSelectedPOI());
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
