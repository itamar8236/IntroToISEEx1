package geometries;

import primitives.*;

/**
 * interface for Geometry objects
 */
public abstract class Geometry implements Intersectable {
    /**
     * the emission of the element
     */
    protected Color emission = Color.BLACK;

    /**
     * the material of the geometry
     */
    private Material material = new Material();

    /**
     * getter of the class
     * @return the material field
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * the setter of the class
     * @param material the kind of material
     * @return the geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter of the class
     * @return the emission field
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * the setter of the class
     * @param emission the value for emission
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * calculate the normal from the geometry body in specific point
     * @param point3D the point on the body
     * @return the normal to the body
     */
    public abstract Vector getNormal(Point3D point3D);
}
