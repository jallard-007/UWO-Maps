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
  public JSONObject createJSONObjectOfCustomPOI(POILocation poiLocation) {
    JSONObject jsonPOI = new JSONObject();
    jsonPOI.put("building", poiLocation.building.getName());
    jsonPOI.put("floor", poiLocation.floor.getName());
    jsonPOI.put("roomNum", poiLocation.poi.getRoomNumber());
    jsonPOI.put("type", "custom");
    jsonPOI.put("position", position.getPair());
    if (capacity != null) {
      jsonPOI.put("capacity", capacity);
    }
    if (information != null) {
      jsonPOI.put("information", information);
    }
    if (hoursOfOperation != null) {
      jsonPOI.put("capacity", hoursOfOperation);
    }
    return jsonPOI;
  }

  public String toString() {
    return roomNumber;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public String getRoomNameOrNumber() {
    if (this.name != null) {
      return this.name;
    }
    return this.roomNumber;
  }

  public String getName() {
    return name;
  }

  public String getPOIType() {
    return type.toString();
  }

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
