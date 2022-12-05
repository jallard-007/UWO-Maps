package mapsJavaFX.editFeatures;

import javafx.stage.Stage;
import maps.Application;

/**
 *
 */
public class EditHelper {
  private static Stage stage;
  private static Application app;

  /**
   * @return current application stage
   */
  public static Application getApp() {
    return app;
  }

  /**
   * Called to set up the app
   *
   * @param newApp referring to the map application
   */
  public static void setApp(Application newApp) {
    app = newApp;
  }

  /**
   * @return current application stage
   */
  public static Stage getStage() {
    return stage;
  }

  /**
   * Sets stage of application
   *
   * @param newStage stage to be set
   */
  public static void setStage(Stage newStage) {
    stage = newStage;
  }

}
