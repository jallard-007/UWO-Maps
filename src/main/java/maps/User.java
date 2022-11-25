package maps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class User {
  private String username;
  private String password;
  private UserType type;
  private List<POILocation> favourites;

  public User(String name, String password, UserType type) {
    this.username = name;
    this.password = password;
    this.type = type;
    this.favourites = new ArrayList<>();
  }

  public User(JSONObject jsonUser) {
    this(jsonUser.getString("username"), jsonUser.getString("password"),
        UserType.valueOf(jsonUser.getString("userType")));
  }

  public void addFavourite(POILocation poi) {
    favourites.add(poi);
  }

  public void removeFavourites(POILocation poiLocation) {
    favourites.remove(poiLocation);
  }

  public int searchFavourites(POILocation poiLocation) {
    return favourites.indexOf(poiLocation);
  }

  public void saveToFile(List<POILocation> customPOIs) {
    JSONObject jsonUser = createJSONObjectOfUser(customPOIs);
    try (FileWriter file =
        new FileWriter(Util.getRootPath() + "/appData/users/" + this.username + ".json")) {
      file.write(jsonUser.toString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
      customJsonArray.put(poiLocation.poi.createJSONObjectOfCustomPOI(poiLocation));
    }
    jsonUser.put("customPOIs", customJsonArray);
    return jsonUser;
  }

  public String getUserName() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setUserType(UserType type) {
    this.type = type;
  }

  public UserType getUserType() {
    return type;
  }

  public List<POILocation> getFavourites() {
    return this.favourites;
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  public String getPassword() {
    return this.password;
  }
}
