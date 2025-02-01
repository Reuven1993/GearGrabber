package my_game;

import base.Game;
import base.GameCanvas;
import shapes.Image;
import ui_elements.ScreenPoint;

public class Home {
    
    
    private ScreenPoint location;

    private final String image = "resources/small_blue_home.png";
    private final String imageID = "Home";
    private final int imageWidth = 50;
    private final int imageHeight = 50;

    
    public Home() {

        this.setLocation(new ScreenPoint(100, 100));

    }

    public Home(ScreenPoint location) {
    
        this.setLocation(location);

    }

    public void addToCanvas() {

        GameCanvas canvas = Game.UI().canvas();

        Image image = new Image(getImageID(), getImage(), getImageWidth(), getImageHeight(), this.location.x,
                this.location.y);
        // image.setShapeListener(this);
        image.setzOrder(1);
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

    public String getImage() {
        return image;
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

}
