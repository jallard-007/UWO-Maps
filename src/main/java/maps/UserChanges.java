package maps;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserChanges extends User {
  private List<POILocation> customPOIs;
  static Application app;

  public static void setApp(Application newApp) {
    app = newApp;
  }

  public UserChanges(JSONObject jsonObject) {
    super(jsonObject);
    JSONArray customs = jsonObject.getJSONArray("customPOIS");
    for (int i = 0; i < customs.length(); ++i) {
      JSONObject jsonCustom = customs.getJSONObject(i);
      try {
        Building building = app.getMatchingBuilding(jsonCustom.getString("building"));
        Floor floor = building.getMatchingFloor(jsonCustom.getString("floor"));
        POI poi = new POI(jsonCustom);
        POILocation custom = new POILocation(building, floor, poi);
        this.customPOIs.add(custom);
      } catch (Exception e) {
        continue;
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
            if (p.getFloor().equals(floor) && p.getPOI().getName().equals(jsonFavourites.getString("poi"))) {
              favourite = p;
            }
          }
          if (favourite == null) {
            continue;
          }
        }
        this.favourites.add(favourite);
      } catch (Exception e) {
        continue;
      }
    }
  }

  public void saveUser() {
    this.saveUser(this.customPOIs);
  }
}
