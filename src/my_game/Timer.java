package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import shapes.Text;
import ui_elements.ScreenPoint;

public class Timer {

    private long gameTime; // in mili-seconds
    private long timeRemaining = gameTime;
    
    private final String guid = "timer";

    private ScreenPoint location;

    public Timer() {

        gameTime = 30000;
        this.setLocation(new ScreenPoint(50, 50));

    }

    public Timer(int gameTime) {
        this.gameTime = gameTime;
    }

    public void addToCanvas() {

        GameCanvas canvas = Game.UI().canvas();

        Text timeText = new Text(this.guid, this.toString(), this.getLocation().x, this.getLocation().y);
		timeText.setColor(Color.BLACK);
		timeText.setFontSize(40);
        timeText.setzOrder(3);
		canvas.addShape(timeText);

    }

    public void addToCanvas(int zOrder) {

        GameCanvas canvas = Game.UI().canvas();

        Text timeText = new Text(this.guid, this.toString(), this.getLocation().x, this.getLocation().y);
		timeText.setColor(Color.BLACK);
        timeText.setzOrder(zOrder);
		canvas.addShape(timeText);

    }

    public void refresh() {

        GameCanvas canvas = Game.UI().canvas();
        Text t1 = (Text) canvas.getShape(this.getGuid());

        if (PeriodicLoop.elapsedTime() < gameTime) {
            timeRemaining = gameTime - PeriodicLoop.elapsedTime();
            
        } else {
            
            timeRemaining = 0;
            t1.setColor(Color.RED);
        }

		t1.setText(this.toString());
		
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }


    public String getGuid() {
        return guid;
    }

    @Override
    public String toString() {
        return "[TimeRemaining = " + (timeRemaining/1000)/60 + ":" + (timeRemaining/1000)%60 + "]";
    }


}
