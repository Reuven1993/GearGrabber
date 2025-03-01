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
    private Gear heldGear;

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
        if (Game.UI() != null){
            Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);
        }
    }

    public void moveLocation(int dx, int dy) {
        this.location.x += dx;
        this.location.y += dy;

        if (Game.UI() != null) {
            Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);
        }
    }

    public void move(Direction direction) {

        int dx = direction.xVec() * this.velocity;
        int dy = direction.yVec() * this.velocity;

        this.moveLocation(dx, dy);
        if (heldGear != null) {
            heldGear.moveLocation(dx, dy);
        }

        if (!(((MyContent) Game.Content()).board().isLegalRobotLocation())) {
            this.moveLocation(-dx, -dy);
            if (heldGear != null) {
                heldGear.moveLocation(-dx, -dy);
            }
        }

    }



    public void drill() {
        Gear[] gearsNearRobot = ((MyContent) Game.Content()).board().gearsNearRobot();
        
        if (gearsNearRobot.length > 0) {
            Game.soundManager().playSound("drill");
        } else {
            Game.soundManager().playSound("error");
        }
        
        for (Gear gear : gearsNearRobot) {
            gear.unCover();
        }
    }
    
    
    public void pickupGears() {
        if (heldGear != null) {
            Game.soundManager().playSound("error");
            return;
        }
        
        Gear[] gearsNearRobot = ((MyContent) Game.Content()).board().gearsNearRobot();
        
        if (gearsNearRobot.length == 0) {
            Game.soundManager().playSound("error");
            return;
        }
        
        boolean foundUncoveredGear = false;
        
        for (Gear gear : gearsNearRobot) {
            if (gear.isUncoverd()) {
                heldGear = gear;
                Game.soundManager().playSound("pickup");
                foundUncoveredGear = true;
                break;
            }
        }
        
        if (!foundUncoveredGear) {
            Game.soundManager().playSound("error");
        }
    }

    public void dropGear() {

        heldGear = null;

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

        ScreenPoint[] vertices = {
                new ScreenPoint(leftX, topY),
                new ScreenPoint(leftX + intersectionWidth, topY),
                new ScreenPoint(leftX + intersectionWidth, topY + intersectionHeight),
                new ScreenPoint(leftX, topY + intersectionHeight)
        };
        return vertices;
    }

}
