package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * light from point
 */
public class PointLight extends Light implements LightSource{
    /**
     * position of the light
     */
    private final Point3D position;
    /**
     * Attenuation factors:
     */

    private double kC;
    private double kL;
    private double kQ;

    /**
     * setter of the kC
     * @param kC
     * @return this class
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * ctor
     *
     * @param intensity the intensity light
     * @param position
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
