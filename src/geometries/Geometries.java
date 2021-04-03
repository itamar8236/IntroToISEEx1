package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> intersectables = null;

    public Geometries() {
        intersectables = new LinkedList<>();
    }

    public Geometries(Intersectable... intersectables) {
        this.intersectables = new LinkedList<>();
        add(intersectables);
    }

    private void add(Intersectable... intersectables){
//        for(Intersectable item: intersectables){
//            this.intersectables.add(item);
//        }
        this.intersectables.addAll(Arrays.asList(intersectables));
    }


    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable elemenet: this.intersectables) {
            List<Point3D> interList = elemenet.findIntsersections(ray);
            if (interList != null) {
                if (result == null)
                    result = new LinkedList<>();
                result.addAll(interList);
            }
        }
        return result;
    }
}
