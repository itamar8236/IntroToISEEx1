package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * class for represent Sphere
 */
public class Sphere extends Geometry {
    /**
     * point of the center of the sphere
     */
    final Point3D center;
    /**
     * the radius of the sphere
     */
    final double radius;

    /**
     * ctor
     *
     * @param center is the center of the sphere
     * @param radius is the radius of the sphere
     */
    public Sphere(double radius, Point3D center) {
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
        Vector N = point3D.subtract(center);

        N.normalize();

        return N;
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        if (center.equals(P0)) {
            throw new IllegalArgumentException("ray origin cannot be at the Sphere's center");
        }
        try {
            // u = O - P0
            Vector u = center.subtract(P0);

            // tm = v dot product u
            double tm = alignZero(u.dotProduct(v));
            // d = sqrt(u^2 - tm^2)
            double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

            // there are no intersections
            if (d >= radius)
                return null;

            // th = sqrt( r^2 - d^2)
            double th = alignZero(Math.sqrt(radius * radius - d * d));

            // the solutions are tm +- th
            double t1 = alignZero(tm - th);
            double t2 = alignZero(tm + th);

            // take only 𝒕 > 𝟎:
            if (t1 > 0 && t2 > 0)
                return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));

            else if (t1 > 0)
                return List.of(new GeoPoint(this, ray.getPoint(t1)));

            else if (t2 > 0)
                return List.of(new GeoPoint(this, ray.getPoint(t2)));

        } catch (IllegalArgumentException ex) { // the P0 point is the center of the sphere
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        }

        return null;
    }
}
