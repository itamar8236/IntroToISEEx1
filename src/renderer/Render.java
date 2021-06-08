package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Class for rendering scene into image
 */
public class Render {
    /**
     * The image writer
     */
    ImageWriter imageWriter = null;
    /**
     * The camera
     */
    Camera camera = null;
    /**
     * The ray tracer
     */
    RayTracerBase rayTracerBase = null;

    /**
     * setter of superSampling
     * @param superSampling the flag
     * @return The render
     */
    public Render setSuperSampling(boolean superSampling) {
        this.superSampling = superSampling;
        return this;
    }

    /**
     * flag of oparate the superSampling
     */
    boolean superSampling = false;
    /**
     * Set for image writer, using chaining methods
     * @param imageWriter The image writer to set
     * @return The render
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Set for the camera
     * @param camera The camerq to set
     * @return The render
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Set for the race tracer base
     * @param rayTracerBase The ray tracer base to set
     * @return The render
     */
    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * Checking if the image writer is not null.
     */
    private void checkImageWriter() {
        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
    }

    /**
     * Render the image.
     * checking that all fields are not null, and writing all the scene's pixels
     */
    public void renderImage() {
        try {
            checkImageWriter();

            if (camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

        }
        catch (MissingResourceException ex) {
            throw new UnsupportedOperationException("Not implemented yet " + ex.getClassName());
        }

        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        // write all pixels
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                Color color = Color.BLACK;
                if (superSampling){
                    List<Ray> rays = camera.constructRaysBeamThroughPixel( Nx, Ny, j, i);
                    color = rayTracerBase.traceRays( rays );
                }
                else{
                    Ray ray = camera.constructRayThroughPixel( Nx, Ny, j, i);
                    color = rayTracerBase.traceRay( ray );
                }
                imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Printing the image's grid
     * @param interval The interval of the squares
     * @param color The color of the lines.
     */
    public void printGrid(int interval, Color color) {
        checkImageWriter();

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Write the scene into an image.
     */
    public void writeToImage() {
        checkImageWriter();

        imageWriter.writeToImage();
    }





}
