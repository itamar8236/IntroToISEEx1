/**
 * @author Avraham & Itamar
 */

package geometries;
import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * class for represent Tube
 */
public class Tube extends Geometry {
    /**
     * the axis ray of the Tube
     */
    protected final Ray axisRay;

    /**
     * the radius of the Tube
     */
    protected final double radius;

    /**
     * constructor
     * @param axisRay the axis ray of the tube
     * @param radius the radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * Getter the axis ray of the tube
     * @return The exis ray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Getter the radius of the tube
     * @return The radius
     */
    public double getRadius() {
        return radius;
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

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        Point3D Pa = axisRay.getP0();
        Vector Va = axisRay.getDir();

        //coefficient for the At^2 + Bt + C equation.
        double A, B, C;

        //(v,u) = v dot product u
        //calculate the vector: V-(V,Va)Va
        Vector VecA = v;
        double Vva = v.dotProduct(Va);

        try {
            if (!isZero(Vva))
                VecA = v.subtract(Va.scale(Vva));

            //A = (V-(V,Va)Va)^2
            A = VecA.lengthSquared();
        }
        //if A = 0 return null (there are no intersections)
        catch (IllegalArgumentException ex) { // the ray is parallel to the axisRay
            return null;
        }

        try {
            //calculate deltaP (delP) vector, P-Pa
            Vector DeltaP = P0.subtract(Pa);
            //The vector: delP - (delP,Va)Va
            Vector DeltaPMinusDeltaPVaVa = DeltaP;
            double DeltaPVa = DeltaP.dotProduct(Va);

            if (!isZero(DeltaPVa))
                DeltaPMinusDeltaPVaVa = DeltaP.subtract(Va.scale(DeltaPVa));

            //B = 2(V - (V,Va)Va , delP - (delP,Va)Va)
            B = 2 * (VecA.dotProduct(DeltaPMinusDeltaPVaVa));

            //C = (delP - (delP,Va)Va)^2 - r^2
            C = DeltaPMinusDeltaPVaVa.lengthSquared() - radius * radius;
        }
        //in case delP = 0, or delP - (delP,Va)Va = (0, 0, 0)
        catch (IllegalArgumentException ex) {
            B = 0;
            C = -1 * radius * radius;
        }
        //solving At^2 + Bt + C = 0
        //the discrimation, B^2 - 4AC
        double Disc = alignZero(B * B - 4 * A * C);

        //no solutions for the equation. disc = 0 means that the ray parallel to the tube
        if (Disc <= 0)
            return null;

        //the solutions for the equation
        double t1, t2;

        t1 = alignZero(-B + Math.sqrt(Disc)) / (2 * A);
        t2 = alignZero(-B - Math.sqrt(Disc)) / (2 * A);

        //if t1 or t2 are bigger than the max distance, wll be set to negative value and won't count as intersections points
        if(alignZero(t1-maxDistance)>0)
            t1 = -1;
        if(alignZero(t2-maxDistance)>0)
            t2 = -1;

        //taking all positive solutions
        if (t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        if (t1 > 0)
            return List.of(new GeoPoint(this , ray.getPoint(t1)));
        if (t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));

        //all non-positive solutions
        return null;
    }
}
