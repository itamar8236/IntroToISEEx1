package elements;

import primitives.*;

import static primitives.Util.isZero;

public class Camera {
    final private Point3D P0;
    final private Vector vUP;
    final private Vector vTO;
    final private Vector vRIGHT;
    private double width;
    private double height;
    private double distance;

    public Camera(Point3D p0, Vector vTO, Vector vUP) {
        if(!isZero(vUP.dotProduct(vTO))){
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }

        P0 = p0;
        this.vUP = vUP.normalized();
        this.vTO = vTO.normalized();
        vRIGHT = this.vTO.crossProduct(this.vUP);
    }

    public Point3D getP0() {
        return P0;
    }

    public Vector getvUP() {
        return vUP;
    }

    public Vector getvTO() {
        return vTO;
    }

    public Vector getvRIGHT() {
        return vRIGHT;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    //chaining methods
    public Camera setViewPlaneSize(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
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
