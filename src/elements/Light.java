package elements;

import primitives.Color;

abstract class Light {
    /**
     * The intensity color
     */
    final protected Color intensity;

    /**
     * ctor
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
