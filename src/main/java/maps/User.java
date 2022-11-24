package maps;

import org.json.JSONObject;

import java.util.ArrayList;

public class User {
  private String username;
  private String password;
  private UserType type;
  private ArrayList<POILocation> favorites;


  public User(String name, String password, UserType type) {
    this.username = name;
    this.password = password;
    this.type = type;
    this.favorites = new ArrayList<>();
  }

  public User(JSONObject jsonUser) {
    this(jsonUser.getString("username"), jsonUser.getString("password"),
        UserType.valueOf(jsonUser.getString("userType")));
  }

  public String getUserName() {
    return username;
  }

  public UserType getUserType() {
    return type;
  }

  public void addFavourite(POILocation poi) {
    favorites.add(poi);
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  public String getPassword() {
    return this.password;
  }
}
