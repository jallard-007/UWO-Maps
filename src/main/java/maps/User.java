package maps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a user
 */
public class User {
  private String username;
  private String password;
  private UserType type;
  private List<POILocation> favourites;

  /**
   * @param username username
   * @param password password
   * @param type type of user
   */
  public User(String username, String password, UserType type) {
    this.username = username;
    this.password = password;
    this.type = type;
    this.favourites = new ArrayList<>();
  }

  /**
   * @param jsonUser json object representation of a user
   */
  public User(JSONObject jsonUser) {
    this(jsonUser.getString("username"), jsonUser.getString("password"),
        UserType.valueOf(jsonUser.getString("userType")));
  }

  /**
   * Adds a poi to favourites
   * 
   * @param poiLocation poi location to add to favourites
   */
  public void addFavourite(POILocation poiLocation) {
    favourites.add(poiLocation);
  }

  /**
   * Removes a poi from favourites
   * 
   * @param poiLocation poi location to remove from favourites
   */
  public void removeFavourites(POILocation poiLocation) {
    favourites.remove(poiLocation);
  }

  /**
   * Finds the index of a favourite poi
   * 
   * @param poiLocation object to search for
   * @return index of poiLocation in favourites, -1 if it is not in the list
   */
  public int indexOfFavourite(POILocation poiLocation) {
    return favourites.indexOf(poiLocation);
  }

  /**
   * Saves the user object to disk as json
   * 
   * @param customPOIs a list of custom POIs created by the user
   */
  public void saveUser(List<POILocation> customPOIs) {
    JSONObject jsonUser = createJSONObjectOfUser(customPOIs);
    Util.writeToFile(jsonUser, "/appData/users/" + this.username + ".json");
  }

  /**
   * Creates the json representation of the user
   * 
   * @param customPOIs a list of custom POIs created by the user
   * @return json representation of the user
   */
  private JSONObject createJSONObjectOfUser(List<POILocation> customPOIs) {
    JSONObject jsonUser = new JSONObject();
    jsonUser.put("username", this.username);
    jsonUser.put("password", this.password);
    jsonUser.put("userType", this.type.name());
    // favourites to json
    JSONArray favouritesJsonArray = new JSONArray();
    for (POILocation poiLocation : this.favourites) {
      favouritesJsonArray.put(poiLocation.toString());
    }
    jsonUser.put("favourites", favouritesJsonArray);
    // custom to json
    JSONArray customJsonArray = new JSONArray();
    for (POILocation poiLocation : customPOIs) {
      customJsonArray.put(
          poiLocation.poi.createJSONObjectOfCustomPOI(poiLocation.building, poiLocation.floor));
    }
    jsonUser.put("customPOIs", customJsonArray);
    return jsonUser;
  }

  /**
   * @return username
   */
  public String getUserName() {
    return username;
  }

  /**
   * @param username username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @param type user type
   */
  public void setUserType(UserType type) {
    this.type = type;
  }

  /**
   * @return user type
   */
  public UserType getUserType() {
    return type;
  }

  /**
   * @return list of favourites
   */
  public List<POILocation> getFavourites() {
    return this.favourites;
  }

  /**
   * @param newPassword new password
   */
  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  /**
   * @return password
   */
  public String getPassword() {
    return this.password;
  }
}
