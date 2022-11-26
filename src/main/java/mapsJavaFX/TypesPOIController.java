package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import maps.POIType;
import maps.Application;

public class TypesPOIController {
    private Application app;
    @FXML
    private ListView<POIType> poiTypeList;

    public void setApp(Application app) {
        poiTypeList.setPlaceholder(new Label("No Matching POIs"));
        this.app = app;
        poiTypeList.getItems().addAll(maps.POIType.values());
        System.out.println(poiTypeList.getItems());
    }
}
