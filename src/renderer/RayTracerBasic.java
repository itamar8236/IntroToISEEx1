package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class to represent ray tracer
 */
public class RayTracerBasic extends RayTracerBase {
    /**
     * ctor of the class, based on the super class
      * @param scene the scene of the image
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * calculate the color of point
     * @param point3D the point that needed the calculate it color
     * @return color of the point
     */
    private Color calcColor(Point3D point3D) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null ) {
            return scene.background;
        }
        return calcColor(ray.findClosestPoint(intersections));
    }


}
