package base;

import ui_elements.ScreenPoint;

/**
 * An interface where all the implemennting object can check intersection with
 * one another.
 */
public interface Intersectable {

    /**
     * returns the vertices of the intersection polygon of the object. If an object
     * area contains a point within the area locked within the intersection polygon,
     * then it intersects this intersectable object.
     * Each vertex in the array is placed next to it's neighboring vertices. the
     * first and last element are neighboring vertices
     * 
     * @return the vertices of the intersection polygon
     */
    public ScreenPoint[] getIntersectionVertices();

}
