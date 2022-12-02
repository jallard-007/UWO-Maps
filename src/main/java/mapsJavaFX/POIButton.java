package mapsJavaFX;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import maps.Application;
import maps.POI;
import maps.POILocation;
import maps.POIType;
import maps.Pair;

/**
 * Used to represent POIs visually and interactively
 */
public class POIButton extends Button {
  static Application app;
  static Slider zoom;
  final POILocation poiLocation;
  private static double startX;
  private static double startY;
  private final double imageWidth;
  private final double imageHeight;

  /**
   * Static method to set the Application object for all POIButtons
   * 
   * @param newApp the Application
   */
  public static void setApp(Application newApp) {
    app = newApp;
  }

  /**
   * Static method to set the Slider object for all POIButtons
   * 
   * @param newZoom the Slider
   */
  public static void setSlider(Slider newZoom) {
    zoom = newZoom;
  }

  /**
   * Constructor
   * 
   * @param poiLocation the poi to base the button on
   */
  public POIButton(POILocation poiLocation) {
    this.setLayoutX(poiLocation.getPOI().getPosition().getX());
    this.setLayoutY(poiLocation.getPOI().getPosition().getY());

    // requires that app has been set first
    if (app == null) {
      System.out.println("App has not been set in POIButton");
      System.exit(12);
    }
    this.poiLocation = poiLocation;
    this.imageWidth = poiLocation.getFloor().getImage().getWidth();
    this.imageHeight = poiLocation.getFloor().getImage().getHeight();

    // event handler for when the button is double clicked
    this.setOnMouseClicked(mouseEvent -> {
      if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
        if (mouseEvent.getClickCount() == 2) {
          new POIDescriptionController(this, this.poiLocation);
        }
      }
    });

    updateButtonDisplay();
  }

  /**
   * Sets the colour based on the POIType of the POI object associated with this
   * button
   */
  public void updateButtonDisplay() {
    POIType poiType = this.poiLocation.getPOI().getPOIType();
    switch (poiType) {
      case classroom -> this.setStyle("-fx-background-color: Green");
      case lab -> this.setStyle("-fx-background-color: Brown");
      case recreation -> this.setStyle("-fx-background-color: Black");
      case collaboration -> this.setStyle("-fx-background-color: Purple");
      case accessibility -> this.setStyle("-fx-background-color: Pink");
      case restaurant -> this.setStyle("-fx-background-color: Orange");
      case washroom -> this.setStyle("-fx-background-color: Yellow");
      case library -> this.setStyle("-fx-background-color: Blue");
      case custom -> this.setStyle("-fx-background-color: Red");
    }
  }

  /**
   * Saves the current position of the button back to the POI object
   * Does not allow the POI to be outside of the image
   */
  public void savePosition() {
    POI poi = this.poiLocation.getPOI();
    double x = this.getLayoutX();
    double y = this.getLayoutY();

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
  }

  /**
   * Adds the ability to be moved via holding left mouse button on the button and
   * moving the mouse
   */
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
      this.setLayoutX(this.getLayoutX() + this.getTranslateX());
      this.setLayoutY(this.getLayoutY() + this.getTranslateY());
      this.setTranslateX(0);
      this.setTranslateY(0);
    });
  }

  /**
   * Removes the ability to be moved via the mouse
   */
  public void removeDraggable() {
    Pair position = this.poiLocation.getPOI().getPosition();
    this.setLayoutX(position.getX());
    this.setLayoutY(position.getY());

    this.setOnMousePressed(e -> {
    });

    this.setOnMouseDragged(e -> {
    });
  }
}
