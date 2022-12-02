package mapsJavaFX;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import maps.POIType;

/**
 * Controls the filtering by POIType
 */
public class TypesPOIController {
  @FXML
  private VBox poiTypeList;

  ArrayList<POIType> selectedList = new ArrayList<>();

  public TypesPOIController() {
  }

  /**
   * initializes the ListView
   */
  @FXML
  public void initialize() {
    poiTypeList.setFocusTraversable(false);
    for (POIType poiType : POIType.values()) {
      CheckBox cb = new CheckBox(poiType.name());
      cb.setOnAction(e -> {
        if (cb.isSelected()) {
          selectedList.add(poiType);
        } else {
          selectedList.remove(poiType);
        }
        updateFilter();
      });
      poiTypeList.getChildren().add(cb);
    }
  }

  private void updateFilter() {
    if (selectedList.size() == 0) {
      ControllerMediator.getInstance().mapViewControllerFilterList(Arrays.asList(POIType.values()));
    } else {
      ControllerMediator.getInstance().mapViewControllerFilterList(selectedList);
    }
  }
}
