package elements;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
     * Camera constructor
     *
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
     * Getter to P0
     *
     * @return The P0 point
     */
    public Point3D getP0() {
        return P0;
    }

    /**
     * Getter for vUP
     *
     * @return The up direction vector
     */
    public Vector getvUP() {
        return vUP;
    }

    /**
     * Getter for vTO
     *
     * @return The to direction vector
     */
    public Vector getvTO() {
        return vTO;
    }

    /**
     * Getter for vRIGHT
     *
     * @return The right direction vector
     */
    public Vector getvRIGHT() {
        return vRIGHT;
    }

    /**
     * Getter for width
     *
     * @return The width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter for height
     *
     * @return The height
     */
    public double getHeight() {
        return height;
    }

    //chaining methods

    /**
     * Setting the view plane size
     *
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
     * Setting the distance
     *
     * @param distance The view plane's distance from the camera
     * @return The camera for chaining
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Getter for distance
     *
     * @return The distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Create the ray from the camera to the middle of the pixel
     *
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @param j  index of the pixels in columns (center is 0, 0)
     * @param i  index of the pixels in rows (center is 0, 0)
     * @return The ray from the center of the camera to the center of the pixel, as ray.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        Point3D Pc = P0.add(vTO.scale(distance));

        double Rx = width / nX;
        double Ry = height / nY;

        Point3D Pij = Pc;
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(vRIGHT.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUP.scale(Yi));
        }
        Vector Vij = Pij.subtract(P0);

        return new Ray(P0, Vij);
    }

    /**
     * setter of ns
     * @param ns ns^2 is the number of rays in pixel
     * @return the camrea
     */
    public Camera setNs(int ns) {
        this.ns = ns;
        return this;
    }

    /**
     * construct rays beam through pixel according to Monta Carlo method
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @param j index of the pixels in columns (center is 0, 0)
     * @param i index of the pixels in rows (center is 0, 0)
     * @return list of rays
     */
    public List<Ray> constructRaysBeamThroughPixel(int nX, int nY, int j, int i) {
        if (isZero(distance))
            throw new IllegalArgumentException("illegal distance");

        // list of rays
        LinkedList<Ray> rays = new LinkedList<>();

        double Rx = width / nX;
        double Ry = height / nY;
        // delta in relate to the size of pixel
        double deltaWidth = 1d / 100 * Rx ;
        double deltaHeight = 1d / 100 * Ry ;

        // center of the pixel:
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
                double rx= random(0,1);
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
                // the point the ray pass trough in the VP
                Point3D pInPixel = new Point3D(leftUp.getX() + ((sx + rx)/ ns)*Rx,leftUp.getY() - ((ry + sy) / ns)*Ry, leftUp.getZ());
                // add the ray to list
                rays.add(new Ray(P0, pInPixel.subtract(P0)));
            }
        }
        return rays;
    }
}