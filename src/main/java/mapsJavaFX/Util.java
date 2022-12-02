package mapsJavaFX;

import maps.Application;

public class Util {
  /**
   * Sets all controllers, adding them to the ControllerMediator and/or setting
   * the Application object
   * 
   * @param controller the main controller
   * @param app        the application
   */
  public static void setControllers(MainController controller, Application app) {
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    ControllerMediator.getInstance()
        .registerFavouritesController(controller.getFavouritesController());
    ControllerMediator.getInstance().registerSearchPOIController(controller.getSearchPOIController());

    POIButton.setApp(app);
    POIDescriptionController.setApp(app);
    AddPOIController.setApp(app);

    controller.getMapViewController().setApp(app);
    controller.getSearchPOIController().setApp(app);
    controller.getFavouritesController().setApp(app);
  }
}
