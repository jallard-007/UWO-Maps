package uwo.group2;

public class POILocation {
  Building building;
  Floor floor;
  POI poi;

  POILocation(Building building, Floor floor, POI poi) {
    this.building = building;
    this.floor = floor;
    this.poi = poi;
  }

  public String toString() {
    return building.name + " | " + floor.name + " | " + poi.roomNumber;
  }
}