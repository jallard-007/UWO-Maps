package mapsJavaFX;

import maps.Application;

public class Util {
  public static void setControllers(MainController controller, Application app) {
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    ControllerMediator.getInstance()
        .registerFavouritesController(controller.getFavouritesController());
    controller.getSearchPOIController().setPOIs(app.getPoiLocations());
    controller.getSearchPOIController().setUser(app.getUser());
    controller.getMapViewController().setBuildings(app.getBuildings());
    controller.getFavouritesController().setUser(app.getUser());
  }
}
