package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

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
        //case 1 - point on the bottom base
        if(point3D.subtract(axisRay.getP0()).lengthSquared() < radius * radius)
            return axisRay.getDir().scale(-1);
        else {
            Point3D p = axisRay.getP0().add(axisRay.getDir().scale(height));
            //case 2 - point on the upper base
            if (point3D.subtract(p).lengthSquared() < radius * radius)
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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D p1 = axisRay.getP0();
        Point3D p2 = p1.add(axisRay.getDir().scale(height));
        Vector Va = axisRay.getDir();
        List<Point3D> lst = super.findIntersections(ray);

        List<Point3D> result = new LinkedList<>();

        if (lst != null) {
            for (Point3D p : lst) {
                if (Va.dotProduct(p.subtract(p1)) > 0 && Va.dotProduct(p.subtract(p2)) < 0)
                    result.add(p);
            }
        }

        Plane bottomBase = new Plane(p1, Va);
        Plane upperBase = new Plane(p2, Va);
        Point3D p;

        // ======================================================
        // intersection with the bases:
        lst = bottomBase.findIntersections(ray);

        if  (lst != null) {
            p = lst.get(0);
            if (p.distanceSquared(p1) < radius * radius)
                result.add(p);
        }

        lst = upperBase.findIntersections(ray);

        if  (lst != null) {
            p = lst.get(0);
            if (p.distanceSquared(p2) < radius * radius)
                result.add(p);
        }

        return result.size() == 0 ? null : result;




    }
}
