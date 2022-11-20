package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import maps.Building;
import maps.POILocation;

import java.util.List;

public class MapViewController {
    @FXML
    private TabPane tabPane;
    List<Building> buildings;

    public void addBuildings(List<Building> buildings) {
        this.buildings = buildings;
        for (Building building : this.buildings) {
            Tab tab = new Tab(building.getName());
            tab.setClosable(false);
            this.tabPane.getTabs().add(tab);
        }
    }
    public void goToPOI(POILocation poiLocation) {
        // change building/level and highlight poi
        System.out.println("received request to go to " + poiLocation);
    }
}
