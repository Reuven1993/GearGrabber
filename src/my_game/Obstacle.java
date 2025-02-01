package my_game;

import java.awt.Color;
import base.Game;
import base.Intersectable;
import shapes.Polyline;
import ui_elements.ScreenPoint;

public class Obstacle implements Intersectable {

    private ScreenPoint location;
    private Polyline visPolygon;
    private final String polyID;
    private int polyMinRadius = 50;
    private int polyDeltaRadius = 200;
    // private ScreenPoint[] points;

    private boolean aestheticObstacle = true;
    private final String imageID;
    private final String imageObstacle = "resources/mediumBlockWall.png";
    private final int imageWidth = 100;
    private final int imageHeight = 100;


    public Obstacle() {

        this.polyID = "Obstacle";
        this.imageID = "ObstacleImage";
        this.setLocation(new ScreenPoint(200, 150));
        initObstacle();

    }

    public Obstacle(ScreenPoint location) {

        this.polyID = "Obstacle";
        this.imageID = "ObstacleImage";
        this.setLocation(location);
        initObstacle();

    }

    public Obstacle(String polyID) {

        this.polyID = polyID;
        this.imageID = polyID + "Image";
        this.setLocation(new ScreenPoint(200, 150));
        initObstacle();

    }

    public Obstacle(String polyID, ScreenPoint location) {

        this.polyID = polyID;
        this.imageID = polyID + "Image";
        this.setLocation(location);
        initObstacle();

    }

    public Obstacle(String polyID, ScreenPoint location , boolean aestheticObstacle) {

        this.polyID = polyID;
        this.imageID = polyID + "Image";
        this.aestheticObstacle = aestheticObstacle;
        this.setLocation(location);
        initObstacle();

    }

    public Obstacle(String polyID , boolean aestheticObstacle) {

        this.polyID = polyID;
        this.imageID = polyID + "Image";
        this.aestheticObstacle = aestheticObstacle;
        this.setLocation(new ScreenPoint(200, 150));
        initObstacle();

    }
    
    public void initObstacle() {

        int numVertices;
        ScreenPoint[] points;
        int polyPointX, polyPointY;

        if (aestheticObstacle) {

            numVertices = 4;
            points = new ScreenPoint[numVertices];

            points[0] = new ScreenPoint(this.location.x, this.location.y);
            points[1] = new ScreenPoint(this.location.x + imageWidth, this.location.y);
            points[2] = new ScreenPoint(this.location.x + imageWidth, this.location.y + imageHeight);
            points[3] = new ScreenPoint(this.location.x, this.location.y + imageHeight);

            visPolygon = new Polyline(polyID, points);
            visPolygon.setIsFilled(false);
            visPolygon.setFillColor(Color.BLACK);
            visPolygon.setWeight(3);
            visPolygon.setzOrder(3);
            
        } else {
            
            numVertices = (int) (Math.random() * 5) + 3;
            points = new ScreenPoint[numVertices];
            for (int i = 0; i < numVertices; i++) {
                polyPointX = (int) (Math.random() * polyDeltaRadius) + polyMinRadius;
                polyPointY = (int) (Math.random() * polyDeltaRadius) + polyMinRadius;
                points[i] = new ScreenPoint(this.location.x + polyPointX, this.location.y + polyPointY);
            }
    
            visPolygon = new Polyline(polyID, points);
            visPolygon.setIsFilled(true);
            visPolygon.setFillColor(Color.BLACK);
            visPolygon.setWeight(3);
    
            visPolygon.setzOrder(3);

        }



    }

    public void addToCanvas() {

        Game.UI().canvas().addShape(this.visPolygon);

    }

    public void addToCanvas(int zOrder) {

        this.visPolygon.setzOrder(zOrder);
        Game.UI().canvas().addShape(this.visPolygon);

    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;

        if (this.visPolygon != null){
            this.visPolygon.moveToLocation(location.x, location.y);
        }
    }
  
    public Polyline getVisPolygon() {
        return visPolygon;
    }

    public void setVisPolygon(Polyline visPolygon) {
        this.visPolygon = visPolygon;
    }

    public String getPolyID() {
        return polyID;
    }

    public int getPolyMinRadius() {
        return polyMinRadius;
    }

    public void setPolyMinRadius(int polyMinRadius) {
        this.polyMinRadius = polyMinRadius;
    }

    public int getPolyDeltaRadius() {
        return polyDeltaRadius;
    }

    public void setPolyDeltaRadius(int polyDeltaRadius) {
        this.polyDeltaRadius = polyDeltaRadius;
    }

    @Override
    public ScreenPoint[] getIntersectionVertices() {
		return this.visPolygon.getPoints();
    }
}
