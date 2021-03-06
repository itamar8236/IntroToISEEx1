package geometries;

import primitives.*;

public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
