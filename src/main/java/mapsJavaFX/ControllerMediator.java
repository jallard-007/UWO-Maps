package mapsJavaFX;

import java.util.List;
import maps.Application;
import maps.POILocation;
import maps.POIType;

/**
 * Allows interaction between different controllers without having
 * to store a reference to a controller within another controller.
 * Also holds a reference to the Application object so that data does not
 * need to be reloaded when changing scenes
 */
public class ControllerMediator {
  private MapViewController mapViewController;
  private FavouritesController favouritesController;
  private SearchPOIController searchPOIController;
  private Application app;

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
  POIButton mapViewControllerGoToPOI(POILocation poiLocation) {
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
  String mapViewControllerGetBuildingTab() {
    return mapViewController.getBuildingTab();
  }

  /**
   * Tells the registered FavouritesController to refresh it's list
   */
  void favouritesControllerRefreshList() {
    favouritesController.refreshList();
  }

  /**
   * Tells the registered SearchPOIController to refresh it's list
   */
  void searchPOIControllerRefreshList() {
    searchPOIController.refreshList();
  }

  /**
   * Tells the registered MapviewController to remove the POIButton associated
   * with poiLocation
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
  void mapViewControllerUpdateButtonStorage(POIType oldType, POIType newType, POIButton poiButton) {
    mapViewController.updateButtonStorage(oldType, newType, poiButton);
  }

  /**
   * Tells the registered MapViewController to add poiButton to it's approriate
   * floor
   * 
   * @param poiButton the button to add
   */
  void mapViewControllerAddPOIButton(POIButton poiButton) {
    mapViewController.addButton(poiButton);
  }

  private ControllerMediator() {
  }

  /**
   * @return the instance of this class
   */
  public static ControllerMediator getInstance() {
    return ControllerMediatorHolder.INSTANCE;
  }

  /**
   * Creates an instance of this class
   */
  private static class ControllerMediatorHolder {
    private static final ControllerMediator INSTANCE = new ControllerMediator();
  }
}
