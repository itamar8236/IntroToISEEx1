package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        Cylinder cylinder = new Cylinder(new Ray(Point3D.ZERO, new Vector(0, 1, 0)), 2, 4);

        assertEquals(new Vector(0, -1, 0), cylinder.getNormal(new Point3D(0, 0, 0.5)), "Case 1 fail");

        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point3D(0, 4, 0.5)), "Case 2 fail");

        assertEquals(new Vector(1.65, 0, 1.13).normalized(), cylinder.getNormal(new Point3D(1.65, 4.1, 1.13)), "Case 3 fail");
    }
}