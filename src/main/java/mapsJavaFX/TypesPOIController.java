package mapsJavaFX;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import maps.POIType;

public class TypesPOIController {
    @FXML
    private ListView<POIType> poiTypeList;

    public TypesPOIController() {
    }

    @FXML
    public void initialize() {
        poiTypeList.setPlaceholder(new Label("No Matching POIs"));
        poiTypeList.getItems().addAll(maps.POIType.values());
        poiTypeList.setCellFactory(CheckBoxListCell.forListView(new Callback<POIType, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(POIType item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> System.out
                        .println("Check box for " + item + " changed from " + wasSelected + " to " + isNowSelected));
                return observable;
            }
        }));
    }

    public void onPOIListMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            List<POIType> l = new ArrayList<>();
            // will change to be able to select multiple filters
            l.add(getSelectedPOIType());
            ControllerMediator.getInstance().filterList(l);
        }
    }

    public POIType getSelectedPOIType() {
        return poiTypeList.getSelectionModel().getSelectedItem();
    }
}
