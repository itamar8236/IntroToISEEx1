package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Sphere
 *
 * @author Avraham Glasberg & Itamar Cohen
 *
 */
class SphereTest {
    /**
     * Test method for
     * {@link geometries.Sphere#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sp = new Sphere(new Point3D(0, 0, 0), 3);
        Vector N = sp.getNormal(new Point3D(0, 4, 0));

        // Test that the getNormal of the Sphere is proper
        assertEquals(N, new Vector(0, 1, 0));
    }
}