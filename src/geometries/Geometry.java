package geometries;

import primitives.*;

/**
 * interface for Geometry objects
 */
public interface Geometry extends Intersectable {
    public Vector getNormal(Point3D point3D);
}
