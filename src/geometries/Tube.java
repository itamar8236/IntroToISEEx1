package geometries;

import primitives.*;
/**
 * class for represent Cylinder
 */
public class Tube implements Geometry{
    /**
     * the axis ray of the Cylinder
     */
    protected Ray axisRay;
    /**
     * the radius of the Cylinder
     */
    protected double radius;

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

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
}
