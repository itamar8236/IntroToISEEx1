package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Tube
 *
 * @author Avraham Glasberg & Itamar Cohen
 *
 */
class TubeTest {
    /**
     * Test method for
     * {@link geometries.Tube#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tb = new Tube(new Ray(Point3D.ZERO, new Vector(0, 1, 0)), 2);
        Vector N = tb.getNormal(new Point3D(1.65, 4.1, 1.13));

        // Test that the getNormal of the Tube is proper
        assertEquals(new Vector(1.65, 0, 1.13).normalized(), N, "Tube's normal fail");
    }
}