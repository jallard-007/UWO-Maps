package mapsJavaFX;

import javafx.scene.control.Tab;
import maps.*;

import java.util.List;

/**
 * Allows interaction between different controllers without having to store a reference to a
 * controller within another controller. Also holds a reference to the Application object so that
 * data does not need to be reloaded when changing scenes
 */
public class ControllerMediator {
  private MapViewController mapViewController;
  private FavouritesController favouritesController;
  private SearchPOIController searchPOIController;
  private Application app;

  private ControllerMediator() {
  }

  /**
   * @return the instance of this class
   */
  public static ControllerMediator getInstance() {
    return ControllerMediatorHolder.INSTANCE;
  }

  /**
   * Registers a MapViewController
   *
   * @param controller the controller to register
   */
  void registerMapViewController(MapViewController controller) {
    mapViewController = controller;
  }

  /**
   * Registers a FavouritesController
   *
   * @param controller the controller to register
   */
  void registerFavouritesController(FavouritesController controller) {
    favouritesController = controller;
  }

  /**
   * Registers a SearchPOIController
   *
   * @param controller the controller to register
   */
  void registerSearchPOIController(SearchPOIController controller) {
    searchPOIController = controller;
  }

  /**
   * Registers the Application object
   *
   * @param app the app to register
   */
  void registerApplication(Application app) {
    this.app = app;
  }

  /**
   * @return the registered Application object
   */
  public Application getApplication() {
    return app;
  }

  /**
   * Tells the registered MapViewController to navigate to a poi
   *
   * @param poiLocation the poi to go to
   * @return the POIButton object associated with the poi
   */
  public POIButton mapViewControllerGoToPOI(POILocation poiLocation) {
    return mapViewController.goToPOI(poiLocation);
  }

  /**
   * Tells the registered MapViewController to filter pois
   *
   * @param selectedPOIType a list of POITypes to show
   */
  void mapViewControllerFilterList(List<POIType> selectedPOIType) {
    mapViewController.filterList(selectedPOIType);
  }

  /**
   * @return the name of the currently selected building tab
   */
  public String mapViewControllerGetBuildingTab() {
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

  /**
   * Tells the registered FavouritesController to refresh it's list
   */
  public void favouritesControllerRefreshList() {
    favouritesController.refreshList();
  }

  /**
   * Tells the registered SearchPOIController to refresh it's list
   */
  public void searchPOIControllerRefreshList() {
    searchPOIController.refreshList();
  }

  /**
   * Tells the registered MapviewController to remove the POIButton associated with poiLocation
   *
   * @param poiLocation the poi to remove
   */
  void mapViewControllerRemovePOIButton(POILocation poiLocation) {
    mapViewController.removeButton(poiLocation);
  }

  /**
   * Updates where the button is stored
   *
   * @param oldType   old POIType
   * @param newType   new POIType
   * @param poiButton the button to move in storage
   */
  public void mapViewControllerUpdateButtonStorage(POIType oldType, POIType newType,
      POIButton poiButton) {
    mapViewController.updateButtonStorage(oldType, newType, poiButton);
  }

  /**
   * Tells the registered MapViewController to add poiButton to it's approriate floor
   *
   * @param poiButton the button to add
   */
  public void mapViewControllerAddPOIButton(POIButton poiButton) {
    mapViewController.addButton(poiButton);
  }

  public void addFloorTab(Tab building, Floor floor) {
    mapViewController.addFloorTab(building, floor);
  }

  /**
   * Creates an instance of this class
   */
  private static class ControllerMediatorHolder {
    private static final ControllerMediator INSTANCE = new ControllerMediator();
  }
}
