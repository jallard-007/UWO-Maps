package maps;

import java.util.Arrays;

/**
 * Stores the location of a poi, so it can be easily navigated to when selected from a list
 */
public class POILocation {
  final Building building;
  final Floor floor;
  final POI poi;

  /**
   * @param building the building
   * @param floor the floor
   * @param poi the poi
   */
  public POILocation(Building building, Floor floor, POI poi) {
    this.building = building;
    this.floor = floor;
    this.poi = poi;
  }

  /**
   * Removes a POI from its floor. The POI itself is not deleted
   *
   * @return true if the removal was successful, false otherwise
   */
  public boolean removePOI() {
    System.out.println(Arrays.toString(floor.pois));
    boolean b = floor.pois[poi.type.ordinal()].remove(poi);
    System.out.println(Arrays.toString(floor.pois));
    return b;
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
