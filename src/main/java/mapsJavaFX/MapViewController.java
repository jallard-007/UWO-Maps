package mapsJavaFX;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import maps.Application;
import maps.Building;
import maps.Floor;
import maps.POI;
import maps.POILocation;

public class MapViewController {
  @FXML
  private StackPane stackPane;
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

        // Add floor PNGs into a scrollPane so users can pan through the maps; set dimensions to
        // half the original image size
        imageView.setFitHeight(image.getHeight() / 2);
        imageView.setFitWidth(image.getWidth() / 2);

        StackPane floorStackPane = new StackPane();
        Pane poiPane = new Pane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageView);

        for (List<POI> poiList : floor.getPOIS()) {
          for (POI poi : poiList) {
            Button poiButton = new Button();
            makeDraggable(poiButton, poi);
            poiButton.setLayoutX(poi.getPosition().getX());
            poiButton.setLayoutY(poi.getPosition().getY());
            poiPane.getChildren().add(poiButton);
          }
        }

        floorStackPane.getChildren().add(imageView);
        floorStackPane.getChildren().add(poiPane);

        scrollPane.setContent(floorStackPane);
        // Add scrollPane to the tab of each floor
        floorTab.setContent(scrollPane);
        floorTab.setClosable(false);
        buildingTabPane.getTabs().add(floorTab);
      }
    }
  }

  private double startX;
  private double startY;

  private void makeDraggable(Button node, POI poi) {
    node.setOnMousePressed(e -> {
      if (app.getEditMode()) {
        startX = e.getScreenX();
        startY = e.getScreenY();
      }
    });

    node.setOnMouseDragged(e -> {
      if (app.getEditMode()) {
        node.setTranslateX(e.getScreenX() - startX);
        node.setTranslateY(e.getScreenY() - startY);
      }
    });

    node.setOnMouseReleased(e -> {
      if (app.getEditMode()) {
        poi.setPosition(poi.getPosition().getX() + (e.getScreenX() - startX),
            poi.getPosition().getY() + (e.getScreenY() - startY));
        node.setLayoutX(poi.getPosition().getX());
        node.setTranslateX(0);
        node.setLayoutY(poi.getPosition().getY());
        node.setTranslateY(0);
      }

    });
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
