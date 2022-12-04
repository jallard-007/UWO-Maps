package mapsTest;

import maps.Building;
import maps.Floor;
import maps.POI;
import maps.POILocation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class POILocationTest {
    POILocation poiLocation;
    Building building = new Building("TestBuilding");
    Floor floor = new Floor(1, "TestFloor");
    POI poi = new POI("24", maps.POIType.classroom, null);

    @BeforeEach
    void setUp() {
        poiLocation = new POILocation(building, floor, poi);
    }

    @Test
    @DisplayName("Verify POILocations's getBuilding()")
    void testGetBuilding() {
        assertEquals(building, poiLocation.getBuilding());
    }

    @Test
    @DisplayName("Verify POILocations's getFloor()")
    void testGetFloor() {
        assertEquals(floor, poiLocation.getFloor());
    }

    @Test
    @DisplayName("Verify POILocations's getPOI()")
    void testGetPOI() {
        assertEquals(poi, poiLocation.getPOI());
    }

    @Test
    @DisplayName("Verify POILocations's toString()")
    void testtoString() {
        assertEquals("24 | TestBuilding | TestFloor", poiLocation.toString());
    }

}
