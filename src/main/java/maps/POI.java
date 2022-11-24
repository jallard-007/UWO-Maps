package maps;

import org.json.JSONArray;
import org.json.JSONObject;

public class POI {
  String roomNumber;
  POIType type;
  String name;
  Integer capacity;
  Pair position;
  String information;
  String hoursOfOperation;

  POI(JSONObject jsonPOI) {
    JSONArray jsonPosition = jsonPOI.getJSONArray("position");
    this.position = new Pair(jsonPosition.getInt(0), jsonPosition.getInt(1));
    this.roomNumber = jsonPOI.getString("roomNum");
    this.type = POIType.valueOf(jsonPOI.getString("type"));
    // TODO: make POI including HoursOfOperation, Information, Name

    // Add capacity, hours of operation, information, and common name if it exists.
    if (jsonPOI.has("name")) {
      this.name = jsonPOI.getString("name");
    }
    if (jsonPOI.has("capacity")) {
      this.capacity = jsonPOI.getInt("capacity");
    }
    if (jsonPOI.has("hours")) {
     this.hoursOfOperation = jsonPOI.getString("hours");
    }
    if (jsonPOI.has("information")) {
      this.information = jsonPOI.getString("information");
    }
  }

  POI(String roomNumber, String name, POIType poiType, Pair position) {
    this.roomNumber = roomNumber;
    this.name = name;
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

  public Pair getPosition() {
    return position;
  }
}
