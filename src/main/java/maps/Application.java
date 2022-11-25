package maps;

import java.io.File;
import java.util.*;

import org.json.*;

import java.lang.String;

public class Application {
  User user;
  List<Building> buildings;
  List<POILocation> poiLocations;

  public Application() {
    user = null;
    buildings = new ArrayList<>();
    poiLocations = new ArrayList<>();
  }

  public void loadData() {
    String rootPath = Util.getRootPath(); // gets root folder of application
    String fileContent = Util.getJSONFileContents(rootPath + "/appData/metaData/poiMetaData.json");
    JSONObject jsonObject = new JSONObject(fileContent);
    JSONArray buildings = jsonObject.getJSONArray("buildings");
    // add buildings
    for (int buildingIndex = 0; buildingIndex < buildings.length(); ++buildingIndex) {
      JSONObject jsonBuilding = buildings.getJSONObject(buildingIndex);
      Building javaBuilding = new Building(jsonBuilding.getString("buildingName"));
      this.buildings.add(javaBuilding);

      JSONArray floors = jsonBuilding.getJSONArray("floors");
      // add floors of current building
      for (int floorIndex = 0; floorIndex < floors.length(); ++floorIndex) {
        JSONObject jsonFloor = floors.getJSONObject(floorIndex);
        Floor javaFloor = new Floor(jsonFloor.getInt("level"), jsonFloor.getString("levelName"),
            rootPath + jsonFloor.getString("map"));
        javaBuilding.floors.add(javaFloor);

        JSONArray pois = jsonFloor.getJSONArray("pois");

        // add POIs of current floor
        for (int poiIndex = 0; poiIndex < pois.length(); ++poiIndex) {
          POI javaPOI = new POI(pois.getJSONObject(poiIndex));
          javaFloor.pois[javaPOI.type.ordinal()].add(javaPOI);
          POILocation poiLocation = new POILocation(javaBuilding, javaFloor, javaPOI);
          this.poiLocations.add(poiLocation);
        }
      }
    }
  }

  public boolean signup(String username, String password) {
    if (!Util.createUserFile(username, password)) {
      return false;
    }
    return login(username, password);
  }
  public boolean login(String username, String password) {
    String rootPath = Util.getRootPath();
    if (!new File(rootPath + "/appData/users/" + username + ".json").exists()) {
      // username does not exist;
      return false;
    }
    String fileContent =
        Util.getJSONFileContents(rootPath + "/appData/users/" + username + ".json");
    JSONObject jsonObject = new JSONObject(fileContent);
    if (!password.equals(jsonObject.getString("password"))) {
      // password does not match
      return false;
    }
    this.user = new User(jsonObject);
    loadUserPOIs();
    sortPOIs();

    // add favourites to User object
    JSONArray favourites = jsonObject.getJSONArray("favourites");
    for (int i = 0; i < favourites.length(); ++i) {
      String favourite = favourites.getString(i);
      List<POILocation> poiFav = searchForPOI(favourite);
      if (poiFav.size() == 0) {
        // poi does not exist
        // need to remove it
        continue;
      }
      this.user.addFavourite(poiFav.get(0));
    }

    // indicate login was successful
    return true;
  }

  private void loadUserPOIs() {
    String rootPath = Util.getRootPath();
    String fileContent =
        Util.getJSONFileContents(rootPath + "/appData/users/" + this.user.getUserName() + ".json");
    JSONObject jsonUser = new JSONObject(fileContent);

    JSONArray customPOIs = jsonUser.getJSONArray("customPOIs");
    for (int poiIndex = 0; poiIndex < customPOIs.length(); ++poiIndex) {
      JSONObject jsonPOI = customPOIs.getJSONObject(poiIndex);
      POI javaPOI = new POI(jsonPOI);

      Building building = this.getMatchingBuilding(jsonPOI.getString("building"));
      if (building == null) {
        // error
        continue;
      }
      Floor floor = building.getMatchingFloor(jsonPOI.getString("floor"));
      if (floor == null) {
        // error
        continue;
      }

      floor.pois[javaPOI.type.ordinal()].add(javaPOI);
      this.poiLocations.add(new POILocation(building, floor, javaPOI));
    }
  }

  private void sortPOIs() {
    this.poiLocations.sort((lhs, rhs) -> {
      int buildingCompare = lhs.building.getName().compareTo(rhs.building.getName());
      if (buildingCompare != 0) {
        return buildingCompare;
      }
      int floorCompare = Integer.compare(lhs.floor.level, rhs.floor.level);
      if (floorCompare != 0) {
        return floorCompare;
      }
      return lhs.poi.toString().compareTo(rhs.poi.toString());
    });
  }

  public void logout() {
    List<POILocation> poiLocationsToRemove = new ArrayList<>();
    for (POILocation currentPOI : this.poiLocations) {
      if (currentPOI.poi.type == POIType.custom) {
        currentPOI.floor.pois[POIType.custom.ordinal()].clear();
        poiLocationsToRemove.add(currentPOI);
      }
    }
    user.saveToFile(poiLocationsToRemove);
    this.poiLocations.removeAll(poiLocationsToRemove);
    this.user = null;
  }

  private Building getMatchingBuilding(String buildingName) {
    for (Building building : this.buildings) {
      if (building.getName().equals(buildingName)) {
        return building;
      }
    }
    return null;
  }

  public List<POILocation> searchForPOI(String searchText) {
    searchText = searchText.toLowerCase();
    List<POILocation> matchingPOIs = new ArrayList<>();
    for (POILocation poiLocation : this.poiLocations) {
      if (poiLocation.toString().toLowerCase().contains(searchText)) {
        matchingPOIs.add(poiLocation);
      }
    }
    return matchingPOIs;
  }

  public List<POILocation> getPoiLocations() {
    return poiLocations;
  }

  public List<Building> getBuildings() {
    return this.buildings;
  }

  public void save() {
    if (user == null){
      return;
    }
    if (user.getUserType().equals(UserType.admin)) {
      JSONObject jsonApplication = createJSONObjectOfApplication();
      // if changes were made, update poimetadata file.
    }
    logout();
  }

  private JSONObject createJSONObjectOfApplication() {
    JSONObject jsonApplication = new JSONObject();
    // create jsonApplication
    return jsonApplication;
  }

  public User getUser() {
    return this.user;
  }
}
