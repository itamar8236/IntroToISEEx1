/**
 * @author Avraham & Itamar
 */

package primitives;
import java.util.Objects;

/**
 * Class Point3D is basic geometric object to represent 3D point.
 * The class is using the basic class Coordinate to save the value of the 3D point.
 */
public class Point3D {
    /**
     * The x coordinate value. "package-friendly".
     */
    final Coordinate x;
    /**
     * The y coordinate value. "package-friendly".
     */
    final Coordinate y;
    /**
     * The z coordinate value. "package-friendly".
     */
    final Coordinate z;
    /**
     * static field, represent the origin. the 3D point (0,0,0)
     */
    public static final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * constructor
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        // this(x.coord, y.coord, z.coord);
        // for better performance we had modify to:
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor
     * @param x the x coordinate value
     * @param y the y coordinate value
     * @param z the z coordinate value
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * getter of the X coordinate
     * @return the X coordinate
     */
    public double getX() {
        return x.coord;
    }

    /**
     * getter of the Y coordinate
     * @return the Y coordinate
     */
    public double getY() {
        return y.coord;
    }

    /**
     * getter of the Z coordinate
     * @return the Z coordinate
     */
    public double getZ() {
        return z.coord;
    }

    /**
     * Calculate the distance, squared, between this point and another.
     * @param point3D the other point
     * @return the squared distance
     */
    public double distanceSquared(Point3D point3D) {
        double x1 = x.coord;
        double y1 = y.coord;
        double z1 = z.coord;
        double x2 = point3D.x.coord;
        double y2 = point3D.y.coord;
        double z2 = point3D.z.coord;

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
                x.coord + vector.head.x.coord,
                y.coord + vector.head.y.coord,
                z.coord + vector.head.z.coord);
    }

    /**
     * Calculate vector as subtraction between 2 points
     * @param other the second point
     * @return the vector from the current point to the other point
     */
    public Vector subtract(Point3D other) {
        return new Vector(
                x.coord - other.x.coord,
                y.coord - other.y.coord,
                z.coord - other.z.coord
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x.coord, y.coord, z.coord);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
