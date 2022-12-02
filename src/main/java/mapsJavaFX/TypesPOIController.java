package mapsJavaFX;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.POIType;

/**
 * Controls the filtering by POIType
 */
public class TypesPOIController {
  @FXML
  private ListView<POIType> poiTypeList;

  public TypesPOIController() {
  }

  /**
   * initializes the ListView
   */
  @FXML
  public void initialize() {
    poiTypeList.setPlaceholder(new Label("No Matching POIs"));
    poiTypeList.getItems().addAll(maps.POIType.values());
  }

  /**
   * Handles mouse click event
   * 
   * @param mouseEvent
   */
  public void onPOIListMouseClick(MouseEvent mouseEvent) {
    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
      List<POIType> l = new ArrayList<>();
      // will change to be able to select multiple filters
      l.add(getSelectedPOIType());
      ControllerMediator.getInstance().mapViewControllerFilterList(l);
    }
  }

  /**
   * @return the currently selected POIType
   */
  public POIType getSelectedPOIType() {
    return poiTypeList.getSelectionModel().getSelectedItem();
  }
}
