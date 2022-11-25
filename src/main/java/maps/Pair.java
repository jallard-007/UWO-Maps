package maps;

/**
 * Used as coordinate pair for POIs
 */
public class Pair {
  private int x; // first member of pair
  private int y; // second member of pair

  /**
   * @param x x coordinate
   * @param y y coordinate
   */
  public Pair(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @param pair array of coordinates, x coordinate at index 0, y at index 1
   */
  public Pair(int[] pair) {
    this.x = pair[0];
    this.y = pair[1];
  }

  /**
   * @param x x coordinate
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * @param y y coordinate
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * @return x coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * @return y coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * @return int[] of size 2, x coordinate at index 0, y at index 1
   */
  public int[] getPair() {
    return new int[] {x, y};
  }
}
