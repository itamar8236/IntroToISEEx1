package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: The closest point in the middle of the list
        Ray ray = new Ray(new Point3D(0, 0, 1), new Vector(1, 10, -100));

        List<Point3D> list = new LinkedList<Point3D>();
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0, 1, 1));
        list.add(new Point3D(0.5, 0, -100));
        assertEquals(list.get(2), ray.findClosestPoint(list), "not closest point!");

        // =============== Boundary Values Tests ==================

        // TC10: empty list
        ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 10, -100));
        list = null;
        assertNull(ray.findClosestPoint(list), "need to be equal to null");

        // TC11: the closest point in the front of the list
        list = new LinkedList<Point3D>();
        list.add(new Point3D(0, 1, 1));
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0.5, 0, -100));
        assertEquals(list.get(0), ray.findClosestPoint(list), "not closest point!");


        // TC12: the closest point in the tail of the list
        list.clear();
        list.add(new Point3D(1, 1, -100));
        list.add(new Point3D(-1, 1, -99));
        list.add(new Point3D(0.5, 0, -100));
        list.add(new Point3D(0, 1, 1));
        assertEquals(list.get(3), ray.findClosestPoint(list), "not closest point!");
    }

}