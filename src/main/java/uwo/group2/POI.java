package uwo.group2;

public class POI {
  //private int ID;
  String roomNumber;
  String name;
  POIType type;
  Pair position;

  // might change type of metaData to a custom 'MetaData' type
  POI(String roomNumber, POIType poiType, Pair position) {
    this.roomNumber = roomNumber;
    this.name = "";
    this.position = position;
    this.type = poiType;
  }
  POI(String roomNumber, String name, POIType poiType, Pair position) {
    this.roomNumber = roomNumber;
    this.name = name;
    this.position = position;
    this.type = poiType;
  }

  public String toString() {
    return roomNumber;
  }
}
