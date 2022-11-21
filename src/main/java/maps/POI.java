package maps;

public class POI {
  String roomNumber;
  POIType type;
  String name;
  Integer capacity;
  Pair position;
  String information;
  String hoursOfOperation;

  POI(String roomNumber, POIType poiType, Pair position) {
    this.roomNumber = roomNumber;
    this.name = "";
    this.position = position;
    this.type = poiType;
  }




  /**
   * SETTERS
   */

  public void setName(String name) {
    this.name = name;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public void setHoursOfOperation(String hoursOfOperation) {
    this.hoursOfOperation = hoursOfOperation;
  }

  /**
   * GETTERS
   */

  public String toString() {
    return roomNumber;
  }

  public String getRoomNumber() {
    return toString();
  }

  public String getName(){return name;}

  public String getPOIType(){return type.toString();}

  public String getInformation() {
    return information;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public String getHoursOfOperation() {
    return hoursOfOperation;
  }
}
