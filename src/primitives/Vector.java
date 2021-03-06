package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("head can't be zero point");
        }
        _head = new Point3D(head._x, head._y, head._z);
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    public Point3D getHead() {
        return _head;
    }

    public Vector subtract(Vector other) {
        Vector temp = ZERO.subtract(other._head);
        return this.add(temp);
    }

    public Vector add(Vector other) {
        return new Vector(_head.add(other));
    }

    public Vector scale(double scalar) {
        return new Vector(
                _head._x.coord * scalar,
                _head._y.coord * scalar,
                _head._z.coord * scalar
        );
    }

    public double dotProduct(Vector other) {
        return _head._x.coord * other._head._x.coord +
                _head._y.coord * other._head._y.coord +
                _head._z.coord * other._head._z.coord;
    }

    public Vector crossProduct(Vector other) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = other._head._x.coord;
        double v2 = other._head._y.coord;
        double v3 = other._head._z.coord;

        return new Vector(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        );
    }

    public double lengthSquared() {
        return _head.distanceSquared(ZERO);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalized() {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double len = this.length();
        return new Vector(u1 / len, u2 / len, u3 / len);
    }

    public Vector normalize() {
        _head = this.normalized()._head;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_head);
    }

    @Override
    public String toString() {
        return "head=" + _head;
    }
}
