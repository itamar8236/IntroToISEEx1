package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Dan
 */
class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Point3D p1 = new Point3D(1, 2, 3);

    @Test
    void subtract() {
        assertTrue(new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)), "ERROR: Point - Point does not work correctly");
    }

    @Test
    void add() {
        assertTrue(Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))), "ERROR: Point + Vector does not work correctly");
    }

    @Test
    void scale() {
        Vector newV = v1.scale((-2));
        assertEquals(new Vector(-2, -4, -6), newV);
    }

    @Test
    void dotProduct() {
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    @Test
    void lengthSquared() {
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    @Test
    void length() {
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }

    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");

        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");

        Vector u = v.normalized();
        assertNotEquals(u, v, "ERROR: normalizated() function does not create a new vector");
    }

    @Test
    void constructor() {
        try {
            new Vector(0, 0, 0);
            fail("Vector's head cannot be zero point!");
        } catch (IllegalArgumentException ex) {

        }
    }
}