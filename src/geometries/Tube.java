package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;
/**
 * class for represent Cylinder
 */
public class Tube implements Geometry {
    /**
     * the axis ray of the Cylinder
     */
    protected final Ray axisRay;
    /**
     * the radius of the Cylinder
     */
    protected final double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        Vector PP0 = point3D.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(PP0);

        //if the vector pp0 is vertical to the direction, and the scale product returns the 0 vector.
        if(alignZero(t) == 0)
            return PP0.normalized();

        Point3D O = axisRay.getP0().add(axisRay.getDir().scale(t));

        Vector N = point3D.subtract(O);

        N.normalize();

        return N;
    }

    /**
     * Get the axis ray of the tube
     * @return The exis ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Get the radius of the tube
     * @return The radius
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }
}
