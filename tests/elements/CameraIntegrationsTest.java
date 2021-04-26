package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationsTest {

    /**
     * finding the number of intersections between camera and geometric object
     * @param camera the camera
     * @param nX number of pixels by width
     * @param nY number of pixels by height
     * @param geometry geometric object
     * @return he number of intersections
     */
    private int countIntersectionsCamera(Camera camera, int nX, int nY, Intersectable geometry){

        int count = 0;
        List<Point3D> intersections;

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                intersections = geometry.findIntersections(camera.constructRayThroughPixel(nX, nY, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }
        return count;
    }

    @Test
    public void CameraRaySphereIntegration() {

        Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setDistance(1d)
                .setViewPlaneSize(3d, 3d);
        Camera cam2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setDistance(1d)
                .setViewPlaneSize(3d, 3d);
        Sphere sp;

        //TC01:Two intersection points
        sp = new Sphere(1d, new Point3D(0,0,-3));
        assertEquals(2, countIntersectionsCamera(cam1, 3,3, sp), "Bad number of intersections");

        // TC02:  18 intersection points
        sp = new Sphere(2.5, new Point3D(0,0,-2.5));
        assertEquals(18, countIntersectionsCamera(cam2, 3, 3, sp),"Bad number of intersections");

        //TC03: 10 intersection points
        sp = new Sphere(2d, new Point3D(0,0,-2));
        assertEquals(10, countIntersectionsCamera(cam2, 3, 3, sp),"Bad number of intersections");

        // TC04: 9 intersection points
        sp = new Sphere(4d, new Point3D(0, 0, 1));
        assertEquals(9, countIntersectionsCamera(cam2,3,3, sp), "Bad number of intersections");

        // TC05: Zero intersection points
        sp = new Sphere(0.5, new Point3D(0, 0, 1));
        assertEquals(0, countIntersectionsCamera(cam1,3,3, sp), "Bad number of intersections");
    }


    @Test
    public void CameraRayTriangleIntegration() {
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setDistance(1d)
                .setViewPlaneSize(3d, 3d);

        Triangle tr;

        // TC01:  1 intersection points
        tr = new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 1, -2));
        assertEquals(1, countIntersectionsCamera(cam,3,3,tr),"Bad number of intersections");

        // TC02:  2 intersection points
        tr = new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 20, -2));
        assertEquals(2, countIntersectionsCamera(cam,3,3,tr),"Bad number of intersections");
    }

    @Test
    public void CameraRayPlaneIntegration() {
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setDistance(1d)
                .setViewPlaneSize(3d, 3d);

        Plane pl;

        // TC01:  9 intersection points
        pl = new Plane(new Point3D(0, 0, 5), new Vector(0, 0, 1));
        assertEquals(countIntersectionsCamera(cam,3,3 ,pl), 9,"Bad number of intersections");

        // TC02:  9 intersection points
        pl = new Plane(new Point3D(0, 0, 5), new Vector(0, -1, 2));
        assertEquals(countIntersectionsCamera(cam,3,3, pl), 9,"Bad number of intersections");

        // TC03:  6 intersection points
        pl = new Plane(new Point3D(0,0,2), new Vector(1,1,1));
        assertEquals(6, countIntersectionsCamera(cam, 3,3, pl), "Bad number of intersections");

        // TC04:  0 intersection points
        pl = new Plane(new Point3D(0, 0, -4), new Vector(0,0,1));
        assertEquals(0, countIntersectionsCamera(cam, 3, 3, pl), "Bad number of intersections");
    }
}
