/**
 * @author Avraham & Itamar
 */

package geometries;
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
     * constructor
     */
    public Geometries() {
        intersectables = new LinkedList<>();
    }

    /**
     * constructor with params
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
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        // finding the intersections with all the intersectable elements and return all the points
        List<GeoPoint> result = new LinkedList<>();

        for (Intersectable element: this.intersectables) {
            List<GeoPoint> interList = element.findGeoIntersections(ray, maxDistance);
            if (interList != null) {
                result.addAll(interList);
            }
        }
        return result.size() == 0 ? null : result;
    }
}
