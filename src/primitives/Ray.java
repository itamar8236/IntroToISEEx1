/**
 * @author Avraham & Itamar
 */

package primitives;
import static geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;

/**
 * Ray class is basic geometric class to represent geometric ray in space.
 * The class is using the basic classes Vector & Point3D to represent a ray.
 */
public class Ray {
    /**
     * The 3D point value represent the p0 - starting point of the ray.
     */
    final Point3D p0;

    /**
     * The direction of the ray value, as vector(normalized).
     */
    final Vector dir;

    /**
     * Delta for addition to the Ray to avoid unnecessary self-cutting
     */
    private static final double DELTA = 0.1;

    /**
     * Constructor for Ray class
     * @param p0  The p0 start point
     * @param dir The direction of the ray as vector
     */
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    /**
     * Constructor for Ray class, adding Delta from the start point to avoid unnecessary self-cutting
     * @param point The p0 start point
     * @param lightDirection The direction of the ray as vector
     * @param n normal of the object that include "point" for know the direction to add the Delta
     */
    public Ray(Point3D point, Vector lightDirection, Vector n) {
        double vn = lightDirection.dotProduct(n);
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : - DELTA);
        p0 = point.add(delta);
        dir = lightDirection.normalized();
    }

    /**
     * Get the head point of the ray
     * @return p0 - starting point of the ray
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * Get the direction of the ray
     * @return the vector that represents the direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * get point on the ray with distance from P0
     * @param t the distance
     * @return the point on the ray
     */
    public Point3D getPoint (double t){
        return p0.add(dir.scale(t));
    }

    /**
     * finding the closest point to the start of the ray
     * @param point3DS  list of 3D points
     * @return point that is the closest point to the start of the ray
     */
    public Point3D findClosestPoint (List<Point3D> point3DS) {

        Point3D result = null;
        double closestDis = Double.MAX_VALUE;
        if (point3DS != null) {
            for (Point3D p : point3DS) {
                double temp = p.distanceSquared(p0);
                if (temp < closestDis) {
                    closestDis = temp;
                    result = p;
                }
            }
        }
        return result;
    }

    /**
     * finding the closest GeoPoint to the start of the ray
     * @param geoPoints list of GeoPoints
     * @return GeoPoint that is the closest GeoPoint to the start of the ray
     */
    public GeoPoint findClosestGeoPoint (List<GeoPoint> geoPoints) {

        GeoPoint result = null;
        double closestDis = Double.MAX_VALUE;
        if (geoPoints != null) {
            for (GeoPoint geoPoint : geoPoints) {
                double temp = geoPoint.point.distanceSquared(p0);
                if (temp < closestDis) {
                    closestDis = temp;
                    result = geoPoint;
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "p0=" + p0 +
                ", dir=" + dir;
    }
}
