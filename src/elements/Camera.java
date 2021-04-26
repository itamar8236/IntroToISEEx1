package elements;

import primitives.*;

import static primitives.Util.isZero;

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
     * Cameera constructor
     * @param p0 Center point of the camera
     * @param vTO Vector up direction
     * @param vUP Vector right direction
     */
    public Camera(Point3D p0, Vector vTO, Vector vUP) {
        if(!isZero(vUP.dotProduct(vTO))){
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }

        P0 = p0;
        this.vUP = vUP.normalized();
        this.vTO = vTO.normalized();
        vRIGHT = this.vTO.crossProduct(this.vUP);
    }

    /**
     * Getter to P0
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

    //chaining methods

    /**
     * Setting the view plane size
     * @param width The view plane's width
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
     * @param distance The view plane's distance from the camera
     * @return The camera for chaining
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Getter for distance
     * @return The distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Create the ray from the camera to the middle of the pixel
     * @param nX numbers of pixels in width
     * @param nY numbers of pixels in height
     * @param j index of the pixels in columns (center is 0, 0)
     * @param i index of the pixels in rows (center is 0, 0)
     * @return The ray from the center of the camera to the center of the pixel, as ray.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

        Point3D Pc = P0.add(vTO.scale(distance));

        double Rx = width / nX;
        double Ry = height / nY;

        Point3D Pij = Pc;
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if(!isZero(Xj)){
            Pij = Pij.add(vRIGHT.scale(Xj));
        }
        if(!isZero(Yi)){
            Pij = Pij.add(vUP.scale(Yi));
        }
        Vector Vij = Pij.subtract(P0);

        return  new Ray(P0, Vij);
    }
}
