// Alex Beamer
// December 11, 2019
// Galleta Games and Software

public class Wall {

    private Point point1;
    private Point point2;

    public Wall(Point p1, Point p2) {
        init(p1, p2);
    }

    /*
    public Wall(double[] p1, double[] p2) {
        init(new Point(p1), new Point(p2));
    }
    */

    public Wall(Point[] points) {
        init(points[0], points[1]);
    }

    /*
    public Wall(double p1x, double p1y, double p2x, double p2y) {
        init(new Point(p1x, p1y), new Point(p2x, p2y));
    }
    */

    private void init(Point p1, Point p2) {
        point1 = p1;
        point2 = p2;
    }

    @Override
    public boolean equals(Object o) {
        return ((Wall)o).point1.equals(point1) && ((Wall)o).point2.equals(point2);
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }
}
