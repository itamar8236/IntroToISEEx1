package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * class to represent ray tracer
 */
public abstract class RayTracerBase {
    /**
     * scene of the image
     */
    protected Scene scene;

    /**
     * ctor of the class
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
}
