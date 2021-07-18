/**
 * @author Avraham & Itamar
 */

package scene;
import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;
import java.util.LinkedList;
import java.util.List;

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
    public AmbientLight ambientLight = new AmbientLight();

    /**
     * The objects in the scene
     */
    public Geometries geometries = null;

    /**
     * list of the lights in the scene
     */
    public List<LightSource> lights = new LinkedList<LightSource>();

    /**
     * Constructor
     * @param name The scene's name
     */
    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
    }

    /**
     * setter of the list lights, using chaining methods
     * @param lights the list
     * @return this class
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Setter for background, using chaining methods
     * @param background The bacground color
     * @return The scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter for the ambient life, using chaining methods
     * @param ambientLight The ambient light to set
     * @return The scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter for geometries, using chaining methods
     * @param geometries The geometries objects
     * @return The scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
