package mapsJavaFX;

import java.util.List;

import javafx.scene.control.Tab;
import maps.Application;
import maps.Building;
import maps.Floor;
import maps.POILocation;
import maps.POIType;

/**
 * Class that mediates the interaction between different controllers.
 */
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

  String getBuildingTab() {
    return mapViewController.getBuildingTab();
  }

  public Tab getBuildingTabObject() {
    return mapViewController.getBuildingTabObject();
  }

  public Tab getFloorTab() {
    return mapViewController.getFloorTab();
  }

  public void removeTab(Tab tab) {
    mapViewController.removeTab(tab);
  }

  public void addBuildingTab(Building building) {
    mapViewController.addBuildingTab(building);
  }

  public void removeFloorTab(Tab tab) {
    mapViewController.removeFloorTab(tab);
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

  void updateButtonStorage(POIType oldType, POIType newType, POIButton poiButton) {
    mapViewController.updateButtonStorage(oldType, newType, poiButton);
  }

  void addPOIButton(POIButton poiButton, POILocation poiLocation) {
    mapViewController.addButton(poiButton, poiLocation);
  }

  void addFloorTab(Tab building, Floor floor) {
    mapViewController.addFloorTab(building, floor);
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
