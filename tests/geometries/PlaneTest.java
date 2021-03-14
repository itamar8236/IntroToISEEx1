package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {
        Plane pl = new Plane(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 1));
        assertEquals(new Vector(-1, 0, 0), pl.getNormal(null), "Plane getNormal test fail");
    }
}