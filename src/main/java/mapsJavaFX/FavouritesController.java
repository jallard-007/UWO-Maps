package mapsJavaFX;

import maps.POILocation;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.Scanner;

public class FavouritesController {

  public void setFavourites(POILocation poiLocation) throws IOException {
    //Retrieve user JSON file
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    File testFile = new File(rootPath + "/appData/users/example.json");
    Scanner fileScanner = new Scanner(testFile);
    StringBuilder fileContent = new StringBuilder();
    while (fileScanner.hasNext()) {
      fileContent.append(fileScanner.nextLine());
    }
    fileScanner.close();
    JSONObject jsonObject = new JSONObject(fileContent.toString());
    JSONArray favourites = jsonObject.getJSONArray("favourites");
    favourites.put(poiLocation);

    //Write back to User JSON file after putting new favourite POI in
    try (FileWriter file = new FileWriter(rootPath + "/appData/users/example.json")){
      file.write(jsonObject.toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  public void removeFavourites(POILocation poiLocation) throws IOException {
    //Retrieve user JSON file
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    File testFile = new File(rootPath + "/appData/users/example.json");
    Scanner fileScanner = new Scanner(testFile);
    StringBuilder fileContent = new StringBuilder();
    while (fileScanner.hasNext()) {
      fileContent.append(fileScanner.nextLine());
    }
    fileScanner.close();
    JSONObject jsonObject = new JSONObject(fileContent.toString());
    JSONArray favourites = jsonObject.getJSONArray("favourites");

    //Find and delete the POI from the favourites list
    int index = searchFavourites(poiLocation);
    if (index != -1){
      favourites.remove(index);
    }

    //Write back to User JSON file after putting new favourite POI in
    try (FileWriter file = new FileWriter(rootPath + "/appData/users/example.json")){
      file.write(jsonObject.toString());
      file.flush();
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  public int searchFavourites(POILocation poiLocation) throws IOException {
    String rootPath = new File(".").getCanonicalPath(); // gets root folder of application
    File testFile = new File(rootPath + "/appData/users/example.json");
    Scanner fileScanner = new Scanner(testFile);
    StringBuilder fileContent = new StringBuilder();
    while (fileScanner.hasNext()) {
      fileContent.append(fileScanner.nextLine());
    }
    fileScanner.close();
    JSONObject jsonObject = new JSONObject(fileContent.toString());
    JSONArray favourites = jsonObject.getJSONArray("favourites");
    String favouritedPOI = poiLocation.toString();
    for (int i = 0; i < favourites.length(); i++){
      String favourite = favourites.getString(i);
      if (favouritedPOI.equals(favourite)){
        System.out.println(favourite);
        return i;
      }
    }
    return -1;
  }
}
