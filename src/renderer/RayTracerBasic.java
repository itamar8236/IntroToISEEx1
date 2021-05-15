package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {
    // ctor of the class
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

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
