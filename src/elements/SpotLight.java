package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * spot light - light with direction from some position
 */
public class SpotLight extends PointLight {
    /**
     * the direction of the light
     */
    final private Vector direction;

    /**
     * ctor of the class
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
