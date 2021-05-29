package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import elements.LightSource;
import geometries.Geometry;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * class to represent ray tracer
 */
public class RayTracerBasic extends RayTracerBase {
    /**
     * ctor of the class, based on the super class
     *
     * @param scene the scene of the image
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     * TODO
     */
    private static final double INITIAL_K = 1.0;


    /**
     * calculate the color of point
     *
     * @param closestPoint the point that needed the calculate it color
     * @param ray          the ray
     * @return color of the point
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }


    /**
     * TODO
     *
     * @param ls
     * @param l
     * @param n
     * @param geoPoint
     * @return
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1);
        //Ray from point towards light direction offset by delta
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries
                .findGeoIntersections(lightRay, ls.getDistance(geoPoint.point));
        if (intersections == null) {
            return 1.0;
        }
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            ktr *= gp.geometry.getMaterial().kt;
            if (ktr < MIN_CALC_COLOR_K) {
                return 0.0;
            }
        }
        return ktr;
    }


    /**
     * Calculate the local effect on the light
     *
     * @param intersection The point on it you calculate the effects
     * @param ray          The camera view ray
     * @return The color with the local effects
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }

            }
        }
        return color;
    }

    /**
     * TODO
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * TODO
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Calculate the local effect on the light
     * TODO
     *
     * @param gp
     * @param v
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kr;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kr, kkr);
        double kkt = k * material.kt;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kt, kkt));
        return color;
    }

    /**
     * TODO
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)
        ).scale(kx);
    }

    /**
     * TODO
     *
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * TODO
     *
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * TODO
     *
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        double vn = v.dotProduct(n);
        Vector r = v.subtract(n.scale(2 * (vn)));

        return new Ray(point, r, n);
    }


    /**
     * Calculate the specular color according to the formula in phong's module :
     * Ks * (max(0,-v*r))^Nsh*Il
     *
     * @param ks             ks of  the formula
     * @param l              l of  the formula
     * @param n              n of  the formula
     * @param v              v of  the formula
     * @param nShininess     nShininess of  the formula
     * @param lightIntensity lightIntensity of  the formula
     * @return The specular color
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double minusVr = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess));
    }

    /**
     * Calculate the diffuse color according to the formula in phong's module :
     * 𝒌𝑫 ∙ |𝒍 ∙ 𝒏| ∙ Il
     *
     * @param kd             kd of  the formula
     * @param l              l of  the formula
     * @param n              n of  the formula
     * @param lightIntensity lightIntensity of  the formula
     * @return The diffuse color
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray)
                .add(closestPoint.geometry.getEmission());
    }
}
