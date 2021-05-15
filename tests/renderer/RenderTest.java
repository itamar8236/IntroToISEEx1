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

    /**
     * Test for XML based scene - for bonus
     */
   /* @Test
    public void basicRenderXml() {
        Scene scene = new Scene("XML Test scene");
        // enter XML file name and parse from XML file into scene object
        // ...

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }
    */

    // For stage 6 - please disregard in stage 5
    /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */
    /*
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
    */


}