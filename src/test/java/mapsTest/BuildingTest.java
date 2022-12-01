package mapsTest;

import maps.Building;
import maps.Floor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

class BuildingTest {
  Building building;

  @BeforeEach
  void setUp() {
    building = new Building("TestBuilding");
  }

  @Test
  @DisplayName("Verify Building's getName()")
  void testBuildingName() {
    assertEquals("TestBuilding", building.getName());
  }

  @Test
  @DisplayName("Verify Building's getFloors()")
  void testBuildingFloor() {
    ArrayList<Floor> list = new ArrayList<>();
    assertEquals(list, building.getFloors());
  }

  @Test
  @DisplayName("Verify Building's toString()")
  void testBuildingToString() {
    String expected = "\nBuildingName: TestBuilding";
    assertEquals(expected, building.toString());
  }

  @Test
  @DisplayName("Verify Building's toJson()")
  void testBuildingToJSON() {
    assertEquals("\nBuildingName: TestBuilding", building.toString());
  }

  @Test
  void getMatchingFloor() {
    Floor expected = new Floor(1, "some floor");
    building.getFloors().add(expected);
    Floor result = building.getMatchingFloor("some floor");
    assertEquals(result, expected);
  }

  @Test
  void getMatchingFloorNull() {
    building.getFloors().add(new Floor(1, "a floor"));
    Floor result = building.getMatchingFloor("some floor");
    assertNull(result);
  }
}