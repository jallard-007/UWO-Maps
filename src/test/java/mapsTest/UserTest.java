package mapsTest;

import maps.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;

class UserTest {
  // The following uses the User(String username, String password, UserType type)
  // constructor

  @Test
  @DisplayName("Verify User's getPassword()")
  void testPassword() {
    User user = new User("user", "123", maps.UserType.base);
    assertEquals("123", user.getPassword());
  }

  @Test
  @DisplayName("Verify User's getUserType()")
  void testUserType() {
    User user = new User("user", "123", maps.UserType.base);
    assertEquals(maps.UserType.base, user.getUserType());
  }

  @Test
  @DisplayName("Verify User's addFavourite() and removeFavourite()")
  void testAddDelFav() {
    User user = new User("user", "123", maps.UserType.base);
    POILocation poiLocation = new POILocation(new Building("TestBuilding"), new Floor(0, "TestFloor"),
        new POI("11", maps.POIType.classroom, new Pair(1, 1) {
        }));
    user.addFavourite(poiLocation);
    assertEquals(1, user.getFavourites().size());
    user.removeFavourite(poiLocation);
    assertEquals(0, user.getFavourites().size());
  }

  // The following uses the User(JSONObject jsonUser) constructor
  @Test
  @DisplayName("Verify User's getPassword()")
  void testPassword2() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("username", "user");
    jsonObject.put("password", "123");
    jsonObject.put("userType", maps.UserType.base.toString());
    User user = new User(jsonObject);
    assertEquals("123", user.getPassword());
  }

  @Test
  @DisplayName("Verify User's getUserType()")
  void testUserType2() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("username", "user");
    jsonObject.put("password", "123");
    jsonObject.put("userType", maps.UserType.base.toString());
    User user = new User(jsonObject);
    assertEquals(maps.UserType.base, user.getUserType());
  }

  @Test
  @DisplayName("Verify User's addFavourite() and removeFavourite()")
  void testAddDelFav2() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("username", "user");
    jsonObject.put("password", "123");
    jsonObject.put("userType", maps.UserType.base.toString());
    User user = new User(jsonObject);

    POILocation poiLocation = new POILocation(new Building("TestBuilding"), new Floor(0, "TestFloor"),
        new POI("11", maps.POIType.classroom, new Pair(1, 1) {
        }));
    user.addFavourite(poiLocation);
    assertEquals(1, user.getFavourites().size());
    user.removeFavourite(poiLocation);
    assertEquals(0, user.getFavourites().size());
  }
}