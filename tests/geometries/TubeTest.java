package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
        Tube tb = new Tube(new Ray(Point3D.ZERO, new Vector(0, 1, 0)), 2);

        Vector N = tb.getNormal(new Point3D(0, 0, 2));

        assertEquals(new Vector(0, 0, 1), N);
    }
}