package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface for all light sources. have 2 functions,
 * that calculate and returns the intensity  and the direction of the light source.
 */
public interface LightSource {
    /**
     * giving the intensity of the light source in point
     * @param p the point that need to calculate it's intensity
     * @return the intensity of the light source in point p
     */
    public Color getIntensity(Point3D p);

    /**
     * giving the directory of the light source in according to a 3D point
     * @param p the point to get the light's direction
     * @return the direction of the light in that point
     */
    public Vector getL(Point3D p);

    /**
     * TODO - comments
     * @param point
     * @return
     */
    public double getDistance(Point3D point);
}
