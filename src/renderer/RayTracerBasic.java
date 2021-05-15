package renderer;

import static geometries.Intersectable.GeoPoint;

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
     * @param geoPoint the point that needed the calculate it color
     * @return color of the point
     */
    private Color calcColor(GeoPoint geoPoint) {
        Color emission = geoPoint.geometry.getEmission();
        return scene.ambientLight.getIntensity().add(emission);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null ) {
            return scene.background;
        }
        return calcColor(ray.findClosestGeoPoint(intersections));
    }


}
