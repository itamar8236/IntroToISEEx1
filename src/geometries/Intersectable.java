package geometries;

import primitives.*;
import java.util.List;

/**
 *
 */
public interface Intersectable {

    /**
     *
     * @param ray
     * @return
     */
    List<Point3D> findIntsersections(Ray ray);
}