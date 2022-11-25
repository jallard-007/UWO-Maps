package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import maps.Application;
import maps.Building;
import maps.Floor;
import maps.POILocation;

public class MapViewController {
  @FXML
  private TabPane tabPane;
  Application app;

  public void setApp(Application app) {
    this.app = app;

    // creates a new tab for each building
    for (Building building : this.app.getBuildings()) {
      Tab buildingTab = new Tab(building.getName());
      buildingTab.setClosable(false); // prevents the user from closing the tab
      this.tabPane.getTabs().add(buildingTab);

      TabPane buildingTabPane = new TabPane();
      buildingTab.setContent(buildingTabPane); // adds a TabPane to the tab, so we can add floors

      // creates a new tab for each floor within the building tab
      for (Floor floor : building.getFloors()) {
        Tab floorTab = new Tab(floor.getName());
        Image image = floor.getImage();
        ImageView imageView = new ImageView(image);

        // Add floor PNGs into a scrollpane so users can pan through the maps; set dimensions to
        // half the original image size
        imageView.setFitHeight(image.getHeight() / 2);
        imageView.setFitWidth(image.getWidth() / 2);
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setContent(imageView);

        // Add scrollpane to the tab of each floor
        floorTab.setContent(scrollpane);
        floorTab.setClosable(false);
        buildingTabPane.getTabs().add(floorTab);
      }
    }
  }

  public void goToPOI(POILocation poiLocation) {
    System.out.println("go to " + poiLocation);

    Tab floorTab =
        goToTab((TabPane) goToTab(this.tabPane, poiLocation.getBuilding().getName()).getContent(),
            poiLocation.getFloor().getName());

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
