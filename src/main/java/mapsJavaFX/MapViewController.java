package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import maps.Building;
import maps.Floor;
import maps.POILocation;
import java.io.File;
import java.util.List;


public class MapViewController {
  @FXML private TabPane tabPane;
  List<Building> buildings;

  public void addBuildings(List<Building> buildings) {
    this.buildings = buildings;

    // creates a new tab for each building
    for (Building building : this.buildings) {
      Tab buildingTab = new Tab(building.getName());
      buildingTab.setClosable(false); // prevents the user from closing the tab
      this.tabPane.getTabs().add(buildingTab);

      TabPane buildingTabPane = new TabPane();
      buildingTab.setContent(buildingTabPane); // adds a TabPane to the tab, so we can add floors

      // creates a new tab for each floor within the building tab
      for (Floor floor : building.getFloors()) {
        Tab floorTab = new Tab(floor.getName());
        Image image = new Image(new File(floor.getImagePath()).toURI().toString());
        ImageView imageView = new ImageView(image);

        // you can change these values to change the size of the image
        imageView.setFitHeight(450);
        imageView.setFitWidth(900);

        floorTab.setContent(imageView);
        floorTab.setClosable(false);
        buildingTabPane.getTabs().add(floorTab);
      }
    }
  }

  public void goToPOI(POILocation poiLocation) {
    System.out.println("go to " + poiLocation);

    Tab floorTab = goToTab(
      (TabPane) goToTab(this.tabPane, poiLocation.getBuilding().getName()).getContent(),
      poiLocation.getFloor().getName()
    );

    // need to somehow highlight the POI and focus on it. 
    // example: highlightPOI(poILocation, floorTab?)
  }


  private Tab goToTab(TabPane tabPane, String tabName) {
    if (tabPane == null) {
      // error. POI does not exist
      System.exit(44);
    }
    for (Tab tab : tabPane.getTabs()) {
      if (tab.getText().equals(tabName)) {
        tabPane.getSelectionModel().select(tab);
        System.out.println("Selected " + tab.getText());
        return tab;
      }
    }
    return null;
  }
}
