package geometries;

import primitives.*;

/**
 * class for represent Plane
 */
public class Plane implements Geometry {
    /**
     * q0 is point in the plane
     */
    Point3D _q0;
    /**
     * normal of the plane
     */
    Vector _normal;

    /**
     * ctor
     *
     * @param q0     some point on the plane
     * @param normal the vector normal of the plane
     */
    public Plane(Point3D q0, Vector normal) {
        this._q0 = q0;
        this._normal = normal;
    }

    /**
     * ctor that build normal from 2 vector that build from 3 points
     *
     * @param p1 point in the plane
     * @param p2 point in the plane
     * @param p3 point in the plane
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        _normal = null; // need implementation
    }

    /**
     * get of q0
     *
     * @return Point3D p0
     */
    public Point3D getQ0() {
        return _q0;
    }

    /**
     * implaments of the interface Geometry
     *
     * @return normal
     */
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
