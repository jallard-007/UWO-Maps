package mapsTest;

import maps.Floor;
import maps.POI;
import maps.Pair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class FloorTest {
    Floor floor;

    @BeforeEach
    void setUp() {
        floor = new Floor(1, "TestFloor");
    }

    @Test
    @DisplayName("Verify Floors's getName()")
    void testFloorName() {
        assertEquals("TestFloor", floor.getName());
    }

    @Test
    @DisplayName("Verify Floors's addPOI(POI poi)")
    void testFloorAddPOI() {
        POI poi = new POI("23", maps.POIType.classroom, new Pair(134, 324));
        floor.addPOI(poi);
        POI resultPOI = floor.getPOIs()[maps.POIType.classroom.ordinal()].remove(0);
        assertEquals(resultPOI, poi);
    }
}
