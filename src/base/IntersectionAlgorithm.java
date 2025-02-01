package base;

import ui_elements.ScreenPoint;
import java.awt.geom.Area;
import java.awt.Polygon;
import java.awt.Shape;

/**
 * This class is responsible for checking the intersection between shape
 */
public class IntersectionAlgorithm {

    /**
     * Checks if 2 shapes intersect.
     * 
     * @param firstShape - first intersectable shape
     * @param secondShape - second intersectable shape
     * @return true - the objects intersect, false - otherwise.
     */
    public static boolean areIntersecting(Intersectable firstShape, Intersectable secondShape) {
        Polygon firstObjectPolygon = new Polygon();
        for (ScreenPoint vertex : firstShape.getIntersectionVertices()) {
            firstObjectPolygon.addPoint(vertex.x, vertex.y);
        }

        Polygon secondObjectPolygon = new Polygon();
        for (ScreenPoint vertex : secondShape.getIntersectionVertices()) {
            secondObjectPolygon.addPoint(vertex.x, vertex.y);
        }

        return areIntersecting(firstObjectPolygon, secondObjectPolygon);
    }

    private static boolean areIntersecting(Shape firstShape, Shape secondShape) {
        Area intersection = new Area(firstShape);
        intersection.intersect(new Area(secondShape));

        return !intersection.isEmpty();
    }
}
