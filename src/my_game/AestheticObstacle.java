package my_game;

import base.Game;
import base.GameCanvas;
import base.Intersectable;
import shapes.Image;
import ui_elements.ScreenPoint;

public class AestheticObstacle implements Intersectable {

    private ScreenPoint location;

    private final String imageID;
    private final String obstacleImage = "resources/mediumBlockWall.png";
    private final int imageWidth = 100;
    private final int imageHeight = 100;

    public AestheticObstacle() {

        this.imageID = "Obstacle";
        this.setLocation(new ScreenPoint(200, 150));

    }

    public AestheticObstacle(ScreenPoint location) {

        this.imageID = "Obstacle";
        this.setLocation(location);

    }

    public AestheticObstacle(String imageID) {

        this.imageID = imageID;
        this.setLocation(new ScreenPoint(200, 150));

    }

    public AestheticObstacle(String imageID, ScreenPoint location) {

        this.imageID = imageID;
        this.setLocation(location);

    }

    public void addToCanvas() {

        GameCanvas canvas = Game.UI().canvas();

        Image image = new Image(getImageID(), getObstacleImage(), getImageWidth(), getImageHeight(), this.location.x,
                this.location.y);
        image.setzOrder(2);
        canvas.addShape(image);

    }

    public void addToCanvas(int zOrder) {

        GameCanvas canvas = Game.UI().canvas();

        Image image = new Image(getImageID(), getObstacleImage(), getImageWidth(), getImageHeight(), this.location.x,
                this.location.y);
        image.setzOrder(zOrder);
        canvas.addShape(image);

    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;
    }

    public String getImageID() {
        return imageID;
    }

    public String getObstacleImage() {
        return obstacleImage;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    @Override
    public ScreenPoint[] getIntersectionVertices() {
        int intersectionWidth = getImageWidth();
        int intersectionHeight = getImageHeight();

        int leftX = this.location.x;
        int topY = this.location.y;

        ScreenPoint[] vertices = {
                new ScreenPoint(leftX, topY),
                new ScreenPoint(leftX + intersectionWidth, topY),
                new ScreenPoint(leftX + intersectionWidth, topY + intersectionHeight),
                new ScreenPoint(leftX, topY + intersectionHeight)
        };
        return vertices;
    }

}
