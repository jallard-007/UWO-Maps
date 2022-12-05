package maps;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserChanges extends User {
  static Application app;
  private final List<POILocation> customPOIs = new ArrayList<>();

  public UserChanges(JSONObject jsonObject) {
    super(jsonObject);
    if (app == null) {
      System.out.print("app not set");
      System.exit(1);
    }
    JSONArray customs = jsonObject.getJSONArray("customPOIs");
    for (int i = 0; i < customs.length(); ++i) {
      JSONObject jsonCustom = customs.getJSONObject(i);
      try {
        Building building = app.getMatchingBuilding(jsonCustom.getString("building"));
        Floor floor = building.getMatchingFloor(jsonCustom.getString("floor"));
        POI poi = new POI(jsonCustom);
        POILocation custom = new POILocation(building, floor, poi);
        this.customPOIs.add(custom);
      } catch (Exception ignored) {
      }
    }

    JSONArray favourites = jsonObject.getJSONArray("favourites");
    for (int i = 0; i < favourites.length(); ++i) {
      JSONObject jsonFavourites = favourites.getJSONObject(i);
      try {
        Building building = app.getMatchingBuilding(jsonFavourites.getString("building"));
        Floor floor = building.getMatchingFloor(jsonFavourites.getString("floor"));
        POILocation favourite = app.getPoiLocation(floor, jsonFavourites.getString("poi"));
        if (favourite == null) {
          for (POILocation p : this.customPOIs) {
            if (p.getFloor().equals(floor) && p.getPOI().getRoomNumber()
                .equals(jsonFavourites.getString("poi"))) {
              favourite = p;
            }
          }
          if (favourite == null) {
            continue;
          }
        }
        this.favourites.add(favourite);
      } catch (Exception ignored) {
      }
    }
  }

  public static void setApp(Application newApp) {
    app = newApp;
  }

  public void saveUser() {
    this.saveUser(this.customPOIs);
  }
}
