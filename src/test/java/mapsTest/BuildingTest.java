package mapsTest;

import maps.Building;
import maps.Floor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

  @Test
  void getMatchingFloor() {
    System.out.println("Building.getMatchingFloor");
    Building instance = new Building("TestBuilding");
    Floor expected = new Floor(1, "some floor");
    instance.getFloors().add(expected);
    Floor result = instance.getMatchingFloor("some floor");
    assertEquals(result, expected);
  }

  @Test
  void getMatchingFloorNull() {
    System.out.println("Building.getMatchingFloor | null");
    Building instance = new Building("TestBuilding");
    instance.getFloors().add(new Floor(1, "a floor"));
    Floor result = instance.getMatchingFloor("some floor");
    assertNull(result);
  }
}