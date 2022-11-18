package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.POILocation;
import maps.Application;

public class SearchPOIController {
  public Application app;

  @FXML private Label myLabel;
  @FXML private ListView <POILocation> matchingPOIList;
  @FXML private TextField searchInput;

  public void setData(Application app) {
    matchingPOIList.setPlaceholder(new Label("No Matching POIs"));
    this.app = app;
    matchingPOIList.getItems().addAll(app.getPoiLocations());
  }

  public void onSearchInputKeyPress() {
    matchingPOIList.getItems().clear();
    matchingPOIList.getItems().addAll(app.searchForPOI(searchInput.getText()));
  }

  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      if (mouseEvent.getClickCount() == 2) {
        navigateToPOI(getSelectedPOI());
      }
    }
  }

  public void onPOIListKeyPress(KeyEvent keyEvent) {
    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
      navigateToPOI(getSelectedPOI());
    }
  }

  public POILocation getSelectedPOI() {
    return matchingPOIList.getSelectionModel().getSelectedItem();
  }

  public void navigateToPOI(POILocation poi) {
    System.out.println("clicked on " + poi);
    // navigate to selectedPOI;
  }
}