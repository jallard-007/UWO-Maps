module mapsJavaFX {
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive org.json;

  opens mapsJavaFX to javafx.fxml;
  exports mapsJavaFX;
  exports maps;
}