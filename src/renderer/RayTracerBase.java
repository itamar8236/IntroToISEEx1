package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    // ctor of the class
    public RayTracerBase(Scene scene) {
        if(scene == null)
            throw new UnsupportedOperationException("scene is null");
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}
