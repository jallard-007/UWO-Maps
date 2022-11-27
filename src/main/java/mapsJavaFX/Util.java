package mapsJavaFX;

import maps.Application;

public class Util {
  public static void setControllers(MainController controller, Application app) {
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    ControllerMediator.getInstance().registerFavouritesController(controller.getFavouritesController());
    ControllerMediator.getInstance().registerWeatherController(controller.getWeatherController());
    controller.getSearchPOIController().setApp(app);
    controller.getMapViewController().setApp(app);
    controller.getFavouritesController().setApp(app);
    controller.getWeatherController().setApp(app);
  }
}
