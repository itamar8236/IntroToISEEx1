package primitives;

import java.util.Objects;

/**
 * Ray class is basic geometric class to represent geometric ray in space.
 * The class is using the basic classes Vector & Point3D to represent a ray.
 *
 * @author Avraham Glasberg & Itamar Cohen
 */
public class Ray {
    /**
     * The 3D point value represent the p0 - starting point of the ray.
     */
    final Point3D p0;
    /**
     * The direction of the ray value, as vector(normalized).
     */
    final Vector dir;

    /**
     * Constructor for Ray class
     *
     * @param p0  The p0 start point
     * @param dir The direction of the ray as vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    /**
     * Get the head point of the ray
     *
     * @return p0 - starting point of the ray
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * Get the direction of the ray
     *
     * @return the vector that represents the direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "p0=" + p0 +
                ", dir=" + dir;
    }
}
