package geometries;

import primitives.*;
import java.util.List;

/**
 * interface for 3D bodies that can be intersected by ray
 */
public interface Intersectable {

    /**
     * @param ray the ray that intersect the geometry body.
     * this function calculate the intersections between the ray and the geometry body.
     * @return List of the 3DPoints of the intersections, and null if there are no intersections.
     */
    List<Point3D> findIntersections(Ray ray);
}
