package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import shapes.Text;
import ui_elements.ScreenPoint;

public class Timer {

    private long startGameTime;
    private long gameDuration; // in mili-seconds
    private long endGameTime;
    private long timeRemaining;
    private long pausedAt; // Time when timer was paused
    private boolean isRunning; // Is timer currently running?
    
    private final String guid = "timer";

    private ScreenPoint location = new ScreenPoint(50, 50);

    public Timer() {
        gameDuration = 1000;
        timeRemaining = gameDuration;
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameDuration;
        isRunning = false;
    }

    public Timer(long gameTime) {
        this.gameDuration = gameTime;
        timeRemaining = gameTime;
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameTime;
        isRunning = false;
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

    // Reset and start timer
    public void start() {
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameDuration;
        timeRemaining = gameDuration;
        isRunning = true;
        pausedAt = 0;
        
        // Update the display
        updateText();
        
        System.out.println("Timer started: " + isRunning + ", duration: " + gameDuration + "ms");
    }

    // New method to pause the timer
    public void pause() {
        if (isRunning) {
            pausedAt = PeriodicLoop.elapsedTime();
            isRunning = false;
            System.out.println("Timer paused at: " + pausedAt);
            updateText();
        }
    }

    // New method to resume the timer
    public void resume() {
        if (!isRunning && pausedAt > 0) {
            // Calculate how long the timer was paused
            long pauseDuration = PeriodicLoop.elapsedTime() - pausedAt;
            // Adjust the end time by adding the pause duration
            endGameTime += pauseDuration;
            isRunning = true;
            pausedAt = 0;
            System.out.println("Timer resumed, end time adjusted by: " + pauseDuration + "ms");
            updateText();
        }
    }

    // New method to completely reset the timer
    public void reset() {
        isRunning = false;
        pausedAt = 0;
        timeRemaining = gameDuration;
        startGameTime = PeriodicLoop.elapsedTime();
        endGameTime = startGameTime + gameDuration;
        System.out.println("Timer reset, timeRemaining: " + timeRemaining + "ms");
        updateText();
    }

    public void refresh() {
        if (!isRunning) {
            // If not running, don't update the time but still update display
            updateText();
            return;
        }

        GameCanvas canvas = Game.UI().canvas();
        Text t1 = (Text) canvas.getShape(this.getGuid());
        
        if (t1 == null) {
            return;
        }

        long currentTime = PeriodicLoop.elapsedTime();
        if (currentTime < endGameTime) {
            timeRemaining = endGameTime - currentTime;
        } else {
            timeRemaining = 0;
            t1.setColor(Color.RED);
            // Auto-stop the timer when it reaches 0
            isRunning = false;
        }

        t1.setText(this.toString());
    }

    public void updateText() {
        GameCanvas canvas = Game.UI().canvas();
        Text t1 = (Text) canvas.getShape(this.getGuid());
        if (t1 != null) {
            t1.setText(this.toString());
            canvas.repaint();
        }
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
    
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String toString() {
        // Format time as mm:ss
        long seconds = timeRemaining / 1000;
        return "[TimeRemaining = " + (seconds / 60) + ":" + String.format("%02d", seconds % 60) + "]";
    }
}