package renderer;

import static geometries.Intersectable.GeoPoint;

import elements.LightSource;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
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
     *@param ray the ray
     * @return color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Point3D p = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(p);
        double kd = geoPoint.geometry.getMaterial().kD;
        double ks = geoPoint.geometry.getMaterial().kS;
        double nShininess = geoPoint.geometry.getMaterial().nShininess;
        Vector v = ray.getDir();

        Color lightsColor = Color.BLACK;
        for (LightSource ls : scene.lights) {
            Vector l = ls.getL(p);
            double ln = l.dotProduct(n);
            double vn = v.dotProduct(n);
            if (ln * vn < 0) {
                continue;
            }

            Vector r = l.subtract(n.scale(2 * ln)).normalize();
            double vr = v.dotProduct(r);
            double lightEffect = kd * Math.abs(ln) + ks * Math.pow(Math.max(0, -vr),nShininess);
            lightsColor = lightsColor.add(ls.getIntensity(p).scale(lightEffect));
        }
        Color emissionColor = geoPoint.geometry.getEmission();
        Color ambientLightColor = scene.ambientLight.getIntensity();

        lightsColor = lightsColor.add(emissionColor)
                .add(ambientLightColor);
        return lightsColor;
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null ) {
            return scene.background;
        }
        return calcColor(ray.findClosestGeoPoint(intersections), ray);
    }


}
