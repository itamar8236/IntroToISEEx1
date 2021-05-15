package elements;
import primitives.Color;

/**
 * Class for the ambient light iin the scene
 */
public class AmbientLight {
    /**
     * The intensity color
     */
    final private Color intensity;

    /**
     * Constructor
     * @param Ia The intensity color
     * @param Ka The reduction coefficient
     */
    public AmbientLight(Color Ia, double Ka) {
        this.intensity = Ia.scale(Ka);
    }

    /**
     * Getter for the intensity
     * @return The ambient light intensity
     */
    public Color getIntensity() {
        return intensity;
    }

    /**
     * Default constructor, intensity is black.
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }
}
