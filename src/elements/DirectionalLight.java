package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class to represent directional light, light with position and direction
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * the direction of the light
     */
    private final Vector direction;
    /**
     * ctor
     * @param intensity the intensity light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return direction.normalized();
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
