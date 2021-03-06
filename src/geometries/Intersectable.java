/**
 * @author Avraham & Itamar
 */

package geometries;
import primitives.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * interface for 3D bodies that can be intersected by ray
 */
public interface Intersectable {
    /**
     * class for represent point and geometry of the point
      */
    public static class GeoPoint {
        /**
         * constructor
         * @param geometry the geometry in the 3D point
         * @param point the 3D point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * the geometry of the point
         */
        public Geometry geometry;

        /**
         * the 3D point
         */
        public Point3D point;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
    }

    /**
     * @param ray the ray that intersect the geometry body.
     * this function calculate the intersections between the ray and the geometry body.
     * @return List of the 3DPoints of the intersections, and null if there are no intersections.
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * finding the intersections with the ray to infinity
     * @param ray the ray
     * @return the list of the intersections as geoPoints
     */
    default List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * finding the intersections with the ray that closer then a certain distance
     * @param ray the ray
     * @param maxDistance the maximum distance for the intersections
     * @return list of GeoPoint of the intersections that closer from the max distance
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);
}