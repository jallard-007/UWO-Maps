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
  }

  public User(JSONObject jsonUser) {
    this.username = jsonUser.getString("username");
    this.password = jsonUser.getString("password");
    if (jsonUser.getString("userType").equals("admin")) {
      this.type = UserType.admin;
    } else if (jsonUser.getString("userType").equals("base")) {
      this.type = UserType.base;
    } else {
      System.out.println("undefined user");
      System.exit(15);
    }
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

  public boolean passwordMatch(String password) {
    return this.password.equals(password);
  }
}
