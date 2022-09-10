public class Circle {

    /*
     * Here, you should define private variables that represent the radius and
     * centre of this particular Circle. The radius should be of type double,
     * and the centre should be of type Point.
     */
    private double r;
    private Point c;

    // =========================
    // Constructors
    // =========================
    /**
     * Default constructor - performs no initialization.
     */
    public Circle() {
        // This method is complete.
    }

    /**
     * Alternative constructor, which sets the circle up with x and y
     * co-ordinates representing the centre, and a radius. Remember you should
     * not store these x and y co-ordinates explicitly, but instead create a
     * Point to hold them for you.
     *
     * @param xc   x-coordinate of the centre of the circle
     * @param yc   y-coordinate of the centre of the circle
     * @param rad  radius of the circle
     */
    public Circle(double xc, double yc, double rad) {
        // You need to fill in this method.
        c = new Point(xc, yc);
        r=rad;
    }

    /**
     * Alternative constructor, which sets the circle up with a Point
     * representing the centre, and a radius.
     *
     * @param centre  Point representing the centre
     * @param rad     Radius of the circle
     */

    public Circle(Point centre, double rad) {
        // You need to fill in this method.
        c = centre;
        r=rad;
    }

    // =========================
    // Setters and Getters
    // =========================

    /**
     * Setter - sets the co-ordinates of the centre.
     *
     * @param xc  new x-coordinate of the centre
     * @param yc  new y-coordinate of the centre
     */
    public void setCentre(double xc, double yc) {
        // You need to fill in this method.
        c = new Point(xc ,yc);
    }

    /**
     * Setter - sets the centre of the circle to a new Point.
     *
     * @param C  A Point representing the new centre of the circle.
     */
    public void setCentre(Point A) {
        // You need to fill in this method.
        c = A;
    }

    /**
     * Setter - change the radius of this circle.
     *
     * @param rad  New radius for the circle.
     */
    public void setRadius(double rad) {
        // You need to fill in this method.
        r=rad;
    }

    /**
     * Getter - returns the centre of this circle.
     *
     * @return The centre of this circle (a Point).
     */
    public Point getCentre(){
        // You need to fill in this method.
        return c;
    }

    /**
     * Getter - extract the radius of this circle.
     *
     * @return The radius of this circle.
     */
    public double getRadius(){
        // You need to fill in this method.
        return r;
    }

    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Circle.
     *
     * @return A String of the form: "[Ax,Ay], r=Radius" where Ax and Ay are
     *         numerical values of the coordinates, and Radius is a numerical
     *         value of the radius.
     */
    public String toString() {
        // You need to fill in this method.
        double ax = c.getX();
        double ay = c.getY();
        return "["+ax+","+ay+"], r="+r;
    }

    // ==========================
    // Service routines
    // ==========================

    /**
     * Similar to the equals() function in Point. Returns true if two Circles
     * are equal. By this we mean:
     *
     * - They have the same radius (up to tolerance).
     * - They have the same centre (up to tolerance).
     *
     * Remember that the second test is already done in the Point class!
     *
     * @return true if the two circles are equal.
     */
    public boolean equals(Circle c) {
        // You need to fill in this method.
        if (Math.abs(c.getRadius()-r)<=Point.GEOMTOL) {
            Point pc = c.getCentre();
            return pc.equals(this.c);
        }
        return false;
    }

    // -----------------------------------------------------------------------
    // Do not change the method below! It is essential for marking the
    // project, and you may lose marks if it is changed.
    // -----------------------------------------------------------------------

    /**
     * Compare this Circle with some Object, using the test above.
     *
     * @param obj  The object to compare with.
     * @return true if the two objects are equal.
     */
    public boolean equals(Object obj) {
        // This method is complete.

        if (obj instanceof Circle) {
            boolean test = false;
            Circle C = (Circle)obj;

            test = this.equals(C);

            return test;
        } else {
            return false;
        }
    }

    // ======================================
    // Implementors
    // ======================================

    /**
     * Computes and returns the area of the circle.
     *
     * @return  Area of the circle
     */
    public double area() {
        // You need to fill in this method.
        return Math.PI*r*r;
    }

    /**
     * Tests whether this circle envelops another
     * Circle, as set out in the project formulation.
     *
     * @return An integer describing the overlap with C:
     *         1 - this envelops c; 0 - Neither envelops; -1 c envelops this circle ; -2 - identical.
     */
    public int envelops(Circle C) {
        // You need to fill in this method.
        Circle A= new Circle(c,r);
        Point q = C.getCentre();
        if (A.equals(C)) {
            return -2;
        }
        else if (C.getRadius()<r-c.distance(q)){
            return -1;
        }
        else if (r<C.getRadius()-c.distance(q)){
            return 1;
        }
        else {
            return 0;
        }

    }


    // =======================================================
    // Tester - test methods defined in this class
    // =======================================================

    public static void main(String[] args) {
        // You need to fill in this method.
        Circle a = new Circle(9.501293e-01, 2.311385e-01, 1.213685e+00);
        double ar = a.getRadius();
        System.out.println(ar);
        Point bc = new Point(0,3);
        Circle b = new Circle(3.358776e-01, 8.906898e-01, 4.762747e-01);
        Point k = b.getCentre();
        System.out.println(k);
        String h = a.toString();
        System.out.println(h);
        boolean d= a.equals(b);
        System.out.println(d);
        double area = b.area();
        System.out.println(area);
        int s= a.envelops(b);
        System.out.println(s);

    }
}