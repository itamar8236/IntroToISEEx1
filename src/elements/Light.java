/**
 * @author Avraham & Itamar
 */

package elements;
import primitives.Color;

/**
 * abstract class to represent various kinds of lights
 */
abstract class Light {
    /**
     * The intensity color
     */
    final protected Color intensity;

    /**
     * constructor
     * @param intensity the intensity light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for the intensity
     * @return The ambient light intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
