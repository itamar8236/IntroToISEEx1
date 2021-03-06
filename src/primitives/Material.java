/**
 * @author Avraham & Itamar
 */

package primitives;

/**
 * Class to represent an object's material (connection to light)
 */
public class Material {
    /**
     * A diffuse reflection constant, the ratio of reflection of the diffuse term of incoming light (lambertian reflectance)
     */
    public double kD = 0;
    /**
     * A specular reflection constant, the ratio of reflection of the specular term of incoming light
     */
    public double kS = 0;
    /**
     * A shininess constant for this material, which is larger for surfaces that are smoother and more mirror-like. When this constant is large the specular highlight is small
     */
    public int nShininess = 0;
    /**
     * reflection factor
     */
    public double kr = 0;
    /**
     * transparency factor
     */
    public double kt = 0;

    //chaining methods:
    /**
     * setter of kD
     * @param kD The light's diffuse to set
     * @return The material
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for the specular light
     * @param kS The specular light to set
     * @return The material
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for the shininess of the object
     * @param nShininess The shininess to set
     * @return The material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter of Kr
     * @param kr the reflection factor
     * @return the Material
     */
    public Material setKr(double kr) {
        this.kr = kr;
        return this;
    }

    /**
     * setter of Kt
     * @param kt the transparency factor
     * @return the Material
     */
    public Material setKt(double kt) {
        this.kt = kt;
        return this;
    }
}
