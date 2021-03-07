package geometries;

import primitives.*;

/**
 * class for represent Sphere
 */
public class Sphere implements Geometry {
    /**
     * point of the center of the sphere
     */
    Point3D _center;
    /**
     * the radius of the sphere
     */
    double _radius;

    /**
     * ctor
     * @param center is the center of the sphere
     * @param radius is the radius of the sphere
     */
    public Sphere(Point3D center, double radius) {
        this._center = center;
        this._radius = radius;
    }

    /**
     * get of center
     * @return center
     */
    public Point3D getCenter() {
        return _center;
    }
    /**
     * get of radius
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
