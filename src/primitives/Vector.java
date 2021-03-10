package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

/**
 * Vector class is basic geometric class that represents a vector in space.
 * The class is using the basic class Point3D to save the head of the vector.
 *
 * @author Avraham Glasberg & Itamar Cohen
 */
public class Vector {
    /**
     * The head of the vector, represented as 3D point
     */
    Point3D head;

    /**
     * Constructor for Vector class
     *
     * @param head The head of the vector as 3D point.
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("head can't be zero point");
        }
        this.head = head;
    }

    /**
     * Constructor for Vector class
     *
     * @param x The x coordinate of the head of the vector.
     * @param y The y coordinate of the head of the vector.
     * @param z The z coordinate of the head of the vector.
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * Constructor for Vector class
     *
     * @param x The x coordinate value of the head of the vector.
     * @param y The y coordinate value of the head of the vector.
     * @param z The z coordinate value of the head of the vector.
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    /**
     * Get the head of the vector
     * Notice! the head is not final variable, but changing is changing the vector itself!
     *
     * @return The head of the vector.
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * Vector subtraction
     *
     * @param other the other vector to subtract
     * @return the subtraction between the vectors
     */
    public Vector subtract(Vector other) {
        Vector temp = ZERO.subtract(other.head);
        return this.add(temp);
    }

    /**
     * Vector addition
     *
     * @param other the other vector too add
     * @return The addition between the two vectors.
     */
    public Vector add(Vector other) {
        return new Vector(head.add(other));
    }

    /**
     * Scalar product
     *
     * @param scalar the scalar to multiple the vector with
     * @return The new vector after the multiplication.
     */
    public Vector scale(double scalar) {
        return new Vector(
                head.x.coord * scalar,
                head.y.coord * scalar,
                head.z.coord * scalar
        );
    }

    /**
     * Dot product between two vectors.
     *
     * @param other The other vector to multiple
     * @return The value of the dot product
     */
    public double dotProduct(Vector other) {
        return head.x.coord * other.head.x.coord +
                head.y.coord * other.head.y.coord +
                head.z.coord * other.head.z.coord;
    }

    /**
     * Cross product between two vectors
     *
     * @param other the other vector
     * @return The vertical vector to the two vectors (the cross product value).
     */
    public Vector crossProduct(Vector other) {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = other.head.x.coord;
        double v2 = other.head.y.coord;
        double v3 = other.head.z.coord;
        try {
            return new Vector(
                    u2 * v3 - u3 * v2,
                    u3 * v1 - u1 * v3,
                    u1 * v2 - u2 * v1
            );
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Cross product returned the zero vector!");
        }
    }

    /**
     * Calculate the distance, squared, of the vector.
     *
     * @return The squared distance
     */
    public double lengthSquared() {
        return head.distanceSquared(ZERO);
    }

    /**
     * Calculate the distance of the vector.
     *
     * @return The distance
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Calculate the vector  as normal (with length of 1)
     * This function does not change the vector itself.
     *
     * @return The normalized vector.
     */
    public Vector normalized() {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double len = this.length();
        return new Vector(u1 / len, u2 / len, u3 / len);
    }

    /**
     * Calculate the vector  as normal (with length of 1)
     * This function DOES change the vector itself, and so every other vector that was set as equal to this one!
     *
     * @return The normalized vector.
     */
    public Vector normalize() {
        head = this.normalized().head;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head);
    }

    @Override
    public String toString() {
        return "head=" + head;
    }
}
