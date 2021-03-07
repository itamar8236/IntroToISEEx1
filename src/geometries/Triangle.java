package geometries;

import primitives.Point3D;

/**
 * class for represent Cylinder
 */
public class Triangle extends Polygon {
    /**
     * ctor
     *
     * @param p1 is point of vertex of the triangle
     * @param p2 is point of vertex of the triangle
     * @param p3 is point of vertex of the triangle
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }
}
