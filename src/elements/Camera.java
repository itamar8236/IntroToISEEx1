/**
 * @author Avraham & Itamar
 */

package elements;
import primitives.*;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

/**
 * Class to represent Camera in 3D plane view.
 */
public class Camera {
    /**
     * P0 point of the camera, the center point
     */
    final private Point3D P0;
    /**
     * vector towards up direction of the camera
     */
    final private Vector vUP;
    /**
     * vector towards forward direction of the camera
     */
    final private Vector vTO;
    /**
     * vector towards right direction of the camera
     */
    final private Vector vRIGHT;
    /**
     * Width of the view plane
     */
    private double width;
    /**
     * height of the view plane
     */
    private double height;
    /**
     * distance of the camera from the view plane
     */
    private double distance;
    /**
     * ns^2 is the number of rays in pixel
     */
    private int ns = 3;

    /**
     * Camera's constructor
     * @param p0  Center point of the camera
     * @param vTO Vector up direction
     * @param vUP Vector right direction
     */
    public Camera(Point3D p0, Vector vTO, Vector vUP) {
        if (!isZero(vUP.dotProduct(vTO))) {
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }

        P0 = p0;
        this.vUP = vUP.normalized();
        this.vTO = vTO.normalized();
        vRIGHT = this.vTO.crossProduct(this.vUP);
    }

    /**
     * Getter for P0
     * @return The P0 point
     */
    public Point3D getP0() {
        return P0;
    }

    /**
     * Getter for vUP
     * @return The up direction vector
     */
    public Vector getvUP() {
        return vUP;
    }

    /**
     * Getter for vTO
     * @return The to direction vector
     */
    public Vector getvTO() {
        return vTO;
    }

    /**
     * Getter for vRIGHT
     * @return The right direction vector
     */
    public Vector getvRIGHT() {
        return vRIGHT;
    }

    /**
     * Getter for width
     * @return The width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter for height
     * @return The height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Getter for distance
     * @return The distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Setting the view plane size, chaining methods
     * @param width  The view plane's width
     * @param height The view plane's height
     * @return The camera for chaining
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * Setting the distance, chaining methods
     * @param distance The view plane's distance from the camera
     * @return The camera for chaining
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * setter of ns, chaining methods
     * @param ns ns^2 is the number of rays in pixel
     * @return the camrea
     */
    public Camera setNs(int ns) {
        this.ns = ns;
        return this;
    }

    /**
     * Create the ray from the camera to the middle of the pixel
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @param j  index of the pixels in columns (center is 0, 0)
     * @param i  index of the pixels in rows (center is 0, 0)
     * @return The ray from the center of the camera to the center of the pixel, as ray.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        // initial the parameters
        Point3D Pc = P0.add(vTO.scale(distance));
        double Rx = width / nX;
        double Ry = height / nY;

        // the 3D point of the center of the pixel
        Point3D Pij = Pc;
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(vRIGHT.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUP.scale(Yi));
        }
        // the vector from the camera to the Pij point
        Vector Vij = Pij.subtract(P0);

        // the ray through the center of the pixel
        return new Ray(P0, Vij);
    }

    /**
     * construct rays beam through pixel according to Monte Carlo method
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @param j index of the pixels in columns (center is 0, 0)
     * @param i index of the pixels in rows (center is 0, 0)
     * @return list of rays through the pixel
     */
    public List<Ray> constructRaysBeamThroughPixel(int nX, int nY, int j, int i) {
        // the distance to the view plane is zero
        if (isZero(distance))
            throw new IllegalArgumentException("illegal distance");

        // list of rays
        LinkedList<Ray> rays = new LinkedList<>();

        // the width and the height of pixel
        double Rx = width / nX;
        double Ry = height / nY;

        // delta relative to the size of pixel as required
        double deltaWidth = 1d / 100 * Rx ;
        double deltaHeight = 1d / 100 * Ry ;

        // center of the pixel
        Point3D Pc = P0.add(vTO.scale(distance));
        Point3D Pij = Pc;
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(vRIGHT.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUP.scale(Yi));
        }

        Point3D pCenterPixel = Pij;

        // adding the center ray of the pixel
        rays.add(new Ray(P0, pCenterPixel.subtract(P0)));

