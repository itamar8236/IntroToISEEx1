/**
 * @author Avraham & Itamar
 */

package elements;
import primitives.Color;

/**
 * Class for the ambient light in the scene
 */
public class AmbientLight extends Light {
    /**
     * Constructor
     * @param Ia The intensity color
     * @param Ka The reduction coefficient
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * Default constructor, intensity is black.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}
