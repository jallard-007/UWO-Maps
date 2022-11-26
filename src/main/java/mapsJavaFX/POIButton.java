package mapsJavaFX;

import javafx.scene.control.Button;
import maps.Application;
import maps.POI;
import maps.POILocation;
import maps.POIType;

public class POIButton extends Button {
  static Application app;
  private static double startX;
  private static double startY;
  POILocation poiLocation;

  public static void setApp(Application newApp) {
    app = newApp;
  }

  public POIButton(POILocation poiLocation) {
    if (app == null) {
      System.out.println("App has not been set in POIButton");
      System.exit(12);
    }
    this.poiLocation = poiLocation;
    POIType poiType = poiLocation.getPOI().getPOIType();
    switch (poiType) {
      case classroom:
        this.setStyle("-fx-background-color: Green");
        break;

      case lab:
        this.setStyle("-fx-background-color: Brown");
        break;

      case recreation:
        this.setStyle("-fx-background-color: Black");
        break;

      case collaboration:
        this.setStyle("-fx-background-color: White");
        break;

      case accessibility:
        this.setStyle("-fx-background-color: Pink");
        break;

      case restaurant:
        this.setStyle("-fx-background-color: Orange");
        break;

      case washroom:
        this.setStyle("-fx-background-color: Yellow");
        break;

      case library:
        this.setStyle("-fx-background-color: Blue");
        break;

      case custom:
        this.setStyle("-fx-background-color: Red");
        break;

    }
    // classroom, lab, recreation, collaboration, accessibility, restaurant, washroom, library,
    // custom


    // this can be used to set an image on button
    // this.setGraphic();
  }

  public void makeDraggable() {
    this.setOnMousePressed(e -> {
      startX = e.getScreenX();
      startY = e.getScreenY();
    });

    this.setOnMouseClicked(e -> {
      System.out.println(startX + " : " + startY);
    });

    this.setOnMouseDragged(e -> {
      this.setTranslateX(e.getScreenX() - startX);
      this.setTranslateY(e.getScreenY() - startY);
    });

    this.setOnMouseReleased(e -> {
      POI poi = this.poiLocation.getPOI();
      poi.setPosition(poi.getPosition().getX() + (e.getScreenX() - startX),
          poi.getPosition().getY() + (e.getScreenY() - startY));
      this.setLayoutX(poi.getPosition().getX());
      this.setTranslateX(0);
      this.setLayoutY(poi.getPosition().getY());
      this.setTranslateY(0);
    });
  }

  public void removeDraggable() {
    this.setOnMousePressed(e -> {
    });

    this.setOnMouseClicked(e -> {
    });

    this.setOnMouseDragged(e -> {

    });

    this.setOnMouseReleased(e -> {
    });
  }
}
