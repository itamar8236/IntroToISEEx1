/**
 * @author Avraham & Itamar
 */

package renderer;
import primitives.Color;
import primitives.Ray;
import scene.Scene;
import java.util.List;

/**
 * abstract class to represent ray tracer
 */
public abstract class RayTracerBase {
    /**
     * scene of the image
     */
    protected Scene scene;

    /**
     * constructor of the class
     */
    public RayTracerBase(Scene scene) {
        if(scene == null)
            throw new UnsupportedOperationException("scene is null");
        this.scene = scene;
    }

    /**
     * function for finding the color of ray with scene
     * @param ray the ray from the pixel
     * @return the color of the pixel according to the intersections of the ray
     */
    public abstract Color traceRay(Ray ray);

    /**
     * function for finding the color of ray with scene
     * @param rays the list of rays from the pixel
     * @return the average color of the pixel according to the intersections of the rays
     */
    public abstract Color traceRays(List<Ray> rays);
}
