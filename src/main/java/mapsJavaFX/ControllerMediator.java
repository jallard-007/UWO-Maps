package mapsJavaFX;

import java.util.List;
import maps.Application;
import maps.POILocation;
import maps.POIType;

public class ControllerMediator {
  private MapViewController mapViewController;
  private FavouritesController favouritesController;
  private SearchPOIController searchPOIController;
  private Application app;

  void registerMapViewController(MapViewController controller) {
    mapViewController = controller;
  }

  void registerFavouritesController(FavouritesController controller) {
    favouritesController = controller;
  }

  void registerSearchPOIController(SearchPOIController controller) {
    searchPOIController = controller;
  }

  void registerApplication(Application app) {
    this.app = app;
  }

  Application getApplication() {
    return app;
  }

  POIButton mapViewControllerGoToPOI(POILocation poiLocation) {
    return mapViewController.goToPOI(poiLocation);
  }

  void filterList(List<POIType> selectedPOIType) {
    mapViewController.filterList(selectedPOIType);
  }

  void refreshFavouritesList() {
    favouritesController.refreshList();
  }

  void refreshSearchList() {
    searchPOIController.refreshList();
  }

  void removePOIButton(POILocation poiLocation) {
    mapViewController.removeButton(poiLocation);
  }

  private ControllerMediator() {
  }

  public static ControllerMediator getInstance() {
    return ControllerMediatorHolder.INSTANCE;
  }

  private static class ControllerMediatorHolder {
    private static final ControllerMediator INSTANCE = new ControllerMediator();
  }
}
