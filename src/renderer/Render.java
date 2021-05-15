package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

public class Render {
    ImageWriter imageWriter = null;
    Camera camera = null;
    RayTracerBase rayTracerBase = null;

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    private void checkImageWriter() {
        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }
    }

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

        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                Ray ray = camera.constructRayThroughPixel( Nx, Ny, j, i);
                Color color = rayTracerBase.traceRay(ray);
                imageWriter.writePixel(j, i, color);
            }
        }
    }

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

    public void writeToImage() {
        checkImageWriter();

        imageWriter.writeToImage();
    }





}
