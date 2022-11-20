package maps;

public class POI {
  String roomNumber;
  String name;
  POIType type;
  Pair position;

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

  public String getRoomNumber() {
    return toString();
  }
}
