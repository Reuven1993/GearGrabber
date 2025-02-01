package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import shapes.Text;
import ui_elements.ScreenPoint;

public class Timer {

    private long startGameTime;
    private long gameDuration; // in mili-seconds`
    private long endGameTime;
    private long timeRemaining;
    
    private final String guid = "timer";

    private ScreenPoint location = new ScreenPoint(50, 50);

    public Timer() {

        // gameTime = 30000;
        gameDuration = 1000;
        timeRemaining = gameDuration;
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameDuration;

    }

    public Timer(long gameTime) {

        this.gameDuration = gameTime;
        timeRemaining = gameTime;
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameTime;

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

        Text timeText = new Text(this.guid, this.toString(), this.location.x, this.location.y);
		timeText.setColor(Color.BLACK);
        timeText.setzOrder(zOrder);
		canvas.addShape(timeText);

    }

    public void start() {
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameDuration;
    }

    public void refresh() {

        GameCanvas canvas = Game.UI().canvas();
        Text t1 = (Text) canvas.getShape(this.getGuid());

        if (PeriodicLoop.elapsedTime() < endGameTime) {
            timeRemaining = endGameTime - PeriodicLoop.elapsedTime();
            
        } else {
            
            timeRemaining = 0;
            t1.setColor(Color.RED);
        }

		t1.setText(this.toString());
		
    }

    public void updateText() {
        GameCanvas canvas = Game.UI().canvas();
        Text t1 = (Text) canvas.getShape(this.getGuid());
        t1.setText(this.toString());
        canvas.repaint();
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
        this.setTimeRemaining(gameDuration);
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
        this.updateText();
    }


    public String getGuid() {
        return guid;
    }

    @Override
    public String toString() {
        return "[TimeRemaining = " + (timeRemaining/1000)/60 + ":" + (timeRemaining/1000)%60 + "]";
    }


}
