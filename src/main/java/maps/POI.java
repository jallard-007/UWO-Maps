package maps;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents a POI. Room number, type, and position are the minimum required to create a poi
 */
public class POI {
  String roomNumber;
  POIType type;
  String name;
  Integer capacity;
  Pair position;
  String information;
  String hoursOfOperation;


  /**
   * @param jsonPOI the json representation of a poi
   */
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

  /**
   * @param roomNumber room number
   * @param poiType poi type
   * @param position coordinates of poi
   */
  public POI(String roomNumber, POIType poiType, Pair position) {
    this.roomNumber = roomNumber;
    this.position = position;
    this.type = poiType;
  }

  public void setPosition(double x, double y) {
    this.position.setX(x);
    this.position.setY(y);
  }

  /**
   * @param roomNumber room number of POI
   */
  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  /**
   * @param name name of POI
   */
  public void setName(String name) {
    this.name = name;
  }

  public void setType(POIType type) {
    this.type = type;
  }

  /**
   * @param information information about the POI
   */


  public void setInformation(String information) {
    this.information = information;
  }

  /**
   * @param capacity capacity of the POI
   */
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  /**
   * @param hoursOfOperation hours of operation
   */
  public void setHoursOfOperation(String hoursOfOperation) {
    this.hoursOfOperation = hoursOfOperation;
  }

  /**
   * @return a json object representation of this POI
   */
  public JSONObject toJSON() {
    JSONObject jsonPOI = new JSONObject();
    jsonPOI.put("roomNum", roomNumber);
    jsonPOI.put("type", type.name());
    jsonPOI.put("position", position.getPair());
    if (name != null) {
      jsonPOI.put("name", name);
    }
    if (capacity != null) {
      jsonPOI.put("capacity", capacity);
    }
    if (information != null) {
      jsonPOI.put("information", information);
    }
    if (hoursOfOperation != null) {
      jsonPOI.put("hours", hoursOfOperation);
    }
    return jsonPOI;
  }

  /**
   * Creates a json representation of a poi with custom type
   * 
   * @param building the building that this poi resides in
   * @param floor the floor that this poi is on
   * @return a json object representation of this POI to be stored in a users file
   */
  public JSONObject createJSONObjectOfCustomPOI(Building building, Floor floor) {
    if (type != POIType.custom) {
      return null;
    }
    JSONObject jsonPOI = this.toJSON();
    jsonPOI.put("building", building.getName());
    jsonPOI.put("floor", floor.getName());
    return jsonPOI;
  }

  public String toString() {
    return roomNumber;
  }

  /**
   * @return the room number
   */
  public String getRoomNumber() {
    return roomNumber;
  }

  /**
   * @return the name of the room if its set, otherwise the room number
   */
  public String getRoomNameOrNumber() {
    if (this.name != null) {
      return this.name;
    }
    return this.roomNumber;
  }

  /**
   * @return the name of the room
   */
  public String getName() {
    return name;
  }

  /**
   * @return the enum value of this POI's type
   */
  public POIType getPOIType() {
    return type;
  }

  /**
   * @return information regarding the POI
   */
  public String getInformation() {
    return information;
  }

  /**
   * @return capacity of the POI
   */
  public Integer getCapacity() {
    return capacity;
  }

  /**
   * @return hours of operation
   */
  public String getHoursOfOperation() {
    return hoursOfOperation;
  }

  /**
   * @return coordinates of the POI
   */
  public Pair getPosition() {
    return position;
  }
}
