package maps;

import java.util.ArrayList;
import java.util.List;

public class Building {
  String name;
  List<Floor> floors;

  Building(String name) {
    this.name = name;
    this.floors = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  public List<Floor> getFloors() {
    return floors;
  }

  public String toString() {
    StringBuilder str = new StringBuilder("\nBuildingName: " + this.name);
    for (Floor floor : this.floors) {
      str.append("\n\tFloorName: ").append(floor.toString());
    }
    return str.toString();
  }
}
