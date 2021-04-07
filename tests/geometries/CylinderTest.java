package geometries;

import org.junit.jupiter.api.Test;

import primitives.*;

import java.util.List;

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

    @Test
    void findIntersections() {
        Cylinder cylinder = new Cylinder(new Ray(new Point3D(2,0,0), new Vector(0,0,1)), 1d, 2d);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray

        List<Point3D> result = cylinder.findIntersections(new Ray(new Point3D(5,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");


        //TC02 ray starts inside and parallel to the cylinder's ray

        result = cylinder.findIntersections(new Ray(new Point3D(2.5,0,1), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2.5,0,2)), result, "Bad intersection point");

        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2.5,0,-1), new Vector(0,0,1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2.5, 0, 0), new Point3D(2.5,0,2)), result, "Bad intersection point");

        //TC04 ray starts from outside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(-2,0,0), new Vector(1,0,0)));
        //assertEquals(2, result.size(), "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");
        assertNull(result, "Wrong number of points");

        //TC05 ray starts from inside and crosses the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(5,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        // =============== Boundary Values Tests ==================

        //TC07 ray is on the surface of the cylinder (not bases)

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC08 ray is on the base of the cylinder and crosses 2 times

        result = cylinder.findIntersections(new Ray(new Point3D(-1,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");

        //TC08 ray is in center of the cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2,0,2)), result, "Bad intersection points");

        //TC09 ray is perpendicular to cylinder's ray and starts from outside the tube

        result = cylinder.findIntersections(new Ray(new Point3D(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(1,0,0.5), new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC09 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)

        result = cylinder.findIntersections(new Ray(new Point3D(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC10 ray is perpendicular to cylinder's ray and starts from the center of cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC11 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside

        result = cylinder.findIntersections(new Ray(new Point3D(1,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,0.5)), result, "Bad intersection points");

        //TC12 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC13 ray starts from the surface to outside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC14 ray starts from the surface to inside

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,-0.5), new Vector(-1,0,1)));
        //assertEquals(1, result.size(), "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,2)), result, "Bad intersection point");

        //TC15 ray starts from the center

        result = cylinder.findIntersections(new Ray(new Point3D(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(3,0,1)), result, "Bad intersection point");

        //TC16 prolongation of ray crosses cylinder

        result = cylinder.findIntersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

    }
}