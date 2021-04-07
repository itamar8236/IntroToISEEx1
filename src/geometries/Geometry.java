package geometries;

import primitives.*;

/**
 * interface for Geometry objects
 */
public interface Geometry extends Intersectable {
    /**
     * calculate the normal from the geometry body in specific point
     * @param point3D the point on the body
     * @return the normal to the body
     */
    public Vector getNormal(Point3D point3D);
}
