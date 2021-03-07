package geometries;

import primitives.*;

/**
 * class for represent Cylinder
 */
public class Cylinder extends Tube {
    double height;

    /**
     * Constructor for Cylinder class.
     *
     * @param height are the height of the Cylinder
     */
    public Cylinder(double height) {
        this.height = height;
    }

    /**
     * get for height
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
