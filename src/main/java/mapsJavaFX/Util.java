package mapsJavaFX;

import maps.Application;

public class Util {
  public static void setControllers(MainController controller, Application app) {
    ControllerMediator.getInstance().registerMapViewController(controller.getMapViewController());
    ControllerMediator.getInstance()
        .registerFavouritesController(controller.getFavouritesController());
    ControllerMediator.getInstance().registerSearchPOIController(controller.getSearchPOIController());

    POIButton.setApp(app);
    POIDescriptionController.setApp(app);
    AddPOIController.setApp(app);
    editBuildingController.setApp(app);
    editFloorController.setApp(app);

    //set app in the main controller
    controller.setApp(app);

    controller.getMapViewController().setApp(app);
    controller.getSearchPOIController().setApp(app);
    controller.getFavouritesController().setApp(app);
  }
}
