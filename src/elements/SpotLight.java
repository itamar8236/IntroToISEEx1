package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * spot light - light from one point into a direction,
 * the light's intensity is weaker as the angle is far from the main direction.
 */
public class SpotLight extends PointLight {
    /**
     * the main direction of the light
     */
    final private Vector direction;

    /**
     * constructor of the class
     *  @param intensity the intensity light
     * @param position the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
    }


    @Override
    public Vector getL(Point3D p) {
        return super.getL(p);
    }

}