        // finding the point the ray pass trough in the grid of the pixel
        Point3D leftUp = new Point3D(pCenterPixel.getX() - 0.5 * Rx, pCenterPixel.getY() + 0.5 * Ry, pCenterPixel.getZ());
        for (int sx = 0; sx < ns; sx++) {
            for (int sy = 0; sy < ns; sy++) {

                // getting random point in the cell of the grid inside the pixel
                double rx = random(0,1);
                double ry =  random(0,1);

                // make sure the new ray isn't on the lines of the grid
                if (rx < deltaHeight)
                    rx += deltaWidth;
                if (1 - rx < deltaWidth)
                    rx -= deltaWidth;
                if (ry < deltaHeight)
                    ry += deltaHeight;
                if (1 - ry < deltaHeight)
                    ry -= deltaHeight;

                // the point the ray pass through in the view plane
                Point3D pInPixel = new Point3D( leftUp.getX() + ((sx + rx)/ ns)*Rx,
                                                leftUp.getY() - ((ry + sy) / ns)*Ry,
                                                   leftUp.getZ());

                // add the ray to list
                rays.add(new Ray(P0, pInPixel.subtract(P0)));
            }
        }
        return rays;
    }

    /**
     * construct the four rays through the corners of the pixel and return them as a list
     * @param centerRay the ray thrugh the middle of the pixle
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @return the four rays as a list, in that order:
     * left up ray
     * right up ray
     * left down ray
     * right down ray
     */
    public List<Ray> constructFourRaysThroughPixel(Ray centerRay, int nX, int nY) {
        // the width and the height of pixel
        double Rx = width / nX;
        double Ry = height / nY;

        // the list of the 4 rays
        List<Ray> rays = new LinkedList<>();

        double t0 = distance;

        // finding the center point of the pixel by using the center ray
        double temp = t0 / (vTO.dotProduct(centerRay.getDir()));
        Point3D center = centerRay.getPoint(temp);

        //left up
        rays.add(new Ray(P0, center.add(vRIGHT.scale(-Rx / 2)).add(vUP.scale(Ry / 2)).subtract(P0)));
        //right up
        rays.add(new Ray(P0, center.add(vRIGHT.scale(Rx / 2)).add(vUP.scale(Ry / 2)).subtract(P0)));
        //left down
        rays.add(new Ray(P0, center.add(vRIGHT.scale(-Rx / 2)).add(vUP.scale(-Ry / 2)).subtract(P0)));
        //right down
        rays.add(new Ray(P0, center.add(vRIGHT.scale(Rx / 2)).add(vUP.scale(-Ry / 2)).subtract(P0)));

        // return the ray in the right order
        return rays;
    }

    /**
     * construct and return the ray that goes in the VP exactly in the middle of two rays
     * @param ray1 the first ray
     * @param ray2 the second ray
     * @return the middle ray
     */
    public Ray constructRayThroughTwoRays(Ray ray1, Ray ray2) {
        // fining the points of the rays on the VP:
        Point3D p1 = ray1.getP0().add(ray1.getDir().scale(distance));
        Point3D p2 = ray2.getP0().add(ray2.getDir().scale(distance));

        // the point between the two points on the VP
        Point3D p3 = new Point3D(
                (p1.getX() + p2.getX())/2,
                (p1.getY() + p2.getY())/2,
                (p1.getZ() + p2.getZ())/2
        );

        // the ray through the middle point
        return new Ray(P0, p3.subtract(P0));
    }

    /**
     * construct and return the center ray from the left up ray of the pixel
     * @param ray the left up corner ray
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @return the ray through the center
     */
    public Ray constructCenterRayFromLeftUpRay(Ray ray, double nX, double nY){

        // the height and the width of the cell (as pixel) of the pixel
        double h = alignZero(height / nY);
        double w = alignZero(width / nX);

        // finding the left up point of the pixel
        double t0 = distance;
        double temp = t0/(vTO.dotProduct(ray.getDir()));
        Point3D point = ray.getPoint(temp);

        // finding the center point
        point = point.add(vRIGHT.scale(w/2)).add(vUP.scale(-h/2));
        return new Ray(P0, point.subtract(P0));
    }

}