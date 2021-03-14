package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Plane
 *
 * @author Avraham Glasberg & Itamar Cohen
 *
 */
class PlaneTest {
    /**
     * Test method for
     * {@link geometries.Plane#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane pl = new Plane(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 1));

        // Test that the getNormal of the Plane is proper
        assertEquals(new Vector(-1, 0, 0), pl.getNormal(null), "Plane getNormal test fail");
    }
}