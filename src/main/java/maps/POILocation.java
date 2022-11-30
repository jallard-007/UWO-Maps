package maps;

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
  POILocation(Building building, Floor floor, POI poi) {
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
    return floor.pois[poi.type.ordinal()].remove(poi);
  }

  public String toString() {
    return poi.getRoomNameOrNumber() + " | " + building.getName() + " | " + floor.getName();
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
