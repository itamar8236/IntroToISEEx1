package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public interface LightSource {
    /**
     * giving the intensity of the light source in point
     * @param p the point that need it intensity
     * @return the intensity of the light source in point p
     */
    public Color getIntensity(Point3D p);

    /**
     * giving the directory of the light source in according to the point
     * @param p the point
     * @return the directory
     */
    public Vector getL(Point3D p);



}
