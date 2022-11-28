package mapsJavaFX;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import maps.POIType;

public class TypesPOIController {
    @FXML
    private ListView<POIType> poiTypeList;

    public TypesPOIController() {}

    @FXML
    public void initialize() {
        poiTypeList.setPlaceholder(new Label("No Matching POIs"));
        poiTypeList.getItems().addAll(maps.POIType.values());
    }

    public void onPOIListMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            List<POIType> l = new ArrayList<POIType>();
            // will change to be able to select multiple filters
            l.add(getSelectedPOIType());
            ControllerMediator.getInstance().filterList(l);
        }
    }

    public POIType getSelectedPOIType() {
        return poiTypeList.getSelectionModel().getSelectedItem();
    }
}
