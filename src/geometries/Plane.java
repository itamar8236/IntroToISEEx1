package geometries;

import primitives.*;

public class Plane implements Geometry {
    Point3D _q0;
    Vector _normal;

    public Plane(Point3D q0, Vector normal) {
        this._q0 = q0;
        this._normal = normal;
    }


    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        _normal = null; // need implementation
    }

    public Point3D getQ0() {
        return _q0;
    }

    public Vector getNormal() {
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + _q0 +
                ", normal=" + _normal +
                '}';
    }
}
