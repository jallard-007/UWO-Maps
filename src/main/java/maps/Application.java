package maps;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.json.*;

import java.lang.String;

public class Application {
  User user;
  boolean editMode;
  ArrayList<Building> buildings;
  ArrayList<POILocation> poiLocations;

  public Application() {
    user = null;
    editMode = false;
    buildings = new ArrayList<>();
    poiLocations = new ArrayList<>();
  }

  public void loadData() throws IOException {
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

  public boolean login(String username, String password) throws IOException {
    String rootPath = Util.getRootPath();
    if (!new File(rootPath + "/appData/users/" + username + ".json").exists()) {
      // username does not exist;
      return false;
    }
    String fileContent = Util.getJSONFileContents(rootPath + "/appData/users/" + username + ".json");
    JSONObject jsonObject = new JSONObject(fileContent);
    User javaUser = new User(jsonObject);
    if (!javaUser.passwordMatch(password)) {
      // password does not match
      return false;
    }
    this.user = javaUser;
    loadUserPOIs();

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
      javaUser.addFavourite(poiFav.get((0)));
    }

    // indicate login was successful
    return true;
  }

  public void loadUserPOIs() throws IOException {
    String rootPath = Util.getRootPath();
    String fileContent = Util.getJSONFileContents(rootPath + "/appData/users/" + this.user.getUserName() + ".json");
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
      POILocation poiLocation = new POILocation(building, floor, javaPOI);
      this.poiLocations.add(poiLocation);
    }
  }

  public void logout() {
    List<POILocation> poiLocationsToRemove = new ArrayList<>();
    for (POILocation currentPOI : this.poiLocations) {
      if (currentPOI.poi.type == POIType.custom) {
        currentPOI.floor.pois[POIType.custom.ordinal()].clear();
        poiLocationsToRemove.add(currentPOI);
      }
    }
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

  public ArrayList<POILocation> getPoiLocations() {
    return poiLocations;
  }

  public ArrayList<Building> getBuildings() {
    return this.buildings;
  }
}
