package maps;

import java.util.ArrayList;
import java.util.List;

public class Floor {
  int level;
  String name;
  String imagePath;
  List<POI>[] pois;

  @SuppressWarnings("unchecked")
  Floor(int level, String name) {
    this.level = level;
    this.name = name;
    this.imagePath = "";
    this.pois = (ArrayList<POI>[]) new ArrayList[POIType.values().length];
    for (int i = 0; i < POIType.values().length; ++i) {
      this.pois[i] = new ArrayList<>();
    }
  }

  Floor(int level, String name, String imagePath) {
    this(level, name);
    this.imagePath = imagePath;
  }

  public String toString() {
    StringBuilder str = new StringBuilder(this.name);
    for (List<POI> poiList : this.pois) {
      for (POI poi : poiList)
        str.append("\n\t\tPOIRoomNum: ").append(poi.toString());
    }
    return str.toString();
  }

  public String getName() {
    return name;
  }

  public String getImagePath() {
    return imagePath;
  }
}