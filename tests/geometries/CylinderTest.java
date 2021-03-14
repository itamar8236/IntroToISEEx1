package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Cylinder
 *
 * @author Avraham Glasberg & Itamar Cohen
 *
 */
class CylinderTest {
    /**
     * Test method for
     * {@link geometries.Cylinder#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        Cylinder cylinder = new Cylinder(new Ray(Point3D.ZERO, new Vector(0, 1, 0)), 2, 4);
        // ============ Equivalence Partitions Tests ==============
        // Test that in case 1 the getNormal of the cylinder is proper (when the point on the button base)
        assertEquals(new Vector(0, -1, 0), cylinder.getNormal(new Point3D(0, 0, 0.5)), "Case 1 fail");

        // ============ Equivalence Partitions Tests ==============
        // Test that in case 2 the getNormal of the cylinder is proper (when the point on the upper base)
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point3D(0, 4, 0.5)), "Case 2 fail");

        // ============ Equivalence Partitions Tests ==============
        // Test that in case 3 the getNormal of the cylinder is proper (when the point on the side of the cylinder)
        assertEquals(new Vector(1.65, 0, 1.13).normalized(), cylinder.getNormal(new Point3D(1.65, 4.1, 1.13)), "Case 3 fail");
    }
}