package maps;

/**
 * Used as coordinate pair for POIs
 */
public class Pair {
  private double x; // first member of pair
  private double y; // second member of pair

  /**
   * @param x x coordinate
   * @param y y coordinate
   */

  public Pair(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @param pair array of coordinates, x coordinate at index 0, y at index 1
   */

  public Pair(double[] pair) {
    this.x = pair[0];
    this.y = pair[1];
  }

  /**
   * @param x x coordinate
   */

  public void setX(double x) {
    this.x = x;
  }

  /**
   * @param y y coordinate
   */

  public void setY(double y) {
    this.y = y;
  }

  /**
   * @return x coordinate
   */

  public double getX() {
    return x;
  }

  /**
   * @return y coordinate
   */

  public double getY() {
    return y;
  }

  /**
   * @return int[] of size 2, x coordinate at index 0, y at index 1
   */
  public double[] getPair() {
    return new double[] {x, y};
  }
}
