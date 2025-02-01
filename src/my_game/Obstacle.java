package my_game;

import java.awt.Color;
import base.Game;
import base.Intersectable;
import shapes.Polyline;
import ui_elements.ScreenPoint;

public class Obstacle implements Intersectable {

    private ScreenPoint location;

    private Polyline visPolygon;
    // private ScreenPoint[] points;

    public Obstacle() {

        this.setLocation(new ScreenPoint(200, 150));
        initObstacle();

    }

    public Obstacle(ScreenPoint location) {

        this.setLocation(location);
        initObstacle();

    }

    public void initObstacle() {

        int polyMinRadius = 50;
        int polyDeltaRadius = 100;

        int polyPointX, polyPointY;

        int numVertices = (int) (Math.random() * 5) + 3;
        ScreenPoint[] points = new ScreenPoint[numVertices];
        for (int i = 0; i < numVertices; i++) {
            polyPointX = (int) (Math.random() * polyDeltaRadius) + polyMinRadius;
            polyPointY = (int) (Math.random() * polyDeltaRadius) + polyMinRadius;
            points[i] = new ScreenPoint(this.location.x + polyPointX, this.location.y + polyPointY);
        }

        visPolygon = new Polyline("Obstacle", points);
        visPolygon.setIsFilled(true);
        visPolygon.setFillColor(Color.BLACK);
        visPolygon.setWeight(3);

        visPolygon.setzOrder(3);

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
    }


    @Override
    public ScreenPoint[] getIntersectionVertices() {
		return this.visPolygon.getPoints();
    }
}
