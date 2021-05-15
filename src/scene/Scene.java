package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Class to represent scene
 */
public class Scene {
    /**
     * The scene's name
     */
    public final String name;
    /**
     * The scene background color, default is black
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light of the scene
     */
    public AmbientLight ambientLight;
    /**
     * The objects in the scene
     */
    public Geometries geometries = null;

    /**
     * Constructor
     * @param name The scene's name
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * Set fot background, using chaining methods
     * @param background The bacground color
     * @return The scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set for the ambient life, using chaining methods
     * @param ambientLight The ambient light to set
     * @return The scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set for geometries, using chaining methods
     * @param geometries The geometries objects
     * @return The scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
