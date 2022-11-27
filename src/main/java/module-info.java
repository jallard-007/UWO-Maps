module mapsJavaFX {
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires org.json;
  requires json.simple;

  opens mapsJavaFX to javafx.fxml;

  exports mapsJavaFX;
  exports maps;
}
