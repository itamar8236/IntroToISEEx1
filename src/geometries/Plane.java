package geometries;

import primitives.*;

import static primitives.Util.*;

import java.util.List;

/**
 * class for represent Plane
 */
public class Plane implements Geometry {
    /**
     * q0 is point in the plane
     */
    final Point3D q0;
    /**
     * normal of the plane
     */
    final Vector normal;

    /**
     * ctor
     *
     * @param q0     some point on the plane
     * @param normal the vector normal of the plane
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     * ctor that build normal from 2 vector that build from 3 points
     *
     * @param p1 point in the plane
     * @param p2 point in the plane
     * @param p3 point in the plane
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        q0 = p1;

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        N.normalize();
        normal = N;
    }

    /**
     * get of q0
     *
     * @return Point3D p0
     */
    public Point3D getQ0() {
        return q0;
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        // the start of the ray
        Point3D P0 = ray.getP0();
        // the direction of the ray
        Vector v = ray.getDir();

        // nv is the denominator of calculate t
        double nv = normal.dotProduct(v);
        if (isZero(nv)) // the ray parallel to the plane
            return null;
        try {
            // the solution of the intersection
            double t = alignZero(normal.dotProduct(q0.subtract(P0)) / nv);

            if (t > 0) // take only positive solution
                return List.of(P0.add(v.scale(t)));

        } catch (IllegalArgumentException ex) { // when P0 = q0
            return null;
        }

        return null;
    }
}
