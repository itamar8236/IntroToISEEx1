package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Triangle
 *
 * @author Avraham Glasberg & Itamar Cohen
 *
 */
class TriangleTest {
    /**
     * Test method for
     * {@link geometries.Triangle#getNormal(Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the getNormal of the Triangle is proper
        Triangle tr = new Triangle(Point3D.ZERO, new Point3D(0, 0, 1), new Point3D(0, 1, 1));
        assertEquals(new Vector(-1, 0, 0), tr.getNormal(null), "Triangle getNormal test fail");
    }
}