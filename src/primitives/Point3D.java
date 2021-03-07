package primitives;

import java.util.Objects;

/**
 * Class Point3D is basic geometric object to represent 3D point.
 * The class is using the basic class Coordinate to save the value of the 3D point.
 *
 * @author Avraham Glasberg & Itamar Cohen
 */
public class Point3D {
    /**
     * The x coordinate value. "package-friendly".
     */
    final Coordinate _x;
    /**
     * The y coordinate value. "package-friendly".
     */
    final Coordinate _y;
    /**
     * The z coordinate value. "package-friendly".
     */
    final Coordinate _z;
    public static final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        // this(x.coord, y.coord, z.coord);
        // for better performance we had modify to:
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * Constructor for 3D Point
     * @param x the x coordinate value
     * @param y the y coordinate value
     * @param z the z coordinate value
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x.coord, _y.coord, _z.coord);
    }

    /**
     * Calculate the distance, squared, between this point and another.
     * @param point3D the other point
     * @return the squared distance
     */
    public double distanceSquared(Point3D point3D) {
        double x1 = _x.coord;
        double y1 = _y.coord;
        double z1 = _z.coord;
        double x2 = point3D._x.coord;
        double y2 = point3D._y.coord;
        double z2 = point3D._z.coord;

        return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1);
    }

    /**
     * Calculate the distance, between this point and another.
     * @param point3D the other point
     * @return the distance
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    /**
     * Adds a vector to the point
     * @param vector the vector to add
     * @return the point of the head of the new vector from the current point with the vector added
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                _x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord);
    }

    /**
     * Calculate vector as subtraction between 2 points
     * @param other the second point
     * @return the vector from the current point to the other point
     */
    public Vector subtract(Point3D other) {
        return new Vector(
                _x.coord - other._x.coord,
                _y.coord - other._y.coord,
                _z.coord - other._z.coord
        );
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ")";
    }
}
