package mapsJavaFX;

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
        floorStackPane.getChildren().add(imageView);
        floorStackPane.getChildren().add(poiPane);
        scrollPane.setContent(floorStackPane);
        floorTab.setContent(scrollPane);
        floorTab.setClosable(false);
        buildingTabPane.getTabs().add(floorTab);
      }
    }

    String buildingName = "";;
    String floorName = "";
    Pane currPane = null;
    for (POILocation poiLocation : this.app.getPoiLocations()) {

      Button poiButton = new Button();
      addButtonFeatures(poiButton, poiLocation);
      poiButton.setLayoutX(poiLocation.getPOI().getPosition().getX());
      poiButton.setLayoutY(poiLocation.getPOI().getPosition().getY());
      if (!buildingName.equals(poiLocation.getBuilding().getName())
          || !floorName.equals(poiLocation.getFloor().getName())) {
        currPane = getPane(poiLocation);
        if (currPane == null) {
          System.out
              .print("Error: Could not find the specified Pane | in MapViewController.setApp");
          System.exit(55);
        }
      }
      currPane.getChildren().add(poiButton);
    }
  }

  private double startX;
  private double startY;

  private void addButtonFeatures(Button node, POILocation poiLocation) {
    node.setOnMouseClicked(e -> {
    });

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
        POI poi = poiLocation.getPOI();
        poi.setPosition(poi.getPosition().getX() + (e.getScreenX() - startX),
            poi.getPosition().getY() + (e.getScreenY() - startY));
        node.setLayoutX(poi.getPosition().getX());
        node.setTranslateX(0);
        node.setLayoutY(poi.getPosition().getY());
        node.setTranslateY(0);
      } else {
        new POIDescriptionController(poiLocation);
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

  private Pane getPane(POILocation poiLocation) {
    for (Tab buildingTab : this.tabPane.getTabs()) {
      if (buildingTab.getText().equals(poiLocation.getBuilding().getName())) {
        TabPane buildingTabPane = (TabPane) buildingTab.getContent();
        for (Tab floorTab : buildingTabPane.getTabs()) {
          if (floorTab.getText().equals(poiLocation.getFloor().getName())) {
            StackPane stak = (StackPane) ((ScrollPane) floorTab.getContent()).getContent();
            return (Pane) stak.getChildren().get(1);
          }
        }
      }
    }
    return null;
  }

}
