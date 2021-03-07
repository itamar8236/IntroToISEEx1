package geometries;

import primitives.*;

/**
 * class for represent Sphere
 */
public class Sphere implements Geometry {
    /**
     * point of the center of the sphere
     */
    Point3D center;
    /**
     * the radius of the sphere
     */
    double radius;

    /**
     * ctor
     *
     * @param center is the center of the sphere
     * @param radius is the radius of the sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * get of center
     *
     * @return center
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * get of radius
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
