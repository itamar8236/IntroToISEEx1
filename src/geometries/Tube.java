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
    public List<Point3D> findIntersections(Ray ray) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        Point3D Pa = axisRay.getP0();
        Vector Va = axisRay.getDir();

        double A, B, C;

        Vector VecA = v;
        double Vva = v.dotProduct(Va);

        try {
            if (!isZero(Vva))
                VecA = v.subtract(Va.scale(Vva));

            A = VecA.lengthSquared();
        }
        catch (IllegalArgumentException ex) { // the ray is parallel to the axisRay
            return null;
        }

        try {
            Vector DeltaP = P0.subtract(Pa);
            Vector DeltaPMinusDeltaPVaVa = DeltaP;
            double DeltaPVa = DeltaP.dotProduct(Va);

            if (!isZero(DeltaPVa))
                DeltaPMinusDeltaPVaVa = DeltaP.subtract(Va.scale(DeltaPVa));

            B = 2 * (VecA.dotProduct(DeltaPMinusDeltaPVaVa));
            C = DeltaPMinusDeltaPVaVa.lengthSquared() - radius * radius;
        }
        catch (IllegalArgumentException ex) {
            B = 0;
            C = -1 * radius * radius;
        }

        double Disc = alignZero(B * B - 4 * A * C);

        if (Disc <= 0)
            return null;

        double t1, t2;

        t1 = alignZero(-B + Math.sqrt(Disc)) / (2 * A);
        t2 = alignZero(-B - Math.sqrt(Disc)) / (2 * A);

        if (t1 > 0 && t2 > 0)
            return List.of(P0.add(v.scale(t1)), P0.add(v.scale(t2)));
        if (t1 > 0)
            return List.of(P0.add(v.scale(t1)));
        if (t2 > 0)
            return List.of(P0.add(v.scale(t2)));

        return null;
    }
}
