package mapsJavaFX;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
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
import maps.POILocation;
import maps.POIType;
import maps.Pair;

public class MapViewController {
  @FXML
  private StackPane stackPane;
  @FXML
  private TabPane tabPane;
  Application app;
  private List<POIButton>[] poiButtons;

  @SuppressWarnings("unchecked")
  public void setApp(Application app) {
    this.app = app;
    this.poiButtons = (ArrayList<POIButton>[]) new ArrayList[POIType.values().length];
    for (int i = 0; i < POIType.values().length; ++i) {
      this.poiButtons[i] = new ArrayList<>();
    }

    // creates a new tab for each building
    for (Building building : this.app.getBuildings()) {
      Tab buildingTab = new Tab(building.getName());
      buildingTab.setClosable(false);
      this.tabPane.getTabs().add(buildingTab);

      TabPane buildingTabPane = new TabPane();
      buildingTab.setContent(buildingTabPane);

      // creates a new tab for each floor within the building tab
      for (Floor floor : building.getFloors()) {
        Tab floorTab = new Tab(floor.getName());
        Image image = floor.getImage();
        ImageView imageView = new ImageView(image);

        // Add floor PNGs into a scrollPane so users can pan through the maps; set dimensions to
        StackPane stackPane = new StackPane();
        ScrollPane scrollPane = new ScrollPane();

        Pane poiPane = new Pane();
        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(poiPane);
        scrollPane.setContent(stackPane);
        floorTab.setContent(scrollPane);
        floorTab.setClosable(false);
        buildingTabPane.getTabs().add(floorTab);
      }
    }

    String buildingName = "";
    String floorName = "";
    Pane currPane = null;

    for (POILocation poiLocation : this.app.getPoiLocations()) {
      POIButton poiButton = new POIButton(poiLocation);
      poiButtons[poiLocation.getPOI().getPOIType().ordinal()].add(poiButton);
      poiButton.makeDraggable();

      poiButton.setLayoutX(poiLocation.getPOI().getPosition().getX());
      poiButton.setLayoutY(poiLocation.getPOI().getPosition().getY());
      if (!buildingName.equals(poiLocation.getBuilding().getName())
          || !floorName.equals(poiLocation.getFloor().getName())) {
        currPane = getPane(poiLocation);
      }
      if (currPane == null) {
        System.out.print("Error: Could not find the specified Pane | in MapViewController.setApp");
        System.exit(55);
      }
      currPane.getChildren().add(poiButton);
    }
  }

  public void goToPOI(POILocation poiLocation) {
    System.out.println("go to " + poiLocation);

    Tab tab =
        goToTab((TabPane) goToTab(this.tabPane, poiLocation.getBuilding().getName()).getContent(),
            poiLocation.getFloor().getName());

    Pair position = poiLocation.getPOI().getPosition();
    ScrollPane scrollPane = (ScrollPane) tab.getContent();
    double xRatio = position.getX() / poiLocation.getFloor().getImage().getWidth();
    double yRatio = position.getY() / poiLocation.getFloor().getImage().getHeight();
    double errorX = xRatio - 0.5;
    double errorY = yRatio - 0.5;

    scrollPane.setHvalue(xRatio + (errorX * 0.2));
    scrollPane.setVvalue(yRatio + (errorY * 0.2));

  }

  private Tab goToTab(TabPane tabPane, String tabName) {
    if (tabPane == null) {
      System.exit(44);
    }
    for (Tab tab : tabPane.getTabs()) {
      if (tab.getText().equals(tabName)) {
        tabPane.getSelectionModel().select(tab);
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

  public void filterList(List<POIType> selectedPOITypes) {
    for (POIType poiType : POIType.values()) {
      boolean show = selectedPOITypes.contains(poiType);
      for (POIButton poiTypeButtons : this.poiButtons[poiType.ordinal()]) {
        poiTypeButtons.setDisable(!show);
        poiTypeButtons.setVisible(show);
      }
    }
  }
}
