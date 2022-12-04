package mapsTest;

import maps.Pair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

public class PairTest {
    // The following uses the Pair(double x, double y) constructor

    @Test
    @DisplayName("Verify Pair's getX()")
    void testGetX() {
        Pair pair = new Pair(1, 2);
        assertEquals(1, pair.getX());
    }

    @Test
    @DisplayName("Verify Pair's getY()")
    void testGetY() {
        Pair pair = new Pair(1, 2);
        assertEquals(2, pair.getY());
    }

    @Test
    @DisplayName("Verify Pair's getPair()")
    void testGetPair() {
        Pair pair = new Pair(1, 2);
        assertEquals(1, pair.getPair()[0]);
        assertEquals(2, pair.getPair()[1]);
    }

    // The following uses the Pair(double[] pair) constructor

    @Test
    @DisplayName("Verify Pair's getX()")
    void testGetX2() {
        double[] input = new double[] { 1d, 2d };
        Pair pair = new Pair(input);
        assertEquals(input[0], pair.getX());
    }

    @Test
    @DisplayName("Verify Pair's getY()")
    void testGetY2() {
        double[] input = new double[] { 1d, 2d };
        Pair pair = new Pair(input);
        assertEquals(input[1], pair.getY());
    }

    @Test
    @DisplayName("Verify Pair's getPair()")
    void testGetPair2() {
        double[] input = new double[] { 1d, 2d };
        Pair pair = new Pair(input);
        assertEquals(input[0], pair.getPair()[0]);
        assertEquals(input[1], pair.getPair()[1]);
    }
}
