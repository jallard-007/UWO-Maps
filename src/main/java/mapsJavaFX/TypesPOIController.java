package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import maps.POIType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controls the filtering by POIType
 */
public class TypesPOIController {
  final List<POIType> selectedList = new ArrayList<>();
  @FXML
  private VBox poiTypeList;

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
    ControllerMediator.getInstance().getApplication().setFilter(getFilterList());
    ControllerMediator.getInstance().searchPOIControllerRefreshList();
    ControllerMediator.getInstance().mapViewControllerFilterList(getFilterList());
  }

  public List<POIType> getFilterList() {
    if (selectedList.size() == 0) {
      return Arrays.asList(POIType.values());
    } else {
      return selectedList;
    }
  }
}
