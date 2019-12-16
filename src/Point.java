// Alex Beamer
// December 11, 2019
// Galleta Games and Software

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        init(x, y);
    }

    public Point(int[] coords) {
        init(coords[0], coords[1]);
    }

    private void init(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point duplicate() {
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        return ((Point)o).x == x && ((Point)o).y == y;
    }

    public String toString() {
        return "" + x + " - " + y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
