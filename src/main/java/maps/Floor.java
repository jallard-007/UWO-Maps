package maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Stores information regarding a floor
 */
public class Floor {
  int level;
  String name;
  String imagePath;
  Image image;

  /**
   * Each index corresponds to a POIType, for example: pois[POIType.classroom] is all the pois with
   * type POIType.classroom.
   */
  List<POI>[] pois;

  /**
   * Constructor without an image path
   * 
   * @param level integer corresponding to the floor's level
   * @param name the name of the floor
   */
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

  /**
   * Constructor with an image path. Loads the image and stores it in memory
   * 
   * @param level integer corresponding to the floor's level
   * @param name the name of the floor
   * @param imagePath absolute path to the image of this floor
   */
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


  /**
   * @return pois on the floor
   */
  public List<POI>[] getPOIS() {
    return this.pois;
  }

  /**
   * @param pois an array of Lists with POIs
   */
  public void setPOIS(List<POI>[] pois) {
    this.pois = pois;
  }

  /**
   * @return name of the floor
   */
  public String getName() {
    return name;
  }

  /**
   * @return absolute path to image
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * @return image of the floor
   */
  public Image getImage() {
    return image;
  }
}
