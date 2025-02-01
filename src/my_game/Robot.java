package my_game;

import base.Game;
import base.GameCanvas;
import base.Intersectable;
import my_base.MyContent;
import shapes.Image;
import ui_elements.ScreenPoint;

public class Robot implements Intersectable {

    public enum Direction {
        RIGHT(1, 0),
        LEFT(-1, 0),
        UP(0, -1),
        DOWN(0, 1);

        private final int xVec, yVec;

        private Direction(int xVec, int yVec) {
            this.xVec = xVec;
            this.yVec = yVec;
        }

        public int xVec() {
            return xVec;
        }

        public int yVec() {
            return yVec;
        }

    }

    private int velocity = 10;
    private ScreenPoint location;
    private int rotation = 0; // In degrees

    private final String image = "resources/wall-e_icon.png";
    private final int imageWidth = 45;
    private final int imageHeight = 45;
    private final String imageID = "myRobot";

    public Robot() {

        this.setLocation(new ScreenPoint(100, 100));

    }

    public void addToCanvas() {

        GameCanvas canvas = Game.UI().canvas();

        Image image = new Image(getImageID(), getImage(), getImageWidth(), getImageHeight(), this.location.x,
                this.location.y);
        // image.setShapeListener(this);
        image.setzOrder(3);
        canvas.addShape(image);
    }

    public void addToCanvas(int zOrder) {

        GameCanvas canvas = Game.UI().canvas();

        Image image = new Image(getImageID(), getImage(), getImageWidth(), getImageHeight(), this.location.x,
                this.location.y);
        // image.setShapeListener(this);
        image.setzOrder(zOrder);
        canvas.addShape(image);
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;
    }

    public void moveLocation(int dx, int dy) {
        this.location.x += dx;
        this.location.y += dy;
    }

    public void move(Direction direction) {

        ScreenPoint currLocation = new ScreenPoint(location.x, location.y);
        
        ScreenPoint desired = new ScreenPoint(location.x + direction.xVec() * this.velocity,
                location.y + direction.yVec() * this.velocity);

        location.x = desired.x;
        location.y = desired.y;

        if (((MyContent) Game.Content()).board().isLegalRobotLocation()) {
            // After changing the robot self location, move also its image in the canvas
            // accordingly.
            Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);
        } else {
            location = currLocation;
        }

        // // After changing the robot self location, move also its image in the canvas
        // // accordingly.
        // Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);

        // Obstacle myObstacle = ((MyContent) Game.Content()).myObstacle();
        // if (IntersectionAlgorithm.areIntersecting(this, myObstacle)) {

        //     location = currLocation;

        // }

    }

    public void drill() {

        Gear[] gearsNearRobot = ((MyContent) Game.Content()).board().gearsNearRobot();

        for (Gear gear : gearsNearRobot) {
            gear.unCover();
        }

    }



    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    // Image Getters

    public String getImage() {
        return image;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getImageID() {
        return imageID;
    }

    @Override
    public ScreenPoint[] getIntersectionVertices() {
        int intersectionWidth = getImageWidth();
        int intersectionHeight = getImageHeight();

        int leftX = this.location.x;
        int topY = this.location.y;

        // ScreenPoint[] vertices = {
        // new ScreenPoint(centerX - intersectionWidth / 2, centerY - intersectionHeight
        // / 2),
        // new ScreenPoint(centerX + intersectionWidth / 2, centerY - intersectionHeight
        // / 2),
        // new ScreenPoint(centerX + intersectionWidth / 2, centerY + intersectionHeight
        // / 2),
        // new ScreenPoint(centerX - intersectionWidth / 2, centerY + intersectionHeight
        // / 2)
        // };
        ScreenPoint[] vertices = {
                new ScreenPoint(leftX, topY),
                new ScreenPoint(leftX + intersectionWidth, topY),
                new ScreenPoint(leftX + intersectionWidth, topY + intersectionHeight),
                new ScreenPoint(leftX, topY + intersectionHeight)
        };
        return vertices;
    }

}
