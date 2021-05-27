package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * class to represent Cylinder
 */
public class Cylinder extends Tube {
    /**
     * the height of the cylinder
     */
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
        if (point3D.subtract(axisRay.getP0()).lengthSquared() < radius * radius)
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
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        //P1 and P2 in the cylinder, the center of the bottom and upper bases
        Point3D p1 = axisRay.getP0();
        Point3D p2 = axisRay.getPoint(height);
        Vector Va = axisRay.getDir();

        List<GeoPoint> lst = super.findGeoIntersections(ray, maxDistance);

        //the intersections with the finite cylinder
        List<GeoPoint> result = new LinkedList<>();

        //Step 1 - checking if the intersections with the tube are points on the finite cylinder
        if (lst != null) {
            for (GeoPoint p : lst) {
                if (Va.dotProduct(p.point.subtract(p1)) > 0 && Va.dotProduct(p.point.subtract(p2)) < 0)
                    result.add(0, p);
            }
        }

        //Step 2 - checking the intersections with the bases

        //cannot be more than 2 intersections
        if(result.size() < 2) {
            //creating 2 planes for the 2 bases
            Plane bottomBase = new Plane(p1, Va);
            Plane upperBase = new Plane(p2, Va);
            GeoPoint p;

            // ======================================================
            // intersection with the bases:

            //intersections with the bottom bases
            lst = bottomBase.findGeoIntersections(ray, maxDistance);

            if (lst != null) {
                p = lst.get(0);
                //checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p1) < radius * radius)
                    result.add(p);
            }

            //intersections with the upper bases
            lst = upperBase.findGeoIntersections(ray, maxDistance);

            if (lst != null) {
                p = lst.get(0);
                //checking if the intersection is on the cylinder base
                double d = p.point.distance(p2);
                if (p.point.distanceSquared(p2) < radius * radius)
                    result.add(p);
            }
        }
        //return null if there are no intersections.
        return result.size() == 0 ? null : result;


    }
}
