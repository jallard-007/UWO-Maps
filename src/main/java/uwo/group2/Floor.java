package uwo.group2;

import java.util.ArrayList;
import java.util.List;

public class Floor {
  int level;
  String name;
  String imagePath;
  List<POI> pois;

  Floor(int level, String name) {
    this.level = level;
    this.name = name;
    this.imagePath = "";
    this.pois = new ArrayList<>();
  }

  Floor(int level, String name, String imagePath) {
    this.level = level;
    this.name = name;
    this.imagePath = imagePath;
    this.pois = new ArrayList<>();
  }

  Floor(int level, String name, String imagePath, List<POI> POIs) {
    this.level = level;
    this.name = name;
    this.imagePath = imagePath;
    this.pois = POIs;
  }

  public String toString() {
    StringBuilder str = new StringBuilder(this.name);
    for (POI poi : this.pois) {
      str.append("\n\t\tPOIRoomNum: ").append(poi.toString());
    }
    return str.toString();
  }

}