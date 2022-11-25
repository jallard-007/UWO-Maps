package maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Floor {
  int level;
  String name;
  String imagePath;
  Image image;
  List<POI>[] pois;

  @SuppressWarnings("unchecked")
  Floor(int level, String name) {
    this.level = level;
    this.name = name;
    this.imagePath = "";
    this.image = null;
    this.pois = (ArrayList<POI>[]) new ArrayList[POIType.values().length];
    for (int i = 0; i < POIType.values().length; ++i) {
      this.pois[i] = new ArrayList<>();
    }
  }

  Floor(int level, String name, String imagePath) {
    this(level, name);
    this.imagePath = imagePath;
    this.image = new Image(new File(imagePath).toURI().toString());
  }

  public String toString() {
    StringBuilder str = new StringBuilder(this.name);
    for (List<POI> poiList : this.pois) {
      for (POI poi : poiList) {
        str.append("\n\t\tPOIRoomNum: ").append(poi.toString());
      }
    }
    return str.toString();
  }


  public List<POI>[] getPOIS() {
    return this.pois;
  }

  public void setPOIS(List<POI>[] pois) {
    this.pois = pois;
  }

  public String getName() {
    return name;
  }

  public String getImagePath() {
    return imagePath;
  }

  public Image getImage() {
    return image;
  }
}
