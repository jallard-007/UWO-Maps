package mapsJavaFX;

import maps.Application;
import maps.POILocation;

public class ControllerMediator {
  private MapViewController mapViewController;
  private FavouritesController favouritesController;
  private Application app;

  void registerMapViewController(MapViewController controller) {
    mapViewController = controller;
  }

  void registerFavouritesController(FavouritesController controller) {
    favouritesController = controller;
  }

  void registerApplication(Application app) {
    this.app = app;
  }

  Application getApplication() {
    return app;
  }

  void mapViewControllerGoToPOI(POILocation poiLocation) {
    mapViewController.goToPOI(poiLocation);
  }

  void addFavourite(POILocation poiLocation) {
    favouritesController.addFavourite(poiLocation);
  }

  void removeFavourite(POILocation poiLocation) {
    favouritesController.removeFavourite(poiLocation);
  }

  private ControllerMediator() {}

  public static ControllerMediator getInstance() {
    return ControllerMediatorHolder.INSTANCE;
  }

  private static class ControllerMediatorHolder {
    private static final ControllerMediator INSTANCE = new ControllerMediator();
  }
}
