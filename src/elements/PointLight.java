package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class to represent light source from a 3D point to all directions
 */
public class PointLight extends Light implements LightSource{
    /**
     * The position of the light
     */
    private final Point3D position;
    /**
     * Attenuation factors:
     */

    /**
     * The constant attenuation factor
     */
    private double kC;
    /**
     * The linear attenuation factor
     */
    private double kL;
    /**
     * The quadratic attenuation
     */
    private double kQ;

    /**
     * Setter for the constant attenuation factor.
     * @param kC The factor to set
     * @return The point light
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for the linear attenuation factor.
     * @param kL The factor to set
     * @return The point light
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for the quadratic attenuation factor.
     * @param kQ The factor to set
     * @return The point light
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * constructor
     * @param intensity the intensity of the light source
     * @param position The position of the light source
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        kC = 1;
        kL = 0;
        kQ = 0;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(position);
        double attenuator = 1d/(kC + kL * d + kQ * d * d);
        return intensity.scale(attenuator);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalize();
    }
}
