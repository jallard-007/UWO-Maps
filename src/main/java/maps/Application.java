package maps;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.json.*;

public class Application {
  User user;
  boolean editMode;
  ArrayList<Building> buildings;

  private ArrayList<POILocation> poiLocations;

  public Application() {
    user = new User();
    editMode = false;
    buildings = new ArrayList<>();
    poiLocations = new ArrayList<>();
  }

  public ArrayList<POILocation> getPoiLocations() {
    return poiLocations;
  }

  public void setPoiLocations(ArrayList<POILocation> poiLocations) {
    this.poiLocations = poiLocations;
  }

  public void loadData() throws IOException {
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    System.out.println(rootPath);
    File metaDataFile = new File(rootPath + "/appData/metaData/poiMetaData.json");
    if (!metaDataFile.exists()) {
      System.out.println("File does not exist");
      System.exit(13);
    }
    if (!metaDataFile.canRead()) {
      System.out.println("Cannot read the file");
      System.exit(14);
    }
    Scanner fileScanner = new Scanner(metaDataFile);
    StringBuilder fileContent = new StringBuilder();
    while (fileScanner.hasNext()) {
      fileContent.append(fileScanner.nextLine());
    }
    fileScanner.close();

    JSONObject jsonObject = new JSONObject(fileContent.toString());
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
        Floor javaFloor = new Floor(jsonFloor.getInt("level"), jsonFloor.getString("levelName"), rootPath + jsonFloor.getString("map"));
        javaBuilding.floors.add(javaFloor);

        JSONArray pois = jsonFloor.getJSONArray("pois");

        // add POIs of current floor
        for (int poiIndex = 0; poiIndex < pois.length(); ++poiIndex) {
          JSONObject jsonPOI = pois.getJSONObject(poiIndex);
          JSONArray jsonPosition = jsonPOI.getJSONArray("position");
          Pair position = new Pair(jsonPosition.getInt(0), jsonPosition.getInt(1));
          //TODO: make POI including HoursOfOperation, Information, Name
          POI javaPOI = new POI(jsonPOI.getString("roomNum"), POIType.valueOf(jsonPOI.getString("type")), position);
          //Add capacity, hours of operation, information, and common name if it exists.
          if (jsonPOI.has("name")){
            javaPOI.setName(jsonPOI.getString("name"));
          }
          if (jsonPOI.has("capacity")){
            javaPOI.setCapacity(jsonPOI.getInt("capacity"));
          }
          if (jsonPOI.has("hours")){
            javaPOI.setHoursOfOperation(jsonPOI.getString("hours"));
          }
          if (jsonPOI.has("information")){
            javaPOI.setInformation(jsonPOI.getString("information"));
          }
          javaFloor.pois[javaPOI.type.ordinal()].add(javaPOI);
          POILocation poiLocation = new POILocation(javaBuilding, javaFloor, javaPOI);
          this.poiLocations.add(poiLocation);
        }
      }
    }
  }

  public static boolean login(String username, String password) {
    // search users folder within the application folder for username
    // return false/exception if username does not exist
    // verify password matches
    // set 'user' to User object if password is correct and return true;
    // otherwise, return false;
    return username != null;
  }

  public static void logout() {
    // remove custom user POIs from 'POIs' and each building
  }

  public void loadUserPOIs(User user) {
    // same as loadData but only adds user's custom POIs
  }

  public boolean addBuilding(Building building) {
    return this.buildings.add(building);
  }

  public boolean removeBuilding(Building building) {
    return this.buildings.remove(building);
  }

  public void enterEditMode() {
    editMode = true;
  }

  public void exitEditMode() {
    editMode = false;
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

  public ArrayList<Building> getBuildings() {
    return this.buildings;
  }
}
