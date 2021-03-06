package geometries;

import primitives.*;

public class Sphere implements Geometry {
    Point3D _center;
    double _radius;

    public Sphere(Point3D center, double radius) {
        this._center = center;
        this._radius = radius;
    }

    public Point3D getCenter() {
        return _center;
    }

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
