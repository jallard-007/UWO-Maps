package mapsTest;

import maps.Floor;
import maps.POI;
import maps.Pair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class POITest {
    JSONObject jsonObject;

    // The following uses the POI(JSONObject jsonPOI) constructor
    @BeforeEach
    void setUp() {
        jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(1);
        jsonArray.put(1);
        jsonObject.put("position", jsonArray);
        jsonObject.put("roomNum", "1");
        jsonObject.put("type", maps.POIType.classroom.toString());
        jsonObject.put("name", "testName");
        jsonObject.put("capacity", "12");
        jsonObject.put("hours", "24");
        jsonObject.put("information", "blank information");

    }

    @Test
    @DisplayName("Verify POI's getRoomNumber()")
    void testRoomNum() {
        POI poi = new POI(jsonObject);
        assertEquals("1", poi.getRoomNumber());
    }

    @Test
    @DisplayName("Verify POI's getname()")
    void testGetName() {
        POI poi = new POI(jsonObject);
        assertEquals("testName", poi.getName());
    }

    @Test
    @DisplayName("Verify POI's getPOIType()")
    void testGetPOIType() {
        POI poi = new POI(jsonObject);
        assertEquals(maps.POIType.classroom.toString(), poi.getPOIType().toString());
    }

    @Test
    @DisplayName("Verify POI's getInformation()")
    void testGetInformation() {
        POI poi = new POI(jsonObject);
        assertEquals("blank information", poi.getInformation());
    }

    @Test
    @DisplayName("Verify POI's getCapacity()")
    void testGetCapacity() {
        POI poi = new POI(jsonObject);
        assertEquals(12, poi.getCapacity());
    }

    @Test
    @DisplayName("Verify POI's getHoursofOperation()")
    void testGetHours() {
        POI poi = new POI(jsonObject);
        assertEquals("24", poi.getHoursOfOperation());
    }

    @Test
    @DisplayName("Verify POI's getPosition()")
    void testGetPosition() {
        POI poi = new POI(jsonObject);
        assertEquals(1, poi.getPosition().getX());
        assertEquals(1, poi.getPosition().getY());
    }

    // The following uses the POI(String roomNumber, POIType poiType, Pair position)
    // constructor

    @Test
    @DisplayName("Verify POI's getRoomNumber()")
    void testRoomNum2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals("1", poi.getRoomNumber());
    }

    @Test
    @DisplayName("Verify POI's getname()")
    void testGetName2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals(null, poi.getName());
    }

    @Test
    @DisplayName("Verify POI's getPOIType()")
    void testGetPOIType2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals(maps.POIType.classroom.toString(), poi.getPOIType().toString());
    }

    @Test
    @DisplayName("Verify POI's getInformation()")
    void testGetInformation2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals(null, poi.getInformation());
    }

    @Test
    @DisplayName("Verify POI's getCapacity()")
    void testGetCapacity2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals(null, poi.getCapacity());
    }

    @Test
    @DisplayName("Verify POI's getHoursofOperation()")
    void testGetHours2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals(null, poi.getHoursOfOperation());
    }

    @Test
    @DisplayName("Verify POI's getPosition()")
    void testGetPosition2() {
        POI poi = new POI("1", maps.POIType.classroom, new Pair(1, 1));
        assertEquals(1, poi.getPosition().getX());
        assertEquals(1, poi.getPosition().getY());
    }

}
