package maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.image.Image;

/**
 * Stores information regarding a floor
 */
public class Floor {
  private int level;
  private String name;
  String imagePath;
  Image image;

  /**
   * Each index corresponds to a POIType, for example: pois[POIType.classroom] is
   * all the pois with
   * type POIType.classroom.
   */
  final List<POI>[] pois;

  /**
   * Constructor without an image path
   * 
   * @param level integer corresponding to the floor's level
   * @param name  the name of the floor
   */
  @SuppressWarnings("unchecked")
  Floor(int level, String name) {
    this.level = level;
    this.name = name;
    this.imagePath = "";
    this.image = null;
    // Store POIs of each floor are stored by their POIType
    this.pois = (ArrayList<POI>[]) new ArrayList[POIType.values().length];
    for (int i = 0; i < POIType.values().length; ++i) {
      this.pois[i] = new ArrayList<>();
    }
  }

  /**
   * Constructor with an image path. Loads the image and stores it in memory
   * 
   * @param level     integer corresponding to the floor's level
   * @param name      the name of the floor
   * @param imagePath relative path to the image of this floor
   */
  public Floor(int level, String name, String imagePath) {
    this(level, name);
    this.imagePath = imagePath;
    this.image = new Image(new File(Util.getRootPath() + imagePath).toURI().toString());
  }

  /**
   * @return string representation of the floor's POI list
   */
  public String toString() {
    return this.name;
  }

  /**
   * Adds the POIs of a floor into the floor's POI list
   * 
   * @param poi POI to be added to the floor's POIs list
   */
  public void addPOI(POI poi) {
    this.pois[poi.type.ordinal()].add(poi);
  }

  /**
   * Update the storage of a POI in the floor's POI list if its POI type was
   * recently updated
   * 
   * @param oldType old POI type
   * @param newType new POI type
   * @param poi     POI to be updated
   */
  public void updatePOIStorage(POIType oldType, POIType newType, POI poi) {
    this.pois[oldType.ordinal()].remove(poi);
    this.pois[newType.ordinal()].add(poi);
  }

  /**
   * @return a json object representation of this floor
   */
  public JSONObject toJSON() {
    JSONObject jsonFloor = new JSONObject();
    jsonFloor.put("map", this.imagePath);
    jsonFloor.put("levelName", this.name);
    jsonFloor.put("level", this.level);
    JSONArray jsonPOIs = new JSONArray();
    for (List<POI> poiList : this.pois) {
      for (POI poi : poiList) {
        jsonPOIs.put(poi.toJSON());
      }
    }
    jsonFloor.put("pois", jsonPOIs);
    return jsonFloor;
  }

  /**
   * @return name of the floor
   */
  public String getName() {
    return name;
  }

  /**
   * set name of the floor
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return level of the floor
   */
  public int getLevel() {
    return level;
  }

  /**
   * set level of the floor
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * @return image of the floor
   */
  public Image getImage() {
    return image;
  }
}
