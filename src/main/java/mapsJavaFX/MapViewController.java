package mapsJavaFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import maps.Application;
import maps.Building;
import maps.Floor;
import maps.POI;
import maps.POILocation;
import maps.User;

public class MapViewController {
  @FXML
  private TabPane tabPane;
  private POIDescriptionController poiDescriptionController;
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

        // Create the GridPane
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        // Create empty grid to allow POIs to be placed on map
        for (int i = 0; i < image.getWidth() / 25 / 2; i++) {
          ColumnConstraints column = new ColumnConstraints(25);
          grid.getColumnConstraints().add(column);

        }
        for (int j = 0; j < image.getHeight() / 25 / 2; j++) {
          RowConstraints row = new RowConstraints(25);
          grid.getRowConstraints().add(row);
        }

        // Add the POIs to the gridPane
        for (POILocation poiLocation : app.getPoiLocations()) {
          if (poiLocation.getFloor().getName() == floor.getName()
              && poiLocation.getBuilding().getName() == building.getName()) {
            Button btn = new Button("P");
            btn.setOnAction(actionEvent -> {
              // temporary callback function, will trigger the POPUP when clicked

              // poiDescriptionController(app.getUser(),poiLocation);
              System.out.println(poiLocation.getPOI().getInformation());
              System.out.println("X coordinate is:" + poiLocation.getPOI().getPosition().getX());
            });
            btn.setWrapText(true);
            // btn.setMaxSize(25, 25);
            // btn.setMinSize(25, 25);
            GridPane.setConstraints(btn, poiLocation.getPOI().getPosition().getX(),
                poiLocation.getPOI().getPosition().getY());
            grid.getChildren().add(btn);
          } ;
        }


        // Add floor PNGs into a scrollPane so users can pan through the maps; set dimensions to
        // half the original image size
        StackPane stackPane = new StackPane(); // Stack to hold both the imageview and gridpane
        ScrollPane scrollPane = new ScrollPane();

        imageView.setFitHeight(image.getHeight() / 2);
        imageView.setFitWidth(image.getWidth() / 2);

        scrollPane.setContent(imageView);
        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(grid);
        scrollPane.setContent(stackPane);

        // Add scrollPane to the tab of each floor
        floorTab.setContent(scrollPane);
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
