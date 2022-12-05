package maps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information regrading the building
 */
public class Building {
  final List<Floor> floors;
  private String name;

  /**
   * Default constructor
   *
   * @param name The name of the building
   */
  public Building(String name) {
    this.name = name;
    this.floors = new ArrayList<>();
  }

  /**
   * @return name of the building
   */
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return floors in the building
   */
  public List<Floor> getFloors() {
    return floors;
  }

  public String toString() {
    return this.name;
  }

  /**
   * @return a json object representation of this building
   */
  public JSONObject toJSON() {
    JSONObject jsonBuilding = new JSONObject();
    jsonBuilding.put("buildingName", this.name);
    JSONArray jsonFloors = new JSONArray();
    for (Floor floor : floors) {
      jsonFloors.put(floor.toJSON());
    }
    jsonBuilding.put("floors", jsonFloors);
    return jsonBuilding;
  }

  /**
   * Gets the floor matching the input string
   *
   * @param floorName the name of the floor
   * @return floor object with name matching floorName, null if no match
   */
  public Floor getMatchingFloor(String floorName) {
    for (Floor floor : this.floors) {
      if (floor.getName().equals(floorName)) {
        return floor;
      }
    }
    return null;
  }
}
