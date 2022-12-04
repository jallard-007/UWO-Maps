package mapsJavaFX;

import maps.Application;
import mapsJavaFX.editFeatures.*;

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
    EditBuildingController.setApp(app);
    EditFloorController.setApp(app);
    AddFloorController.setApp(app);
    AddBuildingController.setApp(app);
    DeleteBuildingController.setApp(app);
    DeleteFloorController.setApp(app);

    // set app in the main controller
    controller.setApp(app);

    controller.getMapViewController().setApp(app);
    controller.getSearchPOIController().setApp(app);
    controller.getFavouritesController().setApp(app);
  }
}
