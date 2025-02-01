package my_game;

import base.Game;
import base.GameCanvas;
import base.Intersectable;
import shapes.Image;
import ui_elements.ScreenPoint;

public class Gear implements Intersectable {

    private ScreenPoint location;
    private boolean isUncoverd = false;

    private final String image_uncoverd = "resources/black_gear_small.png";
    private final String image_covered = "resources/black_gear_small_covered.png";

    private final String imageID;
    private final int imageWidth = 50;
    private final int imageHeight = 50;


    public Gear() {

        this.imageID = "Gear";
        this.setLocation(new ScreenPoint(800, 500));

    }

    public Gear(String imageID) {

        this.imageID = imageID;
        this.setLocation(new ScreenPoint(800, 500));

    }

    public Gear(ScreenPoint location) {

        this.imageID = "Gear";
        this.setLocation(location);

    }

    public Gear(String imageID, ScreenPoint location) {

        this.imageID = imageID;
        this.setLocation(location);

    }


    public void unCover() {
        isUncoverd = true;
        Game.UI().canvas().changeImage(imageID, getImage(), getImageWidth(), getImageHeight());
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
            Game.UI().canvas().moveShapeToLocation(this.imageID, location.x, location.y);
        }
        
    }

    public void moveLocation(int dx, int dy) {
        this.location.x += dx;
        this.location.y += dy;

        if (Game.UI() != null) {
            Game.UI().canvas().moveShapeToLocation(this.imageID, location.x, location.y);
        }
    }

    public String getImage() {
        if (isUncoverd) {
            return image_uncoverd;

        } else {
            return image_covered;
        }
    }

    public String getImageID() {
        return imageID;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
    
    public boolean isUncoverd() {
        return isUncoverd;
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
