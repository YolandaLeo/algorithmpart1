import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final static Point CENTER = new Point(0, 0);
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void main(String[] args) {
        Point p1 = new Point(92, 434);
        Point p2 = new Point(457, 368);
        System.out.println(p1.slopeTo(p2));
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else if (this.y == that.y && this.x == that.x) {
            return 0;
        } else {
            return 1;
        }
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else {
            return (double) (that.y - this.y) / (double) (that.x - this.x);
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new PointComparator(this);

    }

    private class PointComparator implements Comparator<Point> {

        private Point center;

        public PointComparator(Point center) {
            this.center = center;
        }

        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = o1.slopeTo(center);
            double slope2 = o2.slopeTo(center);
            return slope1 == slope2 ? 0 : (slope1 > slope2 ? 1 : -1);
        }
    }

}
