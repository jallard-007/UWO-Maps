module mapsJavaFX {
  requires transitive javafx.graphics;
  requires transitive javafx.controls;
  requires javafx.fxml;
  requires transitive org.json;

  opens mapsJavaFX to javafx.fxml;
  opens mapsJavaFX.welcomePage to javafx.fxml;

  exports mapsJavaFX;
  exports mapsJavaFX.welcomePage;
  exports maps;
}
