package uwo.group2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.*;
import java.lang.String;

public class Application {
  User user;
  boolean editMode;
  ArrayList<Building> buildings;
  ArrayList<POILocation> poiLocations;
  // ArrayList<User> Users;

  Application() {
    user = new User();
    buildings = new ArrayList<>();
    // Users = new ArrayList<>();
    editMode = false;
    buildings = new ArrayList<>();
    poiLocations = new ArrayList<>();
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
        Floor javaFloor = new Floor(jsonFloor.getInt("level"), jsonFloor.getString("levelName"));
        javaBuilding.floors.add(javaFloor);

        JSONArray pois = jsonFloor.getJSONArray("pois");
        // add POIs of current floor
        for (int poiIndex = 0; poiIndex < pois.length(); ++poiIndex) {
          JSONObject jsonPOI = pois.getJSONObject(poiIndex);
          JSONArray jsonPosition = jsonPOI.getJSONArray("position");
          Pair position = new Pair(jsonPosition.getInt(0), jsonPosition.getInt(1));
          POI javaPOI = new POI(jsonPOI.getString("roomNum"), POIType.valueOf(jsonPOI.getString("type")), position);
          javaFloor.pois[javaPOI.type.ordinal()].add(javaPOI);
          POILocation poiLocation = new POILocation(javaBuilding, javaFloor, javaPOI);
          this.poiLocations.add(poiLocation);
        }
      }
    }
  }

 
  //returns 1 for success, 2 for incorrect password, 3 for incorrect username
  //could make it a better type later like an enum or something
  public int login(String username, String password) throws IOException{
    this.user = null;
    // search users folder within the application folder for username
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    System.out.println(rootPath);
    File metaDataFile = new File(rootPath + "/appData/users/person.json");
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
    JSONArray usersJSON = jsonObject.getJSONArray("users");
    for (int userIndex = 0; userIndex < usersJSON.length(); ++userIndex) {
      JSONObject jsonUser = usersJSON.getJSONObject(userIndex);
      String userString = new String(jsonUser.getString("username"));
      if (username.equals(userString)) 
      {
        if (password.equals(jsonUser.getString("password"))){
          String typeString = new String(jsonUser.getString("userType"));
          UserType type = null;
          if (typeString.equals("admin")){
            type = UserType.admin;
          }
          else if (typeString.equals("base")){
            type = UserType.base;
          }
          else{
            System.out.println("undefined user");
            System.exit(15);
          }
          if (type != null){
            User javaUser = new User(userString, jsonUser.getString("password"), type);
            this.user = javaUser;            
          }
          //indicate login was successful
          return 1;
        }
        else{
          return 2;
        }

      }
      
    }
    
    // return false/exception if username does not exist
    // verify password matches
    // set 'user' to User object if password is correct and return true;
    // otherwise, return false;
    return 3;
  }


  public void logout() {
    // remove custom user POIs from 'POIs' and each building
    List<POILocation> poiLocationsToRemove = new ArrayList<POILocation>();
    for(POILocation currentPOI: this.poiLocations){
      if (currentPOI.poi.type == POIType.custom){
        currentPOI.floor.pois[POIType.custom.ordinal()].clear();
        // List<POI>[] pois = currentPOI.floor.getPOIS();
        // pois[POIType.custom.ordinal()].clear();
        // currentPOI.floor.setPOIS(pois);
        poiLocationsToRemove.add(currentPOI);
      }
    }
    this.poiLocations.removeAll(poiLocationsToRemove);
    //set the current user to null for this app object
    this.user = null;
  }

  public void loadUserPOIs() throws IOException {
    // same as loadData but only adds user's custom POIs
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    System.out.println(rootPath);
    File metaDataFile = new File(rootPath + "/appData/users/person.json");
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

    JSONArray usersJSON = jsonObject.getJSONArray("users");
    for (int userIndex = 0; userIndex < usersJSON.length(); ++userIndex) {
      JSONObject jsonUser = usersJSON.getJSONObject(userIndex);
      String userString = new String(jsonUser.getString("username"));
      if (this.user.getUserName().equals(userString)) {
        Building javaBuilding = null;
        Floor javaFloor = null;
        JSONArray customPOIs = jsonUser.getJSONArray("customPOIs");
        for (int poiIndex = 0; poiIndex < customPOIs.length(); ++poiIndex) {
          JSONObject jsonPOI = customPOIs.getJSONObject(poiIndex);
          JSONArray jsonPosition = jsonPOI.getJSONArray("position");
          Pair position = new Pair(jsonPosition.getInt(0), jsonPosition.getInt(1));
          POI javaPOI = new POI(jsonPOI.getString("roomNum"), jsonPOI.getString("name"), POIType.custom, position);
          for(Building building: this.buildings){
            if (building.name.equals(jsonPOI.getString("buildingName"))){
              for (Floor floor: building.floors){
                if (floor.level == jsonPOI.getInt("level")){
                  System.out.println("POI added to level");
                  floor.pois[javaPOI.type.ordinal()].add(javaPOI);
                  javaBuilding = building;
                  javaFloor = floor;
                  break;
                }
              }
            }
          }
          POILocation poiLocation = new POILocation(javaBuilding, javaFloor, javaPOI);
          this.poiLocations.add(poiLocation);
          System.out.println("POI added");
        }

      }
    }
    
    
  }

  public static boolean newUser(String username, String password)throws IOException{
    // search users folder within the application folder for username
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    System.out.println(rootPath);
    File metaDataFile = new File(rootPath + "/appData/users/person.json");
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
    JSONArray usersJSON = jsonObject.getJSONArray("users");
    for (int userIndex = 0; userIndex < usersJSON.length(); ++userIndex) {
      JSONObject jsonUser = usersJSON.getJSONObject(userIndex);
      String userString = new String(jsonUser.getString("username"));
      if (username.equals(userString)) 
      {
        return false;
      }
    }
    // User newUser = new User(username, password, UserType.base);
    JSONObject newUserJSON = new JSONObject();
    JSONArray jArray = new JSONArray();
    newUserJSON.put("username", username);
    newUserJSON.put("password", password);
    newUserJSON.put("userType", "base");
    newUserJSON.put("customPOIs", jArray);
    usersJSON.put(newUserJSON);
    JSONArray usersJSONArray = jsonObject.getJSONArray("users");
    usersJSONArray.put(newUserJSON);
    JSONObject usersObj = new JSONObject();
    usersObj.put("users", usersJSONArray);
    FileWriter file = new FileWriter(rootPath + "/appData/users/person.json");
    file.write("{}");
    file.write(usersObj.toString());
    file.flush();
    file.close();
    return true;

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

  // this is inefficiently coping references,
  // maybe sorting 'this.POIs' will allow for a matching subsection to be returned?
  // not a high priority issue
  public List<POILocation> searchForPOI(String searchText) {
    List<POILocation> matchingPOIs = new ArrayList<>();
    for (POILocation poiLocation: this.poiLocations) {
      if (poiLocation.poi.name.contains(searchText) || poiLocation.poi.roomNumber.contains(searchText) ) {
        matchingPOIs.add(poiLocation);
      }
    }
    return matchingPOIs;
  }
}
