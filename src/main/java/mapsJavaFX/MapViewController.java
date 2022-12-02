package mapsJavaFX;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import maps.*;

/**
 * Controls the building and floor tabs
 */
public class MapViewController {
  @FXML
  private Slider zoomBar;
  @FXML
  private TabPane tabPane;
  Application app;
  private List<POIButton>[] poiButtons;

  /**
   * Initializes the map view based on the Application object.
   * 
   * @param app the current application being used
   */
  @SuppressWarnings("unchecked")
  public void setApp(Application app) {
    this.app = app;
    POIButton.setSlider(zoomBar);
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
        imageView.setPreserveRatio(true);

        // Add floor PNGs into a scrollPane so users can pan through the maps
        StackPane stackPane = new StackPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);

        Pane poiPane = new Pane();
        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(poiPane);
        scrollPane.setContent(stackPane);
        floorTab.setContent(scrollPane);
        floorTab.setClosable(false);
        buildingTabPane.getTabs().add(floorTab);

        zoomBar.valueProperty().addListener((o, oldV, newV) -> {
          stackPane.setScaleX(newV.doubleValue());
          stackPane.setScaleY(newV.doubleValue());
        });
      }
    }

    String buildingName = "";
    String floorName = "";
    Pane currPane = null;
    for (POILocation poiLocation : this.app.getPoiLocations()) {
      POIButton poiButton = new POIButton(poiLocation);
      poiButtons[poiLocation.getPOI().getPOIType().ordinal()].add(poiButton);

      if (!buildingName.equals(poiLocation.getBuilding().getName())
          || !floorName.equals(poiLocation.getFloor().getName())) {
        currPane = getPane(poiLocation);
      }
      if (currPane == null) {
        System.out.print("Error: Could not find the specified Pane | in MapViewController.setApp");
        System.exit(55);
      }
      zoomBar.valueProperty().addListener((o, oldV, newV) -> {
        poiButton.setScaleX(1 / newV.doubleValue());
        poiButton.setScaleY(1 / newV.doubleValue());
      });
      currPane.getChildren().add(poiButton);
    }
  }

  /**
   * Finds the button that corresponds to a poi
   *
   * @param poiLocation a poi location to find
   * @return the matching button
   */
  public POIButton getButton(POILocation poiLocation) {
    final POI poi = poiLocation.getPOI();
    for (POIButton button : this.poiButtons[poi.getPOIType().ordinal()]) {
      if (button.poiLocation.equals(poiLocation)) {
        return button;
      }
    }
    return null;
  }

  /**
   * Removes a POIButton from the map
   * 
   * @param poiLocation the selected POILocation
   */
  public void removeButton(POILocation poiLocation) {
    POIButton poiButton = getButton(poiLocation);
    this.poiButtons[poiLocation.getPOI().getPOIType().ordinal()].remove(poiButton);
    Pane pane = getPane(poiLocation);
    if (pane == null) {
      // the button is not in a pane? hide the button
      poiButton.setVisible(false);
      return;
    }
    pane.getChildren().remove(poiButton);
  }

  /**
   * Updates the location where the selected POI's button is stored within the POI
   * buttons list; used when the type of a POI is being updated
   * 
   * @param oldType   old POI type
   * @param newType   new POI type
   * @param poiButton the Button associated with the POI whose type is being
   *                  updated
   */
  public void updateButtonStorage(POIType oldType, POIType newType, POIButton poiButton) {
    this.poiButtons[oldType.ordinal()].remove(poiButton);
    this.poiButtons[newType.ordinal()].add(poiButton);
  }

  /**
   * Adds a POIButton to the list and displays it on the map
   * 
   * @param poiButton the POIButton to add
   */
  public void addButton(POIButton poiButton) {
    POILocation poiLocation = poiButton.poiLocation;
    poiButtons[poiLocation.getPOI().getPOIType().ordinal()].add(poiButton);
    // Display new button on map
    poiButton.setLayoutX(poiLocation.getPOI().getPosition().getX());
    poiButton.setLayoutY(poiLocation.getPOI().getPosition().getY());
    Pane currPane = getPane(poiLocation);
    if (currPane == null) {
      System.out.print("Error: Could not find the specified Pane | in MapViewController.setApp");
      System.exit(55);
    }
    currPane.getChildren().add(poiButton);
  }

  /**
   * Displays the floor on which the poi resides and centers the map view on the
   * poi. The centering is not 100% accurate, but it is fairly close
   *
   * @param poiLocation the poi to find
   * @return the button corresponding the to the poi
   */
  public POIButton goToPOI(POILocation poiLocation) {
    System.out.println("go to " + poiLocation);

    Tab tab = goToTab((TabPane) goToTab(this.tabPane, poiLocation.getBuilding().getName()).getContent(),
        poiLocation.getFloor().getName());
    ScrollPane scrollPane = (ScrollPane) tab.getContent();

    Pair position = poiLocation.getPOI().getPosition();
    double xRatio = position.getX() / poiLocation.getFloor().getImage().getWidth();
    double yRatio = position.getY() / poiLocation.getFloor().getImage().getHeight();
    double errorX = xRatio - 0.5;
    double errorY = yRatio - 0.5;

    scrollPane.setHvalue(0.5 + (zoomBar.getValue() * ((xRatio + (errorX * 0.20)) - 0.5)));
    scrollPane.setVvalue(0.5 + (zoomBar.getValue() * ((yRatio + (errorY * 0.20)) - 0.5)));
    return getButton(poiLocation);
  }

  /**
   * Finds and selects a pane within the passed TabPane, displaying it for the
   * user
   *
   * @param tabPane the TabPane to search in
   * @param tabName the name of the Tab to find
   * @return the Tab matching tabName, null if not found
   */
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

  /**
   * Finds the pane (floor), that this poi is on
   *
   * @param poiLocation the poi to find
   * @return The pane which the poi resides in
   */
  private Pane getPane(POILocation poiLocation) {
    for (Tab buildingTab : this.tabPane.getTabs()) {
      if (buildingTab.getText().equals(poiLocation.getBuilding().getName())) {
        TabPane buildingTabPane = (TabPane) buildingTab.getContent();
        for (Tab floorTab : buildingTabPane.getTabs()) {
          if (floorTab.getText().equals(poiLocation.getFloor().getName())) {
            StackPane stack = (StackPane) ((ScrollPane) floorTab.getContent()).getContent();
            return (Pane) (stack.getChildren()).get(1);
          }
        }
      }
    }
    return null;
  }

  /**
   * Displays POIs with the specified POIType(s), and hides all other POIs
   *
   * @param selectedPOITypes a list of POITypes to filter by
   */
  public void filterList(List<POIType> selectedPOITypes) {
    for (POIType poiType : POIType.values()) {
      boolean show = selectedPOITypes.contains(poiType);
      for (POIButton poiTypeButtons : this.poiButtons[poiType.ordinal()]) {
        poiTypeButtons.setVisible(show);
      }
    }
  }

  /**
   * @return the current building being displayed in the main view
   */
  public String getBuildingTab() {
    return tabPane.getSelectionModel().getSelectedItem().getText();
  }
}
