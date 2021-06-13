package renderer;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.io.File;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;



class RenderTest {

//    @Test
//    public void loadXML() throws ParserConfigurationException, IOException, SAXException {
//        String fileName = "basicRenderTestTwoColors.xml";
//        Scene scene = new Scene("XML Test scene");
//
//        //Get Document Builder
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//
////Build Document
//        Document document = builder.parse(new File(fileName));
//
////Normalize the XML Structure; It's just too important !!
//        document.getDocumentElement().normalize();
//
////Here comes the root node
//        Element root = document.getDocumentElement();
//        String str = root.getAttribute("background-color");
//        String[] arr = str.split(" ");
//        Color c = new Color(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
//
//
//        assertEquals( new Color(75, 127, 190).getColor() ,c.getColor() ,"sdfdsaf ");
//    }



    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    // For stage 6 - please disregard in stage 5
    /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracerBase(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }

    @Test
    public void iceManTestWithoutSuperSampling() {

        Scene scene = new Scene("ICE MEN no super sampling").setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0));
        TestForLevel1(scene, false, "ICE MEN no super sampling");
    }
    @Test
    public void iceManTestWithSuperSampling() {
        Scene scene = new Scene("ICE MEN with super sampling").setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0));
        TestForLevel1(scene, true, "ICE MEN with super sampling");
    }

    private  void TestForLevel1(Scene scene, boolean superSampling, String testName){
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000)
                .setNs(9);
        scene.background = new Color(java.awt.Color.BLUE);
        scene.geometries.add( //
                new Sphere(25, new Point3D(30, -20, -50)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.1)),
                new Sphere(2.5, new Point3D(32, 20, -37)) //
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(2.5, new Point3D(20, 20, -40)) //
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
//                new Sphere(80, new Point3D(0, -10, -50)).setEmission(new Color(java.awt.Color.GRAY)) //
//                        .setMaterial(new Material().setKd(0).setKs(0.8).setShininess(30).setKt(0.5)),
                new Polygon(new Point3D(-120, 20, -400), new Point3D(120, 20, -400), new Point3D(75, -120, 400),
                        new Point3D(-75, -120, 400)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
//                        .setEmission(new Color(125, 158, 209)) //
                        .setMaterial(new Material().setKr(0.2)),
                new Triangle(new Point3D(-50, 45, -50), new Point3D(-32.5, 80, -50), new Point3D(-15, 53, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Triangle(new Point3D(-55, 30, -50), new Point3D(-32.5, 65, -50), new Point3D(-10, 38, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Triangle(new Point3D(-60, 15, -50), new Point3D(-32.5, 52, -50), new Point3D(-5, 25, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Triangle(new Point3D(-65, 0, -50), new Point3D(-32.5, 37, -50), new Point3D(0, 10, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Triangle(new Point3D(-70, -15, -50), new Point3D(-32.5, 28, -50), new Point3D(5, -5, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Triangle(new Point3D(10, 12, -25), new Point3D(28, 12, -35), new Point3D(25, 17, -35)) //
                        .setEmission(new Color(java.awt.Color.ORANGE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Cylinder(new Ray(new Point3D(-35,-10,-50), new Vector(0,-1,0)), 5, 50)
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Sphere(2.8, new Point3D(20, -6, -29)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(2.8, new Point3D(17, -18, -28)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(2.8, new Point3D(20, -30, -29)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Polygon(new Point3D(48, -10, -30), new Point3D(48, -12, -30), new Point3D(65, 0, -30),
                        new Point3D(65, 2, -30)) //
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Polygon(new Point3D(-7, 2, -55), new Point3D(-7, 0, -55), new Point3D(10, -12, -55),
                        new Point3D(10, -10, -55)) //
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Sphere(1, new Point3D(60, 20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(56, 22, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(50, 28, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(0, 29, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(25, 30, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-12, 14, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-16, 16, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-25, 0, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(5, 22, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(5, 0, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(8, 9, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(19, 23, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-30, 23, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-32, 32, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-34, 20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-42, 10, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-39, 5, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-47, 39, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-52, 2, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(60, -20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(56, -18, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(50, -12, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(0, -11, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(25, -10, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-12, -26, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-16, -24, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-25, -40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(5, -18, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(5, -40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(8, -41, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(19, -17, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-30, -17, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-32, -8, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-34, -20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-42, -30, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-39, -35, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-47, -1, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-52, -38, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(60, 32, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(56, 37, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(50, 35, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(0, 65, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(25, 48, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-12, 54, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-16, 56, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-25, 40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(5, 62, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(5, 40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(8, 49, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(19, 63, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-30, 39, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-32, 47, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-34, 37, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-42, 39, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-39, 45, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-47, 40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(1, new Point3D(-52, 32, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(15, new Point3D(30, 15, -50)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.1)));

        scene.lights.add( //
                new SpotLight(new Color(10, 600, 0), new Point3D(-250, 400, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter(testName, 500, 500)) //
                .setCamera(camera) //
                .setSuperSampling(superSampling)
                .setRayTracerBase(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }


}