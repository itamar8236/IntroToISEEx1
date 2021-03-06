package geometries;

import primitives.*;

public class Cylinder extends Tube {
    double _height;

    public Cylinder(double height) {
        _height = height;
    }

    public double getHeight() {
        return _height;
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
