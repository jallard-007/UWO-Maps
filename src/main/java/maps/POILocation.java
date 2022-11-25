package maps;

/**
 * Stores the location of a poi, so it can be easily navigated to when selected from a list
 */
public class POILocation {
  Building building;
  Floor floor;
  POI poi;

  /**
   * @param building the building
   * @param floor the floor
   * @param poi the poi
   */
  POILocation(Building building, Floor floor, POI poi) {
    this.building = building;
    this.floor = floor;
    this.poi = poi;
  }

  public String toString() {
    return building.getName() + " | " + floor.getName() + " | " + poi.getRoomNameOrNumber();
  }

  /**
   * @return the building
   */
  public Building getBuilding() {
    return building;
  }

  /**
   * @return the floor
   */
  public Floor getFloor() {
    return floor;
  }

  /**
   * @return the poi
   */
  public POI getPOI() {
    return poi;
  }
}
