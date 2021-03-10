package geometries;

import primitives.*;

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

        Point3D O = axisRay.getP0().add(PP0.scale(t));

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
}
