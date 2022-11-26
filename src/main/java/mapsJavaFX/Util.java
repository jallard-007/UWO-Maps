package mapsJavaFX;

import maps.Application;

public class Util {
  public static void setControllers(MainController controller, Application app) {
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    ControllerMediator.getInstance()
        .registerFavouritesController(controller.getFavouritesController());
    ControllerMediator.getInstance()
        .registerPOIDescriptionController(controller.getPOIDescriptionController());
    ControllerMediator.getInstance().registerPOITypesController(controller.getPOITypesController());
    POIButton.setApp(app);
    controller.getSearchPOIController().setApp(app);
    controller.getMapViewController().setApp(app);
    controller.getFavouritesController().setApp(app);
    POIDescriptionController.setApp(app);
    controller.getPOITypesController().setApp(app);
  }
}
