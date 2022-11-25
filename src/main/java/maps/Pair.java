package maps;

public class Pair {
  private int x; // first member of pair
  private int y; // second member of pair

  public Pair(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Pair(int[] pair) {
    this.x = pair[0];
    this.y = pair[1];
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int[] getPair() {
    return new int[]{x, y};
  }
}
