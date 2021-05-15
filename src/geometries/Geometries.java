package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class of collection of intersectable classes
 */
public class Geometries implements Intersectable {

    /**
     * list of intersectable classes
     */
    private List<Intersectable> intersectables = null;

    /**
     * ctor
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * ctor
     * @param intersectables params of intersectable
     */
    public Geometries(Intersectable... intersectables) {
        this.intersectables = new LinkedList<>();
        add(intersectables);
    }

    /**
     * add intersectable items to the collection
     * @param intersectables params of the items
     */
    public void add(Intersectable... intersectables){
        this.intersectables.addAll(Arrays.asList(intersectables));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = new LinkedList<>();

        for (Intersectable element: this.intersectables) {
            List<Point3D> interList = element.findIntersections(ray);
            if (interList != null) {
                result.addAll(interList);
            }
        }
        return result.size() == 0 ? null : result;
    }
}
