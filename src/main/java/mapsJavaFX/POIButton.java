package mapsJavaFX;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import maps.Application;
import maps.POI;
import maps.POILocation;
import maps.POIType;

public class POIButton extends Button {
  static Application app;
  static Slider zoom;
  final POILocation poiLocation;
  private static double startX;
  private static double startY;
  private final double imageWidth;
  private final double imageHeight;


  public static void setApp(Application newApp) {
    app = newApp;
  }

  public static void setSlider(Slider newZoom) {
    zoom = newZoom;
  }

  public POIButton(POILocation poiLocation) {
    if (app == null) {
      System.out.println("App has not been set in POIButton");
      System.exit(12);
    }
    this.poiLocation = poiLocation;
    POIType poiType = poiLocation.getPOI().getPOIType();
    this.imageWidth = poiLocation.getFloor().getImage().getWidth();
    this.imageHeight = poiLocation.getFloor().getImage().getHeight();


    this.setOnMouseClicked(mouseEvent -> {
      if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
        if (mouseEvent.getClickCount() == 2) {
          new POIDescriptionController(this.poiLocation);
        }
      }
    });

    switch (poiType) {
      case classroom -> this.setStyle("-fx-background-color: Green");
      case lab -> this.setStyle("-fx-background-color: Brown");
      case recreation -> this.setStyle("-fx-background-color: Black");
      case collaboration -> this.setStyle("-fx-background-color: White");
      case accessibility -> this.setStyle("-fx-background-color: Pink");
      case restaurant -> this.setStyle("-fx-background-color: Orange");
      case washroom -> this.setStyle("-fx-background-color: Yellow");
      case library -> this.setStyle("-fx-background-color: Blue");
      case custom -> this.setStyle("-fx-background-color: Red");
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

    this.setOnMouseDragged(e -> {
      this.setTranslateX((e.getScreenX() - startX) / zoom.getValue());
      this.setTranslateY((e.getScreenY() - startY) / zoom.getValue());
    });

    this.setOnMouseReleased(e -> {
      POI poi = this.poiLocation.getPOI();
      double x = poi.getPosition().getX() + this.getTranslateX();
      double y = poi.getPosition().getY() + this.getTranslateY();

      if (x < 0) {
        x = 0;
      } else if (x > imageWidth) {
        x = imageWidth - 20;
      }
      if (y < 0) {
        y = 0;
      } else if (y > imageHeight) {
        y = imageHeight - 30;
      }
      poi.setPosition(x, y);
      this.setLayoutX(poi.getPosition().getX());
      this.setLayoutY(poi.getPosition().getY());
      this.setTranslateX(0);
      this.setTranslateY(0);
    });
  }

  public void removeDraggable() {
    this.setOnMousePressed(e -> {
    });

    this.setOnMouseDragged(e -> {
    });

    this.setOnMouseReleased(e -> {
    });
  }
}
