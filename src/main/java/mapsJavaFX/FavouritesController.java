package mapsJavaFX;

import maps.POILocation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class FavouritesController {

  public void setFavourites(POILocation poiLocation) throws IOException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("building", poiLocation.getBuilding().getName());
    jsonObject.put("levelName", poiLocation.getFloor().getName());
    jsonObject.put("poi", poiLocation.getPOI());
    jsonObject.put("type", poiLocation.getPOI().getPOIType());
    jsonObject.put("position", poiLocation.getPOI().getPosition());

    JSONArray jsonArray = new JSONArray();
    jsonArray.put(jsonObject);
    JSONObject mainObj = new JSONObject();
    mainObj.put("favourites", jsonArray);
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("favourites", jsonArray);
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    try (FileWriter file = new FileWriter(rootPath + "/appData/metaData/test.json")){
      file.write(mainObj.toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }
    System.out.println(mainObj);

    File metaDataFile = new File(rootPath + "/appData/metaData/testOutput.json");
  }

  public void getFavourite() throws IOException {
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    File testFile = new File(rootPath + "/appData/metaData/test.json");
    Scanner fileScanner = new Scanner(testFile);
    StringBuilder fileContent = new StringBuilder();
    while (fileScanner.hasNext()) {
      fileContent.append(fileScanner.nextLine());
    }
    fileScanner.close();

    JSONObject jsonObject = new JSONObject(fileContent.toString());

  }

//  public void setFavourites(POILocation poiLocation) throws IOException {
//    Building building = poiLocation.getBuilding();
//    JSONObject buildingObj = new JSONObject();
//    buildingObj.put("building", building);
//
//    Floor floor = poiLocation.getFloor();
//    JSONObject floorObj = new JSONObject();
//    floorObj.put("levelName", floor);
//
//    POI poi = poiLocation.getPOI();
//    JSONObject poiObj = new JSONObject();
//    poiObj.put("poi", poiObj);
//
//
//    JSONArray jsonArray = new JSONArray();
//    jsonArray.put(buildingObj);
//    jsonArray.put(floorObj);
//    jsonArray.put(poiObj);
//
////    JSONObject jsonObject = new JSONObject();
////    jsonObject.put("favourites", jsonArray);
//    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
//    try (FileWriter file = new FileWriter(rootPath + "/appData/metaData/test.json")){
//      file.write(jsonArray.toString());
//      file.flush();
//    }catch (IOException e){
//      e.printStackTrace();
//    }
//    System.out.println(jsonArray);
//
//    File metaDataFile = new File(rootPath + "/appData/metaData/testOutput.json");
//  }
//
//  public void getFavourite() throws IOException {
//    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
//    File testFile = new File(rootPath + "/appData/metaData/test.json");
//    Scanner fileScanner = new Scanner(testFile);
//    StringBuilder fileContent = new StringBuilder();
//    while (fileScanner.hasNext()) {
//      fileContent.append(fileScanner.nextLine());
//    }
//    fileScanner.close();
//
//    JSONObject jsonObject = new JSONObject(fileContent.toString());
//
//  }
}
