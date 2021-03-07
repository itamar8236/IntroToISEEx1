package geometries;

import primitives.*;

/**
 * class for represent Cylinder
 */
public class Tube implements Geometry {
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
}
