package geometries;

import primitives.*;

/**
 * class for represent Cylinder
 */
public class Cylinder extends Tube {
    final double height;

    /**
     * Constructor for Cylinder class.
     *
     * @param height are the height of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
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
        //case 1 - point on the buttomm base
        if(point3D.subtract(axisRay.getP0()).length() < radius)
            return axisRay.getDir().scale(-1);
        else {
            Point3D p = axisRay.getP0().add(axisRay.getDir().scale(height));
            //case 2 - point on the upper base
            if (point3D.subtract(p).length() < radius)
                return axisRay.getDir();
            //case 3 - point on the side, tube's case.
            else
                return super.getNormal(point3D);
        }
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
