/**
 * @author Avraham & Itamar & Dan
 */

package renderer;
import primitives.*;
import elements.*;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 */
public class Render {
    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase tracer;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";
    private static final int DEPTH_ADAPTIVE = 6;

    /**
     * the number of threads
     */
    private int threadsCount = 0;

    /**
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 2;

    /**
     * printing progress percentage
     */
    private boolean print = false;

    /**
     * flag of operating the superSampling
     */
    boolean superSampling = false;

    /**
     * flag of operating the adaptiveSuperSampling
     */
    boolean adaptiveSuperSampling = false;

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
     * setter of adaptiveSuperSampling
     * @param adaptiveSuperSampling the flag
     * @return The render
     */
    public Render setAdaptiveSuperSampling(boolean adaptiveSuperSampling) {
        this.adaptiveSuperSampling = adaptiveSuperSampling;
        return this;
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     * @author Dan
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracerBase(RayTracerBase tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Color color = Color.BLACK;

        if (superSampling && !adaptiveSuperSampling) {
            List<Ray> rays = camera.constructRaysBeamThroughPixel(nX, nY, col, row);
            color = tracer.traceRays(rays);
        } else if (adaptiveSuperSampling) {
            Ray centerRay = camera.constructRayThroughPixel(nX, nY, col, row);
            color = adaptiveColor(centerRay, nX, nY, col, row, DEPTH_ADAPTIVE);
        } else {
            Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
            color = tracer.traceRay(ray);
        }
        imageWriter.writePixel(col, row, color);

    }

    /**
     * calculate the color using adaptive Super Sampling
     * @param centerRay the ray through the center of the pixel
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     * @param depth the depth of the recursion
     * @return the color of the pixel
     */
    private Color adaptiveColor(Ray centerRay, int nX, int nY, int col, int row, int depth) {

        // create list of the rays through the corners of the pixel
        List<Ray> rays = camera.constructFourRaysThroughPixel(centerRay, nX, nY);

        // c is the color of first ray - the left up corner (the rays order is by the function constructFourRaysThroughPixel)
        Color c = tracer.traceRay(rays.get(0));

        // adding the center ray to the *end* of the list (index 4)
        rays.add(centerRay);

        // calling to the recursion function
        return adaptiveRecColor(rays, nX, nY, depth, c, 0);
    }

    /**
     * recursion function that calculate the color of the pixel using adaptive Super Sampling
     * @param rays list of five rays through the pixel in that exact index order:
     * index 0 : left up ray
     * index 1 : right up ray
     * index 2 : left down ray
     * index 3 : right down ray
     * index 4 : center ray
     * @param nX  resolution on X axis (number of pixels in row)
     * @param nY  resolution on Y axis (number of pixels in column)
     * @param depth the depth of the recursion
     * @param color the color of one ray that already been calculated
     * @param colorIndex the index in the list of the calculated ray color, can't be four (center ray)
     * @return the color of the pixel
     */
    private Color adaptiveRecColor(List<Ray> rays, int nX, int nY, int depth, Color color, int colorIndex) {

        // the list of the colors order by the rays list
        List<Color> listColor = new LinkedList<>();

        // calculate the color in the corners alone
        for (int i = 0; i < 4; i++) {
            // avoiding recalculating the color
            if (i != colorIndex) {
                listColor.add(tracer.traceRay(rays.get(i)));
            }
        }

        // adding the already known color in the right index
        listColor.add(colorIndex, color);

        // flag that all the colors in the corners are the same
        boolean flag = true;

        // checking that all the colors in the corners are the same
        for (Color c : listColor) {
            flag = flag && c.equals(color) ;
        }

        // all the colors in the corners are the same
        if (flag) {
            return color;
        }

        // not all the colors in the corners are the same
        // if the recursion reached it's max depth - calculate and return average color
        if (depth <= 0) {

            // adding the center ray color
            listColor.add(tracer.traceRay(rays.get(4)));

            // the final color
            Color c = Color.BLACK;
            // adding all colors
            for (int i = 0; i < listColor.size(); i++) {
                c = c.add(listColor.get(i));
            }
            // calculate and return average color
            c = c.reduce(listColor.size());
            return c;
        }

        Ray center = rays.get(4);

        // creating the subpixel left up:
        List<Ray> tempRays = new LinkedList<>();
        // the old left up corner is the new left up ray
        tempRays.add(rays.get(0));

        // old up (middle) ray is the new right up ray
        Ray up = camera.constructRayThroughTwoRays(rays.get(0), rays.get(1));
        tempRays.add(up);

        // old left (middle) ray is the new left down ray
        Ray left = camera.constructRayThroughTwoRays(rays.get(0), rays.get(2));
        tempRays.add(left);

        // old center ray is the new right down ray
        tempRays.add(center);

        // calculating and adding the new center ray
        tempRays.add(camera.constructCenterRayFromLeftUpRay(rays.get(0), nX * 2, nY * 2));

        // recursion calling, the known color is of new left up (index 0)
        Color c1 = adaptiveRecColor(tempRays, nX * 2, nY * 2, depth - 1, listColor.get(0), 0);

        // creating the subpixel right up:
        tempRays.clear();

        // the old up (middle) ray is the new left up ray
        tempRays.add(up);

        // the old right up ray is the new right up ray
        tempRays.add(rays.get(1));

        // the old center ray is the new left down ray
        tempRays.add(center);

        // old right (middle) ray is the new right down ray
        Ray right = camera.constructRayThroughTwoRays(rays.get(1), rays.get(3));
        tempRays.add(right);

        // calculating and adding the new center ray
        tempRays.add(camera.constructCenterRayFromLeftUpRay(up, nX * 2, nY * 2));

        // recursion calling, the known color is of new right up (index 1)
        Color c2 = adaptiveRecColor(tempRays, nX * 2, nY * 2, depth - 1, listColor.get(1), 1);

        // creating the subpixel left down:
        tempRays.clear();

        // old left (middle) ray is the new left up ray
        tempRays.add(left);

        // old center is the new right up ray
        tempRays.add(center);

        // old left down is new left down ray
        tempRays.add(rays.get(2));

        // old down (middle) ray is the new right down ray
        Ray down = camera.constructRayThroughTwoRays(rays.get(2), rays.get(3));
        tempRays.add(down);

        // calculating and adding the new center ray
        tempRays.add(camera.constructCenterRayFromLeftUpRay(left, nX * 2, nY * 2));

        // recursion calling, the known color is of new left down (index 2)
        Color c3 = adaptiveRecColor(tempRays, nX * 2, nY * 2, depth - 1, listColor.get(2), 2);

        // creating the subpixel right down:
        tempRays.clear();

        // old center is the new left up ray
        tempRays.add(center);

        // old right (middle) ray is the new right up ray
        tempRays.add(right);

        // old down (middle) ray is the new left down ray
        tempRays.add(down);

        // old right down is new right down ray
        tempRays.add(rays.get(3));

        // calculating and adding the new center ray
        tempRays.add(camera.constructCenterRayFromLeftUpRay(center, nX * 2, nY * 2));

        // recursion calling, the known color is of new right down (index 4)
        Color c4 = adaptiveRecColor(tempRays, nX * 2, nY * 2, depth - 1, listColor.get(3), 3);

        // calculating and return average of the four subPixels colors
        Color result = Color.BLACK.add(c1, c2, c3, c4);
        return result.reduce(4);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (tracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
    }
}