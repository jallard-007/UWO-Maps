package maps;

import java.io.File;
import java.util.*;

import org.json.*;

import java.lang.String;

/**
 * The Application class is the top level class for the application
 */
public class Application {
  User user;
  boolean editMode;
  final List<Building> buildings;
  final List<POILocation> poiLocations;
  List<UserChanges> registeredUsers;

  /**
   * Default constructor
   */
  public Application() {
    user = null;
    editMode = false;
    buildings = new ArrayList<>();
    poiLocations = new ArrayList<>();
    UserChanges.setApp(this);
  }

  /**
   * Reads the main poi meta-data file on disk and loads all data into their
   * respective classes
   */
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
            jsonFloor.getString("map"));
        javaBuilding.floors.add(javaFloor);

        JSONArray pois = jsonFloor.getJSONArray("pois");

        // add POIs of current floor
        for (int poiIndex = 0; poiIndex < pois.length(); ++poiIndex) {
          POI javaPOI = new POI(pois.getJSONObject(poiIndex));
          javaFloor.addPOI(javaPOI);
          POILocation poiLocation = new POILocation(javaBuilding, javaFloor, javaPOI);
          this.poiLocations.add(poiLocation);
        }
      }
    }
  }

  /**
   * Signs up a user and then logs in
   * 
   * @param username The username to use when creating the user
   * @param password The password for the user
   * @return true if the signup was successful, false otherwise
   * 
   */
  public boolean signup(String username, String password) {
    if (!Util.createUserFile(username, password)) {
      return false;
    }
    return login(username, password);
  }

  /**
   * Logs in the user
   * 
   * @param username The username to use when logging in
   * @param password The password for the user
   * @return true if the login was successful, false otherwise
   */
  public boolean login(String username, String password) {
    String rootPath = Util.getRootPath();
    if (!new File(rootPath + "/appData/users/" + username + ".json").exists()) {
      // username does not exist;
      return false;
    }
    String fileContent = Util.getJSONFileContents(rootPath + "/appData/users/" + username + ".json");
    JSONObject jsonObject = new JSONObject(fileContent);
    if (!password.equals(jsonObject.getString("password"))) {
      // password does not match
      return false;
    }
    this.user = new User(jsonObject);

    // Just to test restricting edit mode. will need to be changed.
    // Signing in as 'user' allows you to move the buttons freely, and
    // the new position is stored added to the json file and current application
    // state.
    // Signing in as 'example' the buttons cannot be moved.
    if (this.user.getUserType() == UserType.admin) {
      this.editMode = true;
    }

    loadUserPOIs(jsonObject);
    sortPOIs();

    // add favourites to User object
    JSONArray favourites = jsonObject.getJSONArray("favourites");
    for (int i = 0; i < favourites.length(); ++i) {
      JSONObject favourite = favourites.getJSONObject(i);
      Building building = this.getMatchingBuilding(favourite.getString("building"));
      Floor floor = building.getMatchingFloor(favourite.getString("floor"));
      POILocation poiLocation = this.getPoiLocation(floor, favourite.getString("poi"));
      if (poiLocation == null) {
        continue;
      }
      this.user.addFavourite(poiLocation);
    }

    if (this.user.getUserType().equals(UserType.admin)) {
      this.registeredUsers = new ArrayList<>();
      File dir = new File(Util.getRootPath() + "/appData/users");
      File[] directoryListing = dir.listFiles();
      if (directoryListing != null) {
        for (File userFile : directoryListing) {
          this.registeredUsers.add(new UserChanges(new JSONObject(Util.getJSONFileContents(userFile))));
        }
      }
    }
    // indicate login was successful
    return true;
  }

  /**
   * @return the logged-in user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Loads custom POIs
   * 
   * @param jsonUser the json representation of the user
   */
  private void loadUserPOIs(JSONObject jsonUser) {
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

      floor.addPOI(javaPOI);
      this.poiLocations.add(new POILocation(building, floor, javaPOI));
    }
  }

  /**
   * Whether the user can access the ability to delete, edit, and add buildings,
   * floors, and built-in POIs (i.e., if they're an admin with access to edit
   * mode).
   * 
   * @return whether the user can enter edit mode
   */
  public boolean getEditMode() {
    return this.editMode;
  }

  /**
   * Sorts the poiLocations list in ascending order
   */
  private void sortPOIs() {
    this.poiLocations.sort((lhs, rhs) -> {
      int buildingCompare = lhs.building.getName().compareTo(rhs.building.getName());
      if (buildingCompare != 0) {
        return buildingCompare;
      }
      int floorCompare = Integer.compare(lhs.floor.getLevel(), rhs.floor.getLevel());
      if (floorCompare != 0) {
        return floorCompare;
      }
      return lhs.poi.toString().compareTo(rhs.poi.toString());
    });
  }

  /**
   * Logs out the current user, removing the user's custom POIs and saving any
   * changes such as
   * favourites, and custom POIs to the user's file
   */
  private UserType logout() {
    List<POILocation> poiLocationsToRemove = new ArrayList<>();
    for (POILocation currentPOI : this.poiLocations) {
      if (currentPOI.poi.type == POIType.custom) {
        currentPOI.floor.pois[POIType.custom.ordinal()].clear();
        poiLocationsToRemove.add(currentPOI);

      }
    }
    user.saveUser(poiLocationsToRemove);
    this.poiLocations.removeAll(poiLocationsToRemove);
    UserType userType = user.getUserType();
    this.user = null;
    return userType;
  }

  /**
   * @return a list of buildings
   */
  public List<Building> getBuildings() {
    return this.buildings;
  }

  /**
   * Gets the building object with name attribute matching buildingName
   *
   * @param buildingName the name of the building to find
   * @return the matching building object
   */
  public Building getMatchingBuilding(String buildingName) {
    for (Building building : this.buildings) {
      if (building.getName().equals(buildingName)) {
        return building;
      }
    }
    return null;
  }

  /**
   * Searches for POIs with buildingName, floorName, or POIName/POINum containing
   * the searchText
   *
   * @param searchText the text to search for a POI
   * @return all poi location objects with partial match
   */
  public List<POILocation> searchForPOI(String searchText) {
    searchText = searchText.toLowerCase();
    List<POILocation> matchingPOIs = new ArrayList<>();
    for (POILocation poiLocation : this.poiLocations) {
      if (poiLocation.toString().toLowerCase().contains(searchText)
          || poiLocation.poi.getRoomNumber().toLowerCase().contains(searchText)) {
        matchingPOIs.add(poiLocation);
      }
    }
    return matchingPOIs;
  }

  /**
   * @return a list of poi locations
   */
  public List<POILocation> getPoiLocations() {
    return poiLocations;
  }

  /**
   * Deletes a building from the application
   *
   * @param building the building to delete
   * @return true if successful, false otherwise
   */
  public boolean deleteBuilding(Building building) {
    if (user.getUserType() != UserType.admin) {
      return false;
    }
    List<POILocation> found = new ArrayList<>();
    for (POILocation poiLocation : this.poiLocations) {
      if (poiLocation.building.equals(building)) {
        found.add(poiLocation);
      }
    }
    this.poiLocations.removeAll(found);
    return this.buildings.remove(building);
  }

  /**
   * Deletes a floor from the application
   *
   * @param building the building that the floor is in
   * @param floor    the floor to delete
   * @return true if successful, false otherwise
   */
  public boolean deleteFloor(Building building, Floor floor) {
    if (user.getUserType() != UserType.admin) {
      return false;
    }
    List<POILocation> found = new ArrayList<>();
    for (POILocation poiLocation : this.poiLocations) {
      if (poiLocation.floor.equals(floor)) {
        found.add(poiLocation);
      }
    }
    this.poiLocations.removeAll(found);
    return building.floors.remove(floor);
  }

  /**
   * Deletes a POI from the application, including from the user's favourites list
   *
   * @param poiLocation the POILocation to delete
   */
  public void deletePOI(POILocation poiLocation) {
    if (poiLocation.poi.getPOIType() == POIType.custom || this.user.getUserType() == UserType.admin) {
      this.user.removeFavourite(poiLocation);
      poiLocation.removePOI();
      this.poiLocations.remove(poiLocation);
    }
  }

  /**
   * Adds a building to the application
   *
   * @param building the building to add
   */
  public void addBuilding(Building building) {
    this.buildings.add(building);
  }

  /**
   * Adds a floor to the building
   *
   * @param floor the floor to add
   */
  public void addFloor(Building building, Floor floor) {
    building.floors.add(floor);
  }

  /**
   * Creates a POILocation object for the poi, adds it to the list, and adds the
   * poi to the floor
   *
   * @param building the building that the floor is in
   * @param floor    the floor that the poi is on
   * @param poi      the poi to add
   */
  public void addPOI(Building building, Floor floor, POI poi) {
    this.poiLocations.add(new POILocation(building, floor, poi));
    this.sortPOIs();
    floor.addPOI(poi);
  }

  /**
   * Adds an already-created POI location to the list and adds it to the floor
   * 
   * @param poiLocation newly-created POI location
   */
  public void addPOI(POILocation poiLocation) {
    this.poiLocations.add(poiLocation);
    this.sortPOIs();
    poiLocation.getFloor().addPOI(poiLocation.getPOI());
  }

  /**
   * Used to save all changes made by the current user for both user types
   */
  public void save() {
    if (user == null) {
      return;
    }
    UserType type = logout();
    if (type.equals(UserType.admin)) {
      JSONObject jsonApplication = createJSONObjectOfApplication();
      Util.writeToFile(jsonApplication, "/appData/metaData/poiMetaData.json");
      for (UserChanges user : this.registeredUsers) {
        user.saveUser();
      }
    }
  }

  /**
   * 
   * @param f
   * @param poiName
   * @return null if not found
   */
  public POILocation getPoiLocation(Floor f, String poiName) {
    for (POILocation p : this.poiLocations) {
      if (p.getFloor().equals(f) && p.getPOI().getRoomNumber().equals(poiName)) {
        return p;
      }
    }
    return null;
  }

  /**
   * Creates a JSONObject representation of the application object
   * 
   * @return json representation the application object
   */
  private JSONObject createJSONObjectOfApplication() {
    JSONObject jsonApplication = new JSONObject();
    JSONArray jsonBuildings = new JSONArray();
    jsonApplication.put("buildings", jsonBuildings);
    for (Building building : this.buildings) {
      jsonBuildings.put(building.toJSON());
    }
    return jsonApplication;
  }
}
